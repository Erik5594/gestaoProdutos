package com.github.erik5594.importacao.intertrack.controladores;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import org.primefaces.event.FileUploadEvent;

import com.github.erik5594.importacao.interfaces.ArquivoImportacao;
import com.github.erik5594.importacao.intertrack.entidades.ClienteImportacaoIntertrack;
import com.github.erik5594.importacao.intertrack.interfaces.implementacoes.ImportacaoClientesIntertrack;
import com.github.erik5594.servicos.ServicosCliente;
import com.github.erik5594.util.jsf.FacesUtils;

@Named
@ViewScoped
public @Data class ImportadorClienteControlador implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private final String titulo = "Importar Clientes: ";
	@Inject
	private ServicosCliente servicosClientes;
	
	@SuppressWarnings("unchecked")
	public void uploadDeArquivo(FileUploadEvent event) {
		try {
			ArquivoImportacao importador = new ImportacaoClientesIntertrack(event.getFile());
			salvarClientes((List<ClienteImportacaoIntertrack>) importador.getListaDeObjetoDoArquivo(","));
	        FacesUtils.sendMensagemOk(titulo, "Arquivo importado com sucesso!");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	private void salvarClientes(List<ClienteImportacaoIntertrack> clientes) {
		for(ClienteImportacaoIntertrack cliente : clientes){
			servicosClientes.persistirClienteImportacao(cliente);
		}
	}

}
