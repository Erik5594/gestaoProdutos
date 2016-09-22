package com.kilowats.servicos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.kilowats.annotations.ValidarCidade;
import com.kilowats.entidades.Cidade;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.util.Utils;

public class ServicosImportadorMunicipios implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject @ValidarCidade
	private IValidacaoCadastro validador;
	@Inject
	private ServicosCidade servicosCidade;
	
	public List<Cidade> lerArquivoCsvCidade(BufferedReader linhasTexto) throws IOException{
		List<Cidade> cidades = new ArrayList<>();
		String linha = linhasTexto.readLine();
		while(linha != null){
			String[] dadosCidade = linha.split(";");
			Cidade cidade = new Cidade();
			cidade.setIdCidade(new Long(dadosCidade[0]));
			cidade.setNomeCidade(Utils.retornaStringNaoNull(dadosCidade[1]));
			cidade.setUf(Utils.retornaStringNaoNull(dadosCidade[2]));
			cidade.setCepInicial(new Long(dadosCidade[3]));
			cidade.setCepFinal(new Long(dadosCidade[4]));
			if(servicosCidade.cidadeIsValido(cidade, null, false)){
				cidades.add(cidade);
			}
			linha = linhasTexto.readLine();
		}
		return cidades;
	}
	
	public void guardarCidades(List<Cidade> cidades, String titulo){
		servicosCidade.saveListaCidade(cidades);
	}
	
}
