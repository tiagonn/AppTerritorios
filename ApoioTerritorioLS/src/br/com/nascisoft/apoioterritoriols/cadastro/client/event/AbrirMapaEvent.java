package br.com.nascisoft.apoioterritoriols.cadastro.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AbrirMapaEvent extends GwtEvent<AbrirMapaEventHandler> {
	
	public static Type<AbrirMapaEventHandler> TYPE = new Type<AbrirMapaEventHandler>();
	private Long identificadorMapa;
	
	public AbrirMapaEvent(Long identificadorMapa) {
		this.identificadorMapa = identificadorMapa;
	}

	@Override
	protected void dispatch(AbrirMapaEventHandler handler) {
		handler.onAbrirMapa(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AbrirMapaEventHandler> getAssociatedType() {
		return TYPE;
	}

	public Long getIdentificadorMapa() {
		return identificadorMapa;
	}

}
