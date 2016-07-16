package com.kilowats.servicos;

import com.kilowats.dao.ClienteDao;
import com.kilowats.entidades.Cliente;
import com.kilowats.interfaces.IPersistirBancoDados;

public class ServicosCliente {

	public static boolean persistirCliente(Cliente cliente){
		IPersistirBancoDados persistir = new ClienteDao();
		return persistir.salvar(cliente);
	}
}
