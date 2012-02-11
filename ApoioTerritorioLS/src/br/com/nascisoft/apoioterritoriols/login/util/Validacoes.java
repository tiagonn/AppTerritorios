package br.com.nascisoft.apoioterritoriols.login.util;

import java.util.ArrayList;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class Validacoes extends ArrayList<String> {
	
	private static final long serialVersionUID = 1L;

	public SafeHtml obterValidacoesSumarizadaHTML() {
		SafeHtmlBuilder retorno = new SafeHtmlBuilder();
		retorno.appendHtmlConstant("As seguintes validacoes não foram satisfeitas: <ul>");
		for (String validacao : this) {
			retorno.appendHtmlConstant("<li>" + validacao + "</li>");
		}
		retorno.appendHtmlConstant("</ul> Por favor, resolva as pendências e continue<br/>");
		return retorno.toSafeHtml();
	}
	
	public String obterValidacoesSumarizada() {
		StringBuilder retorno = new StringBuilder();
		retorno.append("/n");
		for (String validacao : this) {
			retorno.append("- " + validacao + "\n");
		}
		return retorno.toString();
	}
	
}
