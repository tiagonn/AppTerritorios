package br.com.nascisoft.apoioterritoriols.admin.server;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.admin.client.AdminService;
import br.com.nascisoft.apoioterritoriols.admin.server.dao.AdminDAO;
import br.com.nascisoft.apoioterritoriols.login.entities.Bairro;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;
import br.com.nascisoft.apoioterritoriols.login.server.AbstractApoioTerritorioLSService;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.googlecode.objectify.Key;

public class AdminServiceImpl extends AbstractApoioTerritorioLSService implements
		AdminService {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(AdminService.class.getName());
	
	private AdminDAO dao = null;
	
	private AdminDAO getDao() {
		if (dao == null) {
			dao = new AdminDAO();
		}
		return dao;
	}

	@Override
	public void dispararBackup(String destinatarios) {
		logger.log(Level.INFO, "Disparando queue de backup");
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(withUrl("/tasks/backup").param("destinatarios", destinatarios));	
	}

	@Override
	public void dispararMapeamentoNovosAtributos() {
		logger.log(Level.INFO, "Disparando queue de mapeamento de novos atributos");
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(withUrl("/tasks/mapeamentoNovosAtributos"));		
	}

	@Override
	public void adicionarOuAtualizarUsuario(String email, Boolean admin) {
		logger.log(Level.INFO, "Adicionando ou atualizando usuario " + email + " com flag de administrador " + admin);
		Usuario usuario = new Usuario();
		usuario.setEmail(email);
		usuario.setAdmin(admin);
		getDao().adicionarOuAtualizarUsuario(usuario);
	}

	@Override
	public List<Usuario> buscarUsuarios() {
		return getDao().buscarUsuarios();
	}

	@Override
	public void apagarUsuario(String email) {
		logger.log(Level.INFO, "Apagando usuario " + email);
		getDao().apagarUsuario(email);
	}

	@Override
	public void adicionarOuAtualizarCidade(Cidade cidade) {
		logger.info("Adicionando ou atualizando cidade " + cidade.getNome());
		getDao().adicionarOuAtualizarCidade(cidade);
		
	}

	@Override
	public List<Cidade> buscarCidades() {
		logger.info("Obtendo lista de cidades");
		return getDao().buscarCidades();
	}

	@Override
	public void apagarCidade(String nome) {
		logger.info("Apagando cidade " + nome);
		List<Regiao> regioes = getDao().buscarRegioes(nome);
		if (regioes == null || regioes.size() == 0) {
			List<Bairro> bairros = getDao().buscarBairros(nome);
			List<Key<Bairro>> keyBairros = new ArrayList<Key<Bairro>>();
			for (Bairro bairro : bairros) {
				keyBairros.add(new Key<Bairro>(Bairro.class, bairro.getNome()));
			}
			getDao().apagarBairros(keyBairros);
			getDao().apagarCidade(nome);
		} else {
			throw new RuntimeException("Não é possivel apagar esta cidade já que ela possui bairros associados a mesma");
		}		
	}
	
	@Override
	public void adicionarOuAtualizarRegiao(Regiao regiao, String nomeCidade) {
		Key<Cidade> keyCidade = new Key<Cidade>(Cidade.class, nomeCidade);
		regiao.setCidade(keyCidade);
		logger.info("Adicionando ou atualizando regiao " + regiao.getNome());
		getDao().adicionarOuAtualizarRegiao(regiao);
	}
	
	@Override
	public List<Regiao> buscarRegioes() {
		logger.info("Obtendo lista de regioes");
		return getDao().buscarRegioes(null);
	}
	
	@Override
	public void apagarRegiao(String nome) {
		logger.info("Apagando regiao " + nome);
		getDao().apagarRegiao(nome);
		//TODO consistência para não apagar regioes que possuam surdos associados
	}

	@Override
	public void adicionarOuAtualizarBairro(Bairro bairro, String nomeCidade) {
		Key<Cidade> keyCidade = new Key<Cidade>(Cidade.class, nomeCidade);
		bairro.setCidade(keyCidade);
		logger.info("Adicionando ou atualizando bairro " + bairro.getNome());
		getDao().adicionarOuAtualizarBairro(bairro);
	}

	@Override
	public List<Bairro> buscarBairros() {
		logger.info("Obtendo lista de bairros");
		return getDao().buscarBairros(null);
	}

	@Override
	public void apagarBairro(String nome) {
		logger.info("Apagando bairro " + nome);
		getDao().apagarBairro(nome);
	}

}
