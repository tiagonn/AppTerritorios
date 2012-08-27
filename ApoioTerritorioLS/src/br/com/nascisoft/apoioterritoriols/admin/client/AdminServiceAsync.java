package br.com.nascisoft.apoioterritoriols.admin.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface AdminServiceAsync {

	void dispararBackup(String destinatarios, AsyncCallback<Void> callback);

	void dispararMapeamentoNovosAtributos(AsyncCallback<Void> callback);
	
}
