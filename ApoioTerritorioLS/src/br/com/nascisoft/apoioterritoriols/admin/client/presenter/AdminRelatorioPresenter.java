package br.com.nascisoft.apoioterritoriols.admin.client.presenter;

import java.util.logging.Level;

import br.com.nascisoft.apoioterritoriols.admin.client.AdminServiceAsync;
import br.com.nascisoft.apoioterritoriols.admin.client.view.AdminRelatorioView;
import br.com.nascisoft.apoioterritoriols.admin.vo.RelatorioVO;
import br.com.nascisoft.apoioterritoriols.resources.client.ApoioTerritorioLSConstants;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AdminRelatorioPresenter extends AbstractAdminPresenter
		implements AdminRelatorioView.Presenter {
	
	private final AdminRelatorioView view;
	
	public AdminRelatorioPresenter(AdminServiceAsync service,
			HandlerManager eventBus, AdminRelatorioView view) {
		super(service, eventBus);
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	AdminRelatorioView getView() {
		return this.view;
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.view.setTabSelectionEventHandler(handler);
	}

	@Override
	public void buscarDadosRelatorio() {
		getView().showWaitingPanel();
		this.service.obterDadosRelatorio(new AsyncCallback<RelatorioVO>() {
			
			@Override
			public void onSuccess(RelatorioVO result) {
				getView().hideWaitingPanel();
				getView().setDados(result);				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter dados para relatório.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning("Falha ao obter dados para relatório. \n" + caught.getMessage(),
						ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
			}
		});
		
	}

	@Override
	public void dispararExport(String destinatario) {
		getView().showWaitingPanel();
		this.service.dispararExport(destinatario, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				getView().hideWaitingPanel();
				getView().mostrarWarning("Export disparado com sucesso",
						ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao disparar export.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning("Falha ao disparar export. \n" + caught.getMessage(),
						ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
			}
			
		});
		
	}

	

}
