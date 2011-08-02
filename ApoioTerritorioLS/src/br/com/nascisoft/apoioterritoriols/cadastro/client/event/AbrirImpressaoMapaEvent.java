package br.com.nascisoft.apoioterritoriols.cadastro.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AbrirImpressaoMapaEvent extends GwtEvent<AbrirImpressaoMapaEventHandler> {
	
	public static Type<AbrirImpressaoMapaEventHandler> TYPE = new Type<AbrirImpressaoMapaEventHandler>();
	private Long identificadorMapa;
	
	public AbrirImpressaoMapaEvent(Long identificadorMapa) {
		this.identificadorMapa = identificadorMapa;
	}

	@Override
	protected void dispatch(AbrirImpressaoMapaEventHandler handler) {
		handler.onAbrirImpressaoMapa(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AbrirImpressaoMapaEventHandler> getAssociatedType() {
		return TYPE;
	}

	public Long getIdentificadorMapa() {
		return identificadorMapa;
	}

}
