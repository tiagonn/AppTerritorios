package br.com.nascisoft.apoioterritoriols.cadastro.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditarSurdoEvent extends GwtEvent<EditarSurdoEventHandler> {
	
	public static Type<EditarSurdoEventHandler> TYPE = new Type<EditarSurdoEventHandler>();
	private Long id;
	
	public EditarSurdoEvent(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	@Override
	protected void dispatch(EditarSurdoEventHandler handler) {
		handler.onEditarSurdo(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<EditarSurdoEventHandler> getAssociatedType() {
		return TYPE;
	}

}
