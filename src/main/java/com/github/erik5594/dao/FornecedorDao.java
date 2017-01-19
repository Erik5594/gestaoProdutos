package com.github.erik5594.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.github.erik5594.entidades.Fornecedor;
import com.github.erik5594.util.Utils;

public class FornecedorDao {

	private final static String SQL_DELETE = "delete %s where fornecedor = :fornecedor";

	@Inject
	private EntityManager manager;

	public Fornecedor salvarOrUpdate(Fornecedor empresa) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		if (Utils.isNotNullOrEmpty(empresa.getId())) {
			deletarEnderecosFornecedor(empresa);
			deletarTelefonesFornecedor(empresa);
			deletarEmailsFornecedor(empresa);
		}
		empresa = (Fornecedor) manager.merge(empresa);
		entityTransaction.commit();
		return empresa;
	}

	public Fornecedor pesquisarById(Long id) {
		return (Fornecedor) manager.find(Fornecedor.class, id);
	}

	public List<Fornecedor> listarTodosFornecedores() {
		return manager.createQuery("from Fornecedor", Fornecedor.class)
				.getResultList();
	}

	private void deletarEnderecosFornecedor(Fornecedor fornecedor) {
		manager.createQuery(String.format(SQL_DELETE, "EnderecoFornecedor"))
				.setParameter("fornecedor", fornecedor).executeUpdate();
	}

	private void deletarTelefonesFornecedor(Fornecedor fornecedor) {
		manager.createQuery(String.format(SQL_DELETE, "TelefoneFornecedor"))
				.setParameter("fornecedor", fornecedor).executeUpdate();
	}

	private void deletarEmailsFornecedor(Fornecedor fornecedor) {
		manager.createQuery(String.format(SQL_DELETE, "EmailFornecedor"))
				.setParameter("fornecedor", fornecedor).executeUpdate();
	}
}
