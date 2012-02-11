package br.com.nascisoft.apoioterritoriols.admin.client.presenter;

import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.admin.client.AdminServiceAsync;
import br.com.nascisoft.apoioterritoriols.admin.client.view.AdminView;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public abstract class AbstractAdminPresenter implements AdminPresenter, AdminView.Presenter {
	
	protected final HandlerManager eventBus;
	protected final AdminServiceAsync service;
	
	public AbstractAdminPresenter(AdminServiceAsync service, HandlerManager eventBus) {
		this.service = service;
		this.eventBus = eventBus;
	}
	
	abstract AdminView getView();
	
	protected static final Logger logger = Logger.getLogger(AbstractAdminPresenter.class.getName());
	
	public void go(HasWidgets container) {
		container.clear();
		container.add(getView().asWidget());
	}
	
	public void initView() {
		getView().initView();
	}
	
	public void selectThisTab() {
		getView().selectThisTab();
	}

}
