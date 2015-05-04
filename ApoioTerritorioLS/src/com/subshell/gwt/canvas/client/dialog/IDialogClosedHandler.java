package com.subshell.gwt.canvas.client.dialog;

import com.google.gwt.event.shared.EventHandler;

public interface IDialogClosedHandler extends EventHandler {
	void dialogClosed(DialogClosedEvent event);
}
