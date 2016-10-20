package com.kilowats.controlador;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

import com.kilowats.entidades.ItemOrdemServico;
import com.kilowats.entidades.OrdemServico;
import com.kilowats.entidades.Produto;
import com.kilowats.servicos.ServicosEan;
import com.kilowats.servicos.ServicosProduto;
import com.kilowats.util.jsf.FacesUtils;

@Named
@ViewScoped
public @Data class GerarOrdemServicoControlador implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Produto produtoLinhaEditavel;
	@Inject
	private OrdemServico ordemServico;
	private String codBarras;
	@Inject
	private ServicosProduto servicosProdutos;
	@Inject
	private ServicosEan servicosCodBarras;
	private static final String TITULO = "Cadastro Ordem Serviço: ";
	
	

	public void carregarProdutoPorCodBarras(){
		if(!StringUtils.isBlank(codBarras)){
			if(codBarras.length() < 13){
				produtoLinhaEditavel = servicosProdutos.pesquisaProdutoByCodProduto(codBarras);
			}else{
				produtoLinhaEditavel = servicosCodBarras.pesquisarEanByCodBarras(codBarras).getProduto();
			}
			carregarProdutoLinhaEditavel();
		}
	}
	
	public void carregarProdutoLinhaEditavel() {
		ItemOrdemServico item = this.ordemServico.getItens().get(0);
		
		if (this.produtoLinhaEditavel != null) {
			if (this.existeItemComProduto(this.produtoLinhaEditavel)) {
				FacesUtils.sendMensagemError(TITULO, "Já existe um item no pedido com o produto informado.");
			} else {
				item.setProduto(this.produtoLinhaEditavel);
				item.setValorUnitario(this.produtoLinhaEditavel.getValor());
				
				this.ordemServico.adicionarItemVazio();
				this.produtoLinhaEditavel = null;
				this.codBarras = null;
				
				this.ordemServico.recalcularValorTotal();
			}
		}
	}
	
	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;
		
		for (ItemOrdemServico item : this.getOrdemServico().getItens()) {
			if (produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}
		
		return existeItem;
	}
	
	public List<Produto> completarProduto(String nome) {
		return this.servicosProdutos.pesquisarProdutoByNome(nome);
	}
	
	public void atualizarQuantidade(ItemOrdemServico item, int linha) {
		if (item.getQuantidadeProduto() < 1) {
			if (linha == 0) {
				item.setQuantidadeProduto(1);
			} else {
				this.getOrdemServico().getItens().remove(linha);
			}
		}
		
		this.ordemServico.recalcularValorTotal();
	}
}
