package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.List;



public interface ImpressaoView extends CadastroView {
	
	public interface Presenter extends CadastroView.Presenter {
		void abrirImpressao(List<Long> mapasIDs, Boolean paisagem);
	}
	
	void setPresenter(Presenter presenter);
}
