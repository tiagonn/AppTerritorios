package br.com.nascisoft.apoioterritoriols.impressao.client.presenter;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;
import br.com.nascisoft.apoioterritoriols.impressao.client.ImpressaoServiceAsync;
import br.com.nascisoft.apoioterritoriols.impressao.client.view.ImpressaoMapaView;
import br.com.nascisoft.apoioterritoriols.impressao.client.view.ImpressaoMapaView.IImpressaoPresenter;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class ImpressaoMapaPresenter implements IImpressaoPresenter {

	private ImpressaoServiceAsync service;
	private ImpressaoMapaView view;
	protected static final Logger logger = Logger
			.getLogger(ImpressaoMapaPresenter.class.getName());

	public ImpressaoMapaPresenter(ImpressaoServiceAsync service, ImpressaoMapaView view) {
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
