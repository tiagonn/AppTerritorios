package br.com.nascisoft.apoioterritoriols.admin.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AbrirRegiaoEvent extends GwtEvent<AbrirRegiaoEventHandler> {
	
	public static Type<AbrirRegiaoEventHandler> TYPE = new Type<AbrirRegiaoEventHandler>();

	@Override
	protected void dispatch(AbrirRegiaoEventHandler handler) {
		handler.onAbrirRegiao(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AbrirRegiaoEventHandler> getAssociatedType() {
		return TYPE;
	}

}
