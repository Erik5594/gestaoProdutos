package com.kilowats.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.kilowats.annotations.CadastrarUsuario;
import com.kilowats.entidades.Grupo;
import com.kilowats.entidades.Usuario;
import com.kilowats.interfaces.IPersistirBancoDados;

@CadastrarUsuario
public class UsuarioDao implements IPersistirBancoDados{

	@Override
	public boolean salvar(Object obj) {
		if(obj instanceof Usuario){
			Usuario usuario = (Usuario) obj;
			try {
				File arquivo = new File("c:\\teste\\Usuario");
				if(!arquivo.exists()){
					arquivo.mkdirs();
				}
				FileWriter arq = new FileWriter(arquivo+"\\teste.txt", true);
				PrintWriter gravarArquivo = new PrintWriter(arq);
				gravarArquivo.println("+--------------------------------------------+\n");
				gravarArquivo.print(adicionarUsuario(usuario)+"\n");
				gravarArquivo.println("+--------------------------------------------+\n");
				arq.close();
				System.out.printf("\nUsuário gravado com sucesso! \""+arquivo.getPath()+"\\teste.txt\".\n");
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
	
	private String adicionarUsuario(Usuario usuario){
		StringBuffer texto = new StringBuffer();
		texto.append("");
		texto.append("########## INICIO DADOS USUÁRIO ##########\n");
		texto.append("Nome: "+ usuario.getNome()+"\n");
		texto.append("Email: "+ usuario.getEmail()+"\n");
		texto.append("Senha: "+ usuario.getSenha()+"\n");
		texto.append("########## FIM DADOS USUÁRIO ##########\n");
		texto.append("\n\n");
		if(usuario.getGrupos() != null && !usuario.getGrupos().isEmpty()){
			texto.append("########## INICIO DADOS USUÁRIOS ##########\n");
		for(Grupo grupo : usuario.getGrupos()){
			texto.append(adicionarGrupo(grupo));
		}
			texto.append("########## FIM DADOS USUÁRIOS ##########\n");
		}
		return texto.toString();
	}

	private String adicionarGrupo(Grupo grupo) {
		StringBuffer texto = new StringBuffer();
		texto.append("");
		texto.append("Nome: "+ grupo.getNome()+"\n");
		texto.append("Email: "+ grupo.getDescricao()+"\n");
		texto.append("\n\n");
		
		return texto.toString();
	}

	public Usuario porEmail(String email) {
		Usuario usuario1 = new Usuario();
		usuario1.setEmail("erik");
		usuario1.setNome("Erik");
		usuario1.setSenha("erik");
		
		Grupo grupo1 = new Grupo();
		grupo1.setDescricao("Administradores");
		grupo1.setNome("Administradores");
		
		List<Grupo> grupos1 = new ArrayList<>();
		grupos1.add(grupo1);
		
		usuario1.setGrupos(grupos1);
		
		Usuario usuario2 = new Usuario();
		usuario2.setEmail("joao");
		usuario2.setNome("João");
		usuario2.setSenha("joao");
		
		Grupo grupo2 = new Grupo();
		grupo2.setDescricao("Vendedores");
		grupo2.setNome("Vendedores");
		
		List<Grupo> grupos2 = new ArrayList<>();
		grupos2.add(grupo2);
		
		usuario2.setGrupos(grupos2);
		
		Usuario usuario3 = new Usuario();
		usuario3.setEmail("manoel");
		usuario3.setNome("Manoel");
		usuario3.setSenha("manoel");
		
		Grupo grupo3 = new Grupo();
		grupo3.setDescricao("Administradores");
		grupo3.setNome("Administradores");
		
		List<Grupo> grupos3 = new ArrayList<>();
		grupos3.add(grupo3);
		
		usuario3.setGrupos(grupos3);
		
		Usuario retorno = null;
		
		if(usuario1.getEmail().equalsIgnoreCase(email)){
			retorno = usuario1;
		}else if(usuario2.getEmail().equalsIgnoreCase(email)){
			retorno = usuario2;
		}else if(usuario3.getEmail().equalsIgnoreCase(email)){
			retorno = usuario3;
		}
		return retorno;
	}
	
}
