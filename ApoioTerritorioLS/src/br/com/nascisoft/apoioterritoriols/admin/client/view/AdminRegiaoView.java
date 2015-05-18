package br.com.nascisoft.apoioterritoriols.admin.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.admin.vo.RegiaoVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;




public interface AdminRegiaoView extends AdminView {
	
	public interface Presenter extends AdminView.Presenter {
		void adicionarOuAtualizarRegiao(RegiaoVO regiao);
		void buscarRegioes();
		void buscarCidades();
		void apagarRegiao(Long id);
	}
	
	void setPresenter(Presenter presenter);
	void setRegioes(List<RegiaoVO> regioes);
	void setCidades(List<Cidade> cidades);
	void onApagarRegiao(Long id);
}
