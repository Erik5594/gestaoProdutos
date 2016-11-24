package com.kilowats.servicos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.kilowats.annotations.ValidarProduto;
import com.kilowats.dao.ProdutoDao;
import com.kilowats.entidades.OrdemServico;
import com.kilowats.entidades.Produto;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosProduto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProdutoDao produtoDao;
	@Inject @ValidarProduto
	private IValidacaoCadastro validador;
	@Inject
	private ServicosEan servicosCodBarras;
	@Inject
	private ServicosEstoque servicosEstoque;

	public Produto persistirProduto(Produto produto) {
		return produtoDao.salvarOrUpdate(produto);
	}
	
	public boolean produtoIsValido(Produto produto, String titulo, boolean mostrarMensagem) {
		return validador.validarCadastroComMensagem(produto, titulo, mostrarMensagem);
	}

	public List<Produto> listarTodosProdutos() {
		return produtoDao.listarTodosProdutos();
	}
	
	public Produto pesquisaProdutoByCodProduto(String codProduto){
		return produtoDao.pesquisarByCodProd(codProduto);
	}
	
	public void persistirListaProduto(List<Produto> produtos){
		produtoDao.salvarOrUpdateLista(produtos);
	}
	
	public List<Produto> pesquisarProdutoByNome(String nomeProduto) {
		return produtoDao.buscarProdutoByNome(nomeProduto);
	}
	
	public Produto buscarProdutoByCodBarras(String codBarras){
		return servicosCodBarras.pesquisarEanByCodBarras(codBarras).getProduto();
	}
	
	public void registrarPendenciaSaidaTodosItens(OrdemServico orcamento){
		servicosEstoque.marcarPendenciaSaidaTodosItens(orcamento);
	}
	
	public void registrarSaidaTodosItens(OrdemServico orcamento){
		servicosEstoque.lancarSaidaTodosItens(orcamento);
	}
}
