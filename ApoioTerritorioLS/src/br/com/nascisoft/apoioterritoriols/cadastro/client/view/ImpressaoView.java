package br.com.nascisoft.apoioterritoriols.cadastro.client.view;



public interface ImpressaoView extends CadastroView {
	
	public interface Presenter extends CadastroView.Presenter {
		void abrirImpressao(Long identificadorMapa, Boolean paisagem);
	}
	
	void setPresenter(Presenter presenter);
}
