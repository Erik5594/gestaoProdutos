package com.github.erik5594.importacao.intertrack.interfaces.implementacoes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.erik5594.entidades.Cliente;
import com.github.erik5594.entidades.EmailCliente;
import com.github.erik5594.entidades.EnderecoCliente;
import com.github.erik5594.entidades.TelefoneCliente;
import com.github.erik5594.enuns.TipoPessoa;
import com.github.erik5594.importacao.enuns.StatusImportacao;
import com.github.erik5594.importacao.interfaces.BancoImportacao;
import com.github.erik5594.importacao.intertrack.entidades.ClienteImportacaoIntertrack;
import com.github.erik5594.importacao.intertrack.entidades.EmailClienteImportacaoIntertrack;
import com.github.erik5594.importacao.intertrack.entidades.EnderecoClienteImportacaoIntertrack;
import com.github.erik5594.importacao.intertrack.entidades.TelefoneClienteImportacaoIntertrack;
import com.github.erik5594.servicos.ServicosCliente;
import com.github.erik5594.servicos.ServicosEmail;
import com.github.erik5594.servicos.ServicosEndereco;
import com.github.erik5594.servicos.ServicosTelefone;
import com.github.erik5594.util.cdi.CDIServiceLocator;

public class BancoImportacaoClienteIntertrack implements BancoImportacao {
	
	private ServicosCliente servicosCliente;
	private ServicosTelefone servicosTelefone;
	private ServicosEmail servicosEmail;
	private ServicosEndereco servicosEndereco;
	private static Log log = LogFactory.getLog(BancoImportacaoClienteIntertrack.class);
	
	public BancoImportacaoClienteIntertrack(){
		servicosCliente = CDIServiceLocator.getBean(ServicosCliente.class);
		servicosEmail = CDIServiceLocator.getBean(ServicosEmail.class);
		servicosTelefone = CDIServiceLocator.getBean(ServicosTelefone.class);
		servicosEndereco = CDIServiceLocator.getBean(ServicosEndereco.class);
	}

