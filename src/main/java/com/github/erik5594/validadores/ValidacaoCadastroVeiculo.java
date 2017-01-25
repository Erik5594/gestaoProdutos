package com.github.erik5594.validadores;

import static com.github.erik5594.util.Utils.isNullOrEmpty;
import static com.github.erik5594.util.Utils.placaIsValida;

import com.github.erik5594.annotations.ValidarVeiculo;
import com.github.erik5594.entidades.Veiculo;
import com.github.erik5594.interfaces.IValidacaoCadastro;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

@ValidarVeiculo
public class ValidacaoCadastroVeiculo implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		Veiculo veiculo = (Veiculo) obj;
		boolean retorno = true;
		if (Utils.isNotNull(veiculo)) {
			if (isNullOrEmpty(veiculo.getPlaca())) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Placa em branco!");
				}
				retorno = false;
			}else if(!placaIsValida(veiculo.getPlaca())){
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Placa inválido!");
				}
				retorno = false;
			}

			if (isNullOrEmpty(veiculo.getChassi())) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Chassi inválido!");
				}
				retorno = false;
			}
			
			if (isNullOrEmpty(veiculo.getTipoVeiculo())) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Tipo do veículo inválido!");
				}
				retorno = false;
			}
		} else {
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Não foram encontrados dados do Veículo!");
			}
			retorno = false;
		}
		return retorno;
	}

}
