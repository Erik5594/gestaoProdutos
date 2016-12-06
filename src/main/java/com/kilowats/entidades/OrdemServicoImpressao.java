package com.kilowats.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

import com.kilowats.enuns.FormaPagamento;
import com.kilowats.enuns.StatusOrdemServico;

public @Data class OrdemServicoImpressao implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Cliente cliente;
	private Endereco endereco;
	private Telefone telefone;
	private Veiculo veiculo;
	private List<ItemOrdemServico> itens = new ArrayList<>();
	private Date dataOrdemServico;
	private Date dataAgendado;
	private Date dataExecutado;
	private FormaPagamento formaPagamento;
	private StatusOrdemServico statusOrdemServico;
	private String observacao;
	private BigDecimal valorTotalDesconto = BigDecimal.ZERO;
	private BigDecimal valorTotal = BigDecimal.ZERO;
	private Contrato contrato;
	
}
