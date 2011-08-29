package br.com.nascisoft.apoioterritoriols.cadastro.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class PesquisarSurdoEvent extends GwtEvent<PesquisarSurdoEventHandler> {
	public static Type<PesquisarSurdoEventHandler> TYPE = new Type<PesquisarSurdoEventHandler>();
	
	String nomeSurdo;
	String nomeRegiao; 
	String identificadorMapa;
	Boolean estaAssociadoMapa;

	public PesquisarSurdoEvent(String nomeSurdo, String nomeRegiao, String identificadorMapa, Boolean estaAssociadoMapa) {
		this.nomeSurdo = nomeSurdo;
		this.nomeRegiao = nomeRegiao;
		this.identificadorMapa = identificadorMapa;
		this.estaAssociadoMapa = estaAssociadoMapa;
	}

	@Override
	protected void dispatch(PesquisarSurdoEventHandler handler) {
		handler.onPesquisarSurdo(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PesquisarSurdoEventHandler> getAssociatedType() {
		return TYPE;
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
