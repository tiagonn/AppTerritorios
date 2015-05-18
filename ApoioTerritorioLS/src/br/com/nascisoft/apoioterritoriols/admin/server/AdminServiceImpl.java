package br.com.nascisoft.apoioterritoriols.admin.server;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.admin.client.AdminService;
import br.com.nascisoft.apoioterritoriols.admin.server.dao.AdminDAO;
import br.com.nascisoft.apoioterritoriols.admin.vo.BairroVO;
import br.com.nascisoft.apoioterritoriols.admin.vo.RegiaoVO;
import br.com.nascisoft.apoioterritoriols.admin.vo.RelatorioVO;
import br.com.nascisoft.apoioterritoriols.cadastro.server.dao.CadastroDAO;
import br.com.nascisoft.apoioterritoriols.login.entities.Bairro;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;
import br.com.nascisoft.apoioterritoriols.login.server.AbstractApoioTerritorioLSService;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.utils.SystemProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.VoidWork;

public class AdminServiceImpl extends AbstractApoioTerritorioLSService implements
		AdminService {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(AdminService.class.getName());
	
	private AdminDAO getDao() {
		return AdminDAO.INSTANCE;
	}
	
	private CadastroDAO getCadastroDao() {
		return CadastroDAO.INSTANCE;
	}

	@Override
	public void dispararBackup(String destinatarios, String tipo) {
		logger.log(Level.INFO, "Disparando queue de backup");
		Queue queue = QueueFactory.getDefaultQueue();
        UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		queue.add(withUrl("/tasks/backup")
				.param("destinatarios", destinatarios)
				.param("remetente", user.getEmail())
				.param("tipo", tipo));	
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
		return new ArrayList<Usuario>(getDao().obterUsuarios());
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
		return new ArrayList<Cidade>(getDao().obterCidades());
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
				keyBairros.add(Key.create(Bairro.class, bairro.getId()));
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
		reg.setCidade(Key.create(Cidade.class, regiao.getCidadeId()));
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
		
		return new ArrayList<RegiaoVO>(retorno);
	}
	
	@Override
	public Boolean apagarRegiao(final Long id) {
		logger.info("Apagando regiao " + id);
		
		List<Surdo> surdos = getCadastroDao().obterSurdos(null, null, id, null, null);
		if (surdos == null || surdos.size() == 0) {
			Iterator<Surdo> it = getCadastroDao().obterSurdosNaoVisitar().iterator();
			while (it.hasNext()) {
				Surdo surdo = it.next();
				if (!id.equals(surdo.getRegiao().getId())) {
					surdos.remove(surdo);
				}
			}
		}
		
		Boolean apagar = surdos == null || surdos.size() == 0;
		if (apagar) {
			ofy().transact(new VoidWork() {
				@Override
				public void vrun() {
					getDao().apagarRegiao(id);
				}			
			});
			logger.info("Região apagada com sucesso");
		} else {
			logger.info("Região não foi apagada pois possui pessoas associadas a ela");
		}
		
		return apagar;
	}

	@Override
	public void adicionarOuAtualizarBairro(BairroVO bairro) {
		logger.info("Adicionando ou atualizando bairro " + bairro.getNome());
		Bairro bar = bairro.getBairro();
		bar.setCidade(Key.create(Cidade.class, bairro.getCidadeId()));
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
		
		return new ArrayList<BairroVO>(retorno);
	}

	@Override
	public void apagarBairro(Long id) {
		logger.info("Apagando bairro " + id);
		getDao().apagarBairro(id);
	}

	@Override
	public RelatorioVO obterDadosRelatorio() {
		List<Surdo> surdos = this.getDao().obterSurdosAtivos();
		
		Set<Key<Regiao>> chavesRegiao = new HashSet<Key<Regiao>>();
		Set<Key<Cidade>> chavesCidade = new HashSet<Key<Cidade>>();
		
		RelatorioVO retorno = new RelatorioVO();
		
		for (Surdo surdo : surdos) {
			retorno.adiciona(surdo);
			chavesRegiao.add(surdo.getRegiao());
			chavesCidade.add(surdo.getCidade());
		}
		
		Map<Key<Regiao>, Regiao> mapasRegiao = this.getDao().obterRegioes(chavesRegiao);
		Map<Key<Cidade>, Cidade> mapasCidade = this.getDao().obterCidades(chavesCidade);
		
		retorno.acertaReferencias(mapasCidade, mapasRegiao);

		return retorno;
	}

	@Override
	public void dispararExport(String destinatario) {
		logger.log(Level.INFO, "Disparando queue de export");
		Queue queue = QueueFactory.getDefaultQueue();
        UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		queue.add(withUrl("/tasks/export").param("destinatario", destinatario).param("remetente", user.getEmail()));	
	}

	@Override
	public String obterUploadAction() {
		BlobstoreService blobStoreService = BlobstoreServiceFactory.getBlobstoreService();
		String url = blobStoreService.createUploadUrl("/restauracao/upload");
		// change the computer name to standard localhost ip address, if in dev mode
		if(SystemProperty.environment.value() == SystemProperty.Environment.Value.Development) {
		    try {
				url = url.replace(InetAddress.getLocalHost().getHostName(), "127.0.0.1");
			} catch (UnknownHostException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return url;
	}
	

}
