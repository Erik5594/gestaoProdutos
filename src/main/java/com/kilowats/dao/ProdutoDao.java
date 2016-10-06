package com.kilowats.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.kilowats.entidades.Produto;
import com.kilowats.util.Utils;


public class ProdutoDao {
	
	private final static String SQL_DELETE = "delete %s where produto = :produto"; 
	
	@Inject
	private EntityManager manager;
	
	public Produto salvarOrUpdate(Produto produto) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		if(Utils.isNotNullOrEmpty(produto.getId())){
			deletarEansProduto(produto);
		}
		produto = (Produto) manager.merge(produto);
		entityTransaction.commit();
		return produto;
	}
	
	public Produto pesquisarById(Long id) {
		return (Produto) manager.find(Produto.class, id);
	}
	
	public List<Produto> listarTodosProdutos() {
		return manager.createQuery("from Produto", Produto.class).getResultList();
	}
	
	private void deletarEansProduto(Produto produto){
		manager.createQuery(String.format(SQL_DELETE, "Ean")).setParameter("produto", produto).executeUpdate();
	}
	
	public Produto pesquisarByCodProd(String codProduto){
		return manager.createQuery("from Produto where codProduto = :codProduto", Produto.class)
		.setParameter("codProduto", codProduto).getSingleResult();
	}
	
	public void salvarOrUpdateLista(List<Produto> produtos) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		for(Produto produto : produtos){
			produto = manager.merge(produto);
		}
		entityTransaction.commit();
	}
}
