package com.kilowats.controlador;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

import com.kilowats.entidades.Cliente;
import com.kilowats.entidades.ItemOrdemServico;
import com.kilowats.entidades.OrdemServico;
import com.kilowats.entidades.Produto;
import com.kilowats.entidades.Veiculo;
import com.kilowats.enuns.FormaPagamento;
import com.kilowats.enuns.StatusOrdemServico;
import com.kilowats.servicos.ServicosCliente;
import com.kilowats.servicos.ServicosEan;
import com.kilowats.servicos.ServicosOrdemServico;
import com.kilowats.servicos.ServicosProduto;
import com.kilowats.servicos.ServicosVeiculo;
import com.kilowats.util.Utils;
import com.kilowats.util.jsf.FacesUtils;

@Named
@ViewScoped
public @Data class GerarOrdemServicoControlador implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Cliente cliente;
	@Inject
	private Veiculo veiculoSelecionado;
	@Inject
	private Produto produtoLinhaEditavel;
	@Inject
	private OrdemServico ordemServico;
	private String codBarras;
	@Inject
	private ServicosProduto servicosProdutos;
	@Inject
	private ServicosEan servicosCodBarras;
	@Inject
	private ServicosCliente servicosCliente;
	@Inject
	private ServicosVeiculo servicosVeiculo;
	@Inject
	private ServicosOrdemServico servicosOrdemServico;
	
	private static final String STATUS_PARA_ABERTO = "*0*";
	private static final String STATUS_PARA_APROVADO = "*0**3*";
	private static final String STATUS_PARA_EM_ANDAMENTO = "*1*";
	private static final String STATUS_PARA_PENDENTE_CONFIRMACAO = "*2*";
	private static final String STATUS_PARA_SERVICOS_REALIZADOS = "*2*";
	private static final String STATUS_PARA_FINALIZADO = "*1**4*";
	private static final String STATUS_PARA_CANCELADO = "*0**1**2**3**4*";
	
	private boolean alterarQuantidadeProduto = true;
	private boolean adicionarItemOrcamento = true;
	private boolean alterarClienteOrcamento = true;
	private boolean alterarVeiculoOrcamento = true;
	private boolean alterarFormaPagamentOrcamento = true;
	
	private static final String TITULO = "Cadastro Ordem Serviço: ";
	
	public void inicializar() {
		if (FacesUtils.isNotPostback()) {
			this.ordemServico.adicionarItemVazio();
			this.recalcularOrdemServico();
		}
	}

	public void carregarProdutoPorCodBarras(){
		if(!StringUtils.isBlank(codBarras)){
			try{
				if(codBarras.length() < 13){
					produtoLinhaEditavel = servicosProdutos.pesquisaProdutoByCodProduto(codBarras);
				}else{
					produtoLinhaEditavel = servicosCodBarras.pesquisarEanByCodBarras(codBarras).getProduto();
				}
				carregarProdutoLinhaEditavel();
			}catch(NoResultException ex){
				FacesUtils.sendMensagemAviso(TITULO, "Não foi encontrado produto!");
			}
		}
	}
	
	public void carregarProdutoLinhaEditavel() {
		ItemOrdemServico item = this.ordemServico.getItens().get(0);
		
		if (this.produtoLinhaEditavel != null) {
			if (this.existeItemComProduto(this.produtoLinhaEditavel)) {
				FacesUtils.sendMensagemError(TITULO, "Já existe um item no pedido com o produto informado.");
			} else {
				item.setProduto(this.produtoLinhaEditavel);
				item.setValorUnitario(this.produtoLinhaEditavel.getValor());
				
				this.ordemServico.adicionarItemVazio();
				this.produtoLinhaEditavel = null;
				this.codBarras = null;
				
				this.ordemServico.recalcularValorTotal();
			}
		}
	}
	
	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;
		
		for (ItemOrdemServico item : this.getOrdemServico().getItens()) {
			if (produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}
		
		return existeItem;
	}
	
	public List<Produto> completarProduto(String nome) {
		return this.servicosProdutos.pesquisarProdutoByNome(nome);
	}
	
	public void atualizarQuantidade(ItemOrdemServico item, int linha) {
		if (item.getQuantidadeProduto() < 1) {
			if (linha == 0) {
				item.setQuantidadeProduto(1);
			} else {
				this.getOrdemServico().getItens().remove(linha);
			}
		}
		
		this.ordemServico.recalcularValorTotal();
	}
	
	public void recalcularOrdemServico() {
		if (this.ordemServico != null) {
			this.ordemServico.recalcularValorTotal();
		}
	}
	
	public void buscarCliente() {
		try{
			this.cliente = servicosCliente.buscarClienteByCpfCgc(cliente.getCgcCpf().replaceAll("\\D", ""));
		}catch(NoResultException ex){
			FacesUtils.sendMensagemAviso(TITULO, "Não foi encontrado uma pessoa!");
		}
	}
	
	public void selecionarVeiculo(Veiculo veiculo){
		setVeiculoSelecionado(veiculo); 
	}
	
	public FormaPagamento[] getFormasPagamentos() {
		return FormaPagamento.values();
	}
	
	public StatusOrdemServico[] getStatusOrdemServico() {
		return StatusOrdemServico.values();
	}
	
	public boolean isRenderizarBotaoAberto(){
		if(Utils.isNotNull(ordemServico) && Utils.isNotNull(ordemServico.getStatusOrdemServico())){
			if(!STATUS_PARA_ABERTO.contains("*".concat(String.valueOf(ordemServico.getStatusOrdemServico().getCodStatus())).concat("*"))){
				return false;
			}
		}
		organizarEdicaoDeCampos();
		return true;
	}
	
	public boolean isRenderizarBotaoAprovado(){
		if(Utils.isNotNull(ordemServico) && Utils.isNotNull(ordemServico.getStatusOrdemServico())){
			if(STATUS_PARA_APROVADO.contains("*".concat(String.valueOf(ordemServico.getStatusOrdemServico().getCodStatus())).concat("*"))){
				organizarEdicaoDeCampos();
				return true;
			}
		}
		return false;
	}
	
	public boolean isRenderizarBotaoEmAndamento(){
		if(Utils.isNotNull(ordemServico) && Utils.isNotNull(ordemServico.getStatusOrdemServico())){
			if(STATUS_PARA_EM_ANDAMENTO.contains("*".concat(String.valueOf(ordemServico.getStatusOrdemServico().getCodStatus())).concat("*"))){
				organizarEdicaoDeCampos();
				return true;
			}
		}
		return false;
	}
	
	public boolean isRenderizarBotaoPendenteConfirmacao(){
		if(Utils.isNotNull(ordemServico) && Utils.isNotNull(ordemServico.getStatusOrdemServico())){
			if(STATUS_PARA_PENDENTE_CONFIRMACAO.contains("*".concat(String.valueOf(ordemServico.getStatusOrdemServico().getCodStatus())).concat("*"))){
				organizarEdicaoDeCampos();
				return true;
			}
		}
		return false;
	}
	
	public boolean isRenderizarBotaoServicosRealizados(){
		if(Utils.isNotNull(ordemServico) && Utils.isNotNull(ordemServico.getStatusOrdemServico())){
			if(STATUS_PARA_SERVICOS_REALIZADOS.contains("*".concat(String.valueOf(ordemServico.getStatusOrdemServico().getCodStatus())).concat("*"))){
				organizarEdicaoDeCampos();
				return true;
			}
		}
		return false;
	}
	
	public boolean isRenderizarBotaoFinalizado(){
		if(Utils.isNotNull(ordemServico) && Utils.isNotNull(ordemServico.getStatusOrdemServico())){
			if(STATUS_PARA_FINALIZADO.contains("*".concat(String.valueOf(ordemServico.getStatusOrdemServico().getCodStatus())).concat("*"))){
				organizarEdicaoDeCampos();
				return true;
			}
		}
		return false;
	}
	
	public boolean isRenderizarBotaoCancelado(){
		if(Utils.isNotNull(ordemServico) && Utils.isNotNull(ordemServico.getStatusOrdemServico())){
			if(STATUS_PARA_CANCELADO.contains("*".concat(String.valueOf(ordemServico.getStatusOrdemServico().getCodStatus())).concat("*"))){
				organizarEdicaoDeCampos();
				return true;
			}
		}
		return false;
	}
	
	private void organizarEdicaoDeCampos() {
		if(ordemServico == null || ordemServico.getStatusOrdemServico() == null){
			habilitarEdicaoOrcamento();
		}else{
			switch (ordemServico.getStatusOrdemServico().getCodStatus()) {
			case 0:
				habilitarEdicaoOrcamento();
				break;
			case 1:
				habilitarEdicaoFormaPagamentoOrcamento();
				break;
			case 2:
				habilitarAdicionamentoProdutoOrcamento();
				break;
			case 3:
				habilitarAdicionamentoProdutoAndFormaPagamentoOrcamento();
				break;
			case 4:
				habilitarEdicaoFormaPagamentoOrcamento();
				break;
			case 5:
				desabilitarEdicaoOrcamento();
				break;
			case 6:
				desabilitarEdicaoOrcamento();
				break;
	
			default:
				break;
		}
		}
	}
	
	private void desabilitarEdicaoOrcamento(){
		alterarQuantidadeProduto = true;
		adicionarItemOrcamento = true;
		alterarClienteOrcamento = true;
		alterarVeiculoOrcamento = true;
		alterarFormaPagamentOrcamento = true;
	}
	
	private void habilitarEdicaoOrcamento(){
		alterarQuantidadeProduto = false;
		adicionarItemOrcamento = false;
		alterarClienteOrcamento = false;
		alterarVeiculoOrcamento = false;
		alterarFormaPagamentOrcamento = false;
	}
	
	private void habilitarEdicaoFormaPagamentoOrcamento(){
		alterarQuantidadeProduto = true;
		adicionarItemOrcamento = true;
		alterarClienteOrcamento = true;
		alterarVeiculoOrcamento = true;
		alterarFormaPagamentOrcamento = false;
	}
	
	private void habilitarAdicionamentoProdutoOrcamento(){
		alterarQuantidadeProduto = false;
		adicionarItemOrcamento = false;
		alterarClienteOrcamento = true;
		alterarVeiculoOrcamento = true;
		alterarFormaPagamentOrcamento = true;
	}
	
	private void habilitarAdicionamentoProdutoAndFormaPagamentoOrcamento(){
		alterarQuantidadeProduto = false;
		adicionarItemOrcamento = false;
		alterarClienteOrcamento = true;
		alterarVeiculoOrcamento = true;
		alterarFormaPagamentOrcamento = false;
	}
	
	private void salvarPedido(){
		ordemServico.removerItemVazio();
		try{
			completarOrdemServico();
			this.ordemServico = servicosOrdemServico.salvarOrUpdateOrdemServico(ordemServico); 
			FacesUtils.sendMensagemOk(TITULO, "Ordem de Serviço salva com sucesso!");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			FacesUtils.sendMensagemError(TITULO, "Ocorreu um erro ao tentar salvar Ordem Serviço!");
			ordemServico.adicionarItemVazio();
		}
	}
	
	private void completarOrdemServico() {
		if(Utils.isNotNull(cliente)){
			ordemServico.setCliente(cliente);
		}
		if(Utils.isNotNull(veiculoSelecionado)){
			ordemServico.setVeiculo(veiculoSelecionado);
		}
		ordemServico.setDataOrdemServico(new Date());
		ordemServico.setDataAgendado(Utils.retornaDataComDiasHaMais(7));
		ordemServico.setDataExecutado(Utils.retornaDataComDiasHaMais(7));
	}

	public void abrirOrdemServico(){
		this.ordemServico.setStatusOrdemServico(StatusOrdemServico.ABERTO);
		salvarPedido();
	}
	
	public void aprovarOrdemServico(){
		this.ordemServico.setStatusOrdemServico(StatusOrdemServico.APROVADO);
		salvarPedido();
	}
	
	public void darAndamentoOrdemServico(){
		this.ordemServico.setStatusOrdemServico(StatusOrdemServico.EM_ANDAMENTO);
		salvarPedido();
	}
	
	public void marcarPendenciaConfirmacaoOrdemServico(){
		this.ordemServico.setStatusOrdemServico(StatusOrdemServico.PENDENTE_CONFIRMACAO);
		salvarPedido();
	}
	
	public void marcarServicosRealizadosOrdemServico(){
		this.ordemServico.setStatusOrdemServico(StatusOrdemServico.SERVICOS_REALIZADOS);
		salvarPedido();
	}
	
	public void finalizarOrdemServico(){
		this.ordemServico.setStatusOrdemServico(StatusOrdemServico.FINALIZADO);
		salvarPedido();
	}
	
	public void cancelarOrdemServico(){
		this.ordemServico.setStatusOrdemServico(StatusOrdemServico.CANCELADO);
		salvarPedido();
	}
	
}
