package br.com.nascisoft.apoioterritoriols.cadastro.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;


public class Regiao implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nome;
	private String letra;
	private Centro centro;
	
	@XmlElement
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	@XmlElement
	public void setLetra(String letra) {
		this.letra = letra;
	}

	public String getLetra() {
		return letra;
	}
	
	@XmlElement
	public void setCentro(Centro centro) {
		this.centro = centro;
	}
	
	public Centro getCentro() {
		return centro;
	}

}
