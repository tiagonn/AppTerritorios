package br.com.nascisoft.apoioterritoriols.admin.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.admin.client.event.AbrirBackupEvent;
import br.com.nascisoft.apoioterritoriols.admin.client.event.AbrirBackupEventHandler;
import br.com.nascisoft.apoioterritoriols.admin.client.presenter.AdminBackupPresenter;
import br.com.nascisoft.apoioterritoriols.admin.client.presenter.AdminPresenter;
import br.com.nascisoft.apoioterritoriols.admin.client.view.AdminBackupViewImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;

public class AdminController implements AdminPresenter,
		ValueChangeHandler<String> {

	private final HandlerManager eventBus;
	private final AdminServiceAsync service;
	private HasWidgets container;
	private String currentToken = null;
	private SelectionHandler<Integer> selectionHandler = new SelectionHandler<Integer>() {
		@Override
		public void onSelection(SelectionEvent<Integer> event) {
			if (event.getSelectedItem() == 0) {
				eventBus.fireEvent(new AbrirBackupEvent());
			}
		}
	};
	private AdminBackupPresenter adminBackupPresenter;
	private AdminBackupViewImpl adminBackupViewImpl;

	private static final Logger logger = Logger
			.getLogger(AdminController.class.getName());

	public AdminController(AdminServiceAsync service,
			HandlerManager eventBusParam) {
		this.service = service;
		this.eventBus = eventBusParam;
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);
		
		eventBus.addHandler(AbrirBackupEvent.TYPE,
				new AbrirBackupEventHandler() {
					
					@Override
					public void onAbrirBackup(AbrirBackupEvent event) {
						History.newItem("backup");
					}
				});
	}

	@Override
	public void go(HasWidgets container) {
		this.container = container;
		if ("".equals(History.getToken())) {
			History.newItem("backup");
		} else {
			History.fireCurrentHistoryState();
		}
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		currentToken = event.getValue();
		if (currentToken != null) {
			GWT.runAsync(new RunAsyncCallback() {
				@Override
				public void onSuccess() {
					try {

						if (currentToken.startsWith("backup")) {
							if (adminBackupViewImpl == null) {
								adminBackupViewImpl = new AdminBackupViewImpl();
							}
							if (adminBackupPresenter == null) {
								adminBackupPresenter = new AdminBackupPresenter(
										service, eventBus, adminBackupViewImpl);
								adminBackupPresenter.setTabSelectionEventHandler(selectionHandler);
							}
							adminBackupPresenter.selectThisTab();
							
								// aba BACKUP
							if ("backup".equals(currentToken)) {
								adminBackupPresenter.initView();
							}
							
							adminBackupPresenter.go(container);
						}
					} catch (Exception ex) {
						logger.log(Level.SEVERE, "Falha ao responder a requisição.\n", ex);
						Window.alert("Falha ao responder a requisição\n" + ex);
					}
				}

				@Override
				public void onFailure(Throwable reason) {
					logger.log(Level.SEVERE, "Falha ao inicializar view.\n",
							reason);
					Window.alert("Falha ao inicializar view\n"
							+ reason.getMessage());
				}
			});
		}
	}
}
