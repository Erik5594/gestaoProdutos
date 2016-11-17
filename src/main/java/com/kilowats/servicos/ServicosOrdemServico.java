package com.kilowats.servicos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.kilowats.dao.OrdemServicoDao;
import com.kilowats.entidades.OrdemServico;

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

}