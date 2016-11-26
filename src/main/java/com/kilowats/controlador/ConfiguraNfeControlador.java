package com.kilowats.controlador;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import com.fincatto.nfe310.classes.NFAmbiente;
import com.fincatto.nfe310.classes.NFTipoEmissao;
import com.kilowats.entidades.ConfigNfeEntidade;
import com.kilowats.exceptions.NegocioException;
import com.kilowats.util.jsf.FacesUtils;
import com.kilowats.util.nfe.ConfiguraNfe;

@Named
@ViewScoped
public @Data class ConfiguraNfeControlador implements Serializable{

	private static final long serialVersionUID = 1L;
	@Inject
	private ConfigNfeEntidade configNfe;
	private String ambiente;
	
	public void gravarConfiguracao(){
		ConfiguraNfe config = new ConfiguraNfe(false);
		try {
			completarConfigNfe();
			config.criarConfiguracao(configNfe);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void lerConfiguracao(){
		try {
			ConfiguraNfe config = new ConfiguraNfe();
			this.configNfe = config.getConfigNfeEntidade();
			ambiente = configNfe.getAmbiente().getCodigo();
		} catch (NegocioException e) {
			FacesUtils.sendMensagemError("Configuração NF-e: ", e.getMessage());
		}
	}

	private void completarConfigNfe() {
		if("1".equals(ambiente)){
			configNfe.setAmbiente(NFAmbiente.PRODUCAO);
		}else{
			configNfe.setAmbiente(NFAmbiente.HOMOLOGACAO);
		}
		configNfe.setCaminhoCadeiaCertificado("configurar_caminho");
		configNfe.setCaminhoCertificado("configurar_caminho");
	}
	
	public NFAmbiente[] getAmbientes(){
		return NFAmbiente.values();
	}
	
	public NFTipoEmissao[] getTiposEmissao(){
		return NFTipoEmissao.values();
	}

}
