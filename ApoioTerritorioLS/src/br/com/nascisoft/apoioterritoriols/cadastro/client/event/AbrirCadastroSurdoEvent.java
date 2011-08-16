package br.com.nascisoft.apoioterritoriols.cadastro.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AbrirCadastroSurdoEvent extends GwtEvent<AbrirCadastroSurdoEventHandler> {
	
	public static Type<AbrirCadastroSurdoEventHandler> TYPE = new Type<AbrirCadastroSurdoEventHandler>();

	@Override
	protected void dispatch(AbrirCadastroSurdoEventHandler handler) {
		handler.onAbrirCadastroSurdo(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AbrirCadastroSurdoEventHandler> getAssociatedType() {
		return TYPE;
	}

}
