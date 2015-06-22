package br.com.nascisoft.apoioterritoriols.cadastro.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirCadastroMapaEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirCadastroMapaEventHandler;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirCadastroSurdoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirCadastroSurdoEventHandler;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirImpressaoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirImpressaoEventHandler;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirImpressaoMapaEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirImpressaoMapaEventHandler;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirMapaEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirMapaEventHandler;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirNaoVisitarEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirNaoVisitarEventHandler;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AdicionarSurdoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AdicionarSurdoEventHandler;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.EditarSurdoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.EditarSurdoEventHandler;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.PesquisarSurdoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.PesquisarSurdoEventHandler;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.RetornarVisitarSurdoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.RetornarVisitarSurdoEventHandler;
import br.com.nascisoft.apoioterritoriols.cadastro.client.presenter.CadastroMapaPresenter;
import br.com.nascisoft.apoioterritoriols.cadastro.client.presenter.CadastroPresenter;
import br.com.nascisoft.apoioterritoriols.cadastro.client.presenter.CadastroSurdoPresenter;
import br.com.nascisoft.apoioterritoriols.cadastro.client.presenter.ImpressaoPresenter;
import br.com.nascisoft.apoioterritoriols.cadastro.client.presenter.NaoVisitarPresenter;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.CadastroMapaViewImpl;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.CadastroSurdoViewImpl;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.ImpressaoViewImpl;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.NaoVisitarViewImpl;
import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;
import br.com.nascisoft.apoioterritoriols.login.vo.LoginVO;

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

