package br.com.nascisoft.apoioterritoriols.cadastro.vo;

import java.io.Serializable;

import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;

public class SurdoDetailsVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private String regiao;
	private String mapa;
	private Double latitude;
	private Double longitude;
	private String endereco;
	private String observacao;
	private String nomeCidade;
	
	public SurdoDetailsVO() {
		super();
	}
	
	public SurdoDetailsVO(Surdo surdo, Mapa mapa, Regiao regiao, Cidade cidade) { 
		super();
		this.setId(surdo.getId());
		this.setNome(surdo.getNome());
		this.setRegiao(regiao.getNomeRegiaoCompleta());
		this.setLatitude(surdo.getLatitude());
		this.setLongitude(surdo.getLongitude());
		this.setMapa(mapa != null ? mapa.getNome() : "");
		this.setEndereco(surdo.getEndereco());
		this.setObservacao(surdo.getObservacao());
		this.setNomeCidade(cidade.getNome());
	}
	
	public SurdoDetailsVO(SurdoVO surdo) {
		this.setId(surdo.getId());
		this.setNome(surdo.getNome());
		this.setRegiao(surdo.getRegiao());
		this.setLatitude(surdo.getLatitude());
		this.setLongitude(surdo.getLongitude());
		this.setMapa(surdo.getMapa());
		this.setEndereco(surdo.getEndereco());
		this.setObservacao(surdo.getObservacao());
		this.setNomeCidade(surdo.getNomeCidade());
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
	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public String getRegiao() {
		return regiao;
	}

	public String getMapa() {
		return mapa;
	}
	public void setMapa(String mapa) {
		this.mapa = mapa;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

}
