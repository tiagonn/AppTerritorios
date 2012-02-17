package br.com.nascisoft.apoioterritoriols.admin.server;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;
import gwtupload.server.exceptions.UploadActionException;
import gwtupload.server.gae.AppEngineUploadAction;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

import org.apache.commons.fileupload.FileItem;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;

public class RestauracaoUploadServlet extends AppEngineUploadAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(RestauracaoUploadServlet.class.getName());
	
	@Override
	public String executeAction(HttpServletRequest request,
			List<FileItem> sessionFiles) throws UploadActionException {
		
		FileItem file = sessionFiles.get(0);
		try {
			Cache cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.EMPTY_MAP);
			CacheManager.getInstance().registerCache("ARQUIVO_UPLOAD", cache);
			cache.put("BACKUP", file.get());
		} catch (CacheException e) {
			logger.log(Level.SEVERE, "Erro ao adicionar cache", e);
			throw new RuntimeException("Erro ao adicionar cache", e);
		} 
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(withUrl("/tasks/restauracao"));
		
//		removeSessionFileItems(request);
		
		return super.executeAction(request, sessionFiles);
	}

}
