package com.kilowats.controlador;

import static com.kilowats.util.Utils.isNotNullOrEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import com.kilowats.entidades.Ean;
import com.kilowats.entidades.Produto;
import com.kilowats.enuns.TipoProdutoUnidadeEnum;
import com.kilowats.servicos.ServicosEan;
import com.kilowats.servicos.ServicosProduto;
import com.kilowats.util.Utils;
import com.kilowats.util.jsf.FacesUtils;

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
	
	private List<Ean> eans = new ArrayList<>();
	
	public void adicionarEan(){
		if(servicosEan.eanIsValido(ean, TITULO, true)){
			ean.setProduto(produto);
			adicionaEanList(ean);
		}
		ean = new Ean();
	}

	private void adicionaEanList(Ean ean) {
		if(Utils.isNullOrEmpty(eans)){
			eans = new ArrayList<>();
		}else{
			if(eans.contains(ean)){
				FacesUtils.sendMensagemError(TITULO, "Validação Ean: Ean já adicionado!");
				return;
			}
		}
		eans.add(ean);
	}
	
	public void salvar() {
		if (servicosProduto.produtoIsValido(this.produto, TITULO, true)) {
			completarDadosProduto();
			produto = servicosProduto.persistirProduto(this.produto);
			if (isNotNullOrEmpty(produto) && produto.getId() > 0L) {
				FacesUtils.sendMensagemOk(TITULO, String.format("Produto %s com sucesso!",editar()?"editado":"cadastrado"));
			} else {
				FacesUtils.sendMensagemError(TITULO, ERRO_INTERNO+" ["+produto.getNomeProduto()+"]");
			}
		}
	}
	
	public void removerEanDaLista(){
		if(Utils.isNotNull(eanSelecionado) && Utils.isNotNullOrEmpty(eanSelecionado.getCodBarras()) && Utils.isNotNullOrEmpty(eans)){
			List<Ean> novaListaEan = new ArrayList<Ean>();
			for(Ean eanValidacao : eans){
				if(!eanValidacao.getCodBarras().equals(eanSelecionado.getCodBarras())){
					novaListaEan.add(eanValidacao);
				}
			}
			eans = new ArrayList<>();
			eans = novaListaEan;
		}
	}
	
	public boolean editar(){
		if(Utils.isNotNull(produto)){
			return isNotNullOrEmpty(produto.getId());
		}
		return false;
	}
	
	private void completarDadosProduto() {
		if(isNotNullOrEmpty(eans)){
			this.produto.setEans(this.eans);
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
		if(Utils.isNotNullOrEmpty(this.produto)){
			eans = produto.getEans();
		}
	}
	
}
