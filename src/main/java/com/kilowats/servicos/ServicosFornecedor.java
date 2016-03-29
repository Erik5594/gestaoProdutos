package com.kilowats.servicos;

import java.util.List;

import com.kilowats.entidades.Emails;
import com.kilowats.entidades.Empresa;
import com.kilowats.entidades.Endereco;
import com.kilowats.entidades.Telefone;
import com.kilowats.interfaces.IPersistirBancoDados;

public class ServicosFornecedor implements IPersistirBancoDados{
	
	public static boolean adicionarFornecedorBancoDados(Empresa empresa){
		return adicionarFornecedor(empresa);
	}
	
	private static boolean adicionarFornecedor(Empresa empresa){
		System.out.println("########## INICIO DADOS EMPRESA ##########");
		System.out.println("Nome: "+ empresa.getNome());
		System.out.println("CNPJ/CPF: "+ empresa.getCgcCpf());
		System.out.println("Incs. Estadual: "+empresa.getInscricaoEstadual());
		System.out.println("########## FIM DADOS EMPRESA ##########");
		System.out.println("#");
		System.out.println("#");
		adicionaEnderecoFornecedor(empresa.getEndereco());
		System.out.println("#");
		System.out.println("#");
		if(empresa.getTelefones() != null && !empresa.getTelefones().isEmpty()){
			adicionaTelefonesFornecedor(empresa.getTelefones());
		}
		System.out.println("#");
		System.out.println("#");
		if(empresa.getEmails() != null && !empresa.getEmails().isEmpty()){
			adicionaEmailsFornecedor(empresa.getEmails());
		}
		return true;
	}
	
	private static void adicionaTelefonesFornecedor(List<Telefone> telefones) {
		for(Telefone fone : telefones){
			System.out.println("########## INICIO DADOS TELEFONE ##########");
			System.out.println("idPessoa: bancoDados");
			System.out.println("DDD: "+fone.getDdd());
			System.out.println("Numero: "+fone.getNumero());
			System.out.println("Tipo: "+fone.getTipoTelefone());
			System.out.println("########## FIM DADOS TELEFONE ##########");
		}
	}

	private static void adicionaEmailsFornecedor(List<Emails> emails) {
		for(Emails email : emails){
			System.out.println("########## INICIO DADOS EMAILS ##########");
			System.out.println("idPessoa: bancoDados");
			System.out.println("Emails: "+email.getEmailDestinatario());
			System.out.println("Destinatário: "+email.getNomePessoaDestinatario());
			System.out.println("########## FIM DADOS EMAILS ##########");
		}
	}
	
	private static boolean adicionaEnderecoFornecedor(Endereco endereco){
		System.out.println("########## INICIO DADOS ENDERECO ##########");
		System.out.println("Cep: "+endereco.getCep());
		System.out.println("Rua: "+endereco.getRua());
		System.out.println("Complemento: "+endereco.getComplemento());
		System.out.println("Numero: "+endereco.getNumero());
		System.out.println("Bairro: "+endereco.getBairro());
		System.out.println("Bairro: "+endereco.getCidade().getNomeCidade());
		System.out.println("Bairro: "+endereco.getCidade().getUf());
		System.out.println("########## FIM DADOS ENDERECO ##########");
		return true;
	}

	@Override
	public boolean salvar(Object obj) {
		return adicionarFornecedor((Empresa) obj);
	}

	@Override
	public boolean alterar(Object obj) {
		return false;
	}

	@Override
	public boolean deletar(Object obj) {
		return false;
	}

}
