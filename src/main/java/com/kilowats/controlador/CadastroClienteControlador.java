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
import com.kilowats.entidades.Email;
import com.kilowats.entidades.Endereco;
import com.kilowats.entidades.Telefone;
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
	private Endereco endereco;
	@Inject
	private Endereco enderecoSelecionado;
	@Inject
	private Telefone telefone;
	@Inject
	private Cidade cidade;
	@Inject
	private Telefone telefoneSelecionado;
	@Inject
	private Email email;
	@Inject
	private Email emailSelecionado;
	@Inject
	private Veiculo veiculo;
	@Inject
	private Veiculo veiculoSelecionado;
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
	@Inject
	private Cep cep;

	private List<Telefone> telefones = new ArrayList<>();
	private List<Email> emails = new ArrayList<>();
	private List<Veiculo> veiculos = new ArrayList<>();
	private List<Endereco> enderecos = new ArrayList<>();
	
	private int tpPessoa;
	private int tipoTelefone;
	
	private boolean bloqueiaEnderecoGeral;
	private boolean bloqPesquisaCep;
	private boolean cepEncontrado;
	
	{
		bloqueiaEnderecoGeral = true;
		bloqPesquisaCep = true;
		cepEncontrado = true;
	}
	
	private final String TITULO = "Cadastro Cliente: ";
	private final String ERRO_INTERNO = "Erro interno: erro interno contate a administração do sistema!";
	
	private void inicializarVariaveis(){
		cliente = new Cliente();
		cep = new Cep();
		cidade = new Cidade();
		endereco = new Endereco();
		telefones = new ArrayList<>();
		emails = new ArrayList<>();
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
		if(servicosTelefone.telefoneIsValido(telefone, TITULO, true)){
			adcionaTelefoneList(this.telefone);
		}
		this.telefone= new Telefone();
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
	
	public void adcionaTelefoneList(Telefone telefone){
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
		if(servicosEmail.emailIsValido(email, TITULO, true)){
			adcionaEmailList(this.email);
		}
		this.email = new Email(); 
	}
	
	public void adcionaEmailList(Email email){
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
		if(servicosEndereco.enderecoIsValido(endereco, TITULO, true)){
			adcionaEnderecoList(this.endereco);
		}
		inicializarEndereco(true);
	}

	private void inicializarEndereco(boolean var) {
		cep = new Cep();
		cidade = new Cidade();
		this.endereco = new Endereco();
		bloqPesquisaCep = var;
		cepEncontrado = true;
		bloqueiaEnderecoGeral = true;
	}
	
	public void adcionaEnderecoList(Endereco ender){
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
				FacesUtils.sendMensagemOk(TITULO, "Cliente cadastrado com suceso!");
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

	private List<Endereco> ajustaDadosEndereco() {
		List<Endereco> enderecosAjustados = new ArrayList<>();
		for(Endereco ender : enderecos){			
			if(Utils.isNotNull(ender)){
				if(ender.isCepGeral()){
					ender.setBairro(ender.getCep().getBairro());
					ender.setRua(ender.getCep().getRua());
					ender.setCep(servicosCep.pesquisarCepByCep(ender.getCep().getCep()));
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
}
