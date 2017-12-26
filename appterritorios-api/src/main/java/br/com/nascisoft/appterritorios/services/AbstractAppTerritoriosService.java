package br.com.nascisoft.appterritorios.services;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.googlecode.objectify.ObjectifyService;

import br.com.nascisoft.appterritorios.entities.Bairro;
import br.com.nascisoft.appterritorios.entities.Cidade;
import br.com.nascisoft.appterritorios.entities.Mapa;
import br.com.nascisoft.appterritorios.entities.Regiao;
import br.com.nascisoft.appterritorios.entities.Surdo;
import br.com.nascisoft.appterritorios.entities.Usuario;

public abstract class AbstractAppTerritoriosService {
	
	private static final Logger logger = Logger
			.getLogger(AbstractAppTerritoriosService.class.getName());
	
	static {
		logger.log(Level.INFO, "Registrando entidade Surdo");
		ObjectifyService.register(Surdo.class);
		logger.log(Level.INFO, "Registrando entidade Mapa");
		ObjectifyService.register(Mapa.class);
		logger.log(Level.INFO, "Registrando entidade Usuario");
		ObjectifyService.register(Usuario.class);
		logger.log(Level.INFO, "Registrando entidade Cidade");
		ObjectifyService.register(Cidade.class);
		logger.log(Level.INFO, "Registrando entidade Regiao");
		ObjectifyService.register(Regiao.class);
		logger.log(Level.INFO, "Registrando entidade Bairro");
		ObjectifyService.register(Bairro.class);
		logger.log(Level.INFO, "Entidades registradas com sucesso");
	}

}
