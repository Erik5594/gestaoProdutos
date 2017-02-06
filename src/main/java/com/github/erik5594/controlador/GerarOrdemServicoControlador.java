package com.github.erik5594.controlador;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;

import lombok.Data;
import net.sf.jasperreports.engine.JRException;

import org.apache.commons.lang3.StringUtils;

import com.github.erik5594.entidades.Cliente;
import com.github.erik5594.entidades.ItemOrdemServico;
import com.github.erik5594.entidades.OrdemServico;
import com.github.erik5594.entidades.Produto;
import com.github.erik5594.entidades.Veiculo;
import com.github.erik5594.enuns.FormaPagamento;
import com.github.erik5594.enuns.StatusOrdemServico;
import com.github.erik5594.interfaces.GerarPDF;
import com.github.erik5594.servicos.GerarPdfDadosObjeto;
import com.github.erik5594.servicos.ServicosCliente;
import com.github.erik5594.servicos.ServicosOrdemServico;
import com.github.erik5594.servicos.ServicosProduto;
import com.github.erik5594.servicos.ServicosVeiculo;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

@Named
@ViewScoped
public @Data class GerarOrdemServicoControlador implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Cliente cliente;
	private List<Cliente> todosCliente = new ArrayList<>();
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
	private ServicosCliente servicosCliente;
	@Inject
	private ServicosVeiculo servicosVeiculo;
	@Inject
	private ServicosOrdemServico servicosOrdemServico;
	@Inject
	private HttpServletResponse response;
	@Inject
	private FacesContext context;
	@Inject
	private ItemOrdemServico itemSelecionado;
	
	private static final String STATUS_PARA_ABERTO = "*0*";
	private static final String STATUS_PARA_APROVADO = "*0**3*";
	private static final String STATUS_PARA_EM_ANDAMENTO = "*1*";
	private static final String STATUS_PARA_PENDENTE_CONFIRMACAO = "*2*";
	private static final String STATUS_PARA_SERVICOS_REALIZADOS = "*2*";
	private static final String STATUS_PARA_FINALIZADO = "*1**4*";
	private static final String STATUS_PARA_CANCELADO = "*0**1**2**3**4*";
	private static final String STATUS_PARA_EMISSAO = "*0**1**2**3**4**5**6*";
	
	private boolean alterarQuantidadeProduto = true;
	private boolean adicionarItemOrcamento = true;
	private boolean alterarClienteOrcamento = true;
	private boolean alterarVeiculoOrcamento = true;
	private boolean alterarFormaPagamentOrcamento = true;
	private boolean podeSomenteSalvar = true;
	
	private static final String TITULO = "Cadastro Ordem Serviço: ";
	
	public void inicializar() {
		if(this.ordemServico == null){
			return;
		}
		if (FacesUtils.isNotPostback()) {
			if(!this.ordemServico.temItemVazio()){
				this.ordemServico.adicionarItemVazio();
			}
			this.recalcularOrdemServico();
		}
	}

	public void carregarProdutoPorCodBarras(){
		if(!StringUtils.isBlank(codBarras)){
			try{
				if(codBarras.length() < 13){
					produtoLinhaEditavel = servicosProdutos.pesquisaProdutoByCodProduto(codBarras);
				}else{
					produtoLinhaEditavel = servicosProdutos.buscarProdutoByCodBarras(codBarras);
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
				item.setValorUnitario(this.produtoLinhaEditavel.getValoresProdutos().getValorComercial());
				
				this.ordemServico.adicionarItemVazio();
				this.produtoLinhaEditavel = null;
				this.codBarras = null;
				
				atualizarDesconto(item, 0);
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
		if (item.getQuantidadeProduto().equals(BigDecimal.ONE)) {
			if (linha == 0) {
				item.setQuantidadeProduto(BigDecimal.ONE);
			}
		}
		this.ordemServico.recalcularValorTotalDesconto();
		this.ordemServico.recalcularValorTotal();
	}
	
	public void atualizarDesconto(ItemOrdemServico item, int linha) {
		if(item.getPorcentualDesconto().compareTo(item.getProduto().getMaiorMaiorDescontoPossivel()) < 1
				&& item.getPorcentualDesconto().compareTo(item.getProduto().getMaiorMenorDescontoPossivel()) > -1){
			calcularDescontos(item);
		}else{
			item.setPorcentualDesconto(item.getProduto().getMaiorMenorDescontoPossivel().setScale(3, RoundingMode.HALF_UP));
			calcularDescontos(item);
		}
	}

	private void calcularDescontos(ItemOrdemServico item) {
		BigDecimal valorDescontoUnitario = item.getPorcentualDesconto().divide(new BigDecimal(100d)).multiply(item.getValorUnitario()).setScale(2, RoundingMode.HALF_UP);
		item.setValorDesconto(valorDescontoUnitario);
		this.ordemServico.recalcularValorTotalDesconto();
		this.ordemServico.recalcularValorTotal();
	}
	
	public void recalcularOrdemServico() {
		if (this.ordemServico != null) {
			this.ordemServico.recalcularValorTotal();
		}
	}
	
	public void buscarCliente() {
		try{
			this.todosCliente = servicosCliente.listarTodosClientes();
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
	
	public boolean isRenderizarBotaoEmissao(){
		if(Utils.isNotNull(ordemServico) && Utils.isNotNull(ordemServico.getStatusOrdemServico())){
			if(STATUS_PARA_EMISSAO.contains("*".concat(String.valueOf(ordemServico.getStatusOrdemServico().getCodStatus())).concat("*"))){
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
		podeSomenteSalvar = false;
	}
	
	private void habilitarEdicaoOrcamento(){
		alterarQuantidadeProduto = false;
		adicionarItemOrcamento = false;
		alterarClienteOrcamento = false;
		alterarVeiculoOrcamento = false;
		alterarFormaPagamentOrcamento = false;
		podeSomenteSalvar = true;
	}
	
	private void habilitarEdicaoFormaPagamentoOrcamento(){
		alterarQuantidadeProduto = true;
		adicionarItemOrcamento = true;
		alterarClienteOrcamento = true;
		alterarVeiculoOrcamento = true;
		alterarFormaPagamentOrcamento = false;
		podeSomenteSalvar = true;
	}
	
	private void habilitarAdicionamentoProdutoOrcamento(){
		alterarQuantidadeProduto = false;
		adicionarItemOrcamento = false;
		alterarClienteOrcamento = true;
		alterarVeiculoOrcamento = true;
		alterarFormaPagamentOrcamento = true;
		podeSomenteSalvar = true;
	}
	
	private void habilitarAdicionamentoProdutoAndFormaPagamentoOrcamento(){
		alterarQuantidadeProduto = false;
		adicionarItemOrcamento = false;
		alterarClienteOrcamento = true;
		alterarVeiculoOrcamento = true;
		alterarFormaPagamentOrcamento = false;
		podeSomenteSalvar = true;
	}
	
	private void salvarPedido(){
		try{
			completarOrdemServico();
			this.ordemServico = servicosOrdemServico.salvarOrUpdateOrdemServico(ordemServico); 
			FacesUtils.sendMensagemOk(TITULO, "Ordem de Serviço salva com sucesso!["+ordemServico.getId()+"]");
		}catch(Exception e){
			FacesUtils.sendMensagemError(TITULO, "Ocorreu um erro ao tentar salvar Ordem Serviço!");
			e.printStackTrace();
		}finally{
			ordemServico.adicionarItemVazio();
		}
	}
	
	private void completarOrdemServico() {
		if(Utils.isNotNullOrEmpty(cliente)){
			ordemServico.setCliente(cliente);
		}
		if(Utils.isNotNullOrEmpty(veiculoSelecionado)){
			ordemServico.setVeiculo(veiculoSelecionado);
		}
	}

	public void abrirOrdemServico(){
		this.ordemServico.setStatusOrdemServico(StatusOrdemServico.ABERTO);
		ordemServico.setDataOrdemServico(new Date());
		ordemServico.setDataAgendado(Utils.retornaDataComDiasHaMais(7));
		ordemServico.setDataExecutado(Utils.retornaDataComDiasHaMais(7));
		ordemServico.removerItemVazio();
		salvarPedido();
	}
	
	public void aprovarOrdemServico(){
		this.ordemServico.setStatusOrdemServico(StatusOrdemServico.APROVADO);
		ordemServico.setDataOrdemServico(new Date());
		ordemServico.setDataAgendado(Utils.retornaDataComDiasHaMais(7));
		ordemServico.setDataExecutado(Utils.retornaDataComDiasHaMais(7));
		ordemServico.removerItemVazio();
		//servicosProdutos.registrarPendenciaSaidaTodosItens(ordemServico);
		salvarPedido();
	}
	
	public void darAndamentoOrdemServico(){
		this.ordemServico.setStatusOrdemServico(StatusOrdemServico.EM_ANDAMENTO);
		ordemServico.removerItemVazio();
		salvarPedido();
	}
	
	public void marcarPendenciaConfirmacaoOrdemServico(){
		this.ordemServico.setStatusOrdemServico(StatusOrdemServico.PENDENTE_CONFIRMACAO);
		ordemServico.setDataOrdemServico(new Date());
		ordemServico.setDataAgendado(Utils.retornaDataComDiasHaMais(7));
		ordemServico.setDataExecutado(Utils.retornaDataComDiasHaMais(7));
		ordemServico.removerItemVazio();
		salvarPedido();
	}
	
	public void marcarServicosRealizadosOrdemServico(){
		this.ordemServico.setStatusOrdemServico(StatusOrdemServico.SERVICOS_REALIZADOS);
		ordemServico.removerItemVazio();
		salvarPedido();
	}
	
	public void finalizarOrdemServico(){
		this.ordemServico.setStatusOrdemServico(StatusOrdemServico.FINALIZADO);
		ordemServico.setDataAgendado(Utils.retornaDataComDiasHaMais(7));
		ordemServico.setDataExecutado(new Date());
		ordemServico.removerItemVazio();
		servicosProdutos.registrarSaidaTodosItens(ordemServico);
		salvarPedido();
	}
	
	public void cancelarOrdemServico(){
		this.ordemServico.setStatusOrdemServico(StatusOrdemServico.CANCELADO);
		ordemServico.removerItemVazio();
		//servicosProdutos.cancelarSaidaTodosItens(ordemServico);
		salvarPedido();
	}
	
	public void salvarOrdemServico(){
		ordemServico.removerItemVazio();
		salvarPedido();
	}
	
	public void setOrdemServico(OrdemServico ordemServico){
		this.ordemServico = ordemServico;
		if(this.ordemServico != null){
			if(this.ordemServico.getCliente() != null){
				this.cliente = this.ordemServico.getCliente();
			}
			if(this.ordemServico.getVeiculo() != null){
				this.veiculoSelecionado = this.ordemServico.getVeiculo();
			}
		}
	}
	
	public void emitirOrcamento(){
		List<Object> listaOrdemServico = new ArrayList<>();
		listaOrdemServico.add(servicosOrdemServico.castToOrdemServicoImpressao(ordemServico));
		String dirApp = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") ;
		String caminhoJasper =  dirApp + "pages\\private\\relatorios\\orcamento_kilowats.jasper";
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("LOGO", dirApp+"resources\\imagens\\logo.png");
		parametros.put("DIR_REPORT_ITENS", dirApp+"pages\\private\\relatorios\\");
		GerarPDF gerarPdf = new GerarPdfDadosObjeto(caminhoJasper, parametros, listaOrdemServico, response, "orcamento-"+ordemServico.getId());
		try {
			gerarPdf.gerarPDF();
		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
		context.responseComplete();
	}
	
	public void removerItem(){
		ordemServico.removerItem(itemSelecionado);
		ordemServico.recalcularValorTotal();
	}
}
