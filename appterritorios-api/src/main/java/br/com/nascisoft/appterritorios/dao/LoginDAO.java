package br.com.nascisoft.appterritorios.dao;


import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import br.com.nascisoft.appterritorios.entities.Usuario;

public class LoginDAO extends BaseDAO {
	
	public static final LoginDAO INSTANCE = new LoginDAO();
	
	public List<Usuario> obterUsuariosCadastrados() {
		return ofy().load().type(Usuario.class).list();
	}

}
