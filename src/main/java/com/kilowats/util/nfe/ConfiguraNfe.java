package com.kilowats.util.nfe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Properties;

import com.fincatto.nfe310.NFeConfig;
import com.fincatto.nfe310.classes.NFUnidadeFederativa;
import com.kilowats.exceptions.NegocioException;

public class ConfiguraNfe extends NFeConfig{
	
	private Properties prop;
	
	private static final String SENHA_CERTIFICADO = "senha_certificado";
	private static final String SENHA_CADEIA_CERTIFICADO = "senha_cadeia_certificado";
	private static final String CAMINHO_PROPERTIES_NFE = System.getProperty("user.home")+"/kilowats/gestaoPorduto/propriedades";
	private static final String CAMINHO_PROPERTIES_NFE_ARQUIVO = CAMINHO_PROPERTIES_NFE+"/configNfe.properties";
	
	private KeyStore certificadoKeyStore;
	private KeyStore cadeiaCertificadosKeyStore;

	@Override
	public NFUnidadeFederativa getCUF() {
		return NFUnidadeFederativa.GO;
	}

	@Override
	public KeyStore getCertificadoKeyStore() throws KeyStoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCertificadoSenha() {
		// TODO Auto-generated method stub
		return prop.getProperty(SENHA_CERTIFICADO);
	}

	@Override
	public KeyStore getCadeiaCertificadosKeyStore() throws KeyStoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCadeiaCertificadosSenha() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Properties getPropriedadesNfe() throws IOException{
		this.prop = new Properties();
		File diretorio = new File(CAMINHO_PROPERTIES_NFE);
		if(diretorio.exists()){
			File arq = new File(CAMINHO_PROPERTIES_NFE_ARQUIVO);
			if(arq.exists()){
				lerArquivoConfiguracoes(this.prop, arq);
			}else{
				throw new NegocioException("Arquivo de configuração do NFe não encontrado.");
			}
		}else{
			throw new NegocioException("Diretorio de configuração do NFe inexistente.");
		}
		
		
		return prop;
	}
	
	private void lerArquivoConfiguracoes(Properties prop, File arq) throws FileNotFoundException, IOException {
		FileInputStream file = new FileInputStream(arq);
		prop.load(file);
	}
	
	public void criarConfiguracao(String senhaCertificado, String senhacadeiaCertificados) throws IOException{
		File diretorio = new File(CAMINHO_PROPERTIES_NFE);
		if(diretorio.exists()){
			return;
		}
		diretorio.mkdirs();
		criarArquivoConfig(senhaCertificado, senhacadeiaCertificados);
	}
	
	private void criarArquivoConfig(String senhaCertificado, String senhacadeiaCertificados) throws IOException {
		FileWriter arq = new FileWriter(CAMINHO_PROPERTIES_NFE_ARQUIVO, true);
		PrintWriter gravarArquivo = new PrintWriter(arq);
		gravarArquivo.println(SENHA_CERTIFICADO + " = "+senhaCertificado);
		gravarArquivo.println(SENHA_CADEIA_CERTIFICADO + " = "+senhacadeiaCertificados);
		gravarArquivo.close();
		arq.close();
	}

}
