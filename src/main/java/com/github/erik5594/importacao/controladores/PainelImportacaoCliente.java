package com.github.erik5594.importacao.controladores;

import static com.kilowats.util.Utils.mascaraPrimefacesCnpjOuCpf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.erik5594.importacao.enuns.StatusImportacao;
import com.github.erik5594.importacao.interfaces.BancoImportacao;
import com.github.erik5594.importacao.intertrack.entidades.ClienteImportacaoIntertrack;
import com.github.erik5594.importacao.intertrack.entidades.EmailClienteImportacaoIntertrack;
import com.github.erik5594.importacao.intertrack.entidades.EnderecoClienteImportacaoIntertrack;
import com.github.erik5594.importacao.intertrack.entidades.TelefoneClienteImportacaoIntertrack;
import com.github.erik5594.importacao.intertrack.interfaces.implementacoes.BancoImportacaoClienteIntertrack;
import com.kilowats.entidades.Cep;
import com.kilowats.entidades.Cidade;
import com.kilowats.enuns.TipoTelefoneEnum;
import com.kilowats.servicos.ServicosCep;
import com.kilowats.servicos.ServicosCidade;
import com.kilowats.servicos.ServicosCliente;
import com.kilowats.servicos.ServicosEmail;
import com.kilowats.servicos.ServicosEndereco;
import com.kilowats.servicos.ServicosTelefone;
import com.kilowats.util.Utils;
import com.kilowats.util.jsf.FacesUtils;

