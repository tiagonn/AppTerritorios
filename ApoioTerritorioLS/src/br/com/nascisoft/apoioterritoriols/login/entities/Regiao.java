package br.com.nascisoft.apoioterritoriols.login.entities;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Transient;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@Unindexed
@Cached
public class Regiao implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Id private Long id;
	private String nome;
	@Indexed private Key<Cidade> cidade;
	@Indexed private String letra;
	private Integer zoom;
	private Double latitudeCentro;
	private Double longitudeCentro;
	private String corLetra;
	private String corFundo;
	
	public String getCorLetra() {
		return corLetra != null ? corLetra : "ff0000";
	}
	public void setCorLetra(String corLetra) {
		this.corLetra = corLetra;
	}
	public String getCorFundo() {
		return corFundo != null ? corFundo : "fff339";
	}
	public void setCorFundo(String corFundo) {
		this.corFundo = corFundo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Integer getZoom() {
		return zoom;
	}
	public void setZoom(Integer zoom) {
		this.zoom = zoom;
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
	@Transient
	public String getNomeRegiaoCompleta() {
		return this.getLetra() + " - " + this.getNome();
	}
}
