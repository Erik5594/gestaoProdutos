package com.kilowats.servicos;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import com.fincatto.nfe310.NFeConfig;
import com.fincatto.nfe310.classes.NFModelo;
import com.fincatto.nfe310.classes.NFUnidadeFederativa;
import com.fincatto.nfe310.classes.statusservico.consulta.NFStatusServicoConsultaRetorno;
import com.fincatto.nfe310.webservices.WSFacade;
import com.kilowats.interfaces.IServicosNfe;

public class ServicosNfeImpl implements IServicosNfe {

	@Override
	public boolean nfeAtivo(NFeConfig config) throws KeyManagementException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, Exception {
		NFStatusServicoConsultaRetorno retorno = new WSFacade(config).consultaStatus(NFUnidadeFederativa.GO, NFModelo.NFE);
		System.out.println(retorno.getStatus());
		System.out.println(retorno.getMotivo());
		if("107".equals(retorno.getStatus())){
			return true;
		}
		return false;
	}

}
