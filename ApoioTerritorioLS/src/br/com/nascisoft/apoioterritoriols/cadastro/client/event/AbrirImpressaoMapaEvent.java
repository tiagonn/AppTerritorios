package br.com.nascisoft.apoioterritoriols.cadastro.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public class AbrirImpressaoMapaEvent extends GwtEvent<AbrirImpressaoMapaEventHandler> {
	
	public static Type<AbrirImpressaoMapaEventHandler> TYPE = new Type<AbrirImpressaoMapaEventHandler>();
	private List<Long> mapaIDs;
	private Boolean paisagem;
	
	public AbrirImpressaoMapaEvent(List<Long> mapaIDs, Boolean paisagem) {
		this.mapaIDs = mapaIDs;
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

	public List<Long> getMapaIDs() {
		return mapaIDs;
	}
	
	public Boolean isPaisagem() {
		return paisagem;
	}

}
