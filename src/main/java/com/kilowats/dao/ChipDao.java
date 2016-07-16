package com.kilowats.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.kilowats.entidades.Chip;
import com.kilowats.entidades.Rastreador;
import com.kilowats.interfaces.IPersistirBancoDados;

public class ChipDao implements IPersistirBancoDados{

	@Override
	public boolean salvar(Object obj) {
		if(obj instanceof Chip){
			Chip chip = (Chip) obj;
			try {
				File arquivo = new File("c:\\testeChip");
				if(!arquivo.exists()){
					arquivo.mkdirs();
				}
				FileWriter arq = new FileWriter(arquivo+"\\teste.txt", true);
				PrintWriter gravarArquivo = new PrintWriter(arq);
				gravarArquivo.print("+--------------------------------------------+\n");
				gravarArquivo.print(adicionarChip(chip)+"\n");
				gravarArquivo.print("+--------------------------------------------+\n");
				arq.close();
				System.out.printf("\nChip gravado com sucesso! \""+arquivo.getPath()+"\\teste.txt\".\n");
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
		texto.append("########## INICIO DADOS CHIP ##########\n");
		texto.append("Imei: "+ chip.getImei()+"\n");
		texto.append("DDD: "+ chip.getDdd()+"\n");
		texto.append("Número: "+ chip.getNumero()+"\n");
		texto.append("Operadora: "+chip.getOperadora().getOperadora()+"\n");
		texto.append("########## FIM DADOS CHIP ##########\n");
		texto.append("\n\n\n");
		if(chip.isRastreador()){
			texto.append(adicionaRastreador(chip.getRastreador()));
		}
		return texto.toString();
	}

	private Object adicionaRastreador(Rastreador rastreador) {
		StringBuffer texto = new StringBuffer();
		texto.append("");
		texto.append("########## INICIO DADOS RASTREADOR ##########\n");
		texto.append("Id Rastreador: "+ rastreador.getIdRastreador()+"\n");
		texto.append("########## FIM DADOS CHIP ##########\n");
		return texto.toString();
	}
	
}
