package com.github.erik5594.importacao.controladores;

import static com.github.erik5594.util.Utils.isNotNullOrEmpty;
import static com.github.erik5594.util.Utils.mascaraPrimefacesCnpjOuCpf;

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

import com.github.erik5594.entidades.Cep;
import com.github.erik5594.entidades.Cidade;
import com.github.erik5594.enuns.TipoPessoa;
import com.github.erik5594.enuns.TipoTelefoneEnum;
import com.github.erik5594.importacao.enuns.StatusImportacao;
import com.github.erik5594.importacao.interfaces.BancoImportacao;
import com.github.erik5594.importacao.intertrack.entidades.ClienteImportacaoIntertrack;
import com.github.erik5594.importacao.intertrack.entidades.EmailClienteImportacaoIntertrack;
import com.github.erik5594.importacao.intertrack.entidades.EnderecoClienteImportacaoIntertrack;
import com.github.erik5594.importacao.intertrack.entidades.TelefoneClienteImportacaoIntertrack;
import com.github.erik5594.importacao.intertrack.interfaces.implementacoes.BancoImportacaoClienteIntertrack;
import com.github.erik5594.servicos.ServicosCep;
import com.github.erik5594.servicos.ServicosCidade;
import com.github.erik5594.servicos.ServicosCliente;
import com.github.erik5594.servicos.ServicosEmail;
import com.github.erik5594.servicos.ServicosEndereco;
import com.github.erik5594.servicos.ServicosTelefone;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

