package com.github.erik5594.controlador;

import static com.github.erik5594.util.Utils.isNotNullOrEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import com.github.erik5594.entidades.Ean;
import com.github.erik5594.entidades.EstoqueProduto;
import com.github.erik5594.entidades.Produto;
import com.github.erik5594.enuns.TipoProdutoUnidadeEnum;
import com.github.erik5594.servicos.ServicosEan;
import com.github.erik5594.servicos.ServicosProduto;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

@Named
@ViewScoped
public @Data class CadastroProdutoControlador implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Produto produto;
	@Inject
	private Ean ean;
	private Ean eanSelecionado;
	@Inject
	private ServicosEan servicosEan;
	@Inject
	private ServicosProduto servicosProduto;
	
	private String TITULO = tituloTela();
	private final String ERRO_INTERNO = "Erro interno: erro interno contate a administração do sistema!";
	
	public void adicionarEan(){
		if(servicosEan.eanIsValido(ean, TITULO, true)){
			ean.setProduto(produto);
			adicionaEanList(ean);
		}
		ean = new Ean();
	}

	private void adicionaEanList(Ean ean) {
		if(Utils.isNullOrEmpty(produto.getEans())){
			produto.setEans(new ArrayList<Ean>());
		}else{
			if(produto.getEans().contains(ean)){
				FacesUtils.sendMensagemError(TITULO, "Validação Ean: Ean já adicionado!");
				return;
			}
		}
		produto.getEans().add(ean);
	}
	
	public void salvar() {
		completarDadosProduto();
		if (servicosProduto.produtoIsValido(this.produto, TITULO, true)) {
			produto = servicosProduto.persistirProduto(this.produto);
			if (isNotNullOrEmpty(produto) && produto.getId() > 0L) {
				FacesUtils.sendMensagemOk(TITULO, String.format("Produto %s com sucesso!",editar()?"editado":"cadastrado"));
			} else {
				FacesUtils.sendMensagemError(TITULO, ERRO_INTERNO+" ["+produto.getNomeProduto()+"]");
			}
		}
	}
	
	public void removerEanDaLista(){
		if(Utils.isNotNull(eanSelecionado) && Utils.isNotNullOrEmpty(eanSelecionado.getCodBarras())){
			produto.removerEan(eanSelecionado);
		}
	}
	
	public boolean editar(){
		if(Utils.isNotNull(produto)){
			return isNotNullOrEmpty(produto.getId());
		}
		return false;
	}
	
	private void completarDadosProduto() {
		produto.setTipoUnidadeTributavel(produto.getTipoUnidade());
		produto.setDataUltimaAtualizacao(new Date());
		if(isNotNullOrEmpty(produto.getEstoqueProduto())){
			this.produto.getEstoqueProduto().setProduto(produto);
		}
		if(isNotNullOrEmpty(produto.getValoresProdutos())){
			this.produto.getValoresProdutos().setProduto(produto);
		}
	}

	public TipoProdutoUnidadeEnum[] getTipoUnidadeProduto() {
		return TipoProdutoUnidadeEnum.values();
	}
	
	private String tituloTela() {
		if(editar()){
			return "Edição de Produto: ";
		}
		return "Cadastro Produto: ";
	}
	
	public void setProduto(Produto produto){
		this.produto = produto;
		if(Utils.isNull(this.produto.getEstoqueProduto())){
			produto.setEstoqueProduto(new EstoqueProduto());
		}
	}
	
}
