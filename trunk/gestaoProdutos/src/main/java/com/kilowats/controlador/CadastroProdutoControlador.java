package com.kilowats.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import com.kilowats.entidades.Ean;
import com.kilowats.entidades.Produto;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.validadores.ValidacaoCadastroEan;

@ManagedBean
public class CadastroProdutoControlador implements Serializable{
	private static final long serialVersionUID = 1L;
	private Produto produto = new Produto();
	private Ean ean = new Ean();
	private List<Ean> eans = new ArrayList<>();
	
	public void adicionarEan(){
		IValidacaoCadastro validacao = new ValidacaoCadastroEan();
		if(this.eans == null || this.eans.isEmpty()){
			this.eans = new ArrayList<>();
		}
		if(validacao.validarCadastroComMensagem(this.ean).get(0).toUpperCase().equals("OK")){
			eans.add(this.ean);
		}
		ean = new Ean();
	}

	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public Ean getEan() {
		return ean;
	}
	public void setEan(Ean ean) {
		this.ean = ean;
	}
	public List<Ean> getEans() {
		return eans;
	}
	public void setEans(List<Ean> eans) {
		this.eans = eans;
	}
	
}