	@Override
	public void importar(StatusImportacao status) {
		List<ClienteImportacaoIntertrack> clientesImportacao = getClientesImportacao(status);
		
		for(ClienteImportacaoIntertrack clienteIntertrack : clientesImportacao){
			try{
				if(this.validar(clienteIntertrack)){
					clienteIntertrack.setStatus(StatusImportacao.IMPORTADO);
					Cliente cliente = (Cliente) criarObjeto(clienteIntertrack);
					salvarBancoObjetoCriado(cliente);
					salvarBancoImportacao(clienteIntertrack);
				}else{
					clienteIntertrack.setStatus(StatusImportacao.ERRO_VALIDACAO);
					salvarBancoImportacao(clienteIntertrack);
				}
			}catch(Exception e){
				log.error("Erro na importacao de Clientes Intertrack: "+ e.getMessage(), e);
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<ClienteImportacaoIntertrack> getClientesImportacao(StatusImportacao status) {
		return servicosCliente.todosClientesImportacaoByStatus(status);
	}

	@Override
	public boolean validar(Object obj) {
		ClienteImportacaoIntertrack clienteImportacao = (ClienteImportacaoIntertrack) obj;
		return servicosCliente.validarCliente(clienteImportacao, null, false)
				&& validarEmails(clienteImportacao.getEmailClienteImportacaoIntertrack())
				&& validarTelefones(clienteImportacao.getTelefoneClienteImportacaoIntertrack())
				&& validarEnderecos(clienteImportacao.getEnderecoClienteImportacaoIntertrack());
	}
	
	private boolean validarEmails(List<EmailClienteImportacaoIntertrack> emailsImportacao){
		if(emailsImportacao != null && !emailsImportacao.isEmpty()){
			for(EmailClienteImportacaoIntertrack emailImportacao : emailsImportacao){
				return servicosEmail.emailIsValido(emailImportacao, null, false);
			}
		}
		return true;
	}
	
	private boolean validarTelefones(List<TelefoneClienteImportacaoIntertrack> telefonesImportacao){
		if(telefonesImportacao != null && !telefonesImportacao.isEmpty()){
			for(TelefoneClienteImportacaoIntertrack telefoneImportacao : telefonesImportacao){
				return servicosTelefone.telefoneIsValido(telefoneImportacao, null, false);
			}
		}
		return true;
	}
	
	private boolean validarEnderecos(List<EnderecoClienteImportacaoIntertrack> enderecosImportacao){
		if(enderecosImportacao != null && !enderecosImportacao.isEmpty()){
			for(EnderecoClienteImportacaoIntertrack enderecoImportacao : enderecosImportacao){
				return servicosEndereco.enderecoIsValido(enderecoImportacao, null, false);
			}
		}
		return true;
	}

	@Override
	public Object criarObjeto(Object obj) throws Exception{
		Cliente cliente = new Cliente();
		ClienteImportacaoIntertrack clienteImportacao = (ClienteImportacaoIntertrack) obj;
		List<TelefoneClienteImportacaoIntertrack> telefonesClienteImportacao = clienteImportacao.getTelefoneClienteImportacaoIntertrack();
		List<EnderecoClienteImportacaoIntertrack> enderecosClienteImportacao = clienteImportacao.getEnderecoClienteImportacaoIntertrack();
		List<EmailClienteImportacaoIntertrack> emailsClienteImportacao = clienteImportacao.getEmailClienteImportacaoIntertrack();
		
		cliente.setNome(clienteImportacao.getNome());
		cliente.setCgcCpf(clienteImportacao.getCgcCpf());
		cliente.setFisicaJuridica(clienteImportacao.getCgcCpf().length() == 11? TipoPessoa.FISICA:TipoPessoa.JURIDICA);
		cliente.setStatus(1);
		cliente.setDataCredenciamento(new Date());
		cliente.setUltimaAtualizacao(new Date());
		
		cliente.setVeiculos(null);
		cliente.setTelefones(getTelefonesImportacao(cliente, telefonesClienteImportacao));
		cliente.setEndereco(getEnderecosImportacao(cliente, enderecosClienteImportacao));
		cliente.setEmails(getEmailsImportacao(cliente, emailsClienteImportacao));
		
		return cliente;
	}

	private List<EnderecoCliente> getEnderecosImportacao(Cliente cliente,
			List<EnderecoClienteImportacaoIntertrack> enderecosClienteImportacao) throws Exception{
		List<EnderecoCliente> enderecosCliente = new ArrayList<>();
		for(EnderecoClienteImportacaoIntertrack enderecoImportacao : enderecosClienteImportacao){
			EnderecoCliente endereco = new EnderecoCliente();
			endereco.setCliente(cliente);
			endereco.setCep(enderecoImportacao.getCep());
			endereco.setBairro(enderecoImportacao.getBairro());
			endereco.setCepByFaixa(false);
			endereco.setComplemento(enderecoImportacao.getComplemento());
			endereco.setNumero(enderecoImportacao.getNumero());
			endereco.setRua(enderecoImportacao.getRua());
			endereco.setCepGeral(false);
			endereco.setEnderecoEntrega(false);
			
			enderecosCliente.add(endereco);
		}
		return enderecosCliente;
	}

	private List<TelefoneCliente> getTelefonesImportacao(Cliente cliente,
			List<TelefoneClienteImportacaoIntertrack> telefonesClienteImportacao) throws Exception{
		List<TelefoneCliente> telefonesCliente = new ArrayList<>();
		for(TelefoneClienteImportacaoIntertrack telefoneImportacao : telefonesClienteImportacao){
			TelefoneCliente telefoneCliente = new TelefoneCliente();
			telefoneCliente.setCliente(cliente);
			telefoneCliente.setDdd(telefoneImportacao.getDdd());
			telefoneCliente.setNumero(telefoneImportacao.getNumero());
			telefoneCliente.setTipoTelefone(telefoneImportacao.getTipoTelefone());
			telefonesCliente.add(telefoneCliente);
		}
		return telefonesCliente;
	}
	
	private List<EmailCliente> getEmailsImportacao(Cliente cliente,
			List<EmailClienteImportacaoIntertrack> emailsClienteImportacao) throws Exception {
		List<EmailCliente> emailsCliente = new ArrayList<>();
		for(EmailClienteImportacaoIntertrack emailImportacao : emailsClienteImportacao){
			EmailCliente emailCliente = new EmailCliente();
			emailCliente.setCliente(cliente);
			emailCliente.setEmailDestinatario(emailImportacao.getEmailDestinatario());
			emailCliente.setNomePessoaDestinatario(emailImportacao.getNomePessoaDestinatario());
			
			emailsCliente.add(emailCliente);
		}
		return emailsCliente;
	}

	@Override
	public void salvarBancoObjetoCriado(Object obj) throws Exception{
		Cliente cliente = (Cliente) obj;
		if(cliente != null){
			servicosCliente.persistirCliente(cliente);
		}else{
			throw new Exception("Objeto [Cliente] está nulo!");
		}
	}

	@Override
	public void salvarBancoImportacao(Object obj) throws Exception{
		ClienteImportacaoIntertrack clienteImportacao = (ClienteImportacaoIntertrack) obj;
		if(clienteImportacao != null){
			servicosCliente.persistirClienteImportacao(clienteImportacao);
		}else{
			throw new Exception("Objeto [ClienteImportacaoIntertrack] está nulo!");
		}
	}
}
