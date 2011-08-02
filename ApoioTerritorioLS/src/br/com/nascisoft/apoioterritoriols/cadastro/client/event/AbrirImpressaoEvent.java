package br.com.nascisoft.apoioterritoriols.cadastro.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AbrirImpressaoEvent extends GwtEvent<AbrirImpressaoEventHandler> {
	
	public static Type<AbrirImpressaoEventHandler> TYPE = new Type<AbrirImpressaoEventHandler>();

	@Override
	protected void dispatch(AbrirImpressaoEventHandler handler) {
		handler.onAbrirImpressao(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AbrirImpressaoEventHandler> getAssociatedType() {
		return TYPE;
	}

}