@Named
@ViewScoped
public @Data class PainelImportacaoCliente implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(PainelImportacaoCliente.class);
	
	@Inject
	private ServicosCliente servicosCliente;
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
	private List<ClienteImportacaoIntertrack> clientesComErro = new ArrayList<>();
	@Inject
	private ClienteImportacaoIntertrack clienteComErroSelecionado;
	private static final String TITULO = "Painel Importação: ";
	private static final String MSG_SUCESSO = "Atualização realizada com sucesso!";
	private final String ERRO_INTERNO = "Erro interno: erro interno contate a administração do sistema!";
	private int tipoTelefone;
	@Inject
	private EmailClienteImportacaoIntertrack email;
	@Inject
	private EmailClienteImportacaoIntertrack emailSelecionado;
	
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
	private void init(){
		carregarClientesErrados();
	}
	private void carregarClientesErrados() {
		clientesComErro = new ArrayList<>();
		clientesComErro = servicosCliente.todosClientesImportacaoByStatus(StatusImportacao.ERRO_VALIDACAO);
	}
	public long getQtdeClientePendenteImportacao() {
		return servicosCliente.qtdeClientePendentesIntertrack();
	}
	public Long getQtdeClienteErroValidacaoImportacao() {
		return servicosCliente.qtdeClienteErroValidacaoIntertrack();
	}
	public Long getQtdeClienteCorrigidoImportacao() {
		return servicosCliente.qtdeClienteCorrigidoImportacao();
	}
	public Long getQtdeClienteCadastrados() {
		return servicosCliente.qtdeClientesCadastrados();
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

	public void adcionaEndereco(){
		endereco.setCep(cep);
		endereco.setClienteImportacaoIntertrack(clienteComErroSelecionado);
		if(servicosEndereco.enderecoIsValido(endereco, TITULO, true)){
			adcionaEnderecoList(this.endereco);
		}
		inicializarEndereco(true);
	}
	
	public void adcionaEnderecoList(EnderecoClienteImportacaoIntertrack ender){
		if(Utils.isNullOrEmpty(clienteComErroSelecionado.getEnderecoClienteImportacaoIntertrack())){
			clienteComErroSelecionado.setEnderecoClienteImportacaoIntertrack(new ArrayList<EnderecoClienteImportacaoIntertrack>());
		}else{
			if(clienteComErroSelecionado.getEnderecoClienteImportacaoIntertrack().contains(ender)){
				FacesUtils.sendMensagemError(TITULO, "Validação Endereço: Endereço já adicionado!");
				return;
			}
		}
		ender.ajustarEndereco();
		clienteComErroSelecionado.getEnderecoClienteImportacaoIntertrack().add(ender);
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
		if(Utils.isNullOrEmpty(clienteComErroSelecionado.getTelefoneClienteImportacaoIntertrack())){
			clienteComErroSelecionado.setTelefoneClienteImportacaoIntertrack(new ArrayList<TelefoneClienteImportacaoIntertrack>());
		}else{
			if(clienteComErroSelecionado.getTelefoneClienteImportacaoIntertrack().contains(telefone)){
				FacesUtils.sendMensagemError(TITULO, "Validação Telefone: Numero já adicionado!");
				return;
			}
		}
		clienteComErroSelecionado.getTelefoneClienteImportacaoIntertrack().add(telefone);
	}
	
	public void adcionaEmail(){
		email.setClienteImportacaoIntertrack(clienteComErroSelecionado);
		if(servicosEmail.emailIsValido(email, TITULO, true)){
			adcionaEmailList(this.email);
		}
		this.email = new EmailClienteImportacaoIntertrack(); 
	}
	
	public void adcionaEmailList(EmailClienteImportacaoIntertrack email){
		if(Utils.isNullOrEmpty(clienteComErroSelecionado.getEmailClienteImportacaoIntertrack())){
			clienteComErroSelecionado.setEmailClienteImportacaoIntertrack(new ArrayList<EmailClienteImportacaoIntertrack>());
		}else{
			if(clienteComErroSelecionado.getEmailClienteImportacaoIntertrack().contains(email)){
				FacesUtils.sendMensagemError(TITULO, "Validação Email: E-mail já adicionado!");
				return;
			}
		}
		clienteComErroSelecionado.getEmailClienteImportacaoIntertrack().add(email);
	}
	
	public String carregaMascaraCnpjOuCpfPrimefaces(){
		int codTipoPessoa = clienteComErroSelecionado != null
				&& clienteComErroSelecionado.getTipo() != null ? clienteComErroSelecionado.getTipo().getCodPessoa():0;
		return mascaraPrimefacesCnpjOuCpf(codTipoPessoa);
	}
	
	public void salvar(){
		if(Utils.isNull(clienteComErroSelecionado)){
			return;
		}
		completarDadosPessoa();
		if(servicosCliente.validarCliente(clienteComErroSelecionado, TITULO, true) || !clienteComErroSelecionado.isAtivo()){
			clienteComErroSelecionado = servicosCliente.persistirClienteImportacao(clienteComErroSelecionado);
			if(Utils.isNotNull(clienteComErroSelecionado) && isNotNullOrEmpty(clienteComErroSelecionado.getId())){
				inicializarVariaveis();
				carregarClientesErrados();
				FacesUtils.sendMensagemOk(TITULO, "Cliente ajustado com sucesso!");
			}else{
				FacesUtils.sendMensagemError(TITULO, ERRO_INTERNO);
			}
		}
	}
	
	private void completarDadosPessoa() {
		clienteComErroSelecionado.setCgcCpf(clienteComErroSelecionado.getCgcCpf().replaceAll("\\D", ""));
		if(clienteComErroSelecionado.isAtivo()){
			clienteComErroSelecionado.setStatus(StatusImportacao.ATUALIZADO_CADASTRO);
		}else{
			clienteComErroSelecionado.setStatus(StatusImportacao.INATIVO);
		}
	}
	
	private void inicializarVariaveis(){
		clienteComErroSelecionado = new ClienteImportacaoIntertrack();
		cep = new Cep();
		cidade = new Cidade();
		enderecoEntrega = false;
		bloqueiaEnderecoGeral = true;
		bloqPesquisaCep = true;
		cepEncontrado = true;
		enderecoEntrega = false;
	}
	
	public void removerEnderecoDaLista(){
		if(Utils.isNotNull(enderecoSelecionado) && Utils.isNotNullOrEmpty(enderecoSelecionado.getCep())){
			clienteComErroSelecionado.removerEndereco(enderecoSelecionado);
		}
	}
	
	public void removerTelefoneDaLista(){
		if(Utils.isNotNull(telefoneSelecionado) && Utils.isNotNullOrEmpty(telefoneSelecionado.getNumero())){
			clienteComErroSelecionado.removerTelefone(telefoneSelecionado);
		}
	}
	
	public void removerEmailDaLista(){
		if(Utils.isNotNull(emailSelecionado) && Utils.isNotNullOrEmpty(emailSelecionado.getEmailDestinatario())){
			clienteComErroSelecionado.removerEmail(emailSelecionado);
		}
	}
	
	public TipoPessoa[] getTiposPessoa() {
		return TipoPessoa.values();
	}
}
