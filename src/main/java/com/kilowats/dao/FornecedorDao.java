package com.kilowats.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.kilowats.annotations.CadastrarFornecedor;
import com.kilowats.entidades.Emails;
import com.kilowats.entidades.Empresa;
import com.kilowats.entidades.Endereco;
import com.kilowats.entidades.Telefone;
import com.kilowats.interfaces.IPersistirBancoDados;

@CadastrarFornecedor
public class FornecedorDao implements IPersistirBancoDados{

	@Override
	public boolean salvar(Object obj) {
		if(obj instanceof Empresa){
			Empresa empresa = (Empresa) obj;
			try {
				File arquivo = new File("c:\\teste\\Fornecedor");
				if(!arquivo.exists()){
					arquivo.mkdirs();
				}
				FileWriter arq = new FileWriter(arquivo+"\\teste.txt", true);
				PrintWriter gravarArquivo = new PrintWriter(arq);
				gravarArquivo.print("+--------------------------------------------+\n");
				gravarArquivo.print(adicionarFornecedor(empresa)+"\n");
				gravarArquivo.print("+--------------------------------------------+\n");
				arq.close();
				System.out.printf("\nFornecedor gravado com sucesso! \""+arquivo.getPath()+"\\teste.txt\".\n");
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
	
	private String adicionarFornecedor(Empresa empresa){
		StringBuffer texto = new StringBuffer();
		texto.append("");
		texto.append("########## INICIO DADOS EMPRESA ##########\n");
		texto.append("Nome: "+ empresa.getNome()+"\n");
		texto.append("Tipo: "+ empresa.getFisicaJuridica().descTipoPessoa+"\n");
		texto.append("CNPJ/CPF: "+ empresa.getCgcCpf()+"\n");
		texto.append("Incs. Estadual: "+empresa.getInscricaoEstadual()+"\n");
		texto.append("########## FIM DADOS EMPRESA ##########\n");
		texto.append("\n\n\n");
		texto.append(adicionaEnderecoFornecedor(empresa.getEndereco()));
		texto.append("\n\n\n");
		if(empresa.getTelefones() != null && !empresa.getTelefones().isEmpty()){
			texto.append(adicionaTelefonesFornecedor(empresa.getTelefones()));
		}
		texto.append("\n\n\n");
		if(empresa.getEmails() != null && !empresa.getEmails().isEmpty()){
			texto.append(adicionaEmailsFornecedor(empresa.getEmails()));
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
