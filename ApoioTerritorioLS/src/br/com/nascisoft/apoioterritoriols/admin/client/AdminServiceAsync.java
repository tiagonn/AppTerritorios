package br.com.nascisoft.apoioterritoriols.admin.client;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.admin.vo.BairroVO;
import br.com.nascisoft.apoioterritoriols.admin.vo.RegiaoVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
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

	void apagarCidade(Long id, AsyncCallback<Boolean> callback);

	void adicionarOuAtualizarRegiao(RegiaoVO regiao,
			AsyncCallback<Void> callback);

	void buscarRegioes(AsyncCallback<List<RegiaoVO>> callback);

	void apagarRegiao(Long id, AsyncCallback<Boolean> callback);

	void adicionarOuAtualizarBairro(BairroVO bairro,
			AsyncCallback<Void> callback);

	void buscarBairros(AsyncCallback<List<BairroVO>> callback);

	void apagarBairro(Long id, AsyncCallback<Void> callback);
	
}
