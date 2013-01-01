package br.com.nascisoft.apoioterritoriols.admin.client;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("AdminService")
public interface AdminService extends RemoteService {
	
	void dispararBackup(String destinatarios);

	void dispararMapeamentoNovosAtributos();
	
	void adicionarOuAtualizarUsuario(String email, Boolean admin);
	
	List<Usuario> buscarUsuarios();
	
	void apagarUsuario(String email);
	
	void adicionarOuAtualizarCidade(Cidade cidade);
	
	List<Cidade> buscarCidades();
	
	void apagarCidade(String nome);
	
	void adicionarOuAtualizarRegiao(Regiao regiao, String nomeCidade);
	
	List<Regiao> buscarRegioes();
	
	void apagarRegiao(String nome);

}
