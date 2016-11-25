package com.kilowats.interfaces;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import com.fincatto.nfe310.NFeConfig;

public interface IServicosNfe {
	
	boolean nfeAtivo(NFeConfig config) throws KeyManagementException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, Exception;

}
