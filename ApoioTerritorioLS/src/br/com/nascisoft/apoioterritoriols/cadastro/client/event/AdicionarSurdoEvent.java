package br.com.nascisoft.apoioterritoriols.cadastro.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AdicionarSurdoEvent extends GwtEvent<AdicionarSurdoEventHandler> {
	
	public static Type<AdicionarSurdoEventHandler> TYPE = new Type<AdicionarSurdoEventHandler>();

	@Override
	protected void dispatch(AdicionarSurdoEventHandler handler) {
		handler.onAdicionarSurdo(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AdicionarSurdoEventHandler> getAssociatedType() {
		return TYPE;
	}

}
