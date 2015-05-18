package br.com.nascisoft.apoioterritoriols.login.server.dao;

import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;
import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.NotFoundException;

public class LoginDAO extends BaseDAO {
	
	public static final LoginDAO INSTANCE = new LoginDAO();
	
	private static final Logger logger = Logger.getLogger(LoginDAO.class.getName());
	
	public Usuario obterUsuario(String email) {
		Usuario usuario = null;
		try {
			usuario = ofy().load().type(Usuario.class).id(email).now();
			logger.info("Usuario encontrado: " + email);
		} catch (NotFoundException notFound) {
			logger.info("Usuario nao encontrado: " + email);
		}
		return usuario;
	}

}
