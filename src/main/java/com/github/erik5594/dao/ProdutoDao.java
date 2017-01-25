package com.github.erik5594.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.github.erik5594.entidades.Produto;

public class ProdutoDao {

	@Inject
	private EntityManager manager;

	public Produto salvarOrUpdate(Produto produto) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		produto = (Produto) manager.merge(produto);
		entityTransaction.commit();
		return produto;
	}

	public Produto pesquisarById(Long id) {
		return (Produto) manager.find(Produto.class, id);
	}

	public List<Produto> listarTodosProdutos() {
		return manager.createQuery("from Produto", Produto.class)
				.getResultList();
	}

	public Produto pesquisarByCodProd(String codProduto) {
		return manager
				.createQuery("from Produto where codProduto = :codProduto and ativo = :ativo",
						Produto.class).setParameter("codProduto", codProduto)
				.setParameter("ativo", true)
				.getSingleResult();
	}

	public void salvarOrUpdateLista(List<Produto> produtos) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		for (Produto produto : produtos) {
			produto = manager.merge(produto);
		}
		entityTransaction.commit();
	}
	
	public List<Produto> buscarProdutoByNome(String nome) {
		return this.manager.createQuery("from Produto where upper(nomeProduto) like :nome and ativo = :ativo", Produto.class)
				.setParameter("ativo", true)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}
}
