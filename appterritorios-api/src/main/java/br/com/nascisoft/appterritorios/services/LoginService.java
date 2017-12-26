package br.com.nascisoft.appterritorios.services;

import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import br.com.nascisoft.appterritorios.dao.LoginDAO;
import br.com.nascisoft.appterritorios.entities.Usuario;
import br.com.nascisoft.appterritorios.vo.LoginVO;

public class LoginService extends AbstractAppTerritoriosService {

	private static final Logger logger = Logger
			.getLogger(LoginService.class.getName());

	private LoginDAO getDao() {
		return LoginDAO.INSTANCE;
	}
	
	public LoginVO login(String requestURI) {
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();		
		LoginVO usuario = new LoginVO();
		
		if (user != null) {
			
			logger.info("Tentando realizar o login do usuário " + 
					user.getUserId() + ", e-mail: " + user.getEmail());
			
			List<Usuario> usuarios = getDao().obterUsuariosCadastrados();
			Usuario usuarioAutenticado = null;
			for (Usuario usuarioLista : usuarios) {
				if (user.getEmail().equalsIgnoreCase(usuarioLista.getEmail())) {
					usuarioAutenticado = usuarioLista;
					break;
				}
			}
			
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
