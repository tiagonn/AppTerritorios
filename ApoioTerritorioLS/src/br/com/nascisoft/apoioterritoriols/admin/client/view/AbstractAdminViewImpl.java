package br.com.nascisoft.apoioterritoriols.admin.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public abstract class AbstractAdminViewImpl extends Composite implements AdminView {
	
	@UiField PopupPanel warningPopUpPanel;
	@UiField PopupPanel confirmationPopUpPanel;
	@UiField Label confirmationMessageLabel;
	@UiField PushButton confirmationBackPushButton;
	@UiField PushButton confirmationConfirmPushButton;
	@UiField PopupPanel waitingPopUpPanel;
	@UiField TabLayoutPanel adminTabLayoutPanel;


	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.adminTabLayoutPanel.addSelectionHandler(handler);		
	}
	
	@Override
	public void mostrarWarning(String msgSafeHtml, int timeout) {
		this.warningPopUpPanel.setPopupPosition(Window.getClientWidth()-440, 20);
		this.warningPopUpPanel.clear();
		this.warningPopUpPanel.add(new HTML(msgSafeHtml));
		this.warningPopUpPanel.setVisible(true);
		this.warningPopUpPanel.show();
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				warningPopUpPanel.hide();
				warningPopUpPanel.setVisible(false);
			}
		};
		timer.schedule(timeout);
		
	}
	
	@Override
	public void mostrarConfirmacao(String mensagem, ClickHandler acao) {
		this.confirmationMessageLabel.setText(mensagem);
		this.confirmationConfirmPushButton.addClickHandler(acao);
		this.confirmationPopUpPanel.setVisible(true);
		this.confirmationPopUpPanel.show();
		this.confirmationPopUpPanel.setPopupPosition(
				(Window.getClientWidth() - this.confirmationPopUpPanel.getOffsetWidth() ) / 2,
				( Window.getClientHeight() - this.confirmationPopUpPanel.getOffsetHeight() ) / 2);
	}
	
	@UiHandler(value={"confirmationBackPushButton", "confirmationConfirmPushButton"})
	void onConfirmationBackPushButtonClick(ClickEvent event) {
		this.confirmationPopUpPanel.hide();
		this.confirmationPopUpPanel.setVisible(false);
	}
	
	@Override
	public void showWaitingPanel() {
		waitingPopUpPanel.setVisible(true);
		waitingPopUpPanel.show();		
	}

	@Override
	public void hideWaitingPanel() {
		waitingPopUpPanel.hide();
		waitingPopUpPanel.setVisible(false);
	}
	
}
