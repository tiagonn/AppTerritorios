package br.com.nascisoft.apoioterritoriols.admin.client.view;

import br.com.nascisoft.apoioterritoriols.admin.vo.RelatorioVO;




public interface AdminRelatorioView extends AdminView {
	
	public interface Presenter extends AdminView.Presenter {
		void buscarDadosRelatorio();
	}
	
	void setPresenter(Presenter presenter);
	void setDados(RelatorioVO relatorio);
}
