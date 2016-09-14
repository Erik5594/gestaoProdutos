package com.kilowats.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

import com.kilowats.enuns.TipoPessoa;

@Entity @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public @Data class Pessoa implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty
	private String nome;
	@NotEmpty @Column(name="cpf_cgc", length=14, unique=true)
	private String cgcCpf;
	@Column(name="tipo_pessoa", length=1)
	private TipoPessoa fisicaJuridica;//0fisica; 1juridica
	@NotNull @Column(name="status", length=1, nullable=false)
	private int status;//1ativo;0inativo
	@NotEmpty
	@OneToMany
	@JoinTable(name = "pessoa_endereco", joinColumns = @JoinColumn(name="pessoa_id"),
			inverseJoinColumns = @JoinColumn(name = "endereco_id"),
			foreignKey = @ForeignKey(name = "fk_pessoa_id"),
			inverseForeignKey = @ForeignKey(name = "fk_endereco_id"))
	private List<Endereco> endereco;
	@OneToMany(mappedBy="cliente")
	private List<Telefone> telefones;
	@OneToMany(mappedBy="pessoa")
	private List<Email> emails;
}
