package br.com.nascisoft.apoioterritoriols.cadastro.client.presenter;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.cadastro.client.CadastroServiceAsync;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.CadastroView;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.vo.LoginVO;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public abstract class AbstractCadastroPresenter implements CadastroPresenter, CadastroView.Presenter {
	
	protected List<Cidade> cidades = null;
	protected final HandlerManager eventBus;
	protected final CadastroServiceAsync service;
	protected LoginVO login;
	
	public AbstractCadastroPresenter(CadastroServiceAsync service, HandlerManager eventBus, LoginVO login) {
		this.service = service;
		this.eventBus = eventBus;
		this.login = login;
	}
	
	abstract CadastroView getView();
	
	protected static final Logger logger = Logger.getLogger(AbstractCadastroPresenter.class.getName());
	
	@Override
	public LoginVO getLoginInformation() {
		return login;
	}
	
	public void populaCidades() {
		if (cidades == null) {
			getView().showWaitingPanel();
			service.obterCidades(new AsyncCallback<List<Cidade>>() {
				
				@Override
				public void onSuccess(List<Cidade> result) {
					cidades = result;
					getView().setCidadeList(result);
					getView().hideWaitingPanel();
				}
				
				@Override
				public void onFailure(Throwable caught) {
					logger.log(Level.SEVERE, "Falha ao obter lista de cidades.\n", caught);
					getView().hideWaitingPanel();
					Window.alert("Falha ao obter lista de cidades. \n" + caught.getMessage());		
				}
			});
		} else {
			getView().setCidadeList(cidades);
		}
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

	public void go(HasWidgets container) {
		container.clear();
		container.add(getView().asWidget());
	}
	
	public void initView() {
		getView().initView();
	}
	
	public void selectThisTab() {
		getView().selectThisTab();
	}

}
