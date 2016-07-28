package com.kilowats.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.kilowats.annotations.CadastrarCliente;
import com.kilowats.entidades.Emails;
import com.kilowats.entidades.Cliente;
import com.kilowats.entidades.Endereco;
import com.kilowats.entidades.Telefone;
import com.kilowats.interfaces.IPersistirBancoDados;

@CadastrarCliente
public class ClienteDao implements IPersistirBancoDados{

	@Override
	public boolean salvar(Object obj) {
		if(obj instanceof Cliente){
			Cliente cliente = (Cliente) obj;
			try {
				File arquivo = new File("c:\\teste\\Cliente");
				if(!arquivo.exists()){
					arquivo.mkdirs();
				}
				FileWriter arq = new FileWriter(arquivo+"\\teste.txt", true);
				PrintWriter gravarArquivo = new PrintWriter(arq);
				gravarArquivo.println("+--------------------------------------------+\n");
				gravarArquivo.print(adicionarCliente(cliente)+"\n");
				gravarArquivo.println("+--------------------------------------------+\n");
				arq.close();
				System.out.printf("\nCliente gravado com sucesso! \""+arquivo.getPath()+"\\teste.txt\".\n");
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}	

	@Override
	public boolean alterar(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletar(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private String adicionarCliente(Cliente cliente){
		StringBuffer texto = new StringBuffer();
		texto.append("");
		texto.append("########## INICIO DADOS CLIENTE ##########\n");
		texto.append("Nome: "+ cliente.getNome()+"\n");
		texto.append("Tipo: "+ cliente.getFisicaJuridica().descTipoPessoa+"\n");
		texto.append("CNPJ/CPF: "+ cliente.getCgcCpf()+"\n");
		texto.append("########## FIM DADOS CLIENTE ##########\n");
		texto.append("\n\n\n");
		texto.append(adicionaEnderecoFornecedor(cliente.getEndereco()));
		texto.append("\n\n\n");
		if(cliente.getTelefones() != null && !cliente.getTelefones().isEmpty()){
			texto.append(adicionaTelefonesFornecedor(cliente.getTelefones()));
		}
		texto.append("\n\n\n");
		if(cliente.getEmails() != null && !cliente.getEmails().isEmpty()){
			texto.append(adicionaEmailsFornecedor(cliente.getEmails()));
		}
		return texto.toString();
	}
	
	private String adicionaTelefonesFornecedor(List<Telefone> telefones) {
		StringBuffer texto = new StringBuffer();
		for(Telefone fone : telefones){
			texto.append("########## INICIO DADOS TELEFONE ##########\n");
			texto.append("idPessoa: bancoDados\n");
			texto.append("DDD: "+fone.getDdd()+"\n");
			texto.append("Numero: "+fone.getNumero()+"\n");
			texto.append("Tipo: "+fone.getTipoTelefone()+"\n");
			texto.append("########## FIM DADOS TELEFONE ##########\n");
		}
		return texto.toString();
	}

	private String adicionaEmailsFornecedor(List<Emails> emails) {
		StringBuffer texto = new StringBuffer();
		for(Emails email : emails){
			texto.append("########## INICIO DADOS EMAILS ##########\n");
			texto.append("idPessoa: bancoDados\n");
			texto.append("Emails: "+email.getEmailDestinatario()+"\n");
			texto.append("Destinatário: "+email.getNomePessoaDestinatario()+"\n");
			texto.append("########## FIM DADOS EMAILS ##########\n");
		}
		return texto.toString();
	}
	
	private String adicionaEnderecoFornecedor(Endereco endereco){
		StringBuffer texto = new StringBuffer();
		texto.append("########## INICIO DADOS ENDERECO ##########\n");
		texto.append("Cep: "+endereco.getCep().getCep()+"\n");
		texto.append("Rua: "+endereco.getCep().getRua()+"\n");
		texto.append("Complemento: "+endereco.getComplemento()+"\n");
		texto.append("Numero: "+endereco.getNumero()+"\n");
		texto.append("Bairro: "+endereco.getCep().getBairro()+"\n");
		texto.append("Cidade: "+endereco.getCep().getCidade().getNomeCidade()+"\n");
		texto.append("UF: "+endereco.getCep().getCidade().getUf()+"\n");
		texto.append("########## FIM DADOS ENDERECO ##########\n");
		return texto.toString();
	}

}
