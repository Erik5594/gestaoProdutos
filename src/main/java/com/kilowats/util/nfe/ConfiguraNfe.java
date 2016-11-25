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
import com.fincatto.nfe310.classes.NFAmbiente;
import com.fincatto.nfe310.classes.NFTipoEmissao;
import com.fincatto.nfe310.classes.NFUnidadeFederativa;
import com.kilowats.entidades.ConfigNfeEntidade;
import com.kilowats.exceptions.NegocioException;

public class ConfiguraNfe extends NFeConfig{
	
	private Properties prop;
	
	private static final String SENHA_CERTIFICADO = "senha_certificado";
	private static final String SENHA_CADEIA_CERTIFICADO = "senha_cadeia_certificado";
	private static final String CAMINHO_PROPERTIES_NFE = System.getProperty("user.home")+"/kilowats/gestaoPorduto/propriedades";
	private static final String CAMINHO_PROPERTIES_NFE_ARQUIVO = CAMINHO_PROPERTIES_NFE+"/configNfe.properties";
	private static final String CAMINHO_CERTIFICADO = "caminho_certificado";
	private static final String CAMINHO_CADEIA_CERTIFICADO = "caminho_cadeia_certificado";
	private static final String AMBIENTE = "ambiente_nfe";
	private static final String TIPO_EMISSAO = "tipo_emissao_nfe";
	private static final String NOME_CERTIFICADO = "nome_certificado";
	private static final String NOME_CADEIA_CERTIFICADO = "nome_cadeia_certificado";
	
	private KeyStore certificadoKeyStore;
	private KeyStore cadeiaCertificadosKeyStore;
	

	public ConfiguraNfe() {
		try {
			lerArquivoConfiguracoes(new File(CAMINHO_PROPERTIES_NFE_ARQUIVO));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ConfiguraNfe(boolean lerArqConfigNaConstrucao) {
		if(lerArqConfigNaConstrucao){
			try {
				lerArquivoConfiguracoes(new File(CAMINHO_PROPERTIES_NFE_ARQUIVO));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public NFUnidadeFederativa getCUF() {
		return NFUnidadeFederativa.GO;
	}
	
	@Override
	public NFAmbiente getAmbiente() {
		if(prop != null){
			NFAmbiente.valueOf(prop.getProperty(AMBIENTE));
		}
		return super.getAmbiente();
	}
	
	@Override
	public NFTipoEmissao getTipoEmissao() {
		if(prop != null){
			return NFTipoEmissao.valueOf(prop.getProperty(TIPO_EMISSAO));
		}
		return super.getTipoEmissao();
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
	
	public Properties criarConfiguracao(ConfigNfeEntidade dadosConfigNfe) throws IOException{
		File diretorio = new File(CAMINHO_PROPERTIES_NFE);
		File arquivo = new File(CAMINHO_PROPERTIES_NFE_ARQUIVO);
		if(diretorio.exists()){
			if(arquivo.exists()){
				lerArquivoConfiguracoes(arquivo);
				return this.prop;
			}
		}else{
			diretorio.mkdirs();
		}
		criarArquivoConfig(dadosConfigNfe);
		lerArquivoConfiguracoes(new File(CAMINHO_PROPERTIES_NFE_ARQUIVO));
		return this.prop;
	}
	
	private void criarArquivoConfig(ConfigNfeEntidade dadosConfigNfe) throws IOException {
		FileWriter arq = new FileWriter(CAMINHO_PROPERTIES_NFE_ARQUIVO, true);
		PrintWriter gravarArquivo = new PrintWriter(arq);
		gravarArquivo.println(SENHA_CERTIFICADO + " = "+dadosConfigNfe.getSenhaCertificado());
		gravarArquivo.println(SENHA_CADEIA_CERTIFICADO + " = "+dadosConfigNfe.getSenhaCadeiaCertificado());
		gravarArquivo.println(CAMINHO_CERTIFICADO + " = "+dadosConfigNfe.getCaminhoCertificado());
		gravarArquivo.println(CAMINHO_CADEIA_CERTIFICADO + " = "+dadosConfigNfe.getCaminhoCadeiaCertificado());
		gravarArquivo.println(NOME_CERTIFICADO + " = "+dadosConfigNfe.getNomeCertificado());
		gravarArquivo.println(NOME_CADEIA_CERTIFICADO + " = "+dadosConfigNfe.getNomeCadeiaCertificado());
		gravarArquivo.println(AMBIENTE + " = "+ dadosConfigNfe.getAmbiente().getCodigo());
		gravarArquivo.println(TIPO_EMISSAO + " = "+ dadosConfigNfe.getTipoEmissaoNfe().getCodigo());
		gravarArquivo.close();
		arq.close();
	}

	private void lerArquivoConfiguracoes(File arq) throws FileNotFoundException, IOException {
		if(arq.exists()){
			if(prop == null){
				FileInputStream file = new FileInputStream(arq);
				this.prop.load(file);
			}
		}else{
			throw new NegocioException("Arquivo de configuração do NFe não foi encontrado!");
		}
	}
}
