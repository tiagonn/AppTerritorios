package br.com.nascisoft.apoioterritoriols.login.client;

import br.com.nascisoft.apoioterritoriols.login.vo.LoginVO;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface LoginServiceAsync {

	void login(String requestURI, AsyncCallback<LoginVO> callback);

}
