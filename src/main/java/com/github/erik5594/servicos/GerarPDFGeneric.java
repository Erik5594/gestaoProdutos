package com.github.erik5594.servicos;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import com.github.erik5594.exceptions.NegocioException;
import com.github.erik5594.interfaces.GerarPDF;

public class GerarPDFGeneric implements GerarPDF{
	
	protected String caminhoJasper;
	protected Map<String, Object> parametros;
	protected HttpServletResponse response;
	protected String nomeArquivoSaida;
	
	public GerarPDFGeneric(String caminhoJasper, Map<String, Object> parametros,
			HttpServletResponse response, String nomeArquivoSaida) {
		super();
		this.caminhoJasper = caminhoJasper;
		this.parametros = parametros;
		this.response = response;
		this.nomeArquivoSaida = nomeArquivoSaida;
	}

	@Override
	public void gerarPDF() throws JRException, IOException {
		throw new NegocioException("Não foi implementado geração de arquivo PDF nesta classe, usar as classe que extende essa!");
	}
}