public class CadastroController implements CadastroPresenter,
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
	private NaoVisitarPresenter naoVisitarPresenter = null;
	private NaoVisitarViewImpl naoVisitarView = null;
	private LoginVO login;
	
	private String currentToken = null;
	private SelectionHandler<Integer> selectionHandler = new SelectionHandler<Integer>() {
		@Override
		public void onSelection(SelectionEvent<Integer> event) {
			if (event.getSelectedItem() == 0) {
				eventBus.fireEvent(new AbrirCadastroSurdoEvent());
			} else if (event.getSelectedItem() == 1) {
				eventBus.fireEvent(new AbrirCadastroMapaEvent());
			} else if (event.getSelectedItem() == 2) {
				eventBus.fireEvent(new AbrirImpressaoEvent());
			} else if (event.getSelectedItem() == 3) {
				eventBus.fireEvent(new AbrirNaoVisitarEvent());
			} else if (event.getSelectedItem() == 4) {
				Window.open("/Admin.html", "_self", "");
			}
		}
	};;

	private static final Logger logger = Logger
			.getLogger(CadastroController.class.getName());

	public CadastroController(CadastroServiceAsync service,
			HandlerManager eventBusParam, LoginVO login) {
		this.service = service;
		this.eventBus = eventBusParam;
		this.login = login;
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(AbrirCadastroSurdoEvent.TYPE,
				new AbrirCadastroSurdoEventHandler() {
					@Override
					public void onAbrirCadastroSurdo(AbrirCadastroSurdoEvent event) {
						History.newItem("surdos");
					}
				});

		eventBus.addHandler(PesquisarSurdoEvent.TYPE,
				new PesquisarSurdoEventHandler() {
					@Override
					public void onPesquisarSurdo(PesquisarSurdoEvent event) {
						History.newItem("surdos!pesquisar#" +
								"identificadorCidade="+event.getIdentificadorCidade()+
								"&identificadorRegiao="+event.getNomeRegiao()+
								"&nomeSurdo="+event.getNomeSurdo()+
								"&identificadorMapa="+event.getIdentificadorMapa()+
								"&estaAssociadoMapa="+event.getEstaAssociadoMapa()+
								"&dispararPesquisa="+event.getDispararPesquisa());					
					}
				});

		eventBus.addHandler(AdicionarSurdoEvent.TYPE,
				new AdicionarSurdoEventHandler() {
					@Override
					public void onAdicionarSurdo(AdicionarSurdoEvent event) {
						History.newItem("surdos!adicionar");
					}
				});

		eventBus.addHandler(EditarSurdoEvent.TYPE,
				new EditarSurdoEventHandler() {
					@Override
					public void onEditarSurdo(EditarSurdoEvent event) {
						History.newItem("surdos!editar#identificadorSurdo=" + event.getId());
					}
				});

		eventBus.addHandler(AbrirCadastroMapaEvent.TYPE,
				new AbrirCadastroMapaEventHandler() {
					@Override
					public void onAbrirCadastroMapa(AbrirCadastroMapaEvent event) {
						History.newItem("mapas");
					}
				});
		
		eventBus.addHandler(AbrirMapaEvent.TYPE, 
				new AbrirMapaEventHandler() {
					
					@Override
					public void onAbrirMapa(AbrirMapaEvent event) {
						History.newItem("mapas!abrir#identificadorMapa=" + event.getIdentificadorMapa(), false);
						History.fireCurrentHistoryState();
					}
				});
		
		eventBus.addHandler(AbrirImpressaoEvent.TYPE, new AbrirImpressaoEventHandler() {
			
			@Override
			public void onAbrirImpressao(AbrirImpressaoEvent event) {
				History.newItem("impressao");
			}
		});
		
		eventBus.addHandler(AbrirImpressaoMapaEvent.TYPE, new AbrirImpressaoMapaEventHandler() {
			
			@Override
			public void onAbrirImpressaoMapa(AbrirImpressaoMapaEvent event) {
				
				// Impressao - pattern de URL: 
				// imprimir!identificadorMapa=ABC;DEF;XYZ&paisagem=ABC&imprimirCabecalho=ABC&imprimirMapa=ABC

				StringBuilder mapas = new StringBuilder();
				for (Long mapaID : event.getMapaIDs()) {
					mapas.append(mapaID).append(";");
				}

				StringBuilder page = new StringBuilder();
				page.append(GWT.getHostPageBaseURL())
					.append("Impressao.html#imprimir!identificadorMapa=")
					.append(mapas.subSequence(0, mapas.length()-1))
					.append("&paisagem=")
					.append(event.isPaisagem())
					.append("&imprimirCabecalho=true&imprimirMapa=true");

				Window.open(page.toString(), "_blank", "");
			}
		});
		
		eventBus.addHandler(AbrirNaoVisitarEvent.TYPE, new AbrirNaoVisitarEventHandler() {
			
			@Override
			public void onAbrirNaoVisitar(AbrirNaoVisitarEvent event) {
				History.newItem("naoVisitar");				
			}
		});
		
		eventBus.addHandler(RetornarVisitarSurdoEvent.TYPE, new RetornarVisitarSurdoEventHandler() {
			
			@Override
			public void onRetornarVisitarSurdo(RetornarVisitarSurdoEvent event) {
				History.newItem("naoVisitar!retornar#"+event.getId());
			}
		});
	}

	@Override
	public void go(HasWidgets container) {
		this.container = container;
		if ("".equals(History.getToken())) {
			History.newItem("surdos");
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
							// Aba Surdo
						if (currentToken.startsWith("surdos")) {
							boolean novaView = false;
							if (cadastroSurdoView == null) {
								cadastroSurdoView = new CadastroSurdoViewImpl();
								novaView = true;
							}
							if (cadastroSurdoPresenter == null) {
								cadastroSurdoPresenter = new CadastroSurdoPresenter(
										service, eventBus, cadastroSurdoView, login);
								cadastroSurdoPresenter.setTabSelectionEventHandler(selectionHandler);
							}	
							
							cadastroSurdoPresenter.selectThisTab();

							if ("surdos".equals(currentToken)) {
								cadastroSurdoPresenter.initView();
								cadastroSurdoPresenter.onPesquisaPesquisarEvent(null, null, null, null, null, true);
							} else if (currentToken.startsWith("surdos!pesquisar")) {
								String queryString = currentToken.split("#")[1];
								String[] parametros = queryString.split("&");
								String identificadorCidade = null;
								String nomeSurdo = null;
								String nomeRegiao = null;
								String identificadorMapa = null;
								Boolean estaAssociadoMapa = null;
								Boolean dispararPesquisa = null;
								try {
									identificadorCidade = parametros[0].split("=")[1];
								} catch (ArrayIndexOutOfBoundsException e) {
									// não faz nada, o nome continua null
								}
								try {
									nomeRegiao = parametros[1].split("=")[1];
								} catch (ArrayIndexOutOfBoundsException e) {
									// não faz nada, o nome continua null
								}
								try {
									nomeSurdo = parametros[2].split("=")[1];
								} catch (ArrayIndexOutOfBoundsException e) {
									// não faz nada, o nome continua null
								}
								try {
									identificadorMapa = parametros[3].split("=")[1];
								} catch (ArrayIndexOutOfBoundsException e) {
									// não faz nada, o identificador continua null
								}
								try {
									String estaAssociadoMapaString = parametros[4].split("=")[1];
									if (!StringUtils.isEmpty(estaAssociadoMapaString) && !"null".equals(estaAssociadoMapaString)) {
										estaAssociadoMapa = Boolean.valueOf(estaAssociadoMapaString);
									}
								} catch (ArrayIndexOutOfBoundsException e) {
									// não faz nada, a flag continua null
								}
								if (estaAssociadoMapa != null && estaAssociadoMapa) {
									estaAssociadoMapa = null;
								}
								String dispararPesquisaString=parametros[5].split("=")[1];
								dispararPesquisa = Boolean.valueOf(dispararPesquisaString) || novaView;
								cadastroSurdoPresenter.onPesquisaPesquisarEvent(
										identificadorCidade,
										nomeSurdo,
										nomeRegiao,
										identificadorMapa,
										estaAssociadoMapa,
										dispararPesquisa);	
							} else if ("surdos!adicionar".equals(currentToken)) {
								cadastroSurdoPresenter.onAdicionar();
							} else if (currentToken.startsWith("surdos!editar")) {
								Long id = Long.valueOf(currentToken.split("#")[1].split("=")[1]);
								cadastroSurdoPresenter.onEditar(id);
							}
							
							cadastroSurdoPresenter.go(container);					
							// Aba Mapa
						} else if (currentToken.startsWith("mapas")) {
							if (cadastroMapaView == null) {
								cadastroMapaView = new CadastroMapaViewImpl();
							}
							if (cadastroMapaPresenter == null) {
								cadastroMapaPresenter = new CadastroMapaPresenter(
										service, eventBus, cadastroMapaView, login);
								cadastroMapaPresenter.setTabSelectionEventHandler(selectionHandler);
							}
							cadastroMapaPresenter.selectThisTab();
							cadastroMapaPresenter.initView();
							if (currentToken.startsWith("mapas!abrir")) {
								cadastroMapaPresenter.onAbrirMapa(Long.valueOf(currentToken.split("#")[1].split("=")[1]));
							}
						
							cadastroMapaPresenter.go(container);
							// Aba Impressao
						} else if (currentToken.startsWith("impressao")) {
							if (impressaoView == null) {
								impressaoView = new ImpressaoViewImpl();
							}
							if (impressaoPresenter == null) {
								impressaoPresenter = new ImpressaoPresenter(service, eventBus, impressaoView, login);
								impressaoPresenter.setTabSelectionEventHandler(selectionHandler);
							}
							impressaoPresenter.selectThisTab();
							
							if ("impressao".equals(currentToken)) {
								impressaoPresenter.initView();
							} 
							
							impressaoPresenter.go(container);
						} else if (currentToken.startsWith("naoVisitar")) {
							if (naoVisitarView == null) {
								naoVisitarView = new NaoVisitarViewImpl();
							}
							if (naoVisitarPresenter == null) {
								naoVisitarPresenter = new NaoVisitarPresenter(service, eventBus, naoVisitarView, login);
								naoVisitarPresenter.setTabSelectionEventHandler(selectionHandler);
							}
							naoVisitarPresenter.selectThisTab();
							
							if ("naoVisitar".equals(currentToken)) {
								naoVisitarPresenter.initView();
								naoVisitarPresenter.obterSurdosNaoVisitar();
							} else if (currentToken.startsWith("naoVisitar!retornar")) {
								String queryString = currentToken.split("#")[1];
								naoVisitarPresenter.onRetornar(Long.valueOf(queryString));
							}
							naoVisitarPresenter.go(container);
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
