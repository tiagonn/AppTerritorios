package br.com.nascisoft.apoioterritoriols.admin.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AbrirRelatorioEvent extends GwtEvent<AbrirRelatorioEventHandler> {
	
	public static Type<AbrirRelatorioEventHandler> TYPE = new Type<AbrirRelatorioEventHandler>();

	@Override
	protected void dispatch(AbrirRelatorioEventHandler handler) {
		handler.onAbrirRelatorio(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AbrirRelatorioEventHandler> getAssociatedType() {
		return TYPE;
	}

}
