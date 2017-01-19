package com.github.erik5594.servicos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class GerarPdfDadosObjeto extends GerarPDFGeneric{
	
	private List<Object> listaDados;
	private boolean relatorioGerado;
	
	public GerarPdfDadosObjeto(String caminhoJasper, Map<String, Object> parametros,
			List<Object> listaDados, HttpServletResponse response,
			String nomeArquivoSaida) {
		super(caminhoJasper, parametros, response, nomeArquivoSaida);
		this.listaDados = listaDados;
	}
	
	@Override
	public void gerarPDF() throws JRException, IOException {
		File arq = new File(caminhoJasper);
		FileInputStream fin = new FileInputStream(arq);
		JRBeanCollectionDataSource dados = new JRBeanCollectionDataSource(listaDados);
		JasperPrint print = JasperFillManager.fillReport(fin, parametros, dados);
		relatorioGerado = print.getPages().size() > 0;
		
		if(relatorioGerado && !response.isCommitted()){
			response.reset();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=\""+nomeArquivoSaida+".pdf\"");
			
			byte[] pdf = JasperExportManager.exportReportToPdf(print);
			OutputStream outPut = response.getOutputStream();
			outPut.write(pdf);
			outPut.close();
		}
	}

	public boolean isRelatorioGerado() {
		return relatorioGerado;
	}
	
}
