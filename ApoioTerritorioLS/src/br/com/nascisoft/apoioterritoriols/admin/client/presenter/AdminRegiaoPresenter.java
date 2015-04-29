package br.com.nascisoft.apoioterritoriols.admin.client.presenter;

import java.util.List;
import java.util.logging.Level;

import br.com.nascisoft.apoioterritoriols.admin.client.AdminServiceAsync;
import br.com.nascisoft.apoioterritoriols.admin.client.view.AdminRegiaoView;
import br.com.nascisoft.apoioterritoriols.admin.vo.RegiaoVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.resources.client.ApoioTerritorioLSConstants;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
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
	public void adicionarOuAtualizarRegiao(RegiaoVO regiao) {
		getView().showWaitingPanel();
		this.service.adicionarOuAtualizarRegiao(regiao, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				getView().hideWaitingPanel();
				getView().mostrarWarning("Regiao adicionada/atualizada com sucesso",
						ApoioTerritorioLSConstants.WARNING_TIMEOUT);
				getView().initView();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao atualizar/adicionar Regiao.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning("Falha ao atualizar/adicionar Regiao. \n" + caught.getMessage(),
						ApoioTerritorioLSConstants.WARNING_TIMEOUT);
			}
		});
		
	}

	@Override
	public void buscarRegioes() {
		getView().showWaitingPanel();
		this.service.buscarRegioes(new AsyncCallback<List<RegiaoVO>>() {
			
			@Override
			public void onSuccess(List<RegiaoVO> result) {
				getView().hideWaitingPanel();
				getView().setRegioes(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter Regiões.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning("Falha ao obter Regiões. \n" + caught.getMessage(),
						ApoioTerritorioLSConstants.WARNING_TIMEOUT);
			}
		});
	}

	@Override
	public void apagarRegiao(Long id) {
		getView().showWaitingPanel();
		this.service.apagarRegiao(id, new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {
				getView().hideWaitingPanel();
				if (result) {
					getView().mostrarWarning("Regiao apagada com sucesso",
							ApoioTerritorioLSConstants.WARNING_TIMEOUT);
				} else {
					getView().mostrarWarning("Regiao não pode ser apagada pois possui surdos associados a ela",
							ApoioTerritorioLSConstants.WARNING_TIMEOUT);
				}
				getView().initView();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao apagar Regiao.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning("Falha ao apagar Regiao. \n" + caught.getMessage(),
						ApoioTerritorioLSConstants.WARNING_TIMEOUT);
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
				getView().mostrarWarning("Falha ao obter cidades. \n" + caught.getMessage(),
						ApoioTerritorioLSConstants.WARNING_TIMEOUT);
			}
		});
	}
}
