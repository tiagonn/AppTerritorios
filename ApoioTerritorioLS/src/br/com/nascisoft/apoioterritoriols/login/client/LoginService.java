package br.com.nascisoft.apoioterritoriols.login.client;

import br.com.nascisoft.apoioterritoriols.login.vo.LoginVO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("LoginService")
public interface LoginService extends RemoteService {
	
	public LoginVO login(String requestURI);
	
}
