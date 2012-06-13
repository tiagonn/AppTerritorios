package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoNaoVisitarDetailsVO;



public interface NaoVisitarView extends CadastroView {
	
	public interface Presenter extends CadastroView.Presenter {
		void obterSurdosNaoVisitar();
		void onRetornarButtonClick(Long identificadorSurdo);
		void onRetornar(Long identificadorSurdo);
	}
	
	void setPresenter(Presenter presenter);
	void setSurdosNaoVisitar(List<SurdoNaoVisitarDetailsVO> surdos);

}
