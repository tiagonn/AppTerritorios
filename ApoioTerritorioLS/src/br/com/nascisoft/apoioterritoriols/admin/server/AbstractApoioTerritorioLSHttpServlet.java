package br.com.nascisoft.apoioterritoriols.admin.server;

import javax.servlet.http.HttpServlet;

public abstract class AbstractApoioTerritorioLSHttpServlet extends HttpServlet {
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	static {
			// isto é necessário para ter certeza de que as classes de persistencia
			// foram carregadas
		new AdminServiceImpl();
	}

}
