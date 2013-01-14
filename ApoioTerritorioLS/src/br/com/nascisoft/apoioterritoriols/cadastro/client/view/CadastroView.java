package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.vo.LoginVO;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;

public interface CadastroView {
	
	public interface Presenter {
		void initView();
		void setTabSelectionEventHandler(SelectionHandler<Integer> handler);
		void selectThisTab();
		void onPesquisaCidadeListBoxChange(Long cidadeId);
		void onPesquisaRegiaoListBoxChange(Long regiaoId);
		LoginVO getLoginInformation();
	}
	
	void showWaitingPanel();
	void hideWaitingPanel();
	void initView();
	void selectThisTab();
	void setCidadeList(List<Cidade> cidades);
	void setMapaList(List<Mapa> mapas);
	void setRegiaoList(List<Regiao> regioes);
	void setTabSelectionEventHandler(SelectionHandler<Integer> handler);
	Widget asWidget();
}
