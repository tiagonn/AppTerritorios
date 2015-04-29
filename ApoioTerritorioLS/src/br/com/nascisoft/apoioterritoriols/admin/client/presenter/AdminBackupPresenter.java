package br.com.nascisoft.apoioterritoriols.admin.client.presenter;

import java.util.logging.Level;

import br.com.nascisoft.apoioterritoriols.admin.client.AdminServiceAsync;
import br.com.nascisoft.apoioterritoriols.admin.client.view.AdminBackupView;
import br.com.nascisoft.apoioterritoriols.resources.client.ApoioTerritorioLSConstants;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AdminBackupPresenter extends AbstractAdminPresenter
		implements AdminBackupView.Presenter {
	
	private final AdminBackupView view;
	
	public AdminBackupPresenter(AdminServiceAsync service,
			HandlerManager eventBus, AdminBackupView view) {
		super(service, eventBus);
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	AdminBackupView getView() {
		return this.view;
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.view.setTabSelectionEventHandler(handler);
	}

	@Override
	public void dispararBackup(String destinatario, final String tipo) {
		this.getView().showWaitingPanel();
		this.service.dispararBackup(destinatario, tipo, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				getView().hideWaitingPanel();
				getView().mostrarWarning(
						"enderecos".equals(tipo)?"Export disparado com sucesso":"Backup disparado com sucesso",
						ApoioTerritorioLSConstants.WARNING_TIMEOUT);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao disparar o backup/export.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning(
						"Falha ao obter disparar o backup/export. \n" + caught.getMessage(),
						ApoioTerritorioLSConstants.WARNING_TIMEOUT);
			}
		});
	}
	
	public void obterUploadAction() {
		this.getView().showWaitingPanel();
		this.service.obterUploadAction(new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao recuperar a action de upload.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning(
						"Falha ao recuperar a action de upload. \n" + caught.getMessage(),
						ApoioTerritorioLSConstants.WARNING_TIMEOUT);
			}
			
			@Override
			public void onSuccess(String result) {
				getView().setAction(result);	
				getView().hideWaitingPanel();
			}
		});
	}

}
