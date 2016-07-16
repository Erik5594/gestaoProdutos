package com.kilowats.servicos;

import com.kilowats.dao.FornecedorDao;
import com.kilowats.entidades.Empresa;
import com.kilowats.interfaces.IPersistirBancoDados;

public class ServicosFornecedor {
	
	public static boolean persistirFornecedor(Empresa empresa){
		IPersistirBancoDados persistir = new FornecedorDao();
		return persistir.salvar(empresa);
	}
}
