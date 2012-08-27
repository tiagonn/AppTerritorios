package br.com.nascisoft.apoioterritoriols.impressao.client.presenter;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.cadastro.client.CadastroServiceAsync;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;
import br.com.nascisoft.apoioterritoriols.impressao.client.view.ImpressaoView;
import br.com.nascisoft.apoioterritoriols.impressao.client.view.ImpressaoView.IImpressaoPresenter;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class ImpressaoPresenter implements IImpressaoPresenter {

	private CadastroServiceAsync service;
	private ImpressaoView view;
	protected static final Logger logger = Logger
			.getLogger(ImpressaoPresenter.class.getName());

	public ImpressaoPresenter(CadastroServiceAsync service, ImpressaoView view) {
		this.service = service;
		this.view = view;
	}
	
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}

	@Override
	public void setConfiguracoesImpressao(Boolean paisagem,
			Boolean imprimirCabecalho, Boolean imprimirMapa) {
		this.view.setConfiguracoesImpressao(paisagem, imprimirCabecalho, imprimirMapa);		
	}

	@Override
	public void abrirImpressaoMapa(Long identificadorMapa) {
		this.service.obterSurdosCompletos(null, null, identificadorMapa, new AsyncCallback<List<SurdoVO>>() {
			
			@Override
			public void onSuccess(List<SurdoVO> result) {
				view.abrirImpressaoMapa(result);
				logger.log(Level.INFO, "Busca de dados de impressao realizada com sucesso.");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter informações para abrir o mapa.\n", caught);
				Window.alert("Falha ao obter informações para abrir o mapa. \n" + caught.getMessage());				
			}
		});
		
	}

}
