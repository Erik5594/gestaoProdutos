package com.github.erik5594.entidades;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.github.erik5594.util.Utils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Entity
public @Data class Cliente extends Pessoa {

	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy="cliente", fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Veiculo> veiculos = new ArrayList<>();
	@OneToMany(mappedBy="cliente", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<EnderecoCliente> endereco = new ArrayList<>();
	@OneToMany(mappedBy="cliente", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<TelefoneCliente> telefones = new ArrayList<>();
	@OneToMany(mappedBy="cliente", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<EmailCliente> emails = new ArrayList<>();
	
	@Transient
	public void removerTelefone(TelefoneCliente telefone){
		if(Utils.isNotNullOrEmpty(telefones)){
			telefones.remove(telefone);
		}
	}
	
	@Transient
	public void removerEndereco(EnderecoCliente endereco){
		if(Utils.isNotNullOrEmpty(this.endereco)){
			this.endereco.remove(endereco);
		}
	}
	
	@Transient
	public void removerVeiculo(Veiculo veiculo){
		if(Utils.isNotNullOrEmpty(veiculos)){
			veiculos.remove(veiculo);
		}
	}
	
	@Transient
	public void removerEmail(EmailCliente email){
		if(Utils.isNotNullOrEmpty(emails)){
			emails.remove(email);
		}
	}
	
	@Transient
	public void ajustarEnderecos(){
		if(Utils.isNotNullOrEmpty(this.endereco)){
			for(EnderecoCliente endere : this.endereco){
				if(!(endere.isCepGeral() || endere.isCepByFaixa())){
					
				}
			}
		}
	}
}
