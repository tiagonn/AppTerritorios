package br.com.nascisoft.apoioterritoriols.admin.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AbrirBairroEvent extends GwtEvent<AbrirBairroEventHandler> {
	
	public static Type<AbrirBairroEventHandler> TYPE = new Type<AbrirBairroEventHandler>();

	@Override
	protected void dispatch(AbrirBairroEventHandler handler) {
		handler.onAbrirBairro(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AbrirBairroEventHandler> getAssociatedType() {
		return TYPE;
	}

}
