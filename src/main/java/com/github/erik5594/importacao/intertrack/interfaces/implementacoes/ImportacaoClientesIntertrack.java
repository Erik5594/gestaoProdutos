package com.github.erik5594.importacao.intertrack.interfaces.implementacoes;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.UploadedFile;

import com.github.erik5594.entidades.Cep;
import com.github.erik5594.entidades.Cidade;
import com.github.erik5594.entidades.Cliente;
import com.github.erik5594.enuns.TipoPessoa;
import com.github.erik5594.enuns.TipoTelefoneEnum;
import com.github.erik5594.importacao.enuns.StatusImportacao;
import com.github.erik5594.importacao.interfaces.implementacoes.ArquivoImportacaoAbstrato;
import com.github.erik5594.importacao.intertrack.entidades.ClienteImportacaoIntertrack;
import com.github.erik5594.importacao.intertrack.entidades.EmailClienteImportacaoIntertrack;
import com.github.erik5594.importacao.intertrack.entidades.EnderecoClienteImportacaoIntertrack;
import com.github.erik5594.importacao.intertrack.entidades.TelefoneClienteImportacaoIntertrack;
import com.github.erik5594.servicos.ServicosCep;
import com.github.erik5594.servicos.ServicosCidade;
import com.github.erik5594.servicos.ServicosCliente;
import com.github.erik5594.servicos.ServicosEmail;
import com.github.erik5594.servicos.ServicosEndereco;
import com.github.erik5594.servicos.ServicosTelefone;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.cdi.CDIServiceLocator;

public class ImportacaoClientesIntertrack extends ArquivoImportacaoAbstrato{
	
	private static final int TAMANHO_DDD_MAIS_NUMERO_NOVO = 11;
	private static final int TAMANHO_DDD_MAIS_NUMERO_ANTIGO = 10;
	private static final int TAMANHO_NUMERO_NOVO = 9;
	private static final int TAMANHO_NUMERO_ANTIGO = 8;
	private static final int TAMANHO_DDD = 2;
	
	private ServicosCliente servicosCliente;
	private ServicosCep servicosCep;
	private ServicosCidade servicosCidade;
	private ServicosTelefone servicosTelefone;
	private ServicosEmail servicosEmail;
	private ServicosEndereco servicosEndereco;

	public ImportacaoClientesIntertrack(UploadedFile arquivo) {
		super(arquivo);
		servicosCidade = CDIServiceLocator.getBean(ServicosCidade.class);
		servicosCep = CDIServiceLocator.getBean(ServicosCep.class);
		servicosCliente = CDIServiceLocator.getBean(ServicosCliente.class);
		servicosTelefone = CDIServiceLocator.getBean(ServicosTelefone.class);
		servicosEmail = CDIServiceLocator.getBean(ServicosEmail.class);
		servicosEndereco = CDIServiceLocator.getBean(ServicosEndereco.class);
	}

	@Override
	public List<ClienteImportacaoIntertrack> getListaDeObjetoDoArquivo(String separador) throws IOException {
		return manipularLinhasArquivo(this.obterBufferReader(), separador);
	}

	@Override
	public List<ClienteImportacaoIntertrack> manipularLinhasArquivo(BufferedReader linhasArquivo, String separador) throws IOException {
		List<ClienteImportacaoIntertrack> clientesImportacao = new ArrayList<>();
		String linha = linhasArquivo.readLine();
		int contador = 1;
		while(linha != null){
			if(contador != 1){
				ClienteImportacaoIntertrack clienteImportacao = (ClienteImportacaoIntertrack) obterObjeto(linha.split(separador));
				if(clienteImportacao != null){
					clientesImportacao.add(clienteImportacao);
				}
			}
			linha = linhasArquivo.readLine();
			contador ++;
		}
		return clientesImportacao;
	}

	@Override
	public Object obterObjeto(String[] vetorObjeto) {
		String cpfCgc = vetorObjeto[2].replaceAll("\\D", "");
		String nome = vetorObjeto[0].toUpperCase();
		Cliente cliente = servicosCliente.buscarClienteByCpfCgc(cpfCgc);
		if(Utils.isNullOrEmpty(cliente)){
			ClienteImportacaoIntertrack clienteImportacao = servicosCliente.buscarClienteImportacaoByCpfCgc(cpfCgc, nome); 
			if(Utils.isNullOrEmpty(clienteImportacao)){
				clienteImportacao = new ClienteImportacaoIntertrack();
				clienteImportacao.setNome(nome);
				clienteImportacao.setCgcCpf(cpfCgc);
				long cep = new Long("".equals(vetorObjeto[4].replaceAll("\\D", ""))?"0":vetorObjeto[4].replaceAll("\\D", ""));
				clienteImportacao.setEnderecoClienteImportacaoIntertrack(getListaEnderecoCliente(cep, clienteImportacao, vetorObjeto[3]));
				clienteImportacao.setTelefoneClienteImportacaoIntertrack(getListaTelefoneCliente(vetorObjeto[8], vetorObjeto[9], clienteImportacao));
				clienteImportacao.setEmailClienteImportacaoIntertrack(getListaEmailCliente(vetorObjeto[10], clienteImportacao));
				clienteImportacao.setStatus(StatusImportacao.PEDENTE);
				clienteImportacao.setTipo(cpfCgc.length() == 11?TipoPessoa.FISICA:TipoPessoa.JURIDICA);
				return clienteImportacao;
			}
			return null;
		}
		return null;
	}
	
