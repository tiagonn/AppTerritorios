package br.com.nascisoft.apoioterritoriols.login.entities;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Unindexed;

@Unindexed
@Cached
public class Cidade implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Id private Long id; 
	private String nome;
	private Double latitudeCentro;
	private Double longitudeCentro;
	private Integer quantidadeSurdosMapa;
	private Boolean utilizarBairroBuscaEndereco;
	private String UF;
	private String pais;
	
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
	public Integer getQuantidadeSurdosMapa() {
		return quantidadeSurdosMapa;
	}
	public void setQuantidadeSurdosMapa(Integer quantidadeSurdosMapa) {
		this.quantidadeSurdosMapa = quantidadeSurdosMapa;
	}
	public Boolean getUtilizarBairroBuscaEndereco() {
		return utilizarBairroBuscaEndereco;
	}
	public void setUtilizarBairroBuscaEndereco(
			Boolean utilizarBairroBuscaEndereco) {
		this.utilizarBairroBuscaEndereco = utilizarBairroBuscaEndereco;
	}
	public String getUF() {
		return UF;
	}
	public void setUF(String uF) {
		UF = uF;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}

}
