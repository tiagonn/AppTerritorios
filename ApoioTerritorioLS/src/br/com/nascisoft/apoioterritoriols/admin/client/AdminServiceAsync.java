package br.com.nascisoft.apoioterritoriols.admin.client;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface AdminServiceAsync {

	void dispararBackup(String destinatarios, AsyncCallback<Void> callback);

	void dispararMapeamentoNovosAtributos(AsyncCallback<Void> callback);

	void adicionarOuAtualizarUsuario(String email, Boolean admin,
			AsyncCallback<Void> callback);

	void buscarUsuarios(AsyncCallback<List<Usuario>> callback);

	void apagarUsuario(String email, AsyncCallback<Void> callback);
	
}
