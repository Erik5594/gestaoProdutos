package com.github.erik5594.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.erik5594.entidades.EstoqueProduto;
import com.github.erik5594.entidades.Pessoa;
import com.github.erik5594.entidades.ValoresProduto;
import com.github.erik5594.entidades.Veiculo;

public class Utils {

	public static String retornaStringNaoNull(String texto) {
		if (StringUtils.isBlank(texto)) {
			return "";
		}
		return texto;
	}

	public static boolean isNullOrEmpty(Object obj) {
		return !isNotNullOrEmpty(obj);
	}

	public static String mascaraPrimefacesCGC() {
		return "99.999.999/9999-99";
	}

	public static String mascaraPrimefacesCPF() {
		return "999.999.999-99";
	}
	
	public static String mascaraPrimefacesCnpjOuCpf(int tipo){
		if(tipo == 0){
			return mascaraPrimefacesCPF();
		}
		return mascaraPrimefacesCGC();
	}

	public static boolean isCGC(String texto) {
		if (Utils.isNullOrEmpty(texto)) {
			return false;
		} else {
			if (texto.replaceAll("\\D", "").length() == 14) {
				return true;
			}
		}
		return false;
	}

	public static boolean isCPF(String texto) {
		if (Utils.isNullOrEmpty(texto)) {
			return false;
		} else {
			if (texto.replaceAll("\\D", "").length() == 11) {
				return true;
			}
		}
		return false;
	}

