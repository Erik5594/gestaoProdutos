package com.github.erik5594.servicos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.github.erik5594.annotations.ValidarDesconto;
import com.github.erik5594.dao.DescontoDao;
import com.github.erik5594.entidades.Desconto;
import com.github.erik5594.interfaces.IValidacaoCadastro;

public class ServicosDesconto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DescontoDao descontoDao;
	@Inject @ValidarDesconto
	private IValidacaoCadastro validador;

	public Desconto persistirDesconto(Desconto desconto) {
		return descontoDao.salvarOrUpdate(desconto);
	}
	
	public Desconto pesquisarByIdDesconto(Long id){
		return descontoDao.pesquisarById(id);
	}
	
	public boolean descontoIsValido(Desconto desconto, String titulo, boolean mostrarMensagem) {
		return validador.validarCadastroComMensagem(desconto, titulo, mostrarMensagem);
	}

	public List<Desconto> listarTodosDescontos() {
		return descontoDao.listarTodosDescontos();
	}
	
	public void persistirListaDesconto(List<Desconto> descontos){
		descontoDao.salvarOrUpdateLista(descontos);
	}
	
	public List<Desconto> pesquisarDescontoByNome(String nomeDesconto) {
		return descontoDao.buscarDescontoByNome(nomeDesconto);
	}
}
