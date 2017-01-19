package com.github.erik5594.importacao.manual.interfaces.implementacoes;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.UploadedFile;

import com.github.erik5594.entidades.Cidade;
import com.github.erik5594.importacao.interfaces.implementacoes.ArquivoImportacaoAbstrato;
import com.github.erik5594.servicos.ServicosCidade;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.cdi.CDIServiceLocator;

public class ImportacaoMunicipiosArquivoDetran extends ArquivoImportacaoAbstrato {
	
	private ServicosCidade servicosCidade;

	public ImportacaoMunicipiosArquivoDetran(UploadedFile arquivo) {
		super(arquivo);
		servicosCidade = CDIServiceLocator.getBean(ServicosCidade.class);
	}

	@Override
	public List<Cidade> getListaDeObjetoDoArquivo(String separador)
			throws IOException {
		return manipularLinhasArquivo(this.obterBufferReader(), separador);
	}

	@Override
	public List<Cidade> manipularLinhasArquivo(BufferedReader linhasArquivo,
			String separador) throws IOException {
		List<Cidade> cidades = new ArrayList<>();
		String linha = linhasArquivo.readLine();
		while(linha != null){
			Cidade cidade = (Cidade) obterObjeto(linha.split(separador));
			cidades.add(cidade);
			linha = linhasArquivo.readLine();
		}
		return cidades;
	}

	@Override
	public Object obterObjeto(String[] vetorObjeto) {
		Cidade cidade = new Cidade();
		cidade.setIdCidade(new Long(vetorObjeto[0]));
		cidade.setNomeCidade(Utils.retornaStringNaoNull(vetorObjeto[1]));
		cidade.setUf(Utils.retornaStringNaoNull(vetorObjeto[2]));
		cidade.setCepInicial(new Long(vetorObjeto[3]));
		cidade.setCepFinal(new Long(vetorObjeto[4]));
		if(!servicosCidade.cidadeIsValido(cidade, null, false)){
			return null;
		}
		return cidade;
	}

}
