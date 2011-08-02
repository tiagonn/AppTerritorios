package br.com.nascisoft.apoioterritoriols.cadastro.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="regioes")
public class Regioes {
	
	private List<Regiao> regiao;

	@XmlElement
	public void setRegiao(List<Regiao> regiao) {
		this.regiao = regiao;
	}

	public List<Regiao> getRegiao() {
		return regiao;
	}

}
