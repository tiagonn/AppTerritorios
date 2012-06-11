package br.com.nascisoft.apoioterritoriols.admin.server;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;
import br.com.nascisoft.apoioterritoriols.admin.client.AdminService;
import br.com.nascisoft.apoioterritoriols.login.server.AbstractApoioTerritorioLSService;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;

public class AdminServiceImpl extends AbstractApoioTerritorioLSService implements
		AdminService {

	private static final long serialVersionUID = 1L;
	
//	private static final Logger logger = Logger.getLogger(AdminService.class.getName());

	@Override
	public void dispararBackup(String destinatarios) {
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(withUrl("/tasks/backup").param("destinatarios", destinatarios));	
	}

}