	public static boolean isCPFValido(String CPF) {
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (CPF.equals("00000000000") || CPF.equals("11111111111")
				|| CPF.equals("22222222222") || CPF.equals("33333333333")
				|| CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777")
				|| CPF.equals("88888888888") || CPF.equals("99999999999")
				|| (CPF.length() != 11)) {
			return (false);
		}
		char dig10, dig11;
		int sm, i, r, num, peso;
		// "try" - protege o codigo para eventuais erros de conversao de tipo
		// (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11)) {
				dig10 = '0';
			} else {
				dig10 = (char) (r + 48);
			}
			// converte no respectivo caractere numerico
			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11)) {
				dig11 = '0';
			} else {
				dig11 = (char) (r + 48);
			}
			// Verifica se os digitos calculados conferem com os digitos
			// informados.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
				return (true);
			} else {
				return (false);
			}
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	public static boolean isCNPJValido(String CNPJ) {
		// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
		    if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
		        CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
		        CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
		        CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
		        CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
		       (CNPJ.length() != 14))
		       return(false);

		    char dig13, dig14;
		    int sm, i, r, num, peso;

		// "try" - protege o código para eventuais erros de conversao de tipo (int)
		    try {
		// Calculo do 1o. Digito Verificador
		      sm = 0;
		      peso = 2;
		      for (i=11; i>=0; i--) {
		// converte o i-ésimo caractere do CNPJ em um número:
		// por exemplo, transforma o caractere '0' no inteiro 0
		// (48 eh a posição de '0' na tabela ASCII)
		        num = (int)(CNPJ.charAt(i) - 48);
		        sm = sm + (num * peso);
		        peso = peso + 1;
		        if (peso == 10)
		           peso = 2;
		      }

		      r = sm % 11;
		      if ((r == 0) || (r == 1))
		         dig13 = '0';
		      else dig13 = (char)((11-r) + 48);

		// Calculo do 2o. Digito Verificador
		      sm = 0;
		      peso = 2;
		      for (i=12; i>=0; i--) {
		        num = (int)(CNPJ.charAt(i)- 48);
		        sm = sm + (num * peso);
		        peso = peso + 1;
		        if (peso == 10)
		           peso = 2;
		      }

		      r = sm % 11;
		      if ((r == 0) || (r == 1))
		         dig14 = '0';
		      else dig14 = (char)((11-r) + 48);

		// Verifica se os dígitos calculados conferem com os dígitos informados.
		      if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
		         return(true);
		      else return(false);
		    } catch (InputMismatchException erro) {
		        return(false);
		    }
		  }
	
	public static boolean isEmailValido(String email) {
		if (!Utils.isNullOrEmpty(email)) {
			if (email.contains("@")) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNomeValido(String nome) {
		if (!Utils.isNullOrEmpty(nome)) {
			if (!nome.toLowerCase().equals("teste")) {
				if (nome.length() > 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isNotNullOrEmpty(Object obj){
		if(isNull(obj)){
			return false;
		}
		if(obj instanceof String){
			return StringUtils.isNotBlank(obj.toString()) || !"null".equals(obj.toString());
		}else if(obj instanceof List<?>){
			@SuppressWarnings("unchecked")
			List<Object> objs = (List<Object>) obj;
			return isNotNull(objs) && !objs.isEmpty();
		}else if(obj instanceof Long){
			Long lon = (Long)obj;
			return isNotNull(lon) && lon > 0l;
		}else if(obj instanceof Integer){
			Integer inter = (Integer) obj;
			return isNotNull(inter) && inter > 0;
		}else if(obj instanceof Float){
			Float flo = (Float) obj;
			return isNotNull(flo) && flo > 0F;
		}else if(obj instanceof BigDecimal){
			BigDecimal bgDec = (BigDecimal)obj;
			return isNotNull(bgDec) && bgDec != BigDecimal.ZERO;
		}else if(obj instanceof ValoresProduto){
			ValoresProduto valor = (ValoresProduto) obj;
			return isNotNullOrEmpty(valor.getValorComercial())
					/*&& isNotNullOrEmpty(valor.getValorCusto())
					&& isNotNullOrEmpty(valor.getValorTributavel())*/;
			
		}else if(obj instanceof EstoqueProduto){
			EstoqueProduto estoque = (EstoqueProduto) obj;
			return isNotNullOrEmpty(estoque.getQuantidadeEstoque())
					/*&& isNotNullOrEmpty(estoque.getQuantidadePendenteEntrada())
					&& isNotNullOrEmpty(estoque.getQuantidadePendenteSaida())
					&& isNotNullOrEmpty(estoque.getQuantidadeTributavel())*/;
		}else if (obj instanceof Veiculo) {
			Veiculo veiculo = (Veiculo) obj;
			return isNotNullOrEmpty(veiculo.getPlaca())
					&& isNotNullOrEmpty(veiculo.getChassi())
					&& isNotNullOrEmpty(veiculo.getCliente());
		} else if (obj instanceof Pessoa) {
			Pessoa cliente = (Pessoa) obj;
			return isNotNullOrEmpty(cliente.getNome())
					&& isNotNullOrEmpty(cliente.getCgcCpf());
		}
		return isNotNull(obj);
	}
	
	public static boolean isNull(Object obj){
		return obj == null;
	}
	
	public static boolean isNotNull(Object obj){
		return !isNull(obj);
	}

	public static boolean placaIsValida(String placa){
		if(placa.length() != 7){
			return false;
		}
		String textoPlaca = placa.substring(0, 3).replaceAll("\\d", "");
		if(textoPlaca.length() != 3){
			return false;
		}
		try{
			Integer.parseInt(placa.substring(3, 7));
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	
	public static String formataCPFCGC(String valor){
		StringBuilder sb = new StringBuilder();
		if(valor != null){
			valor = valor.replace(" ", "");
			if(valor.length() == 11){
				sb.append(valor.substring(0,3)).append(".");
				sb.append(valor.substring(3,6)).append(".");
				sb.append(valor.substring(6,9)).append("-");
				sb.append(valor.substring(9,11)); 
			}else if(valor.length() == 14){
				sb.append(valor.substring(0,2)).append(".");
				sb.append(valor.substring(2,5)).append(".");
				sb.append(valor.substring(5,8)).append("/");
				sb.append(valor.substring(8,12)).append("-");
				sb.append(valor.substring(12,14));
			}
		}
		return sb.toString();
	}
	
	public static Date retornaDataComDiasHaMais(int qtdeDiasHaMais){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, qtdeDiasHaMais);
		return calendar.getTime();
	}
	
	public static Date retornaDataComDiasHaMenos(int qtdeDiasHaMenos){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -qtdeDiasHaMenos);
		return calendar.getTime();
	}
}
