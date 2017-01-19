package com.github.erik5594.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.github.erik5594.enuns.FormaPagamento;
import com.github.erik5594.enuns.StatusOrdemServico;

public class OrdemServicoImpressao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Cliente cliente;
	private Endereco endereco;
	private String telefone;
	private Veiculo veiculo;
	private List<ItemOrdemServico> itens = new ArrayList<>();
	private FormaPagamento formaPagamento;
	private StatusOrdemServico statusOrdemServico;
	private BigDecimal valorTotal = BigDecimal.ZERO;
	
	public OrdemServicoImpressao(OrdemServico ordemServico){
		this.id = ordemServico.getId();
		this.cliente = ordemServico.getCliente();
		this.endereco = getEndereco(ordemServico);
		this.telefone = getTelefones(ordemServico);
		this.veiculo = ordemServico.getVeiculo();
		this.itens = ordemServico.getItens();
		this.formaPagamento = ordemServico.getFormaPagamento();
		this.statusOrdemServico = ordemServico.getStatusOrdemServico();
		this.valorTotal = ordemServico.getValorTotal();
		removerItemVazio();
	}
	
	private String getTelefones(OrdemServico ordemServico) {
		String telefones = "";
		if(ordemServico != null && ordemServico.getCliente().getTelefones() != null && !ordemServico.getCliente().getTelefones().isEmpty()){
			for(Telefone telefone : ordemServico.getCliente().getTelefones()){
				telefones = telefones + "("+telefone.getDdd()+") "+telefone.getNumero()+"\\ ";
			}
		}
		return telefones;
	}

	public Endereco getEndereco(OrdemServico ordemServico){
		if(ordemServico != null && ordemServico.getCliente().getEndereco() != null && !ordemServico.getCliente().getEndereco().isEmpty()){
			for(Endereco endereco : ordemServico.getCliente().getEndereco()){
				if(endereco.isEnderecoEntrega()){
					return endereco;
				}
			}
			return ordemServico.getCliente().getEndereco().get(0);
		}
		return null;
	}
	
	public void removerItemVazio() {
		ItemOrdemServico primeiroItem = this.getItens().get(0);
		
		if (primeiroItem != null && primeiroItem.getProduto().getId() == null) {
			this.getItens().remove(0);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public List<ItemOrdemServico> getItens() {
		return itens;
	}

	public void setItens(List<ItemOrdemServico> itens) {
		this.itens = itens;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public StatusOrdemServico getStatusOrdemServico() {
		return statusOrdemServico;
	}

	public void setStatusOrdemServico(StatusOrdemServico statusOrdemServico) {
		this.statusOrdemServico = statusOrdemServico;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	
}
