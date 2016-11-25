package com.kilowats.servicos;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Inject;

import com.kilowats.dao.OrdemServicoDao;
import com.kilowats.dao.ProdutoDao;
import com.kilowats.entidades.EstoqueProduto;
import com.kilowats.entidades.ItemOrdemServico;
import com.kilowats.entidades.OrdemServico;
import com.kilowats.entidades.Produto;
import com.kilowats.enuns.TipoProdutoUnidadeEnum;
import com.kilowats.exceptions.NegocioException;

public class ServicosEstoque implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProdutoDao produtoDao;
	
	public void marcarPendenciaSaidaTodosItens(OrdemServico orcamento){
		
		for(ItemOrdemServico item : orcamento.getItens()){
			if(item.getProduto().getTipoUnidade() != TipoProdutoUnidadeEnum.SV){
				marcarPendenteSaida(item.getProduto(), item.getQuantidadeProduto());
			}
		}
	}
	
	public void lancarSaidaTodosItens(OrdemServico orcamento){
		for(ItemOrdemServico item : orcamento.getItens()){
			if(item.getProduto().getTipoUnidade() != TipoProdutoUnidadeEnum.SV){
				marcarSaida(item.getProduto(), item.getQuantidadeProduto());
			}
		}
	}
	
	private void marcarPendenteSaida(Produto produto, BigDecimal qtde){
		EstoqueProduto estoque = produto.getEstoqueProduto();
		BigDecimal novaQtdePendenteSaida = estoque.getQuantidadePendenteSaida().add(qtde);
		
		estoque.setQuantidadePendenteSaida(novaQtdePendenteSaida);
		
		produtoDao.salvarOrUpdate(produto);
	}
	
	private void marcarSaida(Produto produto, BigDecimal qtde){
		EstoqueProduto estoque = produto.getEstoqueProduto();
		BigDecimal novaQtdeEstoque = estoque.getQuantidadeEstoque().subtract(qtde);
		BigDecimal novaQtdePendenteSaida = estoque.getQuantidadePendenteSaida().subtract(qtde);
		
		if(novaQtdeEstoque.compareTo(BigDecimal.ZERO) < 0){
			throw new NegocioException("Não há disponibilidade no estoque de "+qtde.toString()+" itens do produto ["+produto.getCodProduto()+"]");
		}
		
		if(novaQtdePendenteSaida.compareTo(BigDecimal.ZERO) < 0){
			throw new NegocioException("Quantidade pendente de sáida do produto ["+produto.getCodProduto()+"] é menor que ["+qtde+"]");
		}
		
		estoque.setQuantidadeEstoque(novaQtdeEstoque);
		estoque.setQuantidadePendenteSaida(novaQtdePendenteSaida);
		
		produtoDao.salvarOrUpdate(produto);
	}
	
	private void marcarDevolucaoProduto(Produto produto, BigDecimal qtde){
		EstoqueProduto estoque = produto.getEstoqueProduto();
		BigDecimal novaQtdeEstoque = estoque.getQuantidadeEstoque().add(qtde);
		
		estoque.setQuantidadeEstoque(novaQtdeEstoque);
		produtoDao.salvarOrUpdate(produto);
	}
	
	private void cancelarPendenciaSaida(Produto produto, BigDecimal qtde){
		EstoqueProduto estoque = produto.getEstoqueProduto();
		BigDecimal novaQtdePendenteSaida = estoque.getQuantidadePendenteSaida().subtract(qtde);
		
		if(novaQtdePendenteSaida.compareTo(BigDecimal.ZERO) < 0){
			throw new NegocioException("Não foi registrado pendência de saída com a quantidade "+qtde.toString()+" itens do produto ["+produto.getCodProduto()+"]");
		}
		
		estoque.setQuantidadePendenteSaida(novaQtdePendenteSaida);
		produtoDao.salvarOrUpdate(produto);
	}
}
