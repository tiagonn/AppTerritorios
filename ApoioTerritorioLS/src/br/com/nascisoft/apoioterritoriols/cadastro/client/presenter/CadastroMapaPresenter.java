package br.com.nascisoft.apoioterritoriols.cadastro.client.presenter;

import java.util.List;
import java.util.logging.Level;

import br.com.nascisoft.apoioterritoriols.cadastro.client.CadastroServiceAsync;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirMapaEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.CadastroMapaView;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.CadastroView;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.AbrirMapaVO;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class CadastroMapaPresenter extends AbstractPresenter
		implements CadastroMapaView.Presenter {
	
	private final CadastroMapaView view;
	
	public CadastroMapaPresenter(CadastroServiceAsync service,
			HandlerManager eventBus, CadastroMapaView view) {
		super(service, eventBus);
		this.view = view;
		this.view.setPresenter(this);
		populaRegioes();
	}

	@Override
	CadastroView getView() {
		return this.view;
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.view.setTabSelectionEventHandler(handler);
	}

	@Override
	public void adicionarMapa(String nomeRegiao) {
		getView().showWaitingPanel();
		service.adicionarMapa(nomeRegiao, new AsyncCallback<Long>() {
						
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao adicionar mapa.\n", caught);
				Window.alert("Falha ao adicionar mapa. \n" + caught.getMessage());
			}
			
			@Override
			public void onSuccess(Long result) {
				Window.alert("Mapa criado com sucesso.");
				abrirMapa(result);
				getView().hideWaitingPanel();
			}

		});
		
	}

	@Override
	public void onAbrirMapa(Long identificadorMapa) {
		getView().showWaitingPanel();
		service.obterInformacoesAbrirMapa(identificadorMapa, new AsyncCallback<AbrirMapaVO>() {

			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter informações para abrir o mapa. \n", caught);
				Window.alert("Falha ao obter informações para abrir o mapa. \n" + caught.getMessage());
			}

			@Override
			public void onSuccess(AbrirMapaVO result) {
				view.onAbrirMapa(result);
				getView().hideWaitingPanel();
			}
		});
	}

	@Override
	public void abrirMapa(Long identificadorMapa) {
		eventBus.fireEvent(new AbrirMapaEvent(identificadorMapa));
	}

	@Override
	public void adicionarSurdosMapa(List<Long> surdos, Long identificadorMapa) {
		getView().showWaitingPanel();
		service.adicionarSurdosMapa(surdos, identificadorMapa, new AsyncCallback<Long>() {
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao adicionar surdos no mapa.\n", caught);
				Window.alert("Falha ao adicionar surdos no mapa. \n" + caught.getMessage());
			}
			
			@Override
			public void onSuccess(Long result) {
				Window.alert("Surdo(s) adicionados com sucesso ao mapa");
				eventBus.fireEvent(new AbrirMapaEvent(result));
				getView().hideWaitingPanel();
			}
		});		
	}
	
	@Override
	public void removerSurdosMapa(List<Long> surdos) {
		getView().showWaitingPanel();
		service.removerSurdosMapa(surdos, new AsyncCallback<Long>() {

			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao remover surdos no mapa.\n", caught);
				Window.alert("Falha ao adicionar remover no mapa. \n" + caught.getMessage());
			}

			@Override
			public void onSuccess(Long result) {
				Window.alert("Surdo(s) removidos com sucesso ao mapa");
				eventBus.fireEvent(new AbrirMapaEvent(result));
				getView().hideWaitingPanel();
			}
		});		
	}

	@Override
	public void apagarMapa(Long identificadorMapa) {
		getView().showWaitingPanel();
		service.apagarMapa(identificadorMapa, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao apagar o mapa.\n", caught);
				Window.alert("Falha ao apagar o mapa. \n" + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Mapa apagado com sucesso");
				view.onApagarMapa();
				getView().hideWaitingPanel();
			}
		});
	}

}
