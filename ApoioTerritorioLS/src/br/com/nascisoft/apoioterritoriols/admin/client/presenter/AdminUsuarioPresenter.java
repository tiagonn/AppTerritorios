package br.com.nascisoft.apoioterritoriols.admin.client.presenter;

import java.util.List;
import java.util.logging.Level;

import br.com.nascisoft.apoioterritoriols.admin.client.AdminServiceAsync;
import br.com.nascisoft.apoioterritoriols.admin.client.view.AdminUsuarioView;
import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AdminUsuarioPresenter extends AbstractAdminPresenter
		implements AdminUsuarioView.Presenter {
	
	private final AdminUsuarioView view;
	
	public AdminUsuarioPresenter(AdminServiceAsync service,
			HandlerManager eventBus, AdminUsuarioView view) {
		super(service, eventBus);
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	AdminUsuarioView getView() {
		return this.view;
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.view.setTabSelectionEventHandler(handler);
	}

	@Override
	public void adicionarOuAtualizarUsuario(String email, Boolean admin) {
		this.getView().showWaitingPanel();
		this.service.adicionarOuAtualizarUsuario(email, admin, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				getView().hideWaitingPanel();
				Window.alert("Usuario adicionado/atualizado com sucesso");
				getView().initView();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao atualizar/adicionar usuario.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao atualizar/adicionar usuario. \n" + caught.getMessage());
			}
		});
		
	}

	@Override
	public void buscarUsuarios() {
		this.getView().showWaitingPanel();
		this.service.buscarUsuarios(new AsyncCallback<List<Usuario>>() {

			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao buscar usuarios.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao buscar usuarios. \n" + caught.getMessage());
				
			}

			@Override
			public void onSuccess(List<Usuario> result) {
				getView().hideWaitingPanel();
				getView().setUsuarios(result);
			}
		});
	}

	@Override
	public void apagarUsuario(String email) {
		this.getView().showWaitingPanel();
		this.service.apagarUsuario(email, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				getView().hideWaitingPanel();
				Window.alert("Usuario apagado com sucesso");
				getView().initView();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao apagar usuario.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao apagar usuario. \n" + caught.getMessage());
			}
		});
	}

}
