package br.com.nascisoft.apoioterritoriols.admin.client.view;

import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AdminBackupViewImpl extends Composite implements AdminBackupView {

	private static AdminViewUiBinderUiBinder uiBinder = GWT
			.create(AdminViewUiBinderUiBinder.class);
	
	private Presenter presenter;
	@UiField TabLayoutPanel adminTabLayoutPanel;
	@UiField PopupPanel waitingPopUpPanel;
	@UiField TextBox destinatario;
	@UiField FormPanel restauracaoFormPanel;
	@UiField SubmitButton restauracaoSubmeterButton;

	@UiField Button botaoBackup;
	
	@UiTemplate("AdminViewUiBinder.ui.xml")
	interface AdminViewUiBinderUiBinder extends
			UiBinder<Widget, AdminBackupViewImpl> {
	}

	public AdminBackupViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
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

	@Override
	public void initView() {
		this.selectThisTab();
		this.limparFormularios();
		this.presenter.obterUploadAction();
		this.restauracaoFormPanel.setMethod(FormPanel.METHOD_POST);
		this.restauracaoFormPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		
	}

	@Override
	public void selectThisTab() {
		this.adminTabLayoutPanel.selectTab(0, false);
		
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.adminTabLayoutPanel.addSelectionHandler(handler);		
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	private void limparFormularios() {
		this.destinatario.setText("");
	}

	@UiHandler("botaoBackup")
	void onBotaoBackupClick(ClickEvent event) {
		if (!StringUtils.isEmpty(destinatario.getText())) {
			this.presenter.dispararBackup(destinatario.getText()); 
		} else {
			Window.alert("O campo destinatário deve ser preenchido com um e-mail.");
		}
	}
	
	@UiHandler("usuarioAdicionarButton")
	void onUsuarioAdicionarButtonClick(ClickEvent event) {
	}

	@Override
	public void setAction(String action) {
		this.restauracaoFormPanel.setAction(action);		
	}
	
	@UiHandler("restauracaoSubmeterButton")
	void onrestauracaoSubmeterButtonClick(ClickEvent event) {
		this.restauracaoFormPanel.submit();
		Window.alert("Restauração submetida com sucesso");
	}
}
