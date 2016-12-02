package com.kilowats.interfaces;

import java.io.IOException;
import java.sql.Connection;

import net.sf.jasperreports.engine.JRException;

public interface GerarPDF {
	public void gerarPDFDadosObjeto() throws JRException, IOException;
	public void gerarPDFDadosDataBase(Connection con) throws JRException, IOException;
}
