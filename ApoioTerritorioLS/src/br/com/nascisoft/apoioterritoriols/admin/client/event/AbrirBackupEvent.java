package br.com.nascisoft.apoioterritoriols.admin.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AbrirBackupEvent extends GwtEvent<AbrirBackupEventHandler> {
	
	public static Type<AbrirBackupEventHandler> TYPE = new Type<AbrirBackupEventHandler>();

	@Override
	protected void dispatch(AbrirBackupEventHandler handler) {
		handler.onAbrirBackup(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AbrirBackupEventHandler> getAssociatedType() {
		return TYPE;
	}

}
