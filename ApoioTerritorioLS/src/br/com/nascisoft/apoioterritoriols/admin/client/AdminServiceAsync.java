package br.com.nascisoft.apoioterritoriols.admin.client;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.login.entities.Bairro;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface AdminServiceAsync {

	void dispararBackup(String destinatarios, AsyncCallback<Void> callback);

	void dispararMapeamentoNovosAtributos(AsyncCallback<Void> callback);

	void adicionarOuAtualizarUsuario(String email, Boolean admin,
			AsyncCallback<Void> callback);

	void buscarUsuarios(AsyncCallback<List<Usuario>> callback);

	void apagarUsuario(String email, AsyncCallback<Void> callback);

	void adicionarOuAtualizarCidade(Cidade cidade, AsyncCallback<Void> callback);

	void buscarCidades(AsyncCallback<List<Cidade>> callback);

	void apagarCidade(String nome, AsyncCallback<Void> callback);

	void adicionarOuAtualizarRegiao(Regiao regiao, String nomeCidade,
			AsyncCallback<Void> callback);

	void buscarRegioes(AsyncCallback<List<Regiao>> callback);

	void apagarRegiao(String nome, AsyncCallback<Void> callback);

	void adicionarOuAtualizarBairro(Bairro bairro, String nomeCidade,
			AsyncCallback<Void> callback);

	void buscarBairros(AsyncCallback<List<Bairro>> callback);

	void apagarBairro(String nome, AsyncCallback<Void> callback);
	
}
