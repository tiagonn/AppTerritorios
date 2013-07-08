package br.com.nascisoft.apoioterritoriols.admin.client;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.admin.vo.BairroVO;
import br.com.nascisoft.apoioterritoriols.admin.vo.RegiaoVO;
import br.com.nascisoft.apoioterritoriols.admin.vo.RelatorioVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("AdminService")
public interface AdminService extends RemoteService {
	
	void dispararBackup(String destinatarios);
	
	void adicionarOuAtualizarUsuario(String email, Boolean admin);
	
	List<Usuario> buscarUsuarios();
	
	void apagarUsuario(String email);
	
	void adicionarOuAtualizarCidade(Cidade cidade);
	
	List<Cidade> buscarCidades();
	
	Boolean apagarCidade(Long id);
	
	void adicionarOuAtualizarRegiao(RegiaoVO regiao);
	
	List<RegiaoVO> buscarRegioes();
	
	Boolean apagarRegiao(Long id);
	
	void adicionarOuAtualizarBairro(BairroVO bairro);
	
	List<BairroVO> buscarBairros();
	
	void apagarBairro(Long id);
	
	RelatorioVO obterDadosRelatorio();

}