@Named
@ViewScoped
public @Data class PainelImportacaoCliente implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(PainelImportacaoCliente.class);
	
	@Inject
	private ServicosCliente servivcosCliente;
	@Inject
	private ServicosTelefone servicosTelefone;
	@Inject
	private ServicosEndereco servicosEndereco;
	@Inject
	private ServicosEmail servicosEmail;
	@Inject
	private ServicosCidade servicosCidade;
	@Inject
	private ServicosCep servicosCep;
	@Inject
	private Cep cep;
	@Inject
	private Cidade cidade;
	@Inject
	private EnderecoClienteImportacaoIntertrack endereco;
	@Inject
	private EnderecoClienteImportacaoIntertrack enderecoSelecionado;
	@Inject
	private TelefoneClienteImportacaoIntertrack telefone;
	@Inject
	private TelefoneClienteImportacaoIntertrack telefoneSelecionado;
	private List<TelefoneClienteImportacaoIntertrack> telefones = new ArrayList<>();
	private List<EnderecoClienteImportacaoIntertrack> enderecos = new ArrayList<>();
	private List<ClienteImportacaoIntertrack> clientesComErro = new ArrayList<>();
	@Inject
	private ClienteImportacaoIntertrack clienteComErroSelecionado;
	private static final String TITULO = "Painel Importação";
	private static final String MSG_SUCESSO = "Atualização realizada com sucesso!";
	private int tpPessoa;
	private int tipoTelefone;
	@Inject
	private EmailClienteImportacaoIntertrack email;
	@Inject
	private EmailClienteImportacaoIntertrack emailSelecionado;
	private List<EmailClienteImportacaoIntertrack> emails = new ArrayList<>();
	
	private boolean bloqueiaEnderecoGeral;
	private boolean bloqPesquisaCep;
	private boolean cepEncontrado;
	private boolean enderecoEntrega;
	
	{
		bloqueiaEnderecoGeral = true;
		bloqPesquisaCep = true;
		cepEncontrado = true;
		enderecoEntrega = false;
	}
	
	@PostConstruct
	private void carregarClientesErrados(){
		clientesComErro = new ArrayList<>();
		clientesComErro = servivcosCliente.todosClientesImportacaoByStatus(StatusImportacao.ERRO_VALIDACAO);
	}
	public long getQtdeClientePendenteImportacao() {
		return servivcosCliente.qtdeClientePendentesIntertrack();
	}
	public Long getQtdeClienteErroValidacaoImportacao() {
		return servivcosCliente.qtdeClienteErroValidacaoIntertrack();
	}
	public Long getQtdeClienteCorrigidoImportacao() {
		return servivcosCliente.qtdeClienteCorrigidoImportacao();
	}
	public Long getQtdeClienteCadastrados() {
		return servivcosCliente.qtdeClientesCadastrados();
	}
	
	public void importarClientesPendentes(){
		BancoImportacao importador = new BancoImportacaoClienteIntertrack();
		importador.importar(StatusImportacao.PEDENTE);
		FacesUtils.sendMensagemOk(TITULO+":", MSG_SUCESSO);
	}
	
	public void importarClientesCorrigidos(){
		BancoImportacao importador = new BancoImportacaoClienteIntertrack();
		importador.importar(StatusImportacao.ATUALIZADO_CADASTRO);
		FacesUtils.sendMensagemOk(TITULO+":", MSG_SUCESSO);
	}
	
	public void importarClientesPendenteAndCorrigidos(){
		BancoImportacao importador = new BancoImportacaoClienteIntertrack();
		importador.importar(StatusImportacao.PEDENTE);
		importador.importar(StatusImportacao.ATUALIZADO_CADASTRO);
		FacesUtils.sendMensagemOk(TITULO+":", MSG_SUCESSO);
	}
	
	public boolean isBloqueioNivel1(){
		return bloqueiaEnderecoGeral || cepEncontrado;
	}
	
	public void habilitaPesquisaCep(){
		inicializarEndereco(false);
	}
	
	private void inicializarEndereco(boolean var) {
		cep = new Cep();
		cidade = new Cidade();
		this.endereco = new EnderecoClienteImportacaoIntertrack();
		bloqPesquisaCep = var;
		cepEncontrado = true;
		bloqueiaEnderecoGeral = true;
		enderecoEntrega = false;
	}
	
	public void buscarEnderecoByCep(){
		if(Utils.isNotNullOrEmpty(cep.getCep())){
			pesquisarCepBancoDados();
		}else{
			FacesUtils.sendMensagemError(TITULO, "Cep não informado!");
		}
	}
	
	private void pesquisarCepBancoDados() {
		long numrCep = cep.getCep();
		cep = servicosCep.pesquisarCepByCep(numrCep);
		if(Utils.isNullOrEmpty(cep)){
			cep = new Cep(numrCep);
			cidade = servicosCidade.pesquisarMunicipioByCepGeral(numrCep);
			if(Utils.isNullOrEmpty(cidade)){
				cidade = servicosCidade.pesquisarMunicipioByFaixaCep(numrCep);
				if(Utils.isNullOrEmpty(cidade)){
					FacesUtils.sendMensagemError(TITULO, "Cep inválido!");
					inicializarEndereco(true);
				}else{
					cep.setCidade(cidade);
					acaoCepByFaixaEncontrado();
				}
			}else{
				cep.setCidade(cidade);
				acaoCepGeralEncontrado();
			}
		}else{
			acaoCepEncontrado();
		}
	}
	
	private void acaoCepByFaixaEncontrado() {
		FacesUtils.sendMensagemAviso(TITULO, "Cep por faixa encontrado!");
		endereco.setCepGeral(false);
		endereco.setCepByFaixa(true);
		habilitaEdicaoCepGeralOrFaixa();
	}

	private void acaoCepGeralEncontrado() {
		FacesUtils.sendMensagemAviso(TITULO, "Cep geral informado!");
		endereco.setCepGeral(true);
		endereco.setCepByFaixa(false);
		habilitaEdicaoCepGeralOrFaixa();
	}
	
	private void acaoCepEncontrado() {
		if(Utils.isNotNullOrEmpty(cep.getCidade()) && cep.getCidade().getCepInicial().equals(cep.getCidade().getCepFinal())){
			acaoCepGeralEncontrado();
		}else{
			habilitaEdicaoComplemento();
		}
	}

	private void habilitaEdicaoCepGeralOrFaixa() {
		bloqueiaEnderecoGeral = false;
		bloqPesquisaCep = true;
		cepEncontrado = false;
	}

	private void habilitaEdicaoComplemento() {
		cepEncontrado = true;
		bloqPesquisaCep = true;
		bloqueiaEnderecoGeral = false;
	}

	public void setClienteComErroSelecionado(ClienteImportacaoIntertrack clienteComErroSelecionado) {
		this.clienteComErroSelecionado = clienteComErroSelecionado;
		if(Utils.isNotNullOrEmpty(this.clienteComErroSelecionado)){
			this.enderecos = this.clienteComErroSelecionado.getEnderecoClienteImportacaoIntertrack();
			this.telefones = this.clienteComErroSelecionado.getTelefoneClienteImportacaoIntertrack();
			this.emails = this.clienteComErroSelecionado.getEmailClienteImportacaoIntertrack();
		}
	}
	
	public void adcionaEndereco(){
		endereco.setCep(cep);
		endereco.setClienteImportacaoIntertrack(clienteComErroSelecionado);
		if(servicosEndereco.enderecoIsValido(endereco, TITULO, true)){
			adcionaEnderecoList(this.endereco);
		}
		inicializarEndereco(true);
	}
	
	public void adcionaEnderecoList(EnderecoClienteImportacaoIntertrack ender){
		if(Utils.isNullOrEmpty(enderecos)){
			enderecos = new ArrayList<>();
		}else{
			if(enderecos.contains(ender)){
				FacesUtils.sendMensagemError(TITULO, "Validação Endereço: Endereço já adicionado!");
				return;
			}
		}
		enderecos.add(ender);
	}
	
	public void adcionaTelefone(){
		telefone.setTipoTelefone(returnTipoTelefone());
		telefone.setClienteImportacaoIntertrack(clienteComErroSelecionado);
		if(servicosTelefone.telefoneIsValido(telefone, TITULO, true)){
			adcionaTelefoneList(this.telefone);
		}
		this.telefone= new TelefoneClienteImportacaoIntertrack();
		tipoTelefone = 9;
	}
	
	private TipoTelefoneEnum returnTipoTelefone() {
		switch (tipoTelefone) {
		case 0:
			return TipoTelefoneEnum.CELULAR;
		case 1:
			return TipoTelefoneEnum.COMERCIAL;
		case 2:
			return TipoTelefoneEnum.RESIDENCIAL;
		case 3:
			return TipoTelefoneEnum.OUTRO;
		default:
			return TipoTelefoneEnum.CELULAR;
		}
	}
	
	public void adcionaTelefoneList(TelefoneClienteImportacaoIntertrack telefone){
		if(telefones.isEmpty()){
			telefones = new ArrayList<>();
		}else{
			if(telefones.contains(telefone)){
				FacesUtils.sendMensagemError(TITULO, "Validação Telefone: Numero já adicionado!");
				return;
			}
		}
		telefones.add(telefone);
	}
	
	public void adcionaEmail(){
		email.setClienteImportacaoIntertrack(clienteComErroSelecionado);
		if(servicosEmail.emailIsValido(email, TITULO, true)){
			adcionaEmailList(this.email);
		}
		this.email = new EmailClienteImportacaoIntertrack(); 
	}
	
	public void adcionaEmailList(EmailClienteImportacaoIntertrack email){
		if(emails.isEmpty()){
			emails = new ArrayList<>();
		}else{
			if(emails.contains(email)){
				FacesUtils.sendMensagemError(TITULO, "Validação Email: E-mail já adicionado!");
				return;
			}
		}
		emails.add(email);
	}
	public String carregaMascaraCnpjOuCpfPrimefaces(){
		return mascaraPrimefacesCnpjOuCpf(tpPessoa);
	}
}
