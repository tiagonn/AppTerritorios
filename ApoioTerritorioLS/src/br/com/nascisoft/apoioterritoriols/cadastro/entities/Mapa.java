package br.com.nascisoft.apoioterritoriols.cadastro.entities;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
@Cached
public class Mapa implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id private Long id;
	private Integer numero;
	private String letra;
	private String regiao;
	
	public Mapa() {
		super();
	}

	public Mapa(Integer numero) {
		super();
		this.numero = numero;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public String getRegiao() {
		return regiao;
	}
	
	public String getNome() {
		return "Mapa " + letra + numero;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public String getLetra() {
		return letra;
	}

}
