package br.com.nascisoft.apoioterritoriols.admin.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;




public interface AdminUsuarioView extends AdminView {
	
	public interface Presenter extends AdminView.Presenter {
		void adicionarOuAtualizarUsuario(String email, Boolean admin);
		void buscarUsuarios();
		void apagarUsuario(String email);
	}
	
	void setPresenter(Presenter presenter);
	void setUsuarios(List<Usuario> usuarios);
}
