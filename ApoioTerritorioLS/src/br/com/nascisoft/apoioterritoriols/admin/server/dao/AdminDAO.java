package br.com.nascisoft.apoioterritoriols.admin.server.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import br.com.nascisoft.apoioterritoriols.login.entities.Bairro;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.util.DAOBase;

public class AdminDAO extends DAOBase {
	
//	private static final Logger logger = Logger.getLogger(AdminDAO.class.getName());
	
	public List<Mapa> obterMapas() {
		Objectify ofy = ObjectifyService.begin();
		Query<Mapa> mapas = ofy.query(Mapa.class);
		return mapas.list();
	}
	
	public List<Surdo> obterSurdosAtivos() {
		Objectify ofy = ObjectifyService.begin();
		Query<Surdo> surdos = ofy.query(Surdo.class);
		surdos.filter("estaAssociadoMapa", true);
		return surdos.list();
	}
	
	public List<Surdo> obterSurdos() {
		Objectify ofy = ObjectifyService.begin();
		Query<Surdo> surdos = ofy.query(Surdo.class);
		return surdos.list();
	}
	
	public Long adicionarMapa(Mapa mapa) {
		Objectify ofy = ObjectifyService.begin();
		return ofy.put(mapa).getId();
	}

	public Long adicionarSurdo(Surdo surdo) {
		Objectify ofy = ObjectifyService.begin();
		return ofy.put(surdo).getId();
	}
	
	public void adicionarOuAtualizarUsuario(Usuario usuario) {
		Objectify ofy = ObjectifyService.begin();
		ofy.put(usuario);
	}
	
	public List<Usuario> obterUsuarios() {
		Objectify ofy = ObjectifyService.begin();
		Query<Usuario> usuarios = ofy.query(Usuario.class);
		return usuarios.list();
	}
	
	public void apagarUsuario(String email) {
		Objectify ofy = ObjectifyService.begin();
		ofy.delete(Usuario.class, email);
	}
	
	public Long adicionarOuAtualizarCidade(Cidade cidade) {
		Objectify ofy = ObjectifyService.begin();
		return ofy.put(cidade).getId();
	}
	
	public List<Cidade> obterCidades() {
		Objectify ofy = ObjectifyService.begin();
		return ofy.query(Cidade.class).list();
	}
	
	public List<Regiao> obterRegioes() {
		Objectify ofy = ObjectifyService.begin();
		return ofy.query(Regiao.class).list();
	}
	
	public List<Bairro> obterBairros() {
		Objectify ofy = ObjectifyService.begin();
		return ofy.query(Bairro.class).list();
	}
	
	public void apagarCidade(Long id) {
		Objectify ofy = ObjectifyService.begin();
		ofy.delete(Cidade.class, id);
	}
	
	public Long adicionarOuAtualizarRegiao(Regiao regiao) {
		Objectify ofy = ObjectifyService.begin();
		return ofy.put(regiao).getId();
	}
	
	public List<Regiao> buscarRegioes(Long cidadeId) {
		Objectify ofy = ObjectifyService.begin();
		
		Query<Regiao> query = ofy.query(Regiao.class);
		
		if (cidadeId != null) {
			query.filter("cidade", new Key<Cidade>(Cidade.class, cidadeId));
		}
		
		query.order("letra");
		
		return query.list();
	}
	
	public void apagarRegiao(Long id) {
		Objectify ofy = ObjectifyService.begin();
		ofy.delete(Regiao.class, id);
	}
	
	public void adicionarOuAtualizarBairro(Bairro bairro) {
		Objectify ofy = ObjectifyService.begin();
		ofy.put(bairro);
	}
	
	public List<Bairro> buscarBairros(Long cidadeId, String nome) {
		Objectify ofy = ObjectifyService.begin();
		
		Query<Bairro> query = ofy.query(Bairro.class);
		
		if (cidadeId != null) {
			query.filter("cidade", new Key<Cidade>(Cidade.class, cidadeId));
		}
		
		if (nome != null) {
			query.filter("nome", nome);
		}
		
		return query.list();
	}
	
	public void apagarBairro(Long id) {
		Objectify ofy = ObjectifyService.begin();
		ofy.delete(Bairro.class, id);
	}
	
	public void apagarBairros(Iterable<Key<Bairro>> bairros) {
		Objectify ofy = ObjectifyService.begin();
		ofy.delete(bairros);
	}
	
	public Map<Key<Cidade>, Cidade> obterCidades(Collection<Key<Cidade>> chaves) {
		Objectify ofy = ObjectifyService.begin();
		return ofy.get(chaves);
	}
	
	public Map<Key<Regiao>, Regiao> obterRegioes(Collection<Key<Regiao>> chaves) {
		Objectify ofy = ObjectifyService.begin();
		return ofy.get(chaves);
	}
	
	public Regiao obterRegiao(Long regiaoId) {
		Objectify ofy = ObjectifyService.begin();
		return ofy.get(new Key<Regiao>(Regiao.class, regiaoId));
	}
	
	public Cidade obterCidade(Long cidadeId) {
		Objectify ofy = ObjectifyService.begin();
		return ofy.get(new Key<Cidade>(Cidade.class, cidadeId));
	}

}
