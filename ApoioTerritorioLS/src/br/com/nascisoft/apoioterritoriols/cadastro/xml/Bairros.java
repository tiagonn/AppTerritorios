package br.com.nascisoft.apoioterritoriols.cadastro.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="bairros")
public class Bairros {
	
	private List<String> bairro;

	@XmlElement
	public List<String> getBairro() {
		return bairro;
	}

	public void setBairro(List<String> bairro) {
		this.bairro = bairro;
	}
	
}
