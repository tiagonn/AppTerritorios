package br.com.nascisoft.apoioterritoriols.cadastro.client.presenter;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import br.com.nascisoft.apoioterritoriols.cadastro.client.CadastroServiceAsync;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirMapaEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.CadastroMapaView;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.AbrirMapaVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.vo.LoginVO;
import br.com.nascisoft.apoioterritoriols.resources.client.ApoioTerritorioLSConstants;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class CadastroMapaPresenter extends AbstractCadastroPresenter
		implements CadastroMapaView.Presenter {
	
	private final CadastroMapaView view;
	
	public CadastroMapaPresenter(CadastroServiceAsync service,
			HandlerManager eventBus, CadastroMapaView view, LoginVO login) {
		super(service, eventBus, login);
		this.view = view;
		this.view.setPresenter(this);
	}
	
	public void onPesquisaCidadeListBoxChange(Long cidadeId) {
		getView().showWaitingPanel();
		service.obterRegioes(cidadeId, new AsyncCallback<List<Regiao>>() {

			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter lista de regiões.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning(
						"Falha ao obter lista de regiões. \n" + caught.getMessage(), 
						ApoioTerritorioLSConstants.WARNING_TIMEOUT);					
			}

			@Override
			public void onSuccess(List<Regiao> result) {
				getView().setRegiaoList(result);
				getView().hideWaitingPanel();
			}
		});
	}
	
	public void onPesquisaRegiaoListBoxChange(Long regiaoId) {
		getView().showWaitingPanel();
		service.obterMapasRegiao(regiaoId, new AsyncCallback<List<Mapa>>() {

			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter lista de mapas.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning(
						"Falha ao obter lista de mapas. \n" + caught.getMessage(),
						ApoioTerritorioLSConstants.WARNING_TIMEOUT);
			}

			@Override
			public void onSuccess(List<Mapa> result) {
				getView().setMapaList(result);
				getView().hideWaitingPanel();
			}
		});		
	}

	@Override
	public void initView() {
		populaCidades();
		super.initView();
	}
	
	@Override
	CadastroMapaView getView() {
		return this.view;
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.view.setTabSelectionEventHandler(handler);
	}

	@Override
	public void adicionarMapa(Long identificadorRegiao) {
		getView().showWaitingPanel();
		service.adicionarMapa(identificadorRegiao, new AsyncCallback<Long>() {
						
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao adicionar mapa.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning(
						"Falha ao adicionar mapa. \n" + caught.getMessage(),
						ApoioTerritorioLSConstants.WARNING_TIMEOUT);
			}
			
			@Override
			public void onSuccess(Long result) {
				getView().mostrarWarning("Mapa criado com sucesso.", ApoioTerritorioLSConstants.WARNING_TIMEOUT);
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
				getView().hideWaitingPanel();
				getView().mostrarWarning(
						"Falha ao obter informações para abrir o mapa. \n" + caught.getMessage(),
						ApoioTerritorioLSConstants.WARNING_TIMEOUT);
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
	public void adicionarSurdosMapa(Set<Long> surdos, Long identificadorMapa) {
		getView().showWaitingPanel();
		service.adicionarSurdosMapa(surdos, identificadorMapa, new AsyncCallback<Long>() {
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao adicionar pessoas no mapa.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning(
						"Falha ao adicionar pessoas no mapa. \n" + caught.getMessage(),
						ApoioTerritorioLSConstants.WARNING_TIMEOUT);
			}
			
			@Override
			public void onSuccess(Long result) {
				getView().mostrarWarning(
						"Pessoa(s) adicionadas com sucesso ao mapa",
						ApoioTerritorioLSConstants.WARNING_TIMEOUT);
				eventBus.fireEvent(new AbrirMapaEvent(result));
				getView().hideWaitingPanel();
			}
		});		
	}
	
	@Override
	public void removerSurdosMapa(Set<Long> surdos) {
		getView().showWaitingPanel();
		service.removerSurdosMapa(surdos, new AsyncCallback<Long>() {

			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao remover pessoas no mapa.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning(
						"Falha ao remover pessoas no mapa. \n" + caught.getMessage(),
						ApoioTerritorioLSConstants.WARNING_TIMEOUT);
			}

			@Override
			public void onSuccess(Long result) {
				getView().mostrarWarning(
						"Pessoas(s) removidas com sucesso ao mapa",
						ApoioTerritorioLSConstants.WARNING_TIMEOUT);
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
				getView().hideWaitingPanel();
				getView().mostrarWarning(
						"Falha ao apagar o mapa. \n" + caught.getMessage(),
						ApoioTerritorioLSConstants.WARNING_TIMEOUT);
			}

			@Override
			public void onSuccess(Void result) {
				getView().mostrarWarning(
						"Mapa apagado com sucesso",
						ApoioTerritorioLSConstants.WARNING_TIMEOUT);
				view.onApagarMapa();
				getView().hideWaitingPanel();
			}
		});
	}

}
