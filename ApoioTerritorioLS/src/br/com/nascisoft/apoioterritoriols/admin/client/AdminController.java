package br.com.nascisoft.apoioterritoriols.admin.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.admin.client.event.AbrirBackupEvent;
import br.com.nascisoft.apoioterritoriols.admin.client.event.AbrirBackupEventHandler;
import br.com.nascisoft.apoioterritoriols.admin.client.event.AbrirCidadeEvent;
import br.com.nascisoft.apoioterritoriols.admin.client.event.AbrirCidadeEventHandler;
import br.com.nascisoft.apoioterritoriols.admin.client.event.AbrirRegiaoEvent;
import br.com.nascisoft.apoioterritoriols.admin.client.event.AbrirRegiaoEventHandler;
import br.com.nascisoft.apoioterritoriols.admin.client.event.AbrirUsuarioEvent;
import br.com.nascisoft.apoioterritoriols.admin.client.event.AbrirUsuarioEventHandler;
import br.com.nascisoft.apoioterritoriols.admin.client.presenter.AdminBackupPresenter;
import br.com.nascisoft.apoioterritoriols.admin.client.presenter.AdminCidadePresenter;
import br.com.nascisoft.apoioterritoriols.admin.client.presenter.AdminPresenter;
import br.com.nascisoft.apoioterritoriols.admin.client.presenter.AdminRegiaoPresenter;
import br.com.nascisoft.apoioterritoriols.admin.client.presenter.AdminUsuarioPresenter;
import br.com.nascisoft.apoioterritoriols.admin.client.view.AdminBackupViewImpl;
import br.com.nascisoft.apoioterritoriols.admin.client.view.AdminCidadeViewImpl;
import br.com.nascisoft.apoioterritoriols.admin.client.view.AdminRegiaoViewImpl;
import br.com.nascisoft.apoioterritoriols.admin.client.view.AdminUsuarioViewImpl;

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
			} else if (event.getSelectedItem() == 1) {
				eventBus.fireEvent(new AbrirUsuarioEvent());
			} else if (event.getSelectedItem() == 2) {
				eventBus.fireEvent(new AbrirCidadeEvent());
			} else if (event.getSelectedItem() == 3) {
				eventBus.fireEvent(new AbrirRegiaoEvent());
			}
		}
	};
	private AdminBackupPresenter adminBackupPresenter;
	private AdminBackupViewImpl adminBackupViewImpl;
	private AdminUsuarioPresenter adminUsuarioPresenter;
	private AdminUsuarioViewImpl adminUsuarioViewImpl;
	private AdminCidadePresenter adminCidadePresenter;
	private AdminCidadeViewImpl adminCidadeViewImpl;
	private AdminRegiaoPresenter adminRegiaoPresenter;
	private AdminRegiaoViewImpl adminRegiaoViewImpl;

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
		
		eventBus.addHandler(AbrirUsuarioEvent.TYPE, 
				new AbrirUsuarioEventHandler() {
			
					@Override
					public void onAbrirUsuario(AbrirUsuarioEvent event) {
						History.newItem("usuario");
					}
					
			});
		
		eventBus.addHandler(AbrirCidadeEvent.TYPE, 
				new AbrirCidadeEventHandler() {
			
					@Override
					public void onAbrirCidade(AbrirCidadeEvent event) {
						History.newItem("cidade");
					}
		});
		
		eventBus.addHandler(AbrirRegiaoEvent.TYPE, 
				new AbrirRegiaoEventHandler() {
					
					@Override
					public void onAbrirRegiao(AbrirRegiaoEvent event) {
						History.newItem("regiao");
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
							// aba BACKUP
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
							
							if ("backup".equals(currentToken)) {
								adminBackupPresenter.initView();
							}
							
							adminBackupPresenter.go(container);
							
							// aba USUARIOS
						} else if (currentToken.startsWith("usuario")) {
							if (adminUsuarioViewImpl == null) {
								adminUsuarioViewImpl = new AdminUsuarioViewImpl();
							}
							if (adminUsuarioPresenter == null) {
								adminUsuarioPresenter = new AdminUsuarioPresenter(service, eventBus, adminUsuarioViewImpl);
								adminUsuarioPresenter.setTabSelectionEventHandler(selectionHandler);
							}
							adminUsuarioPresenter.selectThisTab();
							
							if ("usuario".equals(currentToken)) {
								adminUsuarioPresenter.initView();
							}
							adminUsuarioPresenter.go(container);
							
							// aba CIDADES
						} else if (currentToken.startsWith("cidade")) {
							if (adminCidadeViewImpl == null) {
								adminCidadeViewImpl = new AdminCidadeViewImpl();
							}
							if (adminCidadePresenter == null) {
								adminCidadePresenter = new AdminCidadePresenter(service, eventBus, adminCidadeViewImpl);
								adminCidadePresenter.setTabSelectionEventHandler(selectionHandler);
							}
							adminCidadePresenter.selectThisTab();

							if ("cidade".equals(currentToken)) {
								adminCidadePresenter.initView();
							}
							adminCidadePresenter.go(container);
							
							// aba REGIÕES
						} else if (currentToken.startsWith("regiao")) {
							if (adminRegiaoViewImpl == null) {
								adminRegiaoViewImpl = new AdminRegiaoViewImpl();
							}
							if (adminRegiaoPresenter == null) {
								adminRegiaoPresenter = new AdminRegiaoPresenter(service, eventBus, adminRegiaoViewImpl);
								adminRegiaoPresenter.setTabSelectionEventHandler(selectionHandler);
							}
							adminRegiaoPresenter.selectThisTab();
							
							if ("regiao".equals(currentToken)) {
								adminRegiaoPresenter.initView();
							}
							adminRegiaoPresenter.go(container);
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