	private List<EmailClienteImportacaoIntertrack> getListaEmailCliente(String email,ClienteImportacaoIntertrack cliente) {
		List<EmailClienteImportacaoIntertrack> emailsCliente = new ArrayList<>();
		if(Utils.isNotNullOrEmpty(email)){
			EmailClienteImportacaoIntertrack emailCliente = new EmailClienteImportacaoIntertrack();
			emailCliente.setClienteImportacaoIntertrack(cliente);
			emailCliente.setEmailDestinatario(email);
			emailCliente.setNomePessoaDestinatario(cliente.getNome().split(" ")[0]);
			if(servicosEmail.emailIsValido(emailCliente, null, false)){
				emailsCliente.add(emailCliente);
			}
		}
		
		return Utils.isNullOrEmpty(emailsCliente)?null:emailsCliente;
	}

	private List<TelefoneClienteImportacaoIntertrack> getListaTelefoneCliente(String telefone, String celular, ClienteImportacaoIntertrack cliente) {
		List<TelefoneClienteImportacaoIntertrack> telefonesCliente = new ArrayList<>();
		String telefoneFormatado = telefone.replaceAll("\\D", "");
		String celularFormatado = celular.replaceAll("\\D", "");
		TelefoneClienteImportacaoIntertrack tel1 = getTelefoneCliente(telefoneFormatado, TipoTelefoneEnum.RESIDENCIAL);
		TelefoneClienteImportacaoIntertrack tel2 = getTelefoneCliente(celularFormatado, TipoTelefoneEnum.CELULAR);
		
		if(Utils.isNotNullOrEmpty(tel1)){
			tel1.setClienteImportacaoIntertrack(cliente);
			if(servicosTelefone.telefoneIsValido(tel1, null, false)){
				telefonesCliente.add(tel1);
			}
		}
		if(Utils.isNotNullOrEmpty(tel2)){
			tel2.setClienteImportacaoIntertrack(cliente);
			if(servicosTelefone.telefoneIsValido(tel2, null, false)){
				telefonesCliente.add(tel2);
			}
		}
		return Utils.isNullOrEmpty(telefonesCliente)?null:telefonesCliente;
	}

	private TelefoneClienteImportacaoIntertrack getTelefoneCliente(String numeroFormatado, TipoTelefoneEnum tipoTelefone)
			throws NumberFormatException {
		TelefoneClienteImportacaoIntertrack telefoneCliente = new TelefoneClienteImportacaoIntertrack();
		telefoneCliente.setTipoTelefone(tipoTelefone);
		if(numeroFormatado.length() == TAMANHO_DDD_MAIS_NUMERO_NOVO){
			int comeco = 0;
			telefoneCliente.setDdd(Integer.parseInt(numeroFormatado.substring(comeco, comeco +=TAMANHO_DDD)));
			telefoneCliente.setNumero(numeroFormatado.substring(comeco, comeco +=TAMANHO_NUMERO_NOVO));
		}else if(numeroFormatado.length() == TAMANHO_DDD_MAIS_NUMERO_ANTIGO){
			int comeco = 0;
			telefoneCliente.setDdd(Integer.parseInt(numeroFormatado.substring(comeco, comeco +=TAMANHO_DDD)));
			telefoneCliente.setNumero(numeroFormatado.substring(comeco, comeco +=TAMANHO_NUMERO_ANTIGO));
		}else if(numeroFormatado.length() == TAMANHO_NUMERO_NOVO){
			telefoneCliente.setDdd(62);
			telefoneCliente.setNumero(numeroFormatado);
		}else if(numeroFormatado.length() == TAMANHO_NUMERO_ANTIGO){
			telefoneCliente.setDdd(62);
			telefoneCliente.setNumero(numeroFormatado);
		}else{
			telefoneCliente = null;
		}
		return telefoneCliente;
	}

	private List<EnderecoClienteImportacaoIntertrack> getListaEnderecoCliente(long numrCep, ClienteImportacaoIntertrack cliente, String complemento){
		List<EnderecoClienteImportacaoIntertrack> enderecos = new ArrayList<>();
		EnderecoClienteImportacaoIntertrack enderecoCliente = getEnderecoClienteBancoDados(numrCep, complemento);
		if(Utils.isNotNullOrEmpty(enderecoCliente)){
			enderecoCliente.setClienteImportacaoIntertrack(cliente);
			if(servicosEndereco.enderecoIsValido(enderecoCliente, null, false)){
				enderecos.add(enderecoCliente);
			}
		}
		return Utils.isNullOrEmpty(enderecos)?null:enderecos;
	}
	
	private EnderecoClienteImportacaoIntertrack getEnderecoClienteBancoDados(long numrCep, String complemento) {
		EnderecoClienteImportacaoIntertrack enderecoCliente = new EnderecoClienteImportacaoIntertrack();
		Cep cep;
		Cidade cidade;
		cep = servicosCep.pesquisarCepByCep(numrCep);
		if(Utils.isNullOrEmpty(cep)){
			cep = new Cep(numrCep);
			cidade = servicosCidade.pesquisarMunicipioByCepGeral(numrCep);
			if(Utils.isNullOrEmpty(cidade)){
				cidade = servicosCidade.pesquisarMunicipioByFaixaCep(numrCep);
				if(Utils.isNullOrEmpty(cidade)){
					cep = null;
				}else{
					cep.setCidade(cidade);
				}
			}else{
				cep.setCidade(cidade);
			}
		}
		if(Utils.isNotNullOrEmpty(cep)){
			enderecoCliente.setCep(cep);
			enderecoCliente.setBairro(cep.getBairro());
			enderecoCliente.setComplemento(complemento);
			enderecoCliente.setNumero("SN");
			enderecoCliente.setEnderecoEntrega(false);
			enderecoCliente.setRua(cep.getRua());
		}else{
			enderecoCliente = null;
		}
		return enderecoCliente;
	}
	
}
