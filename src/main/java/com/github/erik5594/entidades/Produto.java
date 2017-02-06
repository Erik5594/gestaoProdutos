package com.github.erik5594.entidades;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Data;

import com.github.erik5594.enuns.TipoProdutoUnidadeEnum;
import com.github.erik5594.util.Utils;

@Entity
@Table(name="produto")
public @Data class Produto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull @Column(name="cod_produto", length=15, nullable=false, unique=true)
	private String codProduto;
	
	@NotNull @Column(name="nome_produto", length=120, nullable=false)
	private String nomeProduto;
	
	@NotNull @Enumerated(EnumType.ORDINAL) @Column(name="tipo_unidade")
	private TipoProdutoUnidadeEnum tipoUnidade;
	
	@Enumerated(EnumType.ORDINAL) @Column(name="tipo_unidade_tributavel")
	private TipoProdutoUnidadeEnum tipoUnidadeTributavel;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_ultima_atualizacao", nullable=false, columnDefinition="TIMESTAMP WITH TIME ZONE")
	private Date dataUltimaAtualizacao;
	
	@OneToMany(mappedBy="produto", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Ean> eans;
	
	@Inject @OneToOne(mappedBy = "produto", cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval = true)
	private ValoresProduto valoresProdutos;
	
	@Inject @OneToOne(mappedBy = "produto", cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval = true)
	private EstoqueProduto estoqueProduto;
	
	@Column(name="ativo", nullable=false, length=1, columnDefinition = "boolean default 't'")
	private boolean ativo;
	
	@ManyToMany(mappedBy="produtos")
	private List<Desconto> descontos;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Transient
	public void removerEan(Ean ean){
		if(Utils.isNotNullOrEmpty(eans)){
			eans.remove(ean);
		}
	}
	
	@Transient
	public boolean isTemDesconto(){
		boolean retorno = false;
		if(this.descontos != null && !this.descontos.isEmpty()){
			for(Desconto desconto : this.descontos){
				if(descontoValido(desconto)){
					retorno = true;
					break;
				}
			}
		}
		return retorno;
	}
	
	@Transient
	public BigDecimal getMaiorMenorDescontoPossivel(){
		BigDecimal descontoMin = BigDecimal.ZERO;
		if(isTemDesconto()){
			for(Desconto desconto : this.descontos){
				if(descontoValido(desconto)){
					if(desconto.getPercentualMinimoDesconto().compareTo(descontoMin) > 0){
						descontoMin = desconto.getPercentualMinimoDesconto();
					}
				}
			}
		}
		return descontoMin;
	}
	
	@Transient
	private boolean descontoValido(Desconto desconto) {
		Calendar dataHoraFimDesconto = Calendar.getInstance();
		dataHoraFimDesconto.setTime(desconto.getDataFim());
		dataHoraFimDesconto.set(Calendar.HOUR, 23);
		dataHoraFimDesconto.set(Calendar.MINUTE, 59);
		dataHoraFimDesconto.set(Calendar.SECOND, 59);
		
		return !dataHoraFimDesconto.before(new Date());
	}
	
	@Transient
	public BigDecimal getMaiorMaiorDescontoPossivel(){
		BigDecimal descontoMax = BigDecimal.ZERO;
		if(isTemDesconto()){
			for(Desconto desconto : this.descontos){
				if(descontoValido(desconto)){
					if(desconto.getPercentualMaximoDesconto().compareTo(descontoMax) > 0){
						descontoMax = desconto.getPercentualMaximoDesconto();
					}
				}
			}
		}
		return descontoMax;
	}
}
