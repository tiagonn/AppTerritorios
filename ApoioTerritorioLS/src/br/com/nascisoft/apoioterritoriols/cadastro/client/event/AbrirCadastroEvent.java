package br.com.nascisoft.apoioterritoriols.cadastro.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AbrirCadastroEvent extends GwtEvent<AbrirCadastroEventHandler> {
	
	public static Type<AbrirCadastroEventHandler> TYPE = new Type<AbrirCadastroEventHandler>();

	@Override
	protected void dispatch(AbrirCadastroEventHandler handler) {
		handler.onAbrirCadastro(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AbrirCadastroEventHandler> getAssociatedType() {
		return TYPE;
	}

}
