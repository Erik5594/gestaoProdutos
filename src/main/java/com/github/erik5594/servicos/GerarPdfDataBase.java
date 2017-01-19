package com.github.erik5594.servicos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.jdbc.Work;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class GerarPdfDataBase extends GerarPDFGeneric implements Work{
	
	private boolean relatorioGerado;
	private Connection con;
	
	public GerarPdfDataBase(String caminhoJasper, Map<String, Object> parametros,
			HttpServletResponse response, String nomeArquivoSaida) {
		super(caminhoJasper, parametros, response, nomeArquivoSaida);
	}
	
	@Override
	public void gerarPDF() throws JRException, IOException {
		InputStream relatorioStream = this.getClass().getResourceAsStream(caminhoJasper);
		JasperPrint print = JasperFillManager.fillReport(relatorioStream, parametros, con);
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

	public boolean isRelatorioGerado() {
		return relatorioGerado;
	}

	@Override
	public void execute(Connection con) throws SQLException {
		this.con = con;
		try {
			gerarPDF();
		} catch (JRException | IOException e) {
			throw new SQLException("Ocorreu um erro ao gerar o PDF. ["+caminhoJasper+"]");
		}
		con.commit();
		con.close();
	}
	
}
