package br.com.nascisoft.appterritorios.entities;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Subclass;

@Subclass(index=true)
public class MelhorDia implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Index private Boolean segunda = false;
	@Index private Boolean terca = false;
	@Index private Boolean quarta = false;
	@Index private Boolean quinta = false;
	@Index private Boolean sexta = false;
	@Index private Boolean sabado = false;
	@Index private Boolean domingo = false;
	
	public MelhorDia() {
		super();
	}
	
	public MelhorDia(String melhoresDiasCSV, Boolean isAbreviado) {
		super();
		if (melhoresDiasCSV != null && melhoresDiasCSV.length() > 0) {
			String[] melhoresDias = melhoresDiasCSV.split(",");
			for (int i = 0; i < melhoresDias.length; i++) {
				if (isAbreviado) {
					switch (melhoresDias[i]) {
						case "Seg":
							this.segunda = Boolean.TRUE;
							break;
						case "Ter":
							this.terca = Boolean.TRUE;
							break;
						case "Qua":
							this.quarta = Boolean.TRUE;
							break;
						case "Qui":
							this.quinta = Boolean.TRUE;
							break;
						case "Sex":
							this.sexta = Boolean.TRUE;
							break;
						case "Sáb":
							this.sabado = Boolean.TRUE;
							break;
						case "Dom":
							this.domingo = Boolean.TRUE;
							break;
		
						default:
							break;
					}
				} else {
					switch (melhoresDias[i]) {
						case "Segunda":
							this.segunda = Boolean.TRUE;
							break;
						case "Terça":
							this.terca = Boolean.TRUE;
							break;
						case "Quarta":
							this.quarta = Boolean.TRUE;
							break;
						case "Quinta":
							this.quinta = Boolean.TRUE;
							break;
						case "Sexta":
							this.sexta = Boolean.TRUE;
							break;
						case "Sábado":
							this.sabado = Boolean.TRUE;
							break;
						case "Domingo":
							this.domingo = Boolean.TRUE;
							break;
		
						default:
							break;
					}
				}
			}
		}
		
	}
	
	public Boolean getSegunda() {
		return segunda;
	}

	public void setSegunda(Boolean segunda) {
		this.segunda = segunda;
	}

	public Boolean getTerca() {
		return terca;
	}

	public void setTerca(Boolean terca) {
		this.terca = terca;
	}

	public Boolean getQuarta() {
		return quarta;
	}

	public void setQuarta(Boolean quarta) {
		this.quarta = quarta;
	}

	public Boolean getQuinta() {
		return quinta;
	}

	public void setQuinta(Boolean quinta) {
		this.quinta = quinta;
	}

	public Boolean getSexta() {
		return sexta;
	}

	public void setSexta(Boolean sexta) {
		this.sexta = sexta;
	}

	public Boolean getSabado() {
		return sabado;
	}

	public void setSabado(Boolean sabado) {
		this.sabado = sabado;
	}

	public Boolean getDomingo() {
		return domingo;
	}

	public void setDomingo(Boolean domingo) {
		this.domingo = domingo;
	}

	public enum Dia {
		segunda("Segunda", "Seg"),
		terca("Terça", "Ter"),
		quarta("Quarta", "Qua"),
		quinta("Quinta", "Qui"),
		sexta("Sexta", "Sex"),
		sabado("Sábado", "Sáb"),
		domingo("Domingo", "Dom");		
		private String nome;
		private String nomeAbreviado;
		Dia(String nome, String nomeAbreviado) {
			this.nome = nome;
			this.nomeAbreviado = nomeAbreviado;
		}
		public String getNome() {
			return nome;
		}
		public String getNomeAbreviado() {
			return nomeAbreviado;
		}
	}
	
	
}

