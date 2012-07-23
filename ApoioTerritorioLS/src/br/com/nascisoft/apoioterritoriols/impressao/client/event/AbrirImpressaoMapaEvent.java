package br.com.nascisoft.apoioterritoriols.impressao.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AbrirImpressaoMapaEvent extends GwtEvent<AbrirImpressaoMapaEventHandler> {
	
	public static Type<AbrirImpressaoMapaEventHandler> TYPE = new Type<AbrirImpressaoMapaEventHandler>();
	private Long identificadorMapa;
	private Boolean paisagem;
	
	public AbrirImpressaoMapaEvent(Long identificadorMapa, Boolean paisagem) {
		this.identificadorMapa = identificadorMapa;
		this.paisagem = paisagem;
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
	
	public Boolean isPaisagem() {
		return paisagem;
	}

}
