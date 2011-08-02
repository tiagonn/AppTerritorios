package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.AbrirMapaVO;

public interface CadastroMapaView extends CadastroView {
	
	public interface Presenter extends CadastroView.Presenter {
		void adicionarMapa(String nomeRegiao);
		void abrirMapa(Long identificadorMapa);
		void onAbrirMapa(Long identificadorMapa);
		void adicionarSurdosMapa(List<Long> surdos, Long identificadorMapa);
		void removerSurdosMapa(List<Long> surdos);
		void apagarMapa(Long identificadorMapa);
	}
	
	void setPresenter(Presenter presenter);
	void onAbrirMapa(AbrirMapaVO vo);
	void onApagarMapa();
}
