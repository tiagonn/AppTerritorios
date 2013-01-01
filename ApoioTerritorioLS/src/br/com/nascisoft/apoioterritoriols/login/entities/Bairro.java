package br.com.nascisoft.apoioterritoriols.login.entities;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Unindexed;

@Unindexed
@Cached
public class Bairro implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Id private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
