package br.com.nascisoft.apoioterritoriols.login.server;

import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.login.client.LoginService;
import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;
import br.com.nascisoft.apoioterritoriols.login.server.dao.LoginDAO;
import br.com.nascisoft.apoioterritoriols.login.vo.LoginVO;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class LoginServiceImpl extends AbstractApoioTerritorioLSService implements
		LoginService {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(LoginServiceImpl.class.getName());

	private LoginDAO getDao() {
		return LoginDAO.INSTANCE;
	}
	
	@Override
	public LoginVO login(String requestURI) {
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();		
		LoginVO usuario = new LoginVO();
		
		if (user != null) {
			
			logger.info("Tentando realizar o login do usuário " + 
					user.getUserId() + ", e-mail: " + user.getEmail());
			
			Usuario usuarioAutenticado = getDao().obterUsuario(user.getEmail());
			
			if (usuarioAutenticado != null) {			
				usuario.setEmail(user.getEmail());
				usuario.setIdentificador(user.getUserId());
				usuario.setNickname(user.getNickname());
				usuario.setLogado(true);
				usuario.setAutorizado(true);
				usuario.setLogoutURL(userService.createLogoutURL(requestURI));
				usuario.setAdmin(usuarioAutenticado.getAdmin());				
			} else {
				logger.info("Usuário " + user.getEmail() + " não autorizado");
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
