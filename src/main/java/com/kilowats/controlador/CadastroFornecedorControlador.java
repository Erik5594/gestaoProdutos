package com.kilowats.controlador;

import static com.kilowats.util.Utils.isNotNullOrEmpty;
import static com.kilowats.util.Utils.mascaraPrimefacesCnpjOuCpf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import com.kilowats.entidades.Cep;
import com.kilowats.entidades.Cidade;
import com.kilowats.entidades.EmailFornecedor;
import com.kilowats.entidades.EnderecoFornecedor;
import com.kilowats.entidades.Fornecedor;
import com.kilowats.entidades.TelefoneFornecedor;
import com.kilowats.enuns.TipoPessoa;
import com.kilowats.enuns.TipoTelefoneEnum;
import com.kilowats.servicos.ServicosCep;
import com.kilowats.servicos.ServicosCidade;
import com.kilowats.servicos.ServicosEmail;
import com.kilowats.servicos.ServicosEndereco;
import com.kilowats.servicos.ServicosFornecedor;
import com.kilowats.servicos.ServicosTelefone;
import com.kilowats.util.Utils;
import com.kilowats.util.jsf.FacesUtils;

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
				FacesUtils.sendMensagemOk(TITULO, String.format("Fornecedor %s com suceso!", editar() ? "editado":"cadastrado"));
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
}
