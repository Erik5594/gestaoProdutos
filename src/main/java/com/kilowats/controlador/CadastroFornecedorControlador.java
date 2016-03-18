package com.kilowats.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.kilowats.entidades.Empresa;
import com.kilowats.entidades.Telefone;
import com.kilowats.enuns.TipoTelefoneEnum;
import com.kilowats.utils.Utils;

@ManagedBean
@ViewScoped
public class CadastroFornecedorControlador implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Empresa empresa = new Empresa(); 
	
	public String carregaMascaraCnpjOuCpfPrimefaces(){
		return Utils.mascarPrimefacesCnpjOuCpf(empresa.getFisicaJuridica());
	}

}
