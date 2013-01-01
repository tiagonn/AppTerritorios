package br.com.nascisoft.apoioterritoriols.admin.client.presenter;

import java.util.List;
import java.util.logging.Level;

import br.com.nascisoft.apoioterritoriols.admin.client.AdminServiceAsync;
import br.com.nascisoft.apoioterritoriols.admin.client.view.AdminRegiaoView;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AdminRegiaoPresenter extends AbstractAdminPresenter
		implements AdminRegiaoView.Presenter {
	
	private final AdminRegiaoView view;
	
	public AdminRegiaoPresenter(AdminServiceAsync service,
			HandlerManager eventBus, AdminRegiaoView view) {
		super(service, eventBus);
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	AdminRegiaoView getView() {
		return this.view;
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.view.setTabSelectionEventHandler(handler);
	}

	@Override
	public void adicionarOuAtualizarRegiao(Regiao regiao, String nomeCidade) {
		getView().showWaitingPanel();
		this.service.adicionarOuAtualizarRegiao(regiao, nomeCidade, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				getView().hideWaitingPanel();
				Window.alert("Regiao adicionada/atualizada com sucesso");
				getView().initView();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao atualizar/adicionar Regiao.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao atualizar/adicionar Regiao. \n" + caught.getMessage());
			}
		});
		
	}

	@Override
	public void buscarRegioes() {
		getView().showWaitingPanel();
		this.service.buscarRegioes(new AsyncCallback<List<Regiao>>() {
			
			@Override
			public void onSuccess(List<Regiao> result) {
				getView().hideWaitingPanel();
				getView().setRegioes(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter Regiões.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao obter Regiões. \n" + caught.getMessage());
			}
		});
	}

	@Override
	public void apagarRegiao(String nome) {
		getView().showWaitingPanel();
		this.service.apagarRegiao(nome, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				getView().hideWaitingPanel();
				Window.alert("Regiao apagada com sucesso");
				getView().initView();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao apagar Regiao.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao apagar Regiao. \n" + caught.getMessage());
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
