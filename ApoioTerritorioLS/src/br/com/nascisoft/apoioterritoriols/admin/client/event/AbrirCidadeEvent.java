package br.com.nascisoft.apoioterritoriols.admin.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AbrirCidadeEvent extends GwtEvent<AbrirCidadeEventHandler> {
	
	public static Type<AbrirCidadeEventHandler> TYPE = new Type<AbrirCidadeEventHandler>();

	@Override
	protected void dispatch(AbrirCidadeEventHandler handler) {
		handler.onAbrirCidade(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AbrirCidadeEventHandler> getAssociatedType() {
		return TYPE;
	}

}
