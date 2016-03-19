package com.kilowats.interfaces;

import java.util.List;

public interface IValidacaoCadastro {
	boolean validarCadastro(Object obj);
	List<String> validarCadastroComMensagem(Object obj);
}
