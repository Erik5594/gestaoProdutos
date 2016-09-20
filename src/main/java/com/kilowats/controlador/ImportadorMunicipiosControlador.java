package com.kilowats.controlador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.kilowats.entidades.Cidade;
import com.kilowats.servicos.ServicosImportadorMunicipios;
import com.kilowats.util.jsf.FacesUtils;

@Named
@ViewScoped
public @Data class ImportadorMunicipiosControlador{
	private final String titulo = "Importar Municípios: ";
	@Inject
	private ServicosImportadorMunicipios servicosImportadorMunicipios;
	
	public void uploadDeArquivo(FileUploadEvent event) {
		List<Cidade> cidades = new ArrayList<>();
		UploadedFile uploadedFile = event.getFile();
		cidades = extrairCidadesDoArquivo(cidades, uploadedFile);
		servicosImportadorMunicipios.guardarCidades(cidades, titulo);
        FacesUtils.sendMensagemOk(titulo, "Arquivo importado com sucesso!");
    }

	private List<Cidade> extrairCidadesDoArquivo(List<Cidade> cidades,
			UploadedFile uploadedFile) {
		try {
			InputStream arqByte = uploadedFile.getInputstream();
			InputStreamReader arqChar = new InputStreamReader(arqByte);
			cidades = servicosImportadorMunicipios.lerArquivoCsvCidade(new BufferedReader(arqChar));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cidades;
	}

}
