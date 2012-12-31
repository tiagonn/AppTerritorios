package br.com.nascisoft.apoioterritoriols.login.server.dao;

import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;

import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;

public class LoginDAO extends DAOBase {
	
	private static final Logger logger = Logger.getLogger(LoginDAO.class.getName());
	
	public Usuario obterUsuario(String email) {
		Objectify ofy = ObjectifyService.begin();
		Usuario usuario = null;
		try {
			usuario = ofy.get(Usuario.class, email);
			logger.info("Usuario encontrado: " + email);
		} catch (NotFoundException notFound) {
			logger.info("Usuario nao encontrado: " + email);
		}
		return usuario;
	}

}
