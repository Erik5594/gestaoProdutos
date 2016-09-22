package com.kilowats.servicos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.kilowats.annotations.ValidarCep;
import com.kilowats.entidades.Cep;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.util.Utils;

public class ServicosImportadorCeps implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject @ValidarCep
	private IValidacaoCadastro validador;
	@Inject
	private ServicosCep servicosCep;
	@Inject
	private ServicosCidade servicosCidade;
	
	public List<Cep> lerArquivoCsvCep(BufferedReader linhasTexto) throws IOException{
		List<Cep> ceps = new ArrayList<>();
		String linha = linhasTexto.readLine();
		while(linha != null){
			String[] dadosCep = linha.split(";");
			Cep cep = new Cep();
			cep.setCidade(servicosCidade.pesquisarById(new Long(dadosCep[0])));
			cep.setRua(Utils.retornaStringNaoNull(dadosCep[1]));
			cep.setBairro(Utils.retornaStringNaoNull(dadosCep[2]));
			cep.setCep(new Long(dadosCep[4]));
			if(servicosCep.cepIsValido(cep, null, false)){
				ceps.add(cep);
			}
			linha = linhasTexto.readLine();
		}
		return ceps;
	}
	
	public void guardarCeps(List<Cep> ceps){
		servicosCep.saveListaCep(ceps);
	}

}
