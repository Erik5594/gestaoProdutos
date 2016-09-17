package com.kilowats.validadores;

import static com.kilowats.util.Utils.isNotNullOrEmpty;
import static com.kilowats.util.Utils.isNullOrEmpty;
import static com.kilowats.util.Utils.placaIsValida;

import com.kilowats.annotations.ValidarVeiculo;
import com.kilowats.entidades.Veiculo;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.util.jsf.FacesUtils;

@ValidarVeiculo
public class ValidacaoCadastroVeiculo implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		Veiculo veiculo = (Veiculo) obj;
		boolean retorno = true;
		if (isNotNullOrEmpty(veiculo)) {
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
