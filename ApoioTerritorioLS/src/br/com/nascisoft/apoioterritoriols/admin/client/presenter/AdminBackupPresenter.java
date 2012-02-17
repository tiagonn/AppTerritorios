package br.com.nascisoft.apoioterritoriols.admin.client.presenter;

import java.util.logging.Level;

import br.com.nascisoft.apoioterritoriols.admin.client.AdminServiceAsync;
import br.com.nascisoft.apoioterritoriols.admin.client.view.AdminBackupView;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
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
	public void dispararBackup(String destinatario) {
		this.getView().showWaitingPanel();
		this.service.dispararBackup(destinatario, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				getView().hideWaitingPanel();
				Window.alert("Backup disparado com sucesso");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao disparar o backup.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao obter disparar o backup. \n" + caught.getMessage());
			}
		});
	}

}
