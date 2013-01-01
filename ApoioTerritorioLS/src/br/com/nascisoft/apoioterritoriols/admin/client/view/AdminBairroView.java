package br.com.nascisoft.apoioterritoriols.admin.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.admin.vo.BairroVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;




public interface AdminBairroView extends AdminView {
	
	public interface Presenter extends AdminView.Presenter {
		void adicionarOuAtualizarBairro(BairroVO bairro);
		void buscarBairros();
		void buscarCidades();
		void apagarBairro(Long id);
	}
	
	void setPresenter(Presenter presenter);
	void setBairros(List<BairroVO> bairros);
	void setCidades(List<Cidade> cidades);
}
