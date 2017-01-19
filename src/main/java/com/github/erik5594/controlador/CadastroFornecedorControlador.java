package com.github.erik5594.controlador;

import static com.github.erik5594.util.Utils.isNotNullOrEmpty;
import static com.github.erik5594.util.Utils.mascaraPrimefacesCnpjOuCpf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import com.github.erik5594.entidades.Cep;
import com.github.erik5594.entidades.Cidade;
import com.github.erik5594.entidades.EmailFornecedor;
import com.github.erik5594.entidades.EnderecoFornecedor;
import com.github.erik5594.entidades.Fornecedor;
import com.github.erik5594.entidades.TelefoneFornecedor;
import com.github.erik5594.enuns.TipoPessoa;
import com.github.erik5594.enuns.TipoTelefoneEnum;
import com.github.erik5594.servicos.ServicosCep;
import com.github.erik5594.servicos.ServicosCidade;
import com.github.erik5594.servicos.ServicosEmail;
import com.github.erik5594.servicos.ServicosEndereco;
import com.github.erik5594.servicos.ServicosFornecedor;
import com.github.erik5594.servicos.ServicosTelefone;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

@Named
@ViewScoped
public @Data class CadastroFornecedorControlador implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Fornecedor empresa;
	@Inject
	private EnderecoFornecedor endereco;
	private EnderecoFornecedor enderecoSelecionado;
	@Inject
	private TelefoneFornecedor telefone;
	private TelefoneFornecedor telefoneSelecionado;
	@Inject
	private Cidade cidade;
	@Inject
	private EmailFornecedor email;
	private EmailFornecedor emailSelecionado;
	@Inject
	private ServicosEmail servicosEmail;
	@Inject
	private ServicosTelefone servicosTelefone;
	@Inject
	private ServicosEndereco servicosEndereco;
	@Inject
	private ServicosFornecedor servicosFornecedor;
	@Inject
	private ServicosCidade servicosCidade;
	@Inject
	private ServicosCep servicosCep;
	@Inject
	private Cep cep;
	
	private List<TelefoneFornecedor> telefones = new ArrayList<>();
	private List<EmailFornecedor> emails = new ArrayList<>();
	private List<EnderecoFornecedor> enderecos = new ArrayList<>();
	private int tpPessoa;
	
	private final String TITULO = tituloTela();
	private final String ERRO_INTERNO = "Erro interno: erro interno contate a administração do sistema!";
	
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
	
	private void inicializarVariaveis(){
		empresa = new Fornecedor();
		cep = new Cep();
		cidade = new Cidade();
		enderecos = new ArrayList<>();
		telefones = new ArrayList<>();
		emails = new ArrayList<>();
		enderecoEntrega = false;
		bloqueiaEnderecoGeral = true;
		bloqPesquisaCep = true;
		cepEncontrado = true;
		enderecoEntrega = false;
	}
	
	public String carregaMascaraCnpjOuCpfPrimefaces(){
		return mascaraPrimefacesCnpjOuCpf(tpPessoa);
	}
	
	public void adcionaTelefone(){
		telefone.setTipoTelefone(TipoTelefoneEnum.COMERCIAL);
		telefone.setFornecedor(empresa);
		if(servicosTelefone.telefoneIsValido(telefone, TITULO, true)){
			adcionaTelefoneList(this.telefone);
		}
		this.telefone= new TelefoneFornecedor(); 
	}
	
	public void adcionaTelefoneList(TelefoneFornecedor telefone){
		if(telefones.isEmpty()){
			telefones = new ArrayList<>();
		}else{
			if(telefones.contains(telefone)){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validação Telefone: Numero já adicionado!", null));
				return;
			}
		}
		telefones.add(telefone);
	}

	public void adcionaEmail(){
		email.setFornecedor(empresa);
		if(servicosEmail.emailIsValido(email, TITULO, true)){
			adcionaEmailList(this.email);
		}
		this.email = new EmailFornecedor(); 
	}
	
	public void adcionaEmailList(EmailFornecedor email){
		if(emails.isEmpty()){
			emails = new ArrayList<>();
		}else{
			if(emails.contains(email)){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validação Email: E-mail já adicionado!", null));
				return;
			}
		}
		emails.add(email);
	}
	
	public void adcionaEndereco(){
		endereco.setCep(cep);
		endereco.setFornecedor(empresa);
		if(servicosEndereco.enderecoIsValido(endereco, TITULO, true)){
			adcionaEnderecoList(this.endereco);
		}
		inicializarEndereco(true);
	}

	public void adcionaEnderecoList(EnderecoFornecedor ender){
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
	
	public void habilitaPesquisaCep(){
		inicializarEndereco(false);
	}
	
	public boolean isBloqueioNivel1(){
		return bloqueiaEnderecoGeral || cepEncontrado;
	}

	public void salvar(){
		completarDadosEmpresa();
		if(servicosFornecedor.validarFornecedor(empresa, TITULO, true)){
			empresa = servicosFornecedor.persistirFornecedor(this.empresa);
			if(isNotNullOrEmpty(empresa) && empresa.getId() > 0L){
				inicializarVariaveis();
				FacesUtils.sendMensagemOk(TITULO, String.format("Fornecedor %s com sucesso!", editar() ? "editado":"cadastrado"));
			}else{
				FacesUtils.sendMensagemOk(TITULO, ERRO_INTERNO);
			}
		}
	}
	private List<EnderecoFornecedor> ajustaDadosEndereco() {
		List<EnderecoFornecedor> enderecosAjustados = new ArrayList<>();
		for(EnderecoFornecedor ender : enderecos){			
			if(Utils.isNotNull(ender)){
				if(ender.isCepGeral()){
					Cep cep2 = servicosCep.pesquisarCepByCep(ender.getCep().getCep());
					ender.setBairro(ender.getCep().getBairro());
					ender.setRua(ender.getCep().getRua());
					ender.setCep(cep2);
				}else if(ender.isCepByFaixa()){
					Cep cep2 = new Cep(ender.getCep().getCep());
					cep2.setCidade(servicosCidade.pesquisarMunicipioByFaixaCep(cep2.getCep()));
					ender.setBairro(ender.getCep().getBairro());
					ender.setRua(ender.getCep().getRua());
					ender.setCep(cep2);
				}
				enderecosAjustados.add(ender);
			}
		}
		return enderecosAjustados;
	}
	
	public void removerEnderecoDaLista(){
		if(Utils.isNotNull(enderecoSelecionado) && Utils.isNotNullOrEmpty(enderecoSelecionado.getCep()) && Utils.isNotNullOrEmpty(enderecos)){
			List<EnderecoFornecedor> novaListaEndereco = new ArrayList<>();
			for(EnderecoFornecedor enderecoValidacao : enderecos){
				if(!enderecoValidacao.getCep().getCep().equals(enderecoSelecionado.getCep().getCep())){
					novaListaEndereco.add(enderecoValidacao);
				}
			}
			enderecos = new ArrayList<>();
			enderecos = novaListaEndereco;
		}
	}

	private void completarDadosEmpresa() {
		enderecos = ajustaDadosEndereco();
		empresa.setEndereco(enderecos);
		if(Utils.isNullOrEmpty(empresa.getId())){
			empresa.setDataCredenciamento(new Date());
		}
		empresa.setUltimaAtualizacao(new Date());
		if(this.tpPessoa == 0){
			this.empresa.setFisicaJuridica(TipoPessoa.FISICA);
		}else{
			this.empresa.setFisicaJuridica(TipoPessoa.JURIDICA);
		}
		if (!this.telefones.isEmpty()) {
			this.empresa.setTelefones(this.telefones);
		}
		if (!this.emails.isEmpty()) {
			this.empresa.setEmails(this.emails);
		}
		empresa.setCgcCpf(empresa.getCgcCpf().replaceAll("\\D", ""));
	}

	private boolean validarEndereco() {
		return servicosEndereco.enderecoIsValido(endereco, TITULO, true);
	}
	
	public boolean validacoes(){
		if(validarDadosPrincipais() & validarEndereco()){
			return true;
		}
		return false;
	}
	
	private boolean validarDadosPrincipais() {
		return servicosFornecedor.validarFornecedor(empresa, TITULO, true);
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
	
	private void inicializarEndereco(boolean var) {
		cep = new Cep();
		cidade = new Cidade();
		this.endereco = new EnderecoFornecedor();
		bloqPesquisaCep = var;
		cepEncontrado = true;
		bloqueiaEnderecoGeral = true;
		enderecoEntrega = false;
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
	
	public boolean editar(){
		if(Utils.isNotNull(empresa)){
			return Utils.isNotNullOrEmpty(empresa.getId());
		}
		return false;
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
	
	private String tituloTela() {
		if(editar()){
			return "Edição de Fornecedor: ";
		}
		return "Cadastro Fornecedor: ";
	}
	
	public void setEmpresa(Fornecedor fornecedor){
		this.empresa = fornecedor;
		if(Utils.isNotNullOrEmpty(this.empresa)){
			this.enderecos = this.empresa.getEndereco();
			this.telefones = this.empresa.getTelefones();
			this.emails = this.empresa.getEmails();
		}
	}
}
