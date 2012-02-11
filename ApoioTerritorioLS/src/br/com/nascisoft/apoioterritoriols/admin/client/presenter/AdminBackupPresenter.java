package br.com.nascisoft.apoioterritoriols.admin.client.presenter;

import br.com.nascisoft.apoioterritoriols.admin.client.AdminServiceAsync;
import br.com.nascisoft.apoioterritoriols.admin.client.view.AdminBackupView;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;

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

}
