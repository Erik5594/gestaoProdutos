package com.github.erik5594.importacao.manual.controladores;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import org.primefaces.event.FileUploadEvent;

import com.github.erik5594.entidades.Cidade;
import com.github.erik5594.importacao.interfaces.ArquivoImportacao;
import com.github.erik5594.importacao.manual.interfaces.implementacoes.ImportacaoMunicipiosArquivoDetran;
import com.github.erik5594.servicos.ServicosCidade;
import com.github.erik5594.util.jsf.FacesUtils;

@Named
@ViewScoped
public @Data class ImportadorMunicipiosControlador implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ServicosCidade servicosCidade;
	
	private final String titulo = "Importar Municípios: ";
	
	@SuppressWarnings("unchecked")
	public void uploadDeArquivo(FileUploadEvent event) {
		try {
			ArquivoImportacao importador = new ImportacaoMunicipiosArquivoDetran(event.getFile());
			servicosCidade.mergeListaCidade((List<Cidade>)importador.getListaDeObjetoDoArquivo(";"));
			FacesUtils.sendMensagemOk(titulo, "Arquivo importado com sucesso!");
		} catch (IOException e) {
			FacesUtils.sendMensagemError(titulo, "Ocorreu um erro ao importar o arquivo!");
			e.printStackTrace();
		}
    }
}
