package com.kilowats.controlador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.kilowats.entidades.Cep;
import com.kilowats.servicos.ServicosImportadorCeps;
import com.kilowats.util.jsf.FacesUtils;

@Named
@ViewScoped
public @Data class ImportadorCepControlador implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ServicosImportadorCeps servicosImportadorCeps;
	
	private final String titulo = "Importar Municípios: ";
	
	public void uploadDeArquivo(FileUploadEvent event) {
		List<Cep> ceps = new ArrayList<>();
		UploadedFile uploadedFile = event.getFile();
		ceps = extrairCepsDoArquivo(ceps, uploadedFile);
		servicosImportadorCeps.guardarCeps(ceps);
        FacesUtils.sendMensagemOk(titulo, "Arquivo importado com sucesso!");
    }

	private List<Cep> extrairCepsDoArquivo(List<Cep> ceps,
			UploadedFile uploadedFile) {
		try {
			InputStream arqByte = uploadedFile.getInputstream();
			InputStreamReader arqChar = new InputStreamReader(arqByte);
			ceps = servicosImportadorCeps.lerArquivoCsvCep(new BufferedReader(arqChar));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ceps;
	}

}
