package com.kilowats.controlador;

import static com.kilowats.util.Utils.isNotNullOrEmpty;
import static com.kilowats.util.Utils.mascaraPrimefacesCnpjOuCpf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import com.kilowats.entidades.Cep;
import com.kilowats.entidades.Cidade;
import com.kilowats.entidades.Cliente;
import com.kilowats.entidades.EmailCliente;
import com.kilowats.entidades.EnderecoCliente;
import com.kilowats.entidades.TelefoneCliente;
import com.kilowats.entidades.Veiculo;
import com.kilowats.enuns.TipoPessoa;
import com.kilowats.enuns.TipoTelefoneEnum;
import com.kilowats.servicos.ServicosCep;
import com.kilowats.servicos.ServicosCidade;
import com.kilowats.servicos.ServicosCliente;
import com.kilowats.servicos.ServicosEmail;
import com.kilowats.servicos.ServicosEndereco;
import com.kilowats.servicos.ServicosTelefone;
import com.kilowats.servicos.ServicosVeiculo;
import com.kilowats.util.Utils;
import com.kilowats.util.jsf.FacesUtils;

@Named
@ViewScoped
public @Data class CadastroClienteControlador implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Inject
	private Cliente cliente;
	@Inject
	private EnderecoCliente endereco;
	private EnderecoCliente enderecoSelecionado;
	@Inject
	private TelefoneCliente telefone;
	private Cidade cidade;
	private TelefoneCliente telefoneSelecionado;
	@Inject
	private EmailCliente email;
	private EmailCliente emailSelecionado;
	@Inject
	private Veiculo veiculo;
	private Veiculo veiculoSelecionado;
	private Cep cep;
	@Inject
	private ServicosTelefone servicosTelefone;
	@Inject
	private ServicosCliente servicosCliente;
	@Inject
	private ServicosEmail servicosEmail;
	@Inject
	private ServicosVeiculo servicosVeiculo;
	@Inject
	private ServicosCidade servicosCidade;
	@Inject
	private ServicosEndereco servicosEndereco;
	@Inject
	private ServicosCep servicosCep;

	private List<TelefoneCliente> telefones = new ArrayList<>();
	private List<EmailCliente> emails = new ArrayList<>();
	private List<Veiculo> veiculos = new ArrayList<>();
	private List<EnderecoCliente> enderecos = new ArrayList<>();
	
	private int tpPessoa;
	private int tipoTelefone;
	
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
	
	private String TITULO = tituloTela();
	private final String ERRO_INTERNO = "Erro interno: erro interno contate a administração do sistema!";
	
	private void inicializarVariaveis(){
		cliente = new Cliente();
		cep = new Cep();
		cidade = new Cidade();
		enderecos = new ArrayList<>();
		telefones = new ArrayList<>();
		emails = new ArrayList<>();
		veiculos = new ArrayList<>();
		enderecoEntrega = false;
		bloqueiaEnderecoGeral = true;
		bloqPesquisaCep = true;
		cepEncontrado = true;
		enderecoEntrega = false;
	}
	
	public void habilitaPesquisaCep(){
		inicializarEndereco(false);
	}
	
	
	public boolean isBloqueioNivel1(){
		return bloqueiaEnderecoGeral || cepEncontrado;
	}
	
	public void pesquisar(){
		throw new RuntimeException("Cliente não pode pesquisado, pois ainda não foi implementado.");
	}
	
	public String carregaMascaraCnpjOuCpfPrimefaces(){
		return mascaraPrimefacesCnpjOuCpf(tpPessoa);
	}
	
	public void adcionaTelefone(){
		telefone.setTipoTelefone(returnTipoTelefone());
		telefone.setCliente(cliente);
		if(servicosTelefone.telefoneIsValido(telefone, TITULO, true)){
			adcionaTelefoneList(this.telefone);
		}
		this.telefone= new TelefoneCliente();
		tipoTelefone = 9;
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
	
	public boolean editar(){
		if(Utils.isNotNull(cliente)){
			return Utils.isNotNullOrEmpty(cliente.getId());
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
	
	public void adcionaTelefoneList(TelefoneCliente telefone){
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
		email.setCliente(cliente);
		if(servicosEmail.emailIsValido(email, TITULO, true)){
			adcionaEmailList(this.email);
		}
		this.email = new EmailCliente(); 
	}
	
	public void adcionaEmailList(EmailCliente email){
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
	
	public void adcionaVeiculo(){
		veiculo.setCliente(cliente);
		if(servicosVeiculo.veiculoIsValido(veiculo, TITULO, true)){
			adcionaVeiculoList(this.veiculo);
		}
		this.veiculo = new Veiculo(); 
	}
	
	public void adcionaVeiculoList(Veiculo veiculo){
		if(veiculos.isEmpty()){
			veiculos = new ArrayList<>();
		}else{
			if(veiculos.contains(veiculo)){
				FacesUtils.sendMensagemError(TITULO, "Validação Veiculo: Veiculo já adicionado!");
				return;
			}
		}
		veiculos.add(veiculo);
	}
	
	public void adcionaEndereco(){
		endereco.setCep(cep);
		endereco.setCliente(cliente);
		if(servicosEndereco.enderecoIsValido(endereco, TITULO, true)){
			adcionaEnderecoList(this.endereco);
		}
		inicializarEndereco(true);
	}

	private void inicializarEndereco(boolean var) {
		cep = new Cep();
		cidade = new Cidade();
		this.endereco = new EnderecoCliente();
		bloqPesquisaCep = var;
		cepEncontrado = true;
		bloqueiaEnderecoGeral = true;
		enderecoEntrega = false;
	}
	
	public void adcionaEnderecoList(EnderecoCliente ender){
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

	public void salvar(){
		completarDadosPessoa();
		if(servicosCliente.validarCliente(cliente, TITULO, true)){
			cliente = servicosCliente.persistirCliente(cliente);
			if(isNotNullOrEmpty(cliente) && cliente.getId() > 0L){
				inicializarVariaveis();
				FacesUtils.sendMensagemOk(TITULO, String.format("Cliente %s com suceso!", editar() ? "editado":"cadastrado"));
			}else{
				FacesUtils.sendMensagemError(TITULO, ERRO_INTERNO);
			}
		}
	}

	private void completarDadosPessoa() {
		enderecos = ajustaDadosEndereco();
		this.cliente.setEndereco(enderecos);
		if(this.tpPessoa == 0){
			this.cliente.setFisicaJuridica(TipoPessoa.FISICA);
		}else{
			this.cliente.setFisicaJuridica(TipoPessoa.JURIDICA);
		}
		if (isNotNullOrEmpty(telefones)) {
			this.cliente.setTelefones(this.telefones);
		}
		if (isNotNullOrEmpty(emails)) {
			this.cliente.setEmails(this.emails);
		}
		if (isNotNullOrEmpty(veiculos)) {
			this.cliente.setVeiculos(veiculos);
		}
		cliente.setCgcCpf(cliente.getCgcCpf().replaceAll("\\D", ""));
	}

	private List<EnderecoCliente> ajustaDadosEndereco() {
		List<EnderecoCliente> enderecosAjustados = new ArrayList<>();
		for(EnderecoCliente ender : enderecos){			
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
			List<EnderecoCliente> novaListaEndereco = new ArrayList<>();
			for(EnderecoCliente enderecoValidacao : enderecos){
				if(!enderecoValidacao.getCep().getCep().equals(enderecoSelecionado.getCep().getCep())){
					novaListaEndereco.add(enderecoValidacao);
				}
			}
			enderecos = new ArrayList<>();
			enderecos = novaListaEndereco;
		}
	}
	
	public void removerTelefoneDaLista(){
		if(Utils.isNotNull(telefoneSelecionado) && Utils.isNotNullOrEmpty(telefoneSelecionado.getNumero()) && Utils.isNotNullOrEmpty(telefones)){
			List<TelefoneCliente> novaListaTelefones = new ArrayList<>();
			for(TelefoneCliente telefoneValidacao : telefones){
				if(!telefoneValidacao.getNumero().equals(telefoneSelecionado.getNumero())){
					novaListaTelefones.add(telefoneValidacao);
				}
			}
			telefones = new ArrayList<>();
			telefones = novaListaTelefones;
		}
	}
	
	public void removerEmailDaLista(){
		if(Utils.isNotNull(emailSelecionado) && Utils.isNotNullOrEmpty(emailSelecionado.getEmailDestinatario()) && Utils.isNotNullOrEmpty(emails)){
			List<EmailCliente> novaListaEmail = new ArrayList<>();
			for(EmailCliente emailValidacao : emails){
				if(!emailValidacao.getEmailDestinatario().equals(emailSelecionado.getEmailDestinatario())){
					novaListaEmail.add(emailValidacao);
				}
			}
			emails = new ArrayList<>();
			emails = novaListaEmail;
		}
	}
	
	public void removerVeiculoDaLista(){
		if(Utils.isNotNull(veiculoSelecionado) && Utils.isNotNullOrEmpty(veiculoSelecionado.getPlaca()) && Utils.isNotNullOrEmpty(veiculos)){
			List<Veiculo> novaListaVeiculos = new ArrayList<>();
			for(Veiculo veiculoValidacao : veiculos){
				if(!veiculoValidacao.getPlaca().equals(veiculoSelecionado.getPlaca())){
					novaListaVeiculos.add(veiculoValidacao);
				}
			}
			veiculos = new ArrayList<>();
			veiculos = novaListaVeiculos;
		}
	}
	
	public void setCliente(Cliente cliente){
		this.cliente = cliente;
		if(Utils.isNotNullOrEmpty(this.cliente)){
			this.enderecos = this.cliente.getEndereco();
			this.telefones = this.cliente.getTelefones();
			this.emails = this.cliente.getEmails();
			this.veiculos = this.cliente.getVeiculos();
		}
	}
	

	private String tituloTela() {
		if(editar()){
			return "Edição de Cliente: ";
		}
		return "Cadastro Cliente: ";
	}
}
