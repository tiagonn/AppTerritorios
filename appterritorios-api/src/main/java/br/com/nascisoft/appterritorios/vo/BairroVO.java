package br.com.nascisoft.appterritorios.vo;

import java.io.Serializable;

import br.com.nascisoft.appterritorios.entities.Bairro;
import br.com.nascisoft.appterritorios.entities.Cidade;

public class BairroVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private Long cidadeId;
	private String cidadeNome;
	
	public BairroVO() {
		super();
	}
	
	public BairroVO(Bairro bairro, Cidade cidade) {
		this.setId(bairro.getId());
		this.setNome(bairro.getNome());
		this.setCidadeId(cidade.getId());
		this.setCidadeNome(cidade.getNome());
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
	public Long getCidadeId() {
		return cidadeId;
	}
	public void setCidadeId(Long cidadeId) {
		this.cidadeId = cidadeId;
	}
	public String getCidadeNome() {
		return cidadeNome;
	}
	public void setCidadeNome(String cidadeNome) {
		this.cidadeNome = cidadeNome;
	}
	
	public Bairro getBairro() {
		Bairro bairro = new Bairro();
		
		bairro.setId(this.getId());
		bairro.setNome(this.getNome());
		
		return bairro;
	}
	
}
