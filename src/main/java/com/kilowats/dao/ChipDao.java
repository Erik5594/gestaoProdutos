package com.kilowats.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.kilowats.annotations.CadastrarChip;
import com.kilowats.entidades.Chip;
import com.kilowats.entidades.Rastreador;
import com.kilowats.interfaces.IPersistirBancoDados;

@CadastrarChip
public class ChipDao implements IPersistirBancoDados{

	@Override
	public boolean salvar(Object obj) {
		if(obj instanceof Chip){
			Chip chip = (Chip) obj;
			try {
				File arquivo = new File("c:\\teste\\Chip");
				if(!arquivo.exists()){
					arquivo.mkdirs();
				}
				FileWriter arq = new FileWriter(arquivo+"\\teste.txt", true);
				PrintWriter gravarArquivo = new PrintWriter(arq);
				gravarArquivo.print("+--------------------------------------------+\n\b");
				gravarArquivo.print(adicionarChip(chip)+"\n\b");
				gravarArquivo.print("+--------------------------------------------+\n\b");
				arq.close();
				System.out.printf("\n\bChip gravado com sucesso! \""+arquivo.getPath()+"\\teste.txt\".\n\b");
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}	

	@Override
	public boolean alterar(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletar(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private String adicionarChip(Chip chip){
		StringBuffer texto = new StringBuffer();
		texto.append("");
		texto.append("########## INICIO DADOS CHIP ##########\n\b");
		texto.append("Imei: "+ chip.getImei()+"\n\b");
		texto.append("DDD: "+ chip.getDdd()+"\n\b");
		texto.append("Número: "+ chip.getNumero()+"\n\b");
		texto.append("Operadora: "+chip.getOperadora().getOperadora()+"\n\b");
		texto.append("########## FIM DADOS CHIP ##########\n\b");
		texto.append("\n\b\n\b\n\b");
		if(chip.isRastreador()){
			texto.append(adicionaRastreador(chip.getRastreador()));
		}
		return texto.toString();
	}

	private Object adicionaRastreador(Rastreador rastreador) {
		StringBuffer texto = new StringBuffer();
		texto.append("");
		texto.append("########## INICIO DADOS RASTREADOR ##########\n\b");
		texto.append("Id Rastreador: "+ rastreador.getIdRastreador()+"\n\b");
		texto.append("########## FIM DADOS CHIP ##########\n\b");
		return texto.toString();
	}
	
}
