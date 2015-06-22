package br.com.nascisoft.apoioterritoriols.cadastro.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class PesquisarSurdoEvent extends GwtEvent<PesquisarSurdoEventHandler> {
	public static Type<PesquisarSurdoEventHandler> TYPE = new Type<PesquisarSurdoEventHandler>();
	
	String identificadorCidade;
	String nomeSurdo;
	String nomeRegiao; 
	String identificadorMapa;
	Boolean estaAssociadoMapa;
	Boolean dispararPesquisa;

	public Boolean getDispararPesquisa() {
		return dispararPesquisa;
	}

	public void setDispararPesquisa(Boolean dispararPesquisa) {
		this.dispararPesquisa = dispararPesquisa;
	}

	public PesquisarSurdoEvent(String identificadorCidade, String nomeSurdo, String nomeRegiao, String identificadorMapa, Boolean estaAssociadoMapa, Boolean dispararPesquisa) {
		this.identificadorCidade = identificadorCidade;
		this.nomeSurdo = nomeSurdo;
		this.nomeRegiao = nomeRegiao;
		this.identificadorMapa = identificadorMapa;
		this.estaAssociadoMapa = estaAssociadoMapa;
		this.dispararPesquisa = dispararPesquisa;
	}

	@Override
	protected void dispatch(PesquisarSurdoEventHandler handler) {
		handler.onPesquisarSurdo(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PesquisarSurdoEventHandler> getAssociatedType() {
		return TYPE;
	}

	public String getIdentificadorCidade() {
		return identificadorCidade;
	}

	public void setIdentificadorCidade(String identificadorCidade) {
		this.identificadorCidade = identificadorCidade;
	}

	public String getNomeSurdo() {
		return nomeSurdo;
	}

	public void setNomeSurdo(String nomeSurdo) {
		this.nomeSurdo = nomeSurdo;
	}

	public String getNomeRegiao() {
		return nomeRegiao;
	}

	public void setNomeRegiao(String nomeRegiao) {
		this.nomeRegiao = nomeRegiao;
	}

	public String getIdentificadorMapa() {
		return identificadorMapa;
	}

	public void setIdentificadorMapa(String identificadorMapa) {
		this.identificadorMapa = identificadorMapa;
	}
	
	public Boolean getEstaAssociadoMapa() {
		return estaAssociadoMapa;
	}

	public void setEstaAssociadoMapa(Boolean estaAssociadoMapa) {
		this.estaAssociadoMapa = estaAssociadoMapa;
	}

}
