package com.kilowats.interfaces;

import java.io.IOException;

import net.sf.jasperreports.engine.JRException;

public interface GerarPDF {
	public void gerarPDF() throws JRException, IOException;
}
