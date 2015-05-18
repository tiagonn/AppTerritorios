package br.com.nascisoft.apoioterritoriols.admin.client.view;

import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;
import br.com.nascisoft.apoioterritoriols.resources.client.ApoioTerritorioLSConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AdminBackupViewImpl extends AbstractAdminViewImpl implements AdminBackupView {

	private static AdminViewUiBinderUiBinder uiBinder = GWT
			.create(AdminViewUiBinderUiBinder.class);
	
	private AdminBackupView.Presenter presenter;
	@UiField TextBox destinatario;
	@UiField FormPanel restauracaoFormPanel;
	@UiField SubmitButton restauracaoSubmeterButton;

	@UiField Button botaoBackup;
	@UiField Button botaoExport;
	
	@UiTemplate("AdminViewUiBinder.ui.xml")
	interface AdminViewUiBinderUiBinder extends
			UiBinder<Widget, AdminBackupViewImpl> {
	}

	public AdminBackupViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
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
		this.adminTabLayoutPanel.selectTab(5, false);
		
	}

	@Override
	public void setPresenter(AdminBackupView.Presenter presenter) {
		this.presenter = presenter;
	}
	
	private void limparFormularios() {
		this.destinatario.setText("");
	}
	
	@UiHandler("botaoBackup")
	void onBotaoBackupClick(ClickEvent event) {
		if (!StringUtils.isEmpty(destinatario.getText())) {
			this.presenter.dispararBackup(destinatario.getText(), "completo"); 
		} else {
			this.mostrarWarning(
					"O campo destinatário deve ser preenchido com um e-mail.",
					ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
		}
	}

	@UiHandler("botaoExport")
	void onBotaoExportClick(ClickEvent event) {
		if (!StringUtils.isEmpty(destinatario.getText())) {
			this.presenter.dispararBackup(destinatario.getText(), "enderecos"); 
		} else {
			this.mostrarWarning(
					"O campo destinatário deve ser preenchido com um e-mail.",
					ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
		}
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
