package br.com.nascisoft.apoioterritoriols.cadastro.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class PesquisarSurdoEvent extends GwtEvent<PesquisarSurdoEventHandler> {
	public static Type<PesquisarSurdoEventHandler> TYPE = new Type<PesquisarSurdoEventHandler>();

	@Override
	protected void dispatch(PesquisarSurdoEventHandler handler) {
		handler.onPesquisarSurdo(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PesquisarSurdoEventHandler> getAssociatedType() {
		return TYPE;
	}

}
