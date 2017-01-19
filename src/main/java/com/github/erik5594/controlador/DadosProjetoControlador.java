package com.github.erik5594.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Named
@RequestScoped
public class DadosProjetoControlador {
	
	private String versao;
	private String empresa;
	private String nomeProjeto;
	
	private static String VERSAO_PROJETO = "versao";
	private static String NOME_PROJETO = "nomeProjeto";
	private static String NOME_EMPRESA = "nomeEmpresa";
	
	private static Log log = LogFactory.getLog(DadosProjetoControlador.class);
	
	public DadosProjetoControlador(){
		Properties prop = new Properties();
		InputStream input = getClass().getResourceAsStream("/producao.properties");
		try {
			prop.load(input);
			versao = "Versão: "+prop.getProperty(VERSAO_PROJETO);
			nomeProjeto = prop.getProperty(NOME_PROJETO);
			empresa =  prop.getProperty(NOME_EMPRESA);
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		
	}
	
	public String getVersao() {
		return versao;
	}
	public String getEmpresa() {
		return empresa;
	}
	public String getNomeProjeto() {
		return nomeProjeto;
	}
}
