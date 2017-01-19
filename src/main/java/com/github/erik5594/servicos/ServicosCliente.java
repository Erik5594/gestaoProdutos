package com.github.erik5594.servicos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.github.erik5594.annotations.ValidarCliente;
import com.github.erik5594.dao.ClienteDao;
import com.github.erik5594.entidades.Cliente;
import com.github.erik5594.importacao.enuns.StatusImportacao;
import com.github.erik5594.importacao.intertrack.entidades.ClienteImportacaoIntertrack;
import com.github.erik5594.interfaces.IValidacaoCadastro;

public class ServicosCliente implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClienteDao clienteDao;
	@Inject @ValidarCliente
	private IValidacaoCadastro validar;

	public Cliente persistirCliente(Cliente cliente){
		if(cliente != null && (cliente.getId() == null || cliente.getId() == 0l)){
			Cliente cliente2 = clienteDao.pesquisarByCpfCgc(cliente.getCgcCpf());
			if(cliente2 != null){
				return cliente2;
			}
		}
		return clienteDao.salvarOrUpdate(cliente);
	}
	
	public boolean validarCliente(Cliente cliente, String titulo, boolean mostrarMensagem){
		return validar.validarCadastroComMensagem(cliente, titulo, mostrarMensagem);
	}
	
	public boolean validarCliente(ClienteImportacaoIntertrack clienteImportacao, String titulo, boolean mostrarMensagem){
		return validar.validarCadastroComMensagem(clienteImportacao, titulo, mostrarMensagem);
	}

	public List<Cliente> listarTodosClientes() {
		return clienteDao.listarTodosClientes();
	}
	
	public Cliente buscarClienteByCpfCgc(String cpfCgc){
		return clienteDao.pesquisarByCpfCgc(cpfCgc);
	}
	
	public ClienteImportacaoIntertrack buscarClienteImportacaoByCpfCgc(String cpfCgc, String nome){
		return clienteDao.pesquisarClienteImportacaoByCpfCgc(cpfCgc, nome);
	}
	
	public ClienteImportacaoIntertrack persistirClienteImportacao(ClienteImportacaoIntertrack cliente){
		if(cliente != null && (cliente.getId() == null || cliente.getId() == 0l)){
			ClienteImportacaoIntertrack cliente2 = clienteDao.pesquisarClienteImportacaoByCpfCgc(cliente.getCgcCpf(), cliente.getNome());
			if(cliente2 != null){
				return cliente2;
			}
		}
		return clienteDao.salvarOrUpdate(cliente);
	}
	
	public Long qtdeClientePendentesIntertrack(){
		return clienteDao.qtdeClientePendenteIntertrack();
	}
	
	public Long qtdeClienteErroValidacaoIntertrack(){
		return clienteDao.qtdeClienteErroValidacaoIntertrack();
	}

	public Long qtdeClientesCadastrados(){
		return clienteDao.qtdeClienteCadastrados();
	}

	public Long qtdeClienteCorrigidoImportacao() {
		return clienteDao.qtdeClienteCorrigidoIntertrack();
	}
	
	public List<ClienteImportacaoIntertrack> todosClientesImportacaoByStatus(StatusImportacao status) {
		return clienteDao.todosClienteImportacaoByStatus(status);
	}
	
	public ClienteImportacaoIntertrack pesquisarClienteImportadoById(Long id){
		return clienteDao.pesquisarClienteImportadoById(id);
	}
}
