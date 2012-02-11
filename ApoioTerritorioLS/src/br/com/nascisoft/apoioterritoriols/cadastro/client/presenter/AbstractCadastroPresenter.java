package br.com.nascisoft.apoioterritoriols.cadastro.client.presenter;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.cadastro.client.CadastroServiceAsync;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.CadastroView;
import br.com.nascisoft.apoioterritoriols.cadastro.entities.Mapa;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public abstract class AbstractCadastroPresenter implements CadastroPresenter, CadastroView.Presenter {
	
	private static List<String> regioes = null;
	protected final HandlerManager eventBus;
	protected final CadastroServiceAsync service;
	
	public AbstractCadastroPresenter(CadastroServiceAsync service, HandlerManager eventBus) {
		this.service = service;
		this.eventBus = eventBus;
	}
	
	abstract CadastroView getView();
	
	protected static final Logger logger = Logger.getLogger(AbstractCadastroPresenter.class.getName());
	
	protected void populaRegioes() {
		if(regioes == null) {
			getView().showWaitingPanel();
			service.obterRegioesCampinas(new AsyncCallback<List<String>>() {

				@Override
				public void onFailure(Throwable caught) {
					logger.log(Level.SEVERE, "Falha ao obter lista de regiões.\n", caught);
					Window.alert("Falha ao obter lista de regiões. \n" + caught.getMessage());					
				}

				@Override
				public void onSuccess(List<String> result) {
					regioes = result;
					getView().setRegiaoList(result);
					getView().hideWaitingPanel();
				}
			});
		} else {
			getView().setRegiaoList(regioes);
		}
	}
	
	public void onPesquisaRegiaoListBoxChange(String nomeRegiao) {
		getView().showWaitingPanel();
		service.obterMapasRegiao(nomeRegiao, new AsyncCallback<List<Mapa>>() {

			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter lista de mapas.\n", caught);
				Window.alert("Falha ao obter lista de mapas. \n" + caught.getMessage());
			}

			@Override
			public void onSuccess(List<Mapa> result) {
				getView().setMapaList(result);
				getView().hideWaitingPanel();
			}
		});		
	}

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
