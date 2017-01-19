package com.github.erik5594.importacao.manual.interfaces.implementacoes;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.UploadedFile;

import com.github.erik5594.entidades.Cep;
import com.github.erik5594.importacao.interfaces.implementacoes.ArquivoImportacaoAbstrato;
import com.github.erik5594.servicos.ServicosCep;
import com.github.erik5594.servicos.ServicosCidade;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.cdi.CDIServiceLocator;

public class ImportacaoCepsArquivoDetran extends ArquivoImportacaoAbstrato {

	private ServicosCidade servicosCidade;
	private ServicosCep servicosCep;
	
	public ImportacaoCepsArquivoDetran(UploadedFile arquivo) {
		super(arquivo);
		servicosCep = CDIServiceLocator.getBean(ServicosCep.class);
		servicosCidade = CDIServiceLocator.getBean(ServicosCidade.class);
	}

	@Override
	public List<Cep> getListaDeObjetoDoArquivo(String separador)
			throws IOException {
		return manipularLinhasArquivo(this.obterBufferReader(), separador);
	}

	@Override
	public List<Cep> manipularLinhasArquivo(BufferedReader linhasArquivo, String separador) throws IOException {
		List<Cep> ceps = new ArrayList<>();
		String linha = linhasArquivo.readLine();
		while(linha != null){
			Cep cep = (Cep) obterObjeto(linha.split(separador));
			if(Utils.isNotNullOrEmpty(cep)){
				ceps.add(cep);
			}
			linha = linhasArquivo.readLine();
		}
		return ceps;
	}

	@Override
	public Object obterObjeto(String[] vetorObjeto) {
		Cep cep = new Cep();
		cep.setCidade(servicosCidade.pesquisarById(new Long(vetorObjeto[0])));
		cep.setRua(Utils.retornaStringNaoNull(vetorObjeto[1]));
		cep.setBairro(Utils.retornaStringNaoNull(vetorObjeto[2]));
		cep.setCep(new Long(vetorObjeto[4]));
		if(servicosCep.cepIsValido(cep, null, false)){
			return cep;
		}
		return null;
	}

}
