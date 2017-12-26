package br.com.nascisoft.appterritorios.entities;

import java.io.Serializable;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
public class Bairro implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Id private Long id; 
	@Index private String nome;
	@Index private Key<Cidade> cidade;

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

}
