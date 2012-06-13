package br.com.nascisoft.apoioterritoriols.cadastro.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AbrirNaoVisitarEvent extends GwtEvent<AbrirNaoVisitarEventHandler> {
	
	public static Type<AbrirNaoVisitarEventHandler> TYPE = new Type<AbrirNaoVisitarEventHandler>();

	@Override
	protected void dispatch(AbrirNaoVisitarEventHandler handler) {
		handler.onAbrirNaoVisitar(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AbrirNaoVisitarEventHandler> getAssociatedType() {
		return TYPE;
	}

}
