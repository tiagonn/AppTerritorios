package br.com.nascisoft.apoioterritoriols.cadastro.client.presenter;

import java.util.List;
import java.util.logging.Level;

import br.com.nascisoft.apoioterritoriols.cadastro.client.CadastroServiceAsync;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirImpressaoMapaEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.ImpressaoView;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.vo.LoginVO;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ImpressaoPresenter extends AbstractCadastroPresenter
		implements ImpressaoView.Presenter {
	
	private final ImpressaoView view;
	
	public ImpressaoPresenter(CadastroServiceAsync service,
			HandlerManager eventBus, ImpressaoView view, LoginVO login) {
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
				Window.alert("Falha ao obter lista de regiões. \n" + caught.getMessage());					
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
				Window.alert("Falha ao obter lista de mapas. \n" + caught.getMessage());
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
	ImpressaoView getView() {
		return this.view;
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.view.setTabSelectionEventHandler(handler);
	}

	@Override
	public void abrirImpressao(final List<Long> mapasIDs, final Boolean paisagem) {
		getView().showWaitingPanel();
		this.service.existePessoasNosMapas(mapasIDs, new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				getView().hideWaitingPanel();
				if (result) {
					eventBus.fireEvent(new AbrirImpressaoMapaEvent(mapasIDs, paisagem));
				} else {					
					Window.alert("Não existe surdo associado a algum mapa selecionado. Por favor reveja a seleção e tente novamente.");
				}
				logger.log(Level.INFO, "Busca de dados de impressao realizada com sucesso.");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter informações para abrir o mapa.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao obter informações para abrir o mapa. \n" + caught.getMessage());				
			}
		});
				
	}

}
