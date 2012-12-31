package br.com.nascisoft.apoioterritoriols.admin.server.dao;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;

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

}
