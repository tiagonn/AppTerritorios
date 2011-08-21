package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;


public interface ImpressaoView extends CadastroView {
	
	public interface Presenter extends CadastroView.Presenter {
		void onAbrirImpressao(Long identificadorMapa, Boolean paisagem);
		void abrirImpressao(Long identificadorMapa, Boolean paisagem);
		void onVoltar();
	}
	
	void setPresenter(Presenter presenter);
	void onAbrirImpressao(Long identificadorMapa, List<SurdoVO> surdos, Boolean paisagem);
}
