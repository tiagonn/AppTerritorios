package br.com.nascisoft.apoioterritoriols.admin.server;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.admin.client.AdminService;
import br.com.nascisoft.apoioterritoriols.admin.server.dao.AdminDAO;
import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;
import br.com.nascisoft.apoioterritoriols.login.server.AbstractApoioTerritorioLSService;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;

public class AdminServiceImpl extends AbstractApoioTerritorioLSService implements
		AdminService {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(AdminService.class.getName());
	
	private AdminDAO dao = null;
	
	private AdminDAO getDao() {
		if (dao == null) {
			dao = new AdminDAO();
		}
		return dao;
	}

	@Override
	public void dispararBackup(String destinatarios) {
		logger.log(Level.INFO, "Disparando queue de backup");
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(withUrl("/tasks/backup").param("destinatarios", destinatarios));	
	}

	@Override
	public void dispararMapeamentoNovosAtributos() {
		logger.log(Level.INFO, "Disparando queue de mapeamento de novos atributos");
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(withUrl("/tasks/mapeamentoNovosAtributos"));		
	}

	@Override
	public void adicionarOuAtualizarUsuario(String email, Boolean admin) {
		logger.log(Level.INFO, "Adicionando ou atualizando usuario " + email + " com flag de administrador " + admin);
		Usuario usuario = new Usuario();
		usuario.setEmail(email);
		usuario.setAdmin(admin);
		getDao().adicionarOuAtualizarUsuario(usuario);
	}

	@Override
	public List<Usuario> buscarUsuarios() {
		return getDao().buscarUsuarios();
	}

	@Override
	public void apagarUsuario(String email) {
		logger.log(Level.INFO, "Apagando usuario " + email);
		getDao().apagarUsuario(email);
	}

}
