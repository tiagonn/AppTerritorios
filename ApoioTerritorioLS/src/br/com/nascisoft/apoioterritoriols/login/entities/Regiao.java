package br.com.nascisoft.apoioterritoriols.login.entities;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@Unindexed
@Cached
public class Regiao implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Id private String nome;
	@Indexed private Key<Cidade> cidade;
	private String letra;
	private Double latitudeCentro;
	private Double longitudeCentro;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Key<Cidade> getCidade() {
		return cidade;
	}
	public void setCidade(Key<Cidade> cidade) {
		this.cidade = cidade;
	}
	public String getLetra() {
		return letra;
	}
	public void setLetra(String letra) {
		this.letra = letra;
	}
	public Double getLatitudeCentro() {
		return latitudeCentro;
	}
	public void setLatitudeCentro(Double latitudeCentro) {
		this.latitudeCentro = latitudeCentro;
	}
	public Double getLongitudeCentro() {
		return longitudeCentro;
	}
	public void setLongitudeCentro(Double longitudeCentro) {
		this.longitudeCentro = longitudeCentro;
	}
}
