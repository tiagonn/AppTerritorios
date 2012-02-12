package br.com.nascisoft.apoioterritoriols.admin.server.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.cadastro.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.cadastro.entities.Surdo;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.util.DAOBase;

public class AdminDAO extends DAOBase {
	
	private static final Logger logger = Logger.getLogger(AdminDAO.class.getName());
	
	static {
		logger.log(Level.INFO, "Registrando entidade Surdo");
		ObjectifyService.register(Surdo.class);
		logger.log(Level.INFO, "Registrando entidade Mapa");
		ObjectifyService.register(Mapa.class);
		logger.log(Level.INFO, "Entidades registradas com sucesso");
	}
	
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

}
