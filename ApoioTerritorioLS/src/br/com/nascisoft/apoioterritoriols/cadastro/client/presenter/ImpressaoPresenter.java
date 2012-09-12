package br.com.nascisoft.apoioterritoriols.cadastro.client.presenter;

import java.util.List;
import java.util.logging.Level;

import br.com.nascisoft.apoioterritoriols.cadastro.client.CadastroServiceAsync;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirImpressaoMapaEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.CadastroView;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.ImpressaoView;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ImpressaoPresenter extends AbstractCadastroPresenter
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
	public void abrirImpressao(final Long identificadorMapa, final Boolean paisagem) {
		getView().showWaitingPanel();
		this.service.obterSurdosCompletos(null, null, identificadorMapa, new AsyncCallback<List<SurdoVO>>() {
			
			@Override
			public void onSuccess(List<SurdoVO> result) {
				getView().hideWaitingPanel();
				if (result == null || result.size() == 0) {
					Window.alert("Não existe surdo associado a este mapa");
				} else {
					eventBus.fireEvent(new AbrirImpressaoMapaEvent(identificadorMapa, paisagem));
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
