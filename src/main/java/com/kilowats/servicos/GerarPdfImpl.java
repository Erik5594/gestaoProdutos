package com.kilowats.servicos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRExporterContext;

import com.kilowats.interfaces.GerarPDF;

public class GerarPdfImpl implements GerarPDF {
	
	private String caminhoJasper;
	private Map<String, Object> parametros;
	private List<Object> listaDados;
	private HttpServletResponse response;
	private String nomeArquivoSaida;
	
	private boolean relatorioGerado;
	
	public GerarPdfImpl(String caminhoJasper, Map<String, Object> parametros,
			List<Object> listaDados, HttpServletResponse response,
			String nomeArquivoSaida) {
		super();
		this.caminhoJasper = caminhoJasper;
		this.parametros = parametros;
		this.listaDados = listaDados;
		this.response = response;
		this.nomeArquivoSaida = nomeArquivoSaida;
	}
	
	@Override
	public void gerarPDFDadosObjeto() throws JRException, IOException {
		InputStream relatorioStream = this.getClass().getResourceAsStream(caminhoJasper);
		JRBeanCollectionDataSource dados = new JRBeanCollectionDataSource(listaDados);
		JasperPrint print = JasperFillManager.fillReport(relatorioStream, parametros, dados);
		relatorioGerado = print.getPages().size() > 0;
		
		if(relatorioGerado){
			response.reset();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=\""+nomeArquivoSaida+".pdf\"");
			
			byte[] pdf = JasperExportManager.exportReportToPdf(print);
			OutputStream outPut = response.getOutputStream();
			outPut.write(pdf);
			outPut.close();
		}
	}

	@Override
	public void gerarPDFDadosDataBase(Connection con) throws JRException,
			IOException {
		// TODO Auto-generated method stub
		
	}
}
