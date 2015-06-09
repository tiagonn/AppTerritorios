package br.com.nascisoft.apoioterritoriols.admin.server.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.nascisoft.apoioterritoriols.login.entities.Bairro;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;
import br.com.nascisoft.apoioterritoriols.login.server.dao.BaseDAO;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.LoadType;

public class AdminDAO extends BaseDAO {
	
	public static final AdminDAO INSTANCE = new AdminDAO();
	
//	private static final Logger logger = Logger.getLogger(AdminDAO.class.getName());
	
	public List<Mapa> obterMapas() {
		return ofy().load().type(Mapa.class).list();
	}
	
	public List<Mapa> obterMapas(Long regionId) {
		return ofy().load().type(Mapa.class).filter("regiao", Key.create(Regiao.class, regionId)).list();
	}
	
	public List<Surdo> obterSurdosAtivos() {
		return ofy().load().type(Surdo.class).filter("estaAssociadoMapa", true).list();
	}
	
	public List<Surdo> obterSurdos() {
		return ofy().load().type(Surdo.class).list();
	}
	
	public Long adicionarMapa(Mapa mapa) {
		return ofy().save().entity(mapa).now().getId();
	}

	public Long adicionarSurdo(Surdo surdo) {
		return ofy().save().entity(surdo).now().getId();
	}
	
	public void adicionarOuAtualizarUsuario(Usuario usuario) {
		ofy().save().entity(usuario);
	}
	
	public List<Usuario> obterUsuarios() {
		return ofy().load().type(Usuario.class).list();
	}
	
	public void apagarUsuario(String email) {
		ofy().delete().type(Usuario.class).id(email);
	}
	
	public Long adicionarOuAtualizarCidade(Cidade cidade) {
		return ofy().save().entity(cidade).now().getId();
	}
	
	public List<Cidade> obterCidades() {
		return ofy().load().type(Cidade.class).list();
	}
	
	public List<Regiao> obterRegioes() {
		return ofy().load().type(Regiao.class).list();
	}
	
	public List<Bairro> obterBairros() {
		return ofy().load().type(Bairro.class).list();
	}
	
	public void apagarCidade(Long id) {
		ofy().delete().type(Cidade.class).id(id);
	}
	
	public Long adicionarOuAtualizarRegiao(Regiao regiao) {
		return ofy().save().entity(regiao).now().getId();
	}
	
	public List<Regiao> buscarRegioes(Long cidadeId) {
		
		LoadType<Regiao> loader = ofy().load().type(Regiao.class);
		List<Regiao> regioes = null;

		if (cidadeId != null) {
			regioes = loader.filter("cidade", Key.create(Cidade.class, cidadeId)).order("letra").list();
		} else {
			regioes = loader.order("letra").list();
		}
		
		return regioes;
	}
	
	public void apagarRegiao(Long id) {
		ofy().delete().type(Regiao.class).id(id);
	}
	
	public void adicionarOuAtualizarBairro(Bairro bairro) {
		ofy().save().entity(bairro);
	}
	
	public List<Bairro> buscarBairros(Long cidadeId, String nome) {
		
		Map<String, Object> mapaFiltros = new HashMap<String, Object>();
		if (cidadeId != null) {
			mapaFiltros.put("cidade", Key.create(Cidade.class, cidadeId));
		}
		
		if (nome != null) {
			mapaFiltros.put("nome", nome);
		}
		
		return aplicarFiltro(mapaFiltros, Bairro.class, null);
	}
	
	public void apagarBairro(Long id) {
		ofy().delete().type(Bairro.class).id(id);
	}
	
	public void apagarBairros(Iterable<Key<Bairro>> bairros) {
		ofy().delete().keys(bairros);
	}
	
	public Map<Key<Cidade>, Cidade> obterCidades(Collection<Key<Cidade>> chaves) {
		return ofy().load().keys(chaves);
	}
	
	public Map<Key<Regiao>, Regiao> obterRegioes(Collection<Key<Regiao>> chaves) {
		return ofy().load().keys(chaves);
	}
	
	public Regiao obterRegiao(Long regiaoId) {
		return ofy().load().type(Regiao.class).id(regiaoId).now();
	}
	
	public Cidade obterCidade(Long cidadeId) {
		return ofy().load().type(Cidade.class).id(cidadeId).now();
	}

}
