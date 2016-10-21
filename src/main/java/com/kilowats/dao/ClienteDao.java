package com.kilowats.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.kilowats.entidades.Cliente;
import com.kilowats.util.Utils;

public class ClienteDao {

	private final static String SQL_DELETE = "delete %s where cliente = :cliente";

	@Inject
	private EntityManager manager;

	public Cliente salvarOrUpdate(Cliente cliente) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		if (Utils.isNotNullOrEmpty(cliente.getId())) {
			deletarEnderecosCliente(cliente);
			deletarTelefonesCliente(cliente);
			deletarEmailsCliente(cliente);
			deletarVeiculosCliente(cliente);
		}
		cliente = (Cliente) manager.merge(cliente);
		entityTransaction.commit();
		return cliente;
	}

	public Cliente pesquisarById(Long id) {
		return (Cliente) manager.find(Cliente.class, id);
	}

	public List<Cliente> listarTodosClientes() {
		return manager.createQuery("from Cliente", Cliente.class)
				.getResultList();
	}
	
	public Cliente pesquisarByCpfCgc(String cpfCgc) {
		return (Cliente) manager.createQuery("from Cliente where cgcCpf = :cpfCgc", Cliente.class)
				.setParameter("cpfCgc", cpfCgc).getSingleResult();
	}

	private void deletarEnderecosCliente(Cliente cliente) {
		manager.createQuery(String.format(SQL_DELETE, "EnderecoCliente"))
				.setParameter("cliente", cliente).executeUpdate();
	}

	private void deletarTelefonesCliente(Cliente cliente) {
		manager.createQuery(String.format(SQL_DELETE, "TelefoneCliente"))
				.setParameter("cliente", cliente).executeUpdate();
	}

	private void deletarEmailsCliente(Cliente cliente) {
		manager.createQuery(String.format(SQL_DELETE, "EmailCliente"))
				.setParameter("cliente", cliente).executeUpdate();
	}

	private void deletarVeiculosCliente(Cliente cliente) {
		manager.createQuery(String.format(SQL_DELETE, "Veiculo"))
				.setParameter("cliente", cliente).executeUpdate();
	}

}
