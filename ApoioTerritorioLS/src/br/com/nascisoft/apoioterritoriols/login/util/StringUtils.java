package br.com.nascisoft.apoioterritoriols.login.util;

public final class StringUtils {

	public static String toCamelCase(String string) {		
		if (string != null) {
			StringBuilder retorno = new StringBuilder();
			String[] array = string.split(" ");
			for (int i = 0; i < array.length; i++) {
				if (!StringUtils.isEmpty(array[i])) {
					if ("E".equals(array[i])) {
						retorno.append("e");
					} else {
						String aposPrimeiraLetra = array[i].substring(1);
						retorno.append(array[i].substring(0, 1)).append(
								aposPrimeiraLetra.toLowerCase());
					}
					if (i < array.length-1) {
						retorno.append(" ");
					}
				}
			}
			return retorno.toString();
		} else {
			return null;
		}
	}
	
	public static boolean isEmpty(String string) {
		return string == null || "".equals(string);
	}
	
	public static String primeiraLetra(String string) {
		String retorno = "";
		if (!StringUtils.isEmpty(string)) {
			retorno = string.substring(0,1);
		} 
		return retorno;
	}
	
	public static String primeirasLetras(String string, int quantidade) {
		String retorno = "";
		if (!StringUtils.isEmpty(string)) {
			if (string.length() > quantidade) {
				retorno = string.substring(0,quantidade);
			} else {
				retorno = string;
			}
		} 
		return retorno;
	}
}
