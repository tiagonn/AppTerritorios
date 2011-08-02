package br.com.nascisoft.apoioterritoriols.cadastro.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirCadastroEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirCadastroEventHandler;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirCadastroMapaEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirCadastroMapaEventHandler;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirImpressaoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirImpressaoEventHandler;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirImpressaoMapaEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirImpressaoMapaEventHandler;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirMapaEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirMapaEventHandler;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AdicionarSurdoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AdicionarSurdoEventHandler;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.EditarSurdoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.EditarSurdoEventHandler;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.PesquisarSurdoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.PesquisarSurdoEventHandler;
import br.com.nascisoft.apoioterritoriols.cadastro.client.presenter.CadastroMapaPresenter;
import br.com.nascisoft.apoioterritoriols.cadastro.client.presenter.CadastroSurdoPresenter;
import br.com.nascisoft.apoioterritoriols.cadastro.client.presenter.ImpressaoPresenter;
import br.com.nascisoft.apoioterritoriols.cadastro.client.presenter.Presenter;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.CadastroMapaViewImpl;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.CadastroSurdoViewImpl;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.ImpressaoViewImpl;

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

public class CadastroController implements Presenter,
		ValueChangeHandler<String> {

	private final HandlerManager eventBus;
	private final CadastroServiceAsync service;
	private HasWidgets container;
	private CadastroSurdoViewImpl cadastroSurdoView = null;
	private CadastroSurdoPresenter cadastroSurdoPresenter = null;
	private CadastroMapaPresenter cadastroMapaPresenter = null;
	private CadastroMapaViewImpl cadastroMapaView = null;
	private ImpressaoPresenter impressaoPresenter = null;
	private ImpressaoViewImpl impressaoView = null;
	private String currentToken = null;
	private SelectionHandler<Integer> selectionHandler = null;

	private static final Logger logger = Logger
			.getLogger(CadastroController.class.getName());

	public CadastroController(CadastroServiceAsync service,
			HandlerManager eventBusParam) {
		this.service = service;
		this.eventBus = eventBusParam;
		this.selectionHandler = new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if (event.getSelectedItem() == 0) {
					eventBus.fireEvent(new AbrirCadastroEvent());
				} else if (event.getSelectedItem() == 1) {
					eventBus.fireEvent(new AbrirCadastroMapaEvent());
				} else if (event.getSelectedItem() == 2) {
					eventBus.fireEvent(new AbrirImpressaoEvent());
				}
			}
		};
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(AbrirCadastroEvent.TYPE,
				new AbrirCadastroEventHandler() {
					@Override
					public void onAbrirCadastro(AbrirCadastroEvent event) {
						History.newItem("abrir");
					}
				});

		eventBus.addHandler(PesquisarSurdoEvent.TYPE,
				new PesquisarSurdoEventHandler() {
					@Override
					public void onPesquisarSurdo(PesquisarSurdoEvent event) {
						History.newItem("prepararPesquisa");
						History.newItem("pesquisar");
					}
				});

		eventBus.addHandler(AdicionarSurdoEvent.TYPE,
				new AdicionarSurdoEventHandler() {
					@Override
					public void onAdicionarSurdo(AdicionarSurdoEvent event) {
						History.newItem("adicionar");
					}
				});

		eventBus.addHandler(EditarSurdoEvent.TYPE,
				new EditarSurdoEventHandler() {
					@Override
					public void onEditarSurdo(EditarSurdoEvent event) {
						History.newItem("editar!" + event.getId());
					}
				});

		eventBus.addHandler(AbrirCadastroMapaEvent.TYPE,
				new AbrirCadastroMapaEventHandler() {
					@Override
					public void onAbrirCadastroMapa(AbrirCadastroMapaEvent event) {
						History.newItem("abrirMapa");
					}
				});
		
		eventBus.addHandler(AbrirMapaEvent.TYPE, 
				new AbrirMapaEventHandler() {
					
					@Override
					public void onAbrirMapa(AbrirMapaEvent event) {
						History.newItem("prepararAbrirMapaEspecifico");
						History.newItem("abrirMapa!" + event.getIdentificadorMapa());
					}
				});
		
		eventBus.addHandler(AbrirImpressaoEvent.TYPE, new AbrirImpressaoEventHandler() {
			
			@Override
			public void onAbrirImpressao(AbrirImpressaoEvent event) {
				History.newItem("abrirImpressao");
			}
		});
		
		eventBus.addHandler(AbrirImpressaoMapaEvent.TYPE, new AbrirImpressaoMapaEventHandler() {
			
			@Override
			public void onAbrirImpressaoMapa(AbrirImpressaoMapaEvent event) {
				History.newItem("abrirImpressaoMapa!" + event.getIdentificadorMapa());
			}
		});
	}

	@Override
	public void go(HasWidgets container) {
		this.container = container;
		if ("".equals(History.getToken())) {
			History.newItem("abrir");
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
					
						// Aba Surdo
					if ("abrir".equals(currentToken)) {
						if (cadastroSurdoView == null) {
							cadastroSurdoView = new CadastroSurdoViewImpl();
						}
						if (cadastroSurdoPresenter == null) {
							cadastroSurdoPresenter = new CadastroSurdoPresenter(
									service, eventBus, cadastroSurdoView);
							cadastroSurdoPresenter
									.setTabSelectionEventHandler(selectionHandler);
						}
						cadastroSurdoPresenter.go(container);
					} else if ("pesquisar".equals(currentToken)) {
						cadastroSurdoPresenter.onPesquisar();
					} else if ("adicionar".equals(currentToken)) {
						cadastroSurdoPresenter.onAdicionar();
					} else if (currentToken.startsWith("editar!")) {
						Long id = Long.valueOf(currentToken.split("!")[1]);
						cadastroSurdoPresenter.onEditar(id);
						
						
						
						// Aba Mapa
					} else if ("abrirMapa".equals(currentToken)) {
						if (cadastroMapaView == null) {
							cadastroMapaView = new CadastroMapaViewImpl();
						}
						if (cadastroMapaPresenter == null) {
							cadastroMapaPresenter = new CadastroMapaPresenter(
									service, eventBus, cadastroMapaView);
							cadastroMapaPresenter.setTabSelectionEventHandler(selectionHandler);
						}
						cadastroMapaPresenter.go(container);
					} else if (currentToken.startsWith("abrirMapa!")) {
						cadastroMapaPresenter.onAbrirMapa(Long.valueOf(currentToken.split("!")[1]));
					
					
					
						// Aba Impressao
					} else if ("abrirImpressao".equals(currentToken)) {
						if (impressaoView == null) {
							impressaoView = new ImpressaoViewImpl();
						}
						if (impressaoPresenter == null) {
							impressaoPresenter = new ImpressaoPresenter(service, eventBus, impressaoView);
							impressaoPresenter.setTabSelectionEventHandler(selectionHandler);
						}
						impressaoPresenter.go(container);
					} else if (currentToken.startsWith("abrirImpressaoMapa!")) {
						impressaoPresenter.onAbrirImpressao(Long.valueOf(currentToken.split("!")[1]));
					}
				}

				@Override
				public void onFailure(Throwable reason) {
					logger.log(Level.SEVERE, "Falha ao inicializar view",
							reason);
					Window.alert("Falha ao inicializar view\n"
							+ reason.getMessage());
				}
			});
		}
	}
}
