package com.github.erik5594.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import com.github.erik5594.entidades.Cliente;
import com.github.erik5594.importacao.enuns.StatusImportacao;
import com.github.erik5594.importacao.intertrack.entidades.ClienteImportacaoIntertrack;
import com.github.erik5594.util.Utils;

public class ClienteDao {

	private final static String SQL_DELETE = "delete %s where cliente = :cliente";
	private final static String SQL_DELETE_IMPORTACAO = "delete %s where clienteImportacaoIntertrack = :clienteImportacaoIntertrack";

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
	
	public ClienteImportacaoIntertrack pesquisarClienteImportadoById(Long id) {
		return (ClienteImportacaoIntertrack) manager.find(ClienteImportacaoIntertrack.class, id);
	}

	public List<Cliente> listarTodosClientes() {
		return manager.createQuery("from Cliente", Cliente.class)
				.getResultList();
	}
	
	public Cliente pesquisarByCpfCgc(String cpfCgc) {
		try{
			return (Cliente) manager.createQuery("from Cliente where cgcCpf = :cpfCgc", Cliente.class)
					.setParameter("cpfCgc", cpfCgc).getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}
	
	public ClienteImportacaoIntertrack pesquisarClienteImportacaoByCpfCgc(String cpfCgc, String nome) {
		try{
			return (ClienteImportacaoIntertrack) manager.createQuery("from ClienteImportacaoIntertrack where cgcCpf = :cpfCgc and nome = :nome", ClienteImportacaoIntertrack.class)
					.setParameter("cpfCgc", cpfCgc)
					.setParameter("nome", nome)
					.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
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
	
	public ClienteImportacaoIntertrack salvarOrUpdate(ClienteImportacaoIntertrack cliente) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		if (Utils.isNotNullOrEmpty(cliente.getId())) {
			if(cliente.getEnderecoClienteImportacaoIntertrack() != null
					&& !cliente.getEnderecoClienteImportacaoIntertrack().isEmpty()){
				deletarEnderecosClienteImportacao(cliente);
			}
			
			if(cliente.getTelefoneClienteImportacaoIntertrack() != null
					&& !cliente.getTelefoneClienteImportacaoIntertrack().isEmpty()){
				deletarTelefonesClienteImportacao(cliente);
			}
			
			if(cliente.getEmailClienteImportacaoIntertrack() != null
					&& !cliente.getEmailClienteImportacaoIntertrack().isEmpty()){
				deletarEmailsClienteImportacao(cliente);
			}
		}
		cliente = (ClienteImportacaoIntertrack) manager.merge(cliente);
		entityTransaction.commit();
		return cliente;
	}
	
	private void deletarEnderecosClienteImportacao(ClienteImportacaoIntertrack cliente) {
		manager.createQuery(String.format(SQL_DELETE_IMPORTACAO, "EnderecoClienteImportacaoIntertrack"))
				.setParameter("clienteImportacaoIntertrack", cliente).executeUpdate();
	}

	private void deletarTelefonesClienteImportacao(ClienteImportacaoIntertrack cliente) {
		manager.createQuery(String.format(SQL_DELETE_IMPORTACAO, "TelefoneClienteImportacaoIntertrack"))
				.setParameter("clienteImportacaoIntertrack", cliente).executeUpdate();
	}

	private void deletarEmailsClienteImportacao(ClienteImportacaoIntertrack cliente) {
		manager.createQuery(String.format(SQL_DELETE_IMPORTACAO, "EmailClienteImportacaoIntertrack"))
				.setParameter("clienteImportacaoIntertrack", cliente).executeUpdate();
	}
	
	public Long qtdeClienteCadastrados(){
		return manager.createQuery("select count(*) from Cliente", Long.class).getSingleResult();
	}
	
	public Long qtdeClientePendenteIntertrack(){
		String sql = "select count(*) from ClienteImportacaoIntertrack cii where cii.status = :status";
		return manager.createQuery(sql, Long.class)
				.setParameter("status", StatusImportacao.PEDENTE)
				.getSingleResult();
	}
	
	public Long qtdeClienteErroValidacaoIntertrack(){
		String sql = "select count(*) from ClienteImportacaoIntertrack cii where cii.status = :status";
		return manager.createQuery(sql, Long.class)
				.setParameter("status", StatusImportacao.ERRO_VALIDACAO)
				.getSingleResult();
	}

	public Long qtdeClienteCorrigidoIntertrack() {
		String sql = "select count(*) from ClienteImportacaoIntertrack cii where cii.status = :status";
		return manager.createQuery(sql, Long.class)
				.setParameter("status", StatusImportacao.ATUALIZADO_CADASTRO)
				.getSingleResult();
	}
	
	public List<ClienteImportacaoIntertrack> todosClienteImportacaoByStatus(StatusImportacao status) {
		String sql = "from ClienteImportacaoIntertrack where status = :status";
		return manager.createQuery(sql, ClienteImportacaoIntertrack.class)
				.setParameter("status", status)
				.getResultList();
	}
}
