package br.com.nascisoft.apoioterritoriols.cadastro.client.presenter;

import java.util.List;
import java.util.logging.Level;

import br.com.nascisoft.apoioterritoriols.cadastro.client.CadastroServiceAsync;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirImpressaoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirImpressaoMapaEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.CadastroView;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.ImpressaoView;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ImpressaoPresenter extends AbstractPresenter
		implements ImpressaoView.Presenter {
	
	private final ImpressaoView view;
	
	public ImpressaoPresenter(CadastroServiceAsync service,
			HandlerManager eventBus, ImpressaoView view) {
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
	public void onAbrirImpressao(final Long identificadorMapa, final Boolean paisagem) {
		getView().showWaitingPanel();
		service.obterSurdosCompletos(null, null, identificadorMapa, new AsyncCallback<List<SurdoVO>>() {
			
			@Override
			public void onSuccess(List<SurdoVO> result) {
				if (result == null || result.size() == 0) {
					Window.alert("Este mapa nao possui surdo associado");
					eventBus.fireEvent(new AbrirImpressaoEvent());					
				} else {
					view.onAbrirImpressao(identificadorMapa, result, paisagem);
				}
				getView().hideWaitingPanel();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter informa��es para abrir o mapa.\n", caught);
				Window.alert("Falha ao obter informacoes para abrir o mapa. \n" + caught.getMessage());				
			}
		});
	}

	@Override
	public void abrirImpressao(Long identificadorMapa, Boolean paisagem) {
		eventBus.fireEvent(new AbrirImpressaoMapaEvent(identificadorMapa, paisagem));
	}

	@Override
	public void onVoltar() {
		eventBus.fireEvent(new AbrirImpressaoEvent());
	}

}
