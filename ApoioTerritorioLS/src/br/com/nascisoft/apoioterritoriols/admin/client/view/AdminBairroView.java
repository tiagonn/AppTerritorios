package br.com.nascisoft.apoioterritoriols.admin.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Bairro;




public interface AdminBairroView extends AdminView {
	
	public interface Presenter extends AdminView.Presenter {
		void adicionarOuAtualizarBairro(Bairro bairro, String nomeCidade);
		void buscarBairros();
		void buscarCidades();
		void apagarBairro(String nome);
	}
	
	void setPresenter(Presenter presenter);
	void setBairros(List<Bairro> bairros);
	void setCidades(List<Cidade> cidades);
}
