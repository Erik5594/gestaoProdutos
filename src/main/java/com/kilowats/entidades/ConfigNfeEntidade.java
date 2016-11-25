package com.kilowats.entidades;

import java.io.Serializable;

import lombok.Data;

import com.fincatto.nfe310.classes.NFAmbiente;
import com.fincatto.nfe310.classes.NFTipoEmissao;

public @Data class ConfigNfeEntidade implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String senhaCertificado;
	private String senhaCadeiaCertificado;
	private NFAmbiente ambiente;
	private NFTipoEmissao tipoEmissaoNfe;
	private String caminhoCertificado;
	private String nomeCertificado;
	private String caminhoCadeiaCertificado;
	private String nomeCadeiaCertificado;

}
