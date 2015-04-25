package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.List;
import java.util.Set;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.AbrirMapaVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;

public interface CadastroMapaView extends CadastroView {
	
	public interface Presenter extends CadastroView.Presenter {
		void adicionarMapa(Long identificadorRegiao);
		void abrirMapa(Long identificadorMapa);
		void onAbrirMapa(Long identificadorMapa);
		void adicionarSurdosMapa(Set<Long> surdos, Long identificadorMapa);
		void removerSurdosMapa(Set<Long> surdos);
		void apagarMapa(Long identificadorMapa);
		void onPesquisaCidadeListBoxChange(Long cidadeId);
		void onPesquisaRegiaoListBoxChange(Long regiaoId);
	}
	
	void setCidadeList(List<Cidade> cidades);
	void setMapaList(List<Mapa> mapas);
	void setRegiaoList(List<Regiao> regioes);
	void setPresenter(Presenter presenter);
	void onAbrirMapa(AbrirMapaVO vo);
	void onApagarMapa();
}
