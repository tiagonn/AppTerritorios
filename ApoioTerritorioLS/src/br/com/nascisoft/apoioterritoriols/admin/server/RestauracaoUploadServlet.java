package br.com.nascisoft.apoioterritoriols.admin.server;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;

public class RestauracaoUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(RestauracaoUploadServlet.class.getName());
	
	protected void doPost(HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
		
		 BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		 
		 Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		  List<BlobKey> blobKeys = blobs.get("restauracaoFileUpload");
		 
		 if (blobKeys == null || blobKeys.size()==0) {
			 logger.severe("Chave blob não encontrada");
		 } else {
			 
			 logger.info("Chave do blob obtida: " + blobKeys.get(0).toString());
			 Queue queue = QueueFactory.getDefaultQueue();
			 queue.add(
					withUrl("/tasks/restauracao").
						param("key", blobKeys.get(0).getKeyString()).
						param("end", String.valueOf(blobstoreService.getBlobInfos(req).get("restauracaoFileUpload").get(0).getSize())));
			
		 }

		super.doPost(req, resp);
	}

}
