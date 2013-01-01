package br.com.nascisoft.apoioterritoriols.admin.server.dao;

import java.util.List;

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
	
	public List<Usuario> buscarUsuarios() {
		Objectify ofy = ObjectifyService.begin();
		Query<Usuario> usuarios = ofy.query(Usuario.class);
		return usuarios.list();
	}
	
	public void apagarUsuario(String email) {
		Objectify ofy = ObjectifyService.begin();
		ofy.delete(Usuario.class, email);
	}
	
	public void adicionarOuAtualizarCidade(Cidade cidade) {
		Objectify ofy = ObjectifyService.begin();
		ofy.put(cidade);
	}
	
	public List<Cidade> buscarCidades() {
		Objectify ofy = ObjectifyService.begin();
		return ofy.query(Cidade.class).list();
	}
	
	public void apagarCidade(String nome) {
		Objectify ofy = ObjectifyService.begin();
		ofy.delete(Cidade.class, nome);
	}
	
	public void adicionarOuAtualizarRegiao(Regiao regiao) {
		Objectify ofy = ObjectifyService.begin();
		ofy.put(regiao);
	}
	
	public List<Regiao> buscarRegioes(String nomeCidade) {
		Objectify ofy = ObjectifyService.begin();
		
		Query<Regiao> query = ofy.query(Regiao.class);
		
		if (nomeCidade != null && nomeCidade.length() > 0) {
			query.filter("cidade", new Key<Cidade>(Cidade.class, nomeCidade));
		}
		
		query.order("letra");
		
		return query.list();
	}
	
	public void apagarRegiao(String nome) {
		Objectify ofy = ObjectifyService.begin();
		ofy.delete(Regiao.class, nome);
	}
	
	public void adicionarOuAtualizarBairro(Bairro bairro) {
		Objectify ofy = ObjectifyService.begin();
		ofy.put(bairro);
	}
	
	public List<Bairro> buscarBairros(String nomeCidade) {
		Objectify ofy = ObjectifyService.begin();
		
		Query<Bairro> query = ofy.query(Bairro.class);
		
		if (nomeCidade != null && nomeCidade.length() > 0) {
			query.filter("cidade", new Key<Cidade>(Cidade.class, nomeCidade));
		}
		
		return query.list();
	}
	
	public void apagarBairro(String nome) {
		Objectify ofy = ObjectifyService.begin();
		ofy.delete(Bairro.class, nome);
	}
	
	public void apagarBairros(Iterable<Key<Bairro>> bairros) {
		Objectify ofy = ObjectifyService.begin();
		ofy.delete(bairros);
	}

}
