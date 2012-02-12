package br.com.nascisoft.apoioterritoriols.login.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.cadastro.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.cadastro.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.login.client.LoginService;
import br.com.nascisoft.apoioterritoriols.login.vo.LoginVO;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.ObjectifyService;

public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(LoginServiceImpl.class.getName());
	
	private static final List<String> usuariosValidos;
	
	private static final List<String> usuariosAdministradores;
	
	static {
		usuariosValidos = new ArrayList<String>();
		usuariosValidos.add("tiagonn@gmail.com");
		usuariosValidos.add("monteiro.camila@gmail.com");
		usuariosValidos.add("ls.centralcampinas@gmail.com");
		usuariosValidos.add("rogeriocarvalho.carvalho98@gmail.com");
		usuariosValidos.add("holz.julio@gmail.com");
		usuariosValidos.add("carlos.h.marciano@gmail.com");
		usuariosValidos.add("matheus.a.barreira@gmail.com");
		
		usuariosAdministradores = new ArrayList<String>();
		usuariosAdministradores.add("tiagonn@gmail.com");
	
			// já que o LoginService é sempre chamado, vou registrar as classes 
			// de persistência aqui. Quando o LoginService usar um DAO, vou repassar
			// este bloco estático para o DAO.
		logger.log(Level.INFO, "Registrando entidade Surdo");
		ObjectifyService.register(Surdo.class);
		logger.log(Level.INFO, "Registrando entidade Mapa");
		ObjectifyService.register(Mapa.class);
		logger.log(Level.INFO, "Entidades registradas com sucesso");

	}

	@Override
	public LoginVO login(String requestURI) {
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();		
		LoginVO usuario = new LoginVO();
		
		if (user != null) {
			if (usuariosValidos.contains(user.getEmail())) {
//			if (true) {
				logger.info("Tentando realizar o login do usuário " + 
						user.getUserId() + ", e-mail: " + user.getEmail());
				usuario.setEmail(user.getEmail());
				usuario.setIdentificador(user.getUserId());
				usuario.setNickname(user.getNickname());
				usuario.setLogado(true);
				usuario.setAutorizado(true);
				usuario.setLogoutURL(userService.createLogoutURL(requestURI));
				if (usuariosAdministradores.contains(user.getEmail())) {
					usuario.setAdmin(true);
				}
			} else {
				logger.info("Usuário " + user.getEmail() + "não autorizado");
				usuario.setEmail(user.getEmail());
				usuario.setLogado(true);
				usuario.setAutorizado(false);
			}
		} else {
			logger.info("Usuário não logado, redirecionando para tela de login");
			usuario.setLogado(false);
			usuario.setAutorizado(false);
			usuario.setLoginURL(userService.createLoginURL(requestURI));
		}
		return usuario;
	}


}
