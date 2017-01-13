package com.github.erik5594.importacao.interfaces.implementacoes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.primefaces.model.UploadedFile;

import com.github.erik5594.importacao.interfaces.ArquivoImportacao;

public abstract class ArquivoImportacaoAbstrato implements ArquivoImportacao{
	private final UploadedFile arquivo;

	public ArquivoImportacaoAbstrato(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}
	
	public BufferedReader obterBufferReader() throws IOException{
		InputStream inputStream = arquivo.getInputstream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		return new BufferedReader(inputStreamReader);
	}
	
	public abstract List<?> manipularLinhasArquivo(BufferedReader linhasArquivo, String separador)  throws IOException;
	
	public abstract Object obterObjeto(String[] vetorObjeto);
}
