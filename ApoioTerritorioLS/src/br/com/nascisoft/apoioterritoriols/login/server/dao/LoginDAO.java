package br.com.nascisoft.apoioterritoriols.login.server.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;

public class LoginDAO extends BaseDAO {
	
	public static final LoginDAO INSTANCE = new LoginDAO();
	
	public List<Usuario> obterUsuariosCadastrados() {
		return ofy().load().type(Usuario.class).list();
	}

}
