package br.com.nascisoft.apoioterritoriols.admin.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;




public interface AdminCidadeView extends AdminView {
	
	public interface Presenter extends AdminView.Presenter {
		void adicionarOuAtualizarCidade(Cidade cidade);
		void buscarCidades();
		void apagarCidade(Long id);
	}
	
	void setPresenter(Presenter presenter);
	void setCidades(List<Cidade> cidades);
}
