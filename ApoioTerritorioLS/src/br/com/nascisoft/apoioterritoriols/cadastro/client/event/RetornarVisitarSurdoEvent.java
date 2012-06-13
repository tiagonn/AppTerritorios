package br.com.nascisoft.apoioterritoriols.cadastro.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class RetornarVisitarSurdoEvent extends GwtEvent<RetornarVisitarSurdoEventHandler> {
	
	public static Type<RetornarVisitarSurdoEventHandler> TYPE = new Type<RetornarVisitarSurdoEventHandler>();
	
	private Long id;
	
	public RetornarVisitarSurdoEvent(Long id) {
		this.setId(id);
	}

	@Override
	protected void dispatch(RetornarVisitarSurdoEventHandler handler) {
		handler.onRetornarVisitarSurdo(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RetornarVisitarSurdoEventHandler> getAssociatedType() {
		return TYPE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
