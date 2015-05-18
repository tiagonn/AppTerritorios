package br.com.nascisoft.apoioterritoriols.admin.client.presenter;

import java.util.List;
import java.util.logging.Level;

import br.com.nascisoft.apoioterritoriols.admin.client.AdminServiceAsync;
import br.com.nascisoft.apoioterritoriols.admin.client.view.AdminCidadeView;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.resources.client.ApoioTerritorioLSConstants;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AdminCidadePresenter extends AbstractAdminPresenter
		implements AdminCidadeView.Presenter {
	
	private final AdminCidadeView view;
	
	public AdminCidadePresenter(AdminServiceAsync service,
			HandlerManager eventBus, AdminCidadeView view) {
		super(service, eventBus);
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	AdminCidadeView getView() {
		return this.view;
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.view.setTabSelectionEventHandler(handler);
	}

	@Override
	public void adicionarOuAtualizarCidade(Cidade cidade) {
		getView().showWaitingPanel();
		this.service.adicionarOuAtualizarCidade(cidade, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				getView().hideWaitingPanel();
				getView().mostrarWarning("Cidade adicionada/atualizada com sucesso", ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
				getView().initView();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao atualizar/adicionar cidade.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning("Falha ao atualizar/adicionar cidade. \n" + caught.getMessage(),
						ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
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
						ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
			}
		});
	}

	@Override
	public void apagarCidade(Long id) {
		getView().showWaitingPanel();
		this.service.apagarCidade(id, new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {
				getView().hideWaitingPanel();
				if (result) {
					getView().mostrarWarning("Cidade apagada com sucesso",
							ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
				} else {
					getView().mostrarWarning("Não é possível apagar esta cidade pois ela possui regiões associadas",
							ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
				}
				getView().initView();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao apagar cidade.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning("Falha ao apagar cidade. \n" + caught.getMessage(),
						ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
			}
		});
	}


}
