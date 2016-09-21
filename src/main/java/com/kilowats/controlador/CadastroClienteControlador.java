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
	
	private boolean cepGeralOrFaixa;
	private boolean bloqPesquisaCep;
	private boolean cepEncontrado;
	
	{
		cepGeralOrFaixa = true;
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
		bloqPesquisaCep = false;
		cepGeralOrFaixa = false;
		cepEncontrado = false;
	}
	
	public boolean isBloqueioCep1(){
		return cepGeralOrFaixa || isBloqueioCep2();
	}
	
	public boolean isBloqueioCep2(){
		return bloqPesquisaCep || cepEncontrado;
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
			long numrCep = cep.getCep();
			cep = servicosCep.pesquisarCepByCep(numrCep);
			if(Utils.isNullOrEmpty(cep)){
				cep.setCep(numrCep);
				cidade = servicosCidade.pesquisarMunicipioByCepGeral(numrCep);
				if(Utils.isNotNullOrEmpty(cidade)){
					cidade = servicosCidade.pesquisarMunicipioByFaixaCep(numrCep);
					if(Utils.isNotNullOrEmpty(cidade)){
						FacesUtils.sendMensagemError(TITULO, "Cep inválido!");
						inicializarEndereco();
						return;
					}else{
						FacesUtils.sendMensagemAviso(TITULO, "Cep por faixa encontrado!");
						cepGeralOrFaixa = true;
						return;
					}
				}else{
					FacesUtils.sendMensagemAviso(TITULO, "Cep geral informado!");
					cepGeralOrFaixa = true;
					return;
				}
			}else{
				cepEncontrado = true;
				return;
			}
		}else{
			FacesUtils.sendMensagemError(TITULO, "Cep não informado!");
		}
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
		cep.setCidade(cidade);
		endereco.setCep(cep);
		if(servicosEndereco.enderecoIsValido(endereco, TITULO, true)){
			adcionaEnderecoList(this.endereco);
		}
		inicializarEndereco();
	}

	private void inicializarEndereco() {
		cep = new Cep();
		cidade = new Cidade();
		this.endereco = new Endereco();
		bloqPesquisaCep = true;
		cepEncontrado = true;
		cepGeralOrFaixa = true;
	}
	
	public void adcionaEnderecoList(Endereco ender){
		if(enderecos.isEmpty()){
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
		if(validacoes()){
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

	private boolean validarEndereco() {
		return servicosEndereco.enderecoIsValido(endereco, TITULO, true);
	}
	
	private boolean validacoes(){
		if(validarDadosPrincipais() & validarEndereco()){
			return true;
		}
		return false;
	}
	
	private boolean validarDadosPrincipais() {
		return servicosCliente.validarCliente(cliente, TITULO, true);
	}
	
	public List<String> carregarEstados(){
		List<String> ufs = new ArrayList<>();
		ufs.add("GO");
		ufs.add("SP");
		ufs.add("MG");
		ufs.add("RJ");
		return ufs;
	}
	
	public List<String> carregarCidades() {
		List<String> cidades = new ArrayList<>();
		if (cidade != null
				&& (cidade.getUf() != null && !"".equals(cidade.getUf()))) {
			String uf = cidade.getUf();
			if (uf.toUpperCase().equals("GO")) {
				cidades.add("Goiânia");
				cidades.add("Aparecida de Goiânia");
				cidades.add("Trindade");
			}

			if (uf.toUpperCase().equals("SP")) {
				cidades.add("São Paulo");
				cidades.add("Santos");
				cidades.add("Guarulhos");
			}

			if (uf.toUpperCase().equals("MG")) {
				cidades.add("Belo Horizonte");
				cidades.add("Formiga");
				cidades.add("Teófilo Otoni");
			}

			if (uf.toUpperCase().equals("RJ")) {
				cidades.add("Rio de Janeiro");
				cidades.add("Macae");
				cidades.add("Cabo Frio");
			}
		}
		return cidades;
	}
}
