package com.github.erik5594.servicos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.github.erik5594.dao.OrdemServicoDao;
import com.github.erik5594.entidades.OrdemServico;
import com.github.erik5594.entidades.OrdemServicoImpressao;

public class ServicosOrdemServico implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private OrdemServicoDao ordemServicoDao;
	
	
	public OrdemServico salvarOrUpdateOrdemServico(OrdemServico ordemServico){
		return ordemServicoDao.salvarOrUpdate(ordemServico);
	}
	
	public List<OrdemServico> listarTodasOrdemServico(){
		return ordemServicoDao.listarTodasOrdemServico();
	}
	
	public OrdemServicoImpressao castToOrdemServicoImpressao(OrdemServico orcamento){
		return new OrdemServicoImpressao(orcamento);
	}
	
}
