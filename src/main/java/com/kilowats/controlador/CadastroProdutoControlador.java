package com.kilowats.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.kilowats.entidades.Ean;
import com.kilowats.entidades.Produto;
import com.kilowats.enuns.TipoProdutoUnidadeEnum;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.servicos.ServicosProduto;
import com.kilowats.validadores.ValidacaoCadastroEan;
import com.kilowats.validadores.ValidacaoCadastroProduto;

@ManagedBean
@ViewScoped
public class CadastroProdutoControlador implements Serializable{
	private static final long serialVersionUID = 1L;
	private Produto produto = new Produto();
	private Ean ean = new Ean();
	private List<Ean> eans = new ArrayList<>();
	
	private void iniciarVariaveis(){
		this.produto = new Produto();
		this.ean = new Ean();
		this.eans = new ArrayList<>();
	}
	
	public void adicionarEan(){
		IValidacaoCadastro validacao = new ValidacaoCadastroEan();
		if(this.eans == null || this.eans.isEmpty()){
			this.eans = new ArrayList<>();
		}
		List<String> mensagens = validacao.validarCadastroComMensagem(this.ean);
		if(mensagens.size() == 1 && mensagens.get(0).toUpperCase().equals("OK")){
			eans.add(this.ean);
		}else{
			FacesContext context = FacesContext.getCurrentInstance();
			for(String mensagem : mensagens){
				context.addMessage("mensageError", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validação Ean: "+ mensagem, null));
			}
		}
		ean = new Ean();
	}
	
	public void salvar(){
		if(validacoes()){
			completarDadosProduto();
			ServicosProduto servicosProduto = new ServicosProduto();
			servicosProduto.salvar(this.produto);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("growMensage", new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro Produto: Produto cadastrado com sucesso!", null));
		}else{
			return;
		}
		iniciarVariaveis();
	}
	
	private void completarDadosProduto() {
		if(this.eans != null && !this.eans.isEmpty()){
			this.produto.setEans(this.eans);
		}
	}

	private boolean validacoes(){
		return produtoIsValido();
	}

	private boolean produtoIsValido() {
		FacesContext context = FacesContext.getCurrentInstance();
		IValidacaoCadastro validacaoProduto = new ValidacaoCadastroProduto();
		List<String> mensagens = validacaoProduto.validarCadastroComMensagem(this.produto);
		if(mensagens.size() == 1 && mensagens.get(0).toUpperCase().equals("OK")){
			return true;
		}
		for(String mensagem : mensagens){
			context.addMessage("mensageError", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validação Produto: "+ mensagem, null));
		}
		return false;
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
	public TipoProdutoUnidadeEnum[] getTipoUnidadeProduto() {
		return TipoProdutoUnidadeEnum.values();
	}
	
}
