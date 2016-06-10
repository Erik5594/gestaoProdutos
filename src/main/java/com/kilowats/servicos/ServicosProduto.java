package com.kilowats.servicos;

import java.util.List;

import com.kilowats.entidades.Ean;
import com.kilowats.entidades.Produto;
import com.kilowats.interfaces.IPersistirBancoDados;

public class ServicosProduto implements IPersistirBancoDados {

	@Override
	public boolean salvar(Object obj) {
		Produto produto = (Produto) obj;
		perssistirProduto(produto);
		persistirEans(produto.getEans());
		return true;
	}

	@Override
	public boolean alterar(Object obj) {
		return false;
	}

	@Override
	public boolean deletar(Object obj) {
		return false;
	}
	
	private boolean perssistirProduto(Produto produto){
		boolean retorno = false;
		if(produto != null){
			System.out.println("************Inicio Persistir Produto************");
			System.out.println("Cód.: "+produto.getCodProduto() == null ? "": produto.getCodProduto());
			System.out.println("Nome: "+produto.getNomeProduto() == null ? "": produto.getNomeProduto());
			System.out.println("Qtde.: "+produto.getQuantidade());
			System.out.println("Tipo: "+produto.getTipoUnidade().toString());
			System.out.println("Valor: R$ "+produto.getValor());
			System.out.println("************Fim Persistir Produto************");
		}
		return retorno;
	}
	
	private boolean persistirEans(List<Ean> eans){
			boolean retorno = false;
			if(eans != null && !eans.isEmpty()){
				System.out.println("************Inicio Persistir EAN************");
				for(Ean ean : eans){
					System.out.println("Cód.: "+ean.getCodBarras() == null ? "": ean.getCodBarras());
					System.out.println("***Próximo***");
				}
				System.out.println("************Fim Persistir EAN************");
			}
			return retorno;
	}

}
