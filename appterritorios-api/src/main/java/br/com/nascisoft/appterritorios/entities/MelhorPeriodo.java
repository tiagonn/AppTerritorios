package br.com.nascisoft.appterritorios.entities;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Subclass;

@Subclass(index=true)
public class MelhorPeriodo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Index private Boolean manha = false;
	@Index private Boolean tarde = false;
	@Index private Boolean noite = false;
	
	public MelhorPeriodo() {
		super();
	}
	
	public MelhorPeriodo(String melhoresPeriodosCSV) {
		super();
		if (melhoresPeriodosCSV != null && melhoresPeriodosCSV.length() > 0) {
			String[] melhoresPeriodos = melhoresPeriodosCSV.split(",");
			for (int i = 0; i < melhoresPeriodos.length; i++) {
				String periodo = melhoresPeriodos[i];
				switch (periodo) {
					case "Manhã":
						this.manha = Boolean.TRUE;
						break;
					case "Tarde":
						this.tarde = Boolean.TRUE;
						break;
					case "Noite":
						this.noite = Boolean.TRUE;
						break;
	
					default:
						break;
				}
			}
			
		}
	}
	
	public Boolean getManha() {
		return manha;
	}
	public void setManhã(Boolean manha) {
		this.manha = manha;
	}
	public Boolean getTarde() {
		return tarde;
	}
	public void setTarde(Boolean tarde) {
		this.tarde = tarde;
	}
	public Boolean getNoite() {
		return noite;
	}
	public void setNoite(Boolean noite) {
		this.noite = noite;
	}
	
	public enum Periodo {
		manha("Manhã"),
		tarde("Tarde"),
		noite("Noite");
		private String nome;
		Periodo(String nome) {
			this.nome = nome;
		}
		public String getNome() {
			return nome;
		}
	}
	
}

