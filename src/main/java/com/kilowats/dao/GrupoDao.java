package com.kilowats.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.kilowats.annotations.CadastrarGrupo;
import com.kilowats.entidades.Grupo;
import com.kilowats.interfaces.IPersistirBancoDados;

@CadastrarGrupo
public class GrupoDao implements IPersistirBancoDados{

	@Override
	public boolean salvar(Object obj) {
		if(obj instanceof Grupo){
			Grupo grupo = (Grupo) obj;
			try {
				File arquivo = new File("c:\\teste\\Grupo");
				if(!arquivo.exists()){
					arquivo.mkdirs();
				}
				FileWriter arq = new FileWriter(arquivo+"\\teste.txt", true);
				PrintWriter gravarArquivo = new PrintWriter(arq);
				gravarArquivo.println("+--------------------------------------------+\n");
				gravarArquivo.print(adicionarGrupo(grupo)+"\n");
				gravarArquivo.println("+--------------------------------------------+\n");
				arq.close();
				System.out.printf("\nGrupo gravado com sucesso! \""+arquivo.getPath()+"\\teste.txt\".\n");
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
	
	private String adicionarGrupo(Grupo grupo){
		StringBuffer texto = new StringBuffer();
		texto.append("");
		texto.append("########## INICIO DADOS GRUPO ##########\n");
		texto.append("Nome: "+ grupo.getNome()+"\n");
		texto.append("Email: "+ grupo.getDescricao()+"\n");
		texto.append("########## FIM DADOS GRUPO ##########\n");
		texto.append("\n\n");
		return texto.toString();
	}
}
