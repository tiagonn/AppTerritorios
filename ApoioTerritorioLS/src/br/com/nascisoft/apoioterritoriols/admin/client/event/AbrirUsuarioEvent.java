package br.com.nascisoft.apoioterritoriols.admin.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AbrirUsuarioEvent extends GwtEvent<AbrirUsuarioEventHandler> {
	
	public static Type<AbrirUsuarioEventHandler> TYPE = new Type<AbrirUsuarioEventHandler>();

	@Override
	protected void dispatch(AbrirUsuarioEventHandler handler) {
		handler.onAbrirUsuario(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AbrirUsuarioEventHandler> getAssociatedType() {
		return TYPE;
	}

}
