package br.com.nascisoft.apoioterritoriols.login.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.login.client.LoginService;
import br.com.nascisoft.apoioterritoriols.login.vo.LoginVO;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class LoginServiceImpl extends AbstractApoioTerritorioLSService implements
		LoginService {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(LoginServiceImpl.class.getName());
	
	private static final List<String> usuariosValidos;
	
	private static final List<String> usuariosAdministradores;
	
	static {
		usuariosValidos = new ArrayList<String>();
		usuariosValidos.add("lspaulinia@gmail.com");
		usuariosValidos.add("lspaulinia.territorios@gmail.com");
		
		usuariosAdministradores = new ArrayList<String>();
		usuariosAdministradores.add("lspaulinia@gmail.com");

	}

	@Override
	public LoginVO login(String requestURI) {
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();		
		LoginVO usuario = new LoginVO();
		
		if (user != null) {
			if (usuariosValidos.contains(user.getEmail())) {
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
