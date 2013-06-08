package br.com.nascisoft.apoioterritoriols.admin.server;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.admin.client.AdminService;
import br.com.nascisoft.apoioterritoriols.admin.server.dao.AdminDAO;
import br.com.nascisoft.apoioterritoriols.admin.vo.BairroVO;
import br.com.nascisoft.apoioterritoriols.admin.vo.RegiaoVO;
import br.com.nascisoft.apoioterritoriols.cadastro.server.dao.CadastroDAO;
import br.com.nascisoft.apoioterritoriols.login.entities.Bairro;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;
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
	private CadastroDAO cadastroDao = null;
	
	private AdminDAO getDao() {
		if (dao == null) {
			dao = new AdminDAO();
		}
		return dao;
	}
	
	private CadastroDAO getCadastroDao() {
		if (cadastroDao == null) {
			cadastroDao = new CadastroDAO();
		}
		return cadastroDao;
	}

	@Override
	public void dispararBackup(String destinatarios) {
		logger.log(Level.INFO, "Disparando queue de backup");
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(withUrl("/tasks/backup").param("destinatarios", destinatarios));	
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
		return getDao().obterUsuarios();
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
		return getDao().obterCidades();
	}

	@Override
	public Boolean apagarCidade(Long id) {
		logger.info("Apagando cidade " + id);
		List<Regiao> regioes = getDao().buscarRegioes(id);
		Boolean retorno = false;
		if (regioes == null || regioes.size() == 0) {
			List<Bairro> bairros = getDao().buscarBairros(id, null);
			List<Key<Bairro>> keyBairros = new ArrayList<Key<Bairro>>();
			for (Bairro bairro : bairros) {
				keyBairros.add(new Key<Bairro>(Bairro.class, bairro.getId()));
			}
			getDao().apagarBairros(keyBairros);
			getDao().apagarCidade(id);
			retorno = true;
		} 
		
		return retorno;
	}
	
	@Override
	public void adicionarOuAtualizarRegiao(RegiaoVO regiao) {
		logger.info("Adicionando ou atualizando regiao " + regiao.getNome());
		Regiao reg = regiao.getRegiao();
		reg.setCidade(new Key<Cidade>(Cidade.class, regiao.getCidadeId()));
		getDao().adicionarOuAtualizarRegiao(reg);
	}
	
	@Override
	public List<RegiaoVO> buscarRegioes() {
		logger.info("Obtendo lista de regioes");
		List<Regiao> regioes = getDao().buscarRegioes(null);
		
		Set<Key<Cidade>> chavesCidade = new HashSet<Key<Cidade>>();
		for (Regiao regiao : regioes) {
			chavesCidade.add(regiao.getCidade());
		}
		
		Map<Key<Cidade>, Cidade> mapaCidades = getDao().obterCidades(chavesCidade);
		List<RegiaoVO> retorno = new ArrayList<RegiaoVO>();
		
		for (Regiao regiao : regioes) {
			retorno.add(new RegiaoVO(regiao, mapaCidades.get(regiao.getCidade())));
		}
		
		return retorno;
	}
	
	@Override
	public Boolean apagarRegiao(Long id) {
		logger.info("Apagando regiao " + id);
		List<Surdo> surdos = this.getCadastroDao().obterSurdos(null, null, id, null, null);
		if (surdos == null || surdos.size() == 0) {
			surdos = this.getCadastroDao().obterSurdosNaoVisitar(id);
		}
		
		Boolean apagar = surdos == null || surdos.size() == 0;
		if (apagar) {
			getDao().apagarRegiao(id);
		}
		return apagar;
	}

	@Override
	public void adicionarOuAtualizarBairro(BairroVO bairro) {
		logger.info("Adicionando ou atualizando bairro " + bairro.getNome());
		Bairro bar = bairro.getBairro();
		bar.setCidade(new Key<Cidade>(Cidade.class, bairro.getCidadeId()));
		getDao().adicionarOuAtualizarBairro(bar);
	}

	@Override
	public List<BairroVO> buscarBairros() {
		logger.info("Obtendo lista de bairros");
		List<Bairro> bairros = getDao().buscarBairros(null, null);
		List<BairroVO> retorno = new ArrayList<BairroVO>();
		
		Set<Key<Cidade>> chavesCidade = new HashSet<Key<Cidade>>();
		for (Bairro bairro : bairros) {
			chavesCidade.add(bairro.getCidade());
		}
		
		Map<Key<Cidade>, Cidade> mapaCidades = getDao().obterCidades(chavesCidade);
		
		for (Bairro bairro : bairros) {
			retorno.add(new BairroVO(bairro, mapaCidades.get(bairro.getCidade())));
		}
		
		return retorno;
	}

	@Override
	public void apagarBairro(Long id) {
		logger.info("Apagando bairro " + id);
		getDao().apagarBairro(id);
	}

}
