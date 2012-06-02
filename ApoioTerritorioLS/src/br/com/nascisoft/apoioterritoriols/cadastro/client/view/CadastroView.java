package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.cadastro.xml.Regiao;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;

public interface CadastroView {
	
	public interface Presenter {
		void initView();
		void setTabSelectionEventHandler(SelectionHandler<Integer> handler);
		void selectThisTab();
		void onPesquisaRegiaoListBoxChange(String nomeRegiao);
	}
	
	void showWaitingPanel();
	void hideWaitingPanel();
	void initView();
	void selectThisTab();
	void setMapaList(List<Mapa> mapas);
	void setRegiaoList(List<Regiao> regioes);
	void setTabSelectionEventHandler(SelectionHandler<Integer> handler);
	Widget asWidget();
}
