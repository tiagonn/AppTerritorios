package br.com.nascisoft.apoioterritoriols.login.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.ObjectifyService;

public abstract class AbstractApoioTerritorioLSService extends RemoteServiceServlet {
	
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger
			.getLogger(AbstractApoioTerritorioLSService.class.getName());
	
	static {
		logger.log(Level.INFO, "Registrando entidade Surdo");
		ObjectifyService.register(Surdo.class);
		logger.log(Level.INFO, "Registrando entidade Mapa");
		ObjectifyService.register(Mapa.class);
		logger.log(Level.INFO, "Registrando entidade Usuario");
		ObjectifyService.register(Usuario.class);
		logger.log(Level.INFO, "Entidades registradas com sucesso");
	}

}
