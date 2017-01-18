package com.github.erik5594.importacao.intertrack.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.github.erik5594.importacao.enuns.StatusImportacao;
import com.kilowats.enuns.TipoPessoa;
import com.kilowats.util.Utils;

@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name="cliente_importacao_intertrack")
public @Data class ClienteImportacaoIntertrack implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	@Column(name="cpf_cgc", length=14)
	private String cgcCpf;
	@Column(name="tipo_pessoa", length=1, nullable=false)
	private TipoPessoa tipo;
	@Column(name="status", length=1, nullable=false)
	private StatusImportacao status;
	@OneToMany(mappedBy="clienteImportacaoIntertrack", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<EnderecoClienteImportacaoIntertrack> enderecoClienteImportacaoIntertrack;
	@OneToMany(mappedBy="clienteImportacaoIntertrack", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<EmailClienteImportacaoIntertrack> emailClienteImportacaoIntertrack;
	@OneToMany(mappedBy="clienteImportacaoIntertrack", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<TelefoneClienteImportacaoIntertrack> telefoneClienteImportacaoIntertrack;
	
	@Transient
	public String getCgcCpfFormatado(){
		return Utils.formataCPFCGC(cgcCpf);
	}
}
