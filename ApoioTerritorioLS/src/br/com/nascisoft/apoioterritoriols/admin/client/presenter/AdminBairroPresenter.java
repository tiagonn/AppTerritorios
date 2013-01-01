package br.com.nascisoft.apoioterritoriols.admin.client.presenter;

import java.util.List;
import java.util.logging.Level;

import br.com.nascisoft.apoioterritoriols.admin.client.AdminServiceAsync;
import br.com.nascisoft.apoioterritoriols.admin.client.view.AdminBairroView;
import br.com.nascisoft.apoioterritoriols.admin.vo.BairroVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AdminBairroPresenter extends AbstractAdminPresenter
		implements AdminBairroView.Presenter {
	
	private final AdminBairroView view;
	
	public AdminBairroPresenter(AdminServiceAsync service,
			HandlerManager eventBus, AdminBairroView view) {
		super(service, eventBus);
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	AdminBairroView getView() {
		return this.view;
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.view.setTabSelectionEventHandler(handler);
	}

	@Override
	public void adicionarOuAtualizarBairro(BairroVO bairro) {
		getView().showWaitingPanel();
		this.service.adicionarOuAtualizarBairro(bairro, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				getView().hideWaitingPanel();
				Window.alert("Bairro adicionado/atualizado com sucesso");
				getView().initView();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao atualizar/adicionar Bairro.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao atualizar/adicionar Bairro. \n" + caught.getMessage());
			}
		});
		
	}

	@Override
	public void buscarBairros() {
		getView().showWaitingPanel();
		this.service.buscarBairros(new AsyncCallback<List<BairroVO>>() {
			
			@Override
			public void onSuccess(List<BairroVO> result) {
				getView().hideWaitingPanel();
				getView().setBairros(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter Bairros.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao obter Bairros. \n" + caught.getMessage());
			}
		});
	}

	@Override
	public void apagarBairro(Long id) {
		getView().showWaitingPanel();
		this.service.apagarBairro(id, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				getView().hideWaitingPanel();
				Window.alert("Bairro apagado com sucesso");
				getView().initView();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao apagar Bairro.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao apagar Bairro. \n" + caught.getMessage());
			}
		});
	}


	@Override
	public void buscarCidades() {
		getView().showWaitingPanel();
		this.service.buscarCidades(new AsyncCallback<List<Cidade>>() {
			
			@Override
			public void onSuccess(List<Cidade> result) {
				getView().hideWaitingPanel();
				getView().setCidades(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter cidades.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao obter cidades. \n" + caught.getMessage());
			}
		});
	}
}
