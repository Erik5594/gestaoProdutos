package com.github.erik5594.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.erik5594.entidades.Desconto;
import com.github.erik5594.entidades.Produto;
import com.github.erik5594.servicos.ServicosDesconto;
import com.github.erik5594.servicos.ServicosProduto;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

@Named
@ViewScoped
public @Data class VinculoDescontoProdutoControlador implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<Desconto> descontos = new ArrayList<>();
	@Inject
	private Desconto desconto;
	private List<Produto> produtos = new ArrayList<>();
	@Inject
	private ServicosDesconto servicosDesconto;
	@Inject
	private ServicosProduto servicosProduto;
	
	private static Log log = LogFactory.getLog(VinculoDescontoProdutoControlador.class);
	
	private String TITULO = "Cadastro de Desconto: ";
	private final String ERRO_INTERNO = "Erro interno: erro interno contate a administração do sistema!";
	
	public void init(){
		try{
			produtos = servicosProduto.listarTodosProdutos();
			descontos = servicosDesconto.listarTodosDescontos();
		}catch(Exception ex){
			log.error(TITULO, ex);
		}
	}
	
	public void salvar(){
		try{
			if(Utils.isNotNullOrEmpty(desconto.getProdutos())){
				desconto = servicosDesconto.persistirDesconto(desconto);
				if(Utils.isNotNullOrEmpty(desconto.getId())){
					FacesUtils.sendMensagemOk(TITULO, String.format("Desconto [%s] cadastrado com sucesso!", desconto.getNome()));
				}
			}else{
				FacesUtils.sendMensagemError(TITULO, String.format("Desconto [%s] sem produtos!", desconto.getNome()));
			}
		}catch(Exception ex){
			FacesUtils.sendMensagemError(TITULO, ERRO_INTERNO);
			log.error(TITULO, ex);
		}
		
	}
}
