package com.github.erik5594.util.jpa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.github.erik5594.util.Constantes;
import com.github.erik5594.util.Utils;

@ApplicationScoped
public class EntityManagerProducer {

	private EntityManagerFactory factory;
	private static String ARQ_URL = "com.kilowats.host";
	private static String ARQ_USER = "com.kilowats.user";
	private static String ARQ_PASSWORD = "com.kilowats.password";
	private static String ARQ_DRIVE = "com.kilowats.driver";
	
	private static String HIBERNATE_URL = "javax.persistence.jdbc.url";
	private static String HIBERNATE_USER = "javax.persistence.jdbc.user";
	private static String HIBERNATE_PASSWORD = "javax.persistence.jdbc.password";
	private static String HIBERNATE_DRIVE = "javax.persistence.jdbc.driver";
	
	
	public EntityManagerProducer() {
		try {
			Map<String, String> propriedadesBanco = getPropriedadesBanco();
			factory = Persistence.createEntityManagerFactory("gestaoPU", propriedadesBanco);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Produces
	@RequestScoped
	public EntityManager createEntityManager() {
		return factory.createEntityManager();
	}
	
	public void closeEntityManager(@Disposes EntityManager manager) {
		if(Utils.isNotNull(manager) && manager.isOpen()){
			manager.close();
		}
	}
	
	private Map<String, String> getPropriedadesBanco() throws IOException{
		Map<String, String> propriedades = new HashMap<String, String>();
		Properties prop = new Properties();
		File diretorio = new File(Constantes.DIR_PROPIEDADES);
		if(diretorio.exists()){
			File arq = new File(diretorio.getPath() + Constantes.NOME_ARQ_CONFIG_BANCO);
			if(arq.exists()){
				lerArquivoConfiguracoes(propriedades, prop, arq);
			}else{
				propriedades = null;
				criarArquivoConfig(diretorio);
			}
		}else{
			propriedades = null;
			if(diretorio.mkdirs()){
				criarArquivoConfig(diretorio);
			}
		}
		
		
		return propriedades;
	}

	private void lerArquivoConfiguracoes(Map<String, String> propriedades, Properties prop,
			File arq) throws FileNotFoundException, IOException {
		FileInputStream file = new FileInputStream(arq);
		prop.load(file);
		
		propriedades.put(HIBERNATE_DRIVE, prop.getProperty(ARQ_DRIVE));
		propriedades.put(HIBERNATE_URL, prop.getProperty(ARQ_URL));
		propriedades.put(HIBERNATE_USER, prop.getProperty(ARQ_USER));
		propriedades.put(HIBERNATE_PASSWORD, prop.getProperty(ARQ_PASSWORD));
	}

	private void criarArquivoConfig(File diretorio) throws IOException {
		FileWriter arq = new FileWriter(diretorio.getPath() + Constantes.NOME_ARQ_CONFIG_BANCO, true);
		PrintWriter gravarArquivo = new PrintWriter(arq);
		gravarArquivo.println(ARQ_DRIVE + " = org.postgresql.Driver");
		gravarArquivo.println(ARQ_URL + " = jdbc:postgresql://localhost:5432/gestao");
		gravarArquivo.println(ARQ_USER + " = postgres");
		gravarArquivo.println(ARQ_PASSWORD + " = 1234");
		gravarArquivo.close();
		arq.close();
	}
	
}
