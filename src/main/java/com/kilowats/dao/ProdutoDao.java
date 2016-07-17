package com.kilowats.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.kilowats.annotations.CadastrarProduto;
import com.kilowats.entidades.Ean;
import com.kilowats.entidades.Produto;
import com.kilowats.interfaces.IPersistirBancoDados;

@CadastrarProduto
public class ProdutoDao implements IPersistirBancoDados{

	@Override
	public boolean salvar(Object obj) {
		if (obj instanceof Produto) {
			Produto produto = (Produto) obj;
			try {
				File arquivo = new File("c:\\teste\\Produto");
				if (!arquivo.exists()) {
					arquivo.mkdirs();
				}
				FileWriter arq = new FileWriter(arquivo + "\\teste.txt", true);
				PrintWriter gravarArquivo = new PrintWriter(arq);
				gravarArquivo.print("+--------------------------------------------+\n");
				gravarArquivo.print(perssistirProduto(produto));
				gravarArquivo.print("+--------------------------------------------+\n");
				gravarArquivo.print(persistirEans(produto.getEans()));
				gravarArquivo.print("+--------------------------------------------+\n");
				arq.close();
				System.out.printf("\nFornecedor gravado com sucesso! \""
						+ arquivo.getPath() + "\\teste.txt\".\n");
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
	
	private String perssistirProduto(Produto produto){
		StringBuffer texto = new StringBuffer();
		if(produto != null){
			texto.append("************Inicio Persistir Produto************\n");
			texto.append("Cód.: "+produto.getCodProduto() == null ? "\n": produto.getCodProduto()+"\n");
			texto.append("Nome: "+produto.getNomeProduto() == null ? "\n": produto.getNomeProduto()+"\n");
			texto.append("Qtde.: "+produto.getQuantidade()+"\n");
			texto.append("Tipo: "+produto.getTipoUnidade().toString()+"\n");
			texto.append("Valor: R$ "+produto.getValor()+"\n");
			texto.append("************Fim Persistir Produto************\n");
		}
		return texto.toString();
	}
	
	private String persistirEans(List<Ean> eans){
		StringBuffer texto = new StringBuffer();
			if(eans != null && !eans.isEmpty()){
				texto.append("************Inicio Persistir EAN************\n");
				for(Ean ean : eans){
					texto.append("Cód.: "+ean.getCodBarras() == null ? "\n": ean.getCodBarras()+"\n");
					texto.append("***Próximo***\n");
				}
				texto.append("************Fim Persistir EAN************\n");
			}
			return texto.toString();
	}

}
