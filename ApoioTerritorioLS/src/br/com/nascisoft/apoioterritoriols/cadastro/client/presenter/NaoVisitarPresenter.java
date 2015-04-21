package br.com.nascisoft.apoioterritoriols.cadastro.client.presenter;

import java.util.List;
import java.util.logging.Level;

import br.com.nascisoft.apoioterritoriols.cadastro.client.CadastroServiceAsync;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirNaoVisitarEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.RetornarVisitarSurdoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.CadastroView;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.NaoVisitarView;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoNaoVisitarDetailsVO;
import br.com.nascisoft.apoioterritoriols.login.vo.LoginVO;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class NaoVisitarPresenter extends AbstractCadastroPresenter implements NaoVisitarView.Presenter {
	
	private final NaoVisitarView view;

	public NaoVisitarPresenter(CadastroServiceAsync service,
			HandlerManager eventBus, NaoVisitarView view, LoginVO login) {
		super(service, eventBus, login);
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.view.setTabSelectionEventHandler(handler);		
	}

	@Override
	CadastroView getView() {
		return this.view;
	}

	@Override
	public void obterSurdosNaoVisitar() {
		getView().showWaitingPanel();
		this.service.obterSurdosNaoVisitar(new AsyncCallback<List<SurdoNaoVisitarDetailsVO>>() {
			
			@Override
			public void onSuccess(List<SurdoNaoVisitarDetailsVO> result) {
				view.setSurdosNaoVisitar(result);
				view.hideWaitingPanel();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter informações de surdos que estão marcados para não serem visitados.\n", caught);
				view.hideWaitingPanel();
				Window.alert("Falha ao obter informações de surdos que estão marcados para não serem visitados\n" + caught.getMessage());
			}
		});
		
	}
	
	@Override
	public void onRetornarButtonClick(Long identificadorSurdo) {
		eventBus.fireEvent(new RetornarVisitarSurdoEvent(identificadorSurdo));		
	}

	@Override
	public void onRetornar(Long identificadorSurdo) {
		getView().showWaitingPanel();
		this.service.retornarSurdoNaoVisitar(identificadorSurdo, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				view.hideWaitingPanel();
				Window.alert("Surdo retornado para a lista de visitas com sucesso");
				eventBus.fireEvent(new AbrirNaoVisitarEvent());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao retornar surdo para serem visitados.\n", caught);
				view.hideWaitingPanel();
				Window.alert("Falha ao retornar surdo para serem visitados\n" + caught.getMessage());
			}
		});
	}

}
