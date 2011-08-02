package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;


public interface ImpressaoView extends CadastroView {
	
	public interface Presenter extends CadastroView.Presenter {
		void onAbrirImpressao(Long identificadorMapa);
		void abrirImpressao(Long identificadorMapa);
	}
	
	void setPresenter(Presenter presenter);
	void onAbrirImpressao(List<SurdoVO> surdos);
}
