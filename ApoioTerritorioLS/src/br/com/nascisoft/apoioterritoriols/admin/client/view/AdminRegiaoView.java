package br.com.nascisoft.apoioterritoriols.admin.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;




public interface AdminRegiaoView extends AdminView {
	
	public interface Presenter extends AdminView.Presenter {
		void adicionarOuAtualizarRegiao(Regiao regiao, String nomeCidade);
		void buscarRegioes();
		void buscarCidades();
		void apagarRegiao(String nome);
	}
	
	void setPresenter(Presenter presenter);
	void setRegioes(List<Regiao> regioes);
	void setCidades(List<Cidade> cidades);
}
