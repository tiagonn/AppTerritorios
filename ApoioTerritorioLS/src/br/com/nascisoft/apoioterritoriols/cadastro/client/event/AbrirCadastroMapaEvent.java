package br.com.nascisoft.apoioterritoriols.cadastro.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AbrirCadastroMapaEvent extends GwtEvent<AbrirCadastroMapaEventHandler> {
	
	public static Type<AbrirCadastroMapaEventHandler> TYPE = new Type<AbrirCadastroMapaEventHandler>();

	@Override
	protected void dispatch(AbrirCadastroMapaEventHandler handler) {
		handler.onAbrirCadastroMapa(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AbrirCadastroMapaEventHandler> getAssociatedType() {
		return TYPE;
	}

}
