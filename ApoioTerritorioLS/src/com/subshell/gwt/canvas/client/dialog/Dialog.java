package com.subshell.gwt.canvas.client.dialog;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class Dialog extends DialogBox {
	private ClickHandler buttonClickHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			buttonClicked((Widget) event.getSource());
		}
	};
	private Widget dialogArea;
	private Button okButton;
	private Button cancelButton;
	
	public Dialog() {
		VerticalPanel panel = new VerticalPanel();
		dialogArea = createDialogArea();
		panel.add(dialogArea);
		panel.add(createButtonBar());
		setWidget(panel);
	}
	
	public HandlerRegistration addDialogClosedHandler(IDialogClosedHandler handler) {
		return addHandler(handler, DialogClosedEvent.getType());
	}

	protected void close(boolean canceled) {
		hide();
		fireDialogClosed(canceled);
	}

	private void fireDialogClosed(boolean canceled) {
		fireEvent(new DialogClosedEvent(canceled));
	}

	protected Widget createButtonBar() {
		FlowPanel buttonsPanel = new FlowPanel();
		buttonsPanel.getElement().getStyle().setDisplay(Display.TABLE);
		buttonsPanel.getElement().getStyle().setWidth(100, Unit.PCT);
		
		FlowPanel voltarPanel = new FlowPanel();
		voltarPanel.getElement().getStyle().setDisplay(Display.TABLE_CELL);
		voltarPanel.getElement().getStyle().setWidth(50, Unit.PCT);
		
		cancelButton = createButton("Voltar");
		voltarPanel.add(cancelButton);
		
		FlowPanel selecionarPanel = new FlowPanel();
		selecionarPanel.getElement().getStyle().setDisplay(Display.TABLE_CELL);
		selecionarPanel.getElement().getStyle().setWidth(50, Unit.PCT);
		selecionarPanel.getElement().getStyle().setTextAlign(TextAlign.RIGHT);
		
		okButton = createButton("Selecionar");
		selecionarPanel.add(okButton);
		
		buttonsPanel.add(voltarPanel);
		buttonsPanel.add(selecionarPanel);

		return buttonsPanel;
	}

	protected Button createButton(String text) {
		return new Button(text, buttonClickHandler);
	}

	protected abstract Widget createDialogArea();
	
	protected Widget getDialogArea() {
		return dialogArea;
	}

	protected abstract void buttonClicked(Widget button);
	
	protected Button getOkButton() {
		return okButton;
	}
	
	protected Button getCancelButton() {
		return cancelButton;
	}
}
