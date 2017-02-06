package com.github.erik5594.controlador;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.erik5594.entidades.Desconto;
import com.github.erik5594.servicos.ServicosDesconto;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

@Named
@ViewScoped
public @Data class CadastroTabelaDescontoControlador implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Desconto desconto;
	@Inject
	private ServicosDesconto servicosDesconto;
	
	private static Log log = LogFactory.getLog(CadastroTabelaDescontoControlador.class);
	
	private String TITULO = "Cadastro de Desconto: ";
	private final String ERRO_INTERNO = "Erro interno: erro interno contate a administração do sistema!";
	
	public void salvar(){
		try{
			if(servicosDesconto.descontoIsValido(desconto, TITULO, true)){
				desconto = servicosDesconto.persistirDesconto(desconto);
				if(Utils.isNotNullOrEmpty(desconto.getId())){
					FacesUtils.sendMensagemOk(TITULO, String.format("Desconto [%s] cadastrado com sucesso!", desconto.getNome()));
				}
			}
		}catch(Exception ex){
			FacesUtils.sendMensagemError(TITULO, ERRO_INTERNO);
			log.error(TITULO, ex);
		}
		
	}
}
