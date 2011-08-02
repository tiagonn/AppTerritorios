package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.entities.Mapa;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;

public interface CadastroView {
	
	public interface Presenter {
		void setTabSelectionEventHandler(SelectionHandler<Integer> handler);
		void onPesquisaRegiaoListBoxChange(String nomeRegiao);
	}
	
	void initView();
	void setMapaList(List<Mapa> mapas);
	void setRegiaoList(List<String> regioes);
	void setTabSelectionEventHandler(SelectionHandler<Integer> handler);
	Widget asWidget();
}
