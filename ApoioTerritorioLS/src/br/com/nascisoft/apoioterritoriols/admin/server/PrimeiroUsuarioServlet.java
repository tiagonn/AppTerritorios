package br.com.nascisoft.apoioterritoriols.admin.server;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.nascisoft.apoioterritoriols.admin.server.dao.AdminDAO;
import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;

public class PrimeiroUsuarioServlet extends
		AbstractApoioTerritorioLSHttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(PrimeiroUsuarioServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		logger.info("Entrando em servlet de mapeamento de primeiro usuario");
		
		AdminDAO dao = new AdminDAO();
		
		List<Usuario> usuarios = dao.buscarUsuarios();
		
		if (usuarios == null || usuarios.size() == 0) {
			String nome = req.getParameter("nome");
			logger.info("Primeiro usuario, adicionando usuario " + nome + " com perfil admin");
			Usuario usuario = new Usuario();
			usuario.setAdmin(true);
			usuario.setEmail(nome);
			dao.adicionarOuAtualizarUsuario(usuario);
		} else {
			logger.info(
					"Ja existe usuario para esta instancia. Nenhum usario foi adicionado. Tentativa para usuario " + 
							req.getParameter("nome"));
		}
		
	}

}
