package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;



public interface ImpressaoView extends CadastroView {
	
	public interface Presenter extends CadastroView.Presenter {
		void abrirImpressao(List<Long> mapasIDs, Boolean paisagem);
		void onPesquisaCidadeListBoxChange(Long cidadeId);
		void onPesquisaRegiaoListBoxChange(Long regiaoId);
	}
	
	void setCidadeList(List<Cidade> cidades);
	void setMapaList(List<Mapa> mapas);
	void setRegiaoList(List<Regiao> regioes);
	void setPresenter(Presenter presenter);
}
