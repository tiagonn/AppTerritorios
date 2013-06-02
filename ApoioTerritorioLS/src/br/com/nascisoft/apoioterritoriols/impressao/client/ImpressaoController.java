package br.com.nascisoft.apoioterritoriols.impressao.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.impressao.client.presenter.ImpressaoMapaPresenter;
import br.com.nascisoft.apoioterritoriols.impressao.client.view.ImpressaoMapaView;
import br.com.nascisoft.apoioterritoriols.impressao.client.view.ImpressaoMapaView.IImpressaoPresenter;
import br.com.nascisoft.apoioterritoriols.impressao.client.view.ImpressaoMapaViewImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;

public class ImpressaoController implements ValueChangeHandler<String> {
	
	private final ImpressaoServiceAsync service;
	private HasWidgets container;	
	private String currentToken = null;
	private ImpressaoMapaView view;
	private IImpressaoPresenter presenter;

	private static final Logger logger = Logger
			.getLogger(ImpressaoController.class.getName());

	public ImpressaoController(ImpressaoServiceAsync service) {
		this.service = service;
		History.addValueChangeHandler(this);
	}

	public void go(HasWidgets container) {
		this.container = container;
		String token = History.getToken();
		if (token == null || !token.startsWith("imprimir")) {
			Window.open(GWT.getHostPageBaseURL()+"Cadastro.html" , "_self", "");
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
							// Impressao - pattern de URL: 
							// imprimir!identificadorMapa=ABC&paisagem=ABC&imprimirCabecalho=ABC&imprimirMapa=ABC
						if (currentToken.startsWith("imprimir")) {
							if (view == null) {
								view = new ImpressaoMapaViewImpl();
							}
							if (presenter == null) {
								presenter = new ImpressaoMapaPresenter(service, view);
							}	
							String queryString = currentToken.split("!")[1];
							String[] parametros = queryString.split("&");
							
							Long identificadorMapa = Long.valueOf(parametros[0].split("=")[1]);
							Boolean paisagem = Boolean.valueOf(parametros[1].split("=")[1]);
							Boolean imprimirCabecalho = Boolean.valueOf(parametros[2].split("=")[1]);
							Boolean imprimirMapa = Boolean.valueOf(parametros[3].split("=")[1]);
							
							presenter.setConfiguracoesImpressao(paisagem, imprimirCabecalho, imprimirMapa);
							presenter.abrirImpressaoMapa(identificadorMapa);
							
							presenter.go(container);
							
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
