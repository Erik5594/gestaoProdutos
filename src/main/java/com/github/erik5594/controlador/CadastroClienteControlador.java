package com.github.erik5594.controlador;

import static com.github.erik5594.util.Utils.isNotNullOrEmpty;
import static com.github.erik5594.util.Utils.mascaraPrimefacesCnpjOuCpf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import com.github.erik5594.entidades.Cep;
import com.github.erik5594.entidades.Cidade;
import com.github.erik5594.entidades.Cliente;
import com.github.erik5594.entidades.EmailCliente;
import com.github.erik5594.entidades.EnderecoCliente;
import com.github.erik5594.entidades.TelefoneCliente;
import com.github.erik5594.entidades.Veiculo;
import com.github.erik5594.enuns.TipoPessoa;
import com.github.erik5594.enuns.TipoTelefoneEnum;
import com.github.erik5594.enuns.TipoVeiculo;
import com.github.erik5594.servicos.ServicosCep;
import com.github.erik5594.servicos.ServicosCidade;
import com.github.erik5594.servicos.ServicosCliente;
import com.github.erik5594.servicos.ServicosEmail;
import com.github.erik5594.servicos.ServicosEndereco;
import com.github.erik5594.servicos.ServicosTelefone;
import com.github.erik5594.servicos.ServicosVeiculo;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

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
	
	public String carregaMascaraCnpjOuCpfPrimefaces(){
		int codTipoPessoa = cliente != null
				&& cliente.getFisicaJuridica() != null ? cliente.getFisicaJuridica().getCodPessoa():0;
		return mascaraPrimefacesCnpjOuCpf(codTipoPessoa);
	}
	
	public void adicionaTelefone(){
		telefone.setTipoTelefone(returnTipoTelefone());
		telefone.setCliente(cliente);
		if(servicosTelefone.telefoneIsValido(telefone, TITULO, true)){
			adicionaTelefoneList(this.telefone);
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
	
	private void adicionaTelefoneList(TelefoneCliente telefone){
		if(Utils.isNullOrEmpty(cliente.getTelefones())){
			cliente.setTelefones(new ArrayList<TelefoneCliente>());
		}else{
			if(cliente.getTelefones().contains(telefone)){
				FacesUtils.sendMensagemError(TITULO, "Validação Telefone: Numero já adicionado!");
				return;
			}
		}
		cliente.getTelefones().add(telefone);
	}

	public void adicionaEmail(){
		email.setCliente(cliente);
		if(servicosEmail.emailIsValido(email, TITULO, true)){
			adicionaEmailList(this.email);
		}
		this.email = new EmailCliente(); 
	}
	
	private void adicionaEmailList(EmailCliente email){
		if(Utils.isNullOrEmpty(cliente.getEmails())){
			cliente.setEmails(new ArrayList<EmailCliente>());
		}else{
			if(cliente.getEmails().contains(email)){
				FacesUtils.sendMensagemError(TITULO, "Validação Email: E-mail já adicionado!");
				return;
			}
		}
		cliente.getEmails().add(email);
	}
	
	public void adicionaVeiculo(){
		veiculo.setCliente(cliente);
		if(servicosVeiculo.veiculoIsValido(veiculo, TITULO, true)){
			adicionaVeiculoList(this.veiculo);
		}
		this.veiculo = new Veiculo(); 
	}
	
	private void adicionaVeiculoList(Veiculo veiculo){
		if(Utils.isNullOrEmpty(cliente.getVeiculos())){
			cliente.setVeiculos(new ArrayList<Veiculo>());
		}else{
			if(cliente.getVeiculos().contains(veiculo)){
				FacesUtils.sendMensagemError(TITULO, "Validação Veiculo: Veiculo já adicionado!");
				return;
			}
		}
		cliente.getVeiculos().add(veiculo);
	}
	
	public void adicionaEndereco(){
		endereco.setCep(cep);
		endereco.setCliente(cliente);
		if(servicosEndereco.enderecoIsValido(endereco, TITULO, true)){
			adicionaEnderecoList(this.endereco);
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
	
	private void adicionaEnderecoList(EnderecoCliente ender){
		if(Utils.isNullOrEmpty(cliente.getEndereco())){
			cliente.setEndereco(new ArrayList<EnderecoCliente>());
		}else{
			if(cliente.getEndereco().contains(ender)){
				FacesUtils.sendMensagemError(TITULO, "Validação Endereço: Endereço já adicionado!");
				return;
			}
		}
		ender.ajustarEndereco();
		cliente.getEndereco().add(ender);
	}

	public void salvar(){
		completarDadosPessoa();
		if(servicosCliente.validarCliente(cliente, TITULO, true)){
			cliente = servicosCliente.persistirCliente(cliente);
			if(isNotNullOrEmpty(cliente) && cliente.getId() > 0L){
				inicializarVariaveis();
				FacesUtils.sendMensagemOk(TITULO, String.format("Cliente %s com sucesso!", editar() ? "editado":"cadastrado"));
			}else{
				FacesUtils.sendMensagemError(TITULO, ERRO_INTERNO);
			}
		}
	}

	private void completarDadosPessoa() {
		if(Utils.isNullOrEmpty(cliente.getId())){
			this.cliente.setDataCredenciamento(new Date());
		}
		this.cliente.setUltimaAtualizacao(new Date());
		cliente.setCgcCpf(cliente.getCgcCpf().replaceAll("\\D", ""));
	}

	public void removerEnderecoDaLista(){
		if(Utils.isNotNull(enderecoSelecionado) && Utils.isNotNullOrEmpty(enderecoSelecionado.getCep().getCep())){
			cliente.removerEndereco(enderecoSelecionado);
		}
	}
	
	public void removerTelefoneDaLista(){
		if(Utils.isNotNull(telefoneSelecionado) && Utils.isNotNullOrEmpty(telefoneSelecionado.getNumero())){
			cliente.removerTelefone(telefoneSelecionado);
		}
	}
	
	public void removerEmailDaLista(){
		if(Utils.isNotNull(emailSelecionado) && Utils.isNotNullOrEmpty(emailSelecionado.getEmailDestinatario())){
			cliente.removerEmail(emailSelecionado);
		}
	}
	
	public void removerVeiculoDaLista(){
		if(Utils.isNotNull(veiculoSelecionado) && Utils.isNotNullOrEmpty(veiculoSelecionado.getPlaca())){
			cliente.removerVeiculo(veiculoSelecionado);
		}
	}

	private String tituloTela() {
		if(editar()){
			return "Edição de Cliente: ";
		}
		return "Cadastro Cliente: ";
	}
	
	public TipoPessoa[] getTiposPessoa() {
		return TipoPessoa.values();
	}
	
	public TipoVeiculo[] getTipoVeiculo() {
		return TipoVeiculo.values();
	}
}