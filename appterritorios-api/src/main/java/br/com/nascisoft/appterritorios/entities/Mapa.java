package br.com.nascisoft.appterritorios.entities;

import java.io.Serializable;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
public class Mapa implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id private Long id;
	@Index private Integer numero;
	@Index private String letra;
	@Index private Key<Regiao> regiao;
	
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

	public void setRegiao(Key<Regiao> regiao) {
		this.regiao = regiao;
	}

	public Key<Regiao> getRegiao() {
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
