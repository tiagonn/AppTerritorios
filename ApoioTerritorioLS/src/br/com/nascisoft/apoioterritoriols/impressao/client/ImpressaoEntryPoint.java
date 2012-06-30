package br.com.nascisoft.apoioterritoriols.impressao.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.cadastro.client.CadastroService;
import br.com.nascisoft.apoioterritoriols.cadastro.client.CadastroServiceAsync;
import br.com.nascisoft.apoioterritoriols.login.client.LoginService;
import br.com.nascisoft.apoioterritoriols.login.client.LoginServiceAsync;
import br.com.nascisoft.apoioterritoriols.login.vo.LoginVO;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class ImpressaoEntryPoint implements EntryPoint {
	
	private static final Logger logger = Logger
		.getLogger(ImpressaoEntryPoint.class.getName());

	@Override
	public void onModuleLoad() {

		LoginServiceAsync loginService = GWT.create(LoginService.class);
		((ServiceDefTarget)loginService).setServiceEntryPoint("/login/LoginService");
		loginService.login(GWT.getHostPageBaseURL()+"Impressao.html", new AsyncCallback<LoginVO>() {
			
			@Override
			public void onSuccess(LoginVO result) {
				if (result.isLogado()) {
					if (result.isAutorizado()) {
						CadastroServiceAsync service = GWT.create(CadastroService.class);
						((ServiceDefTarget)service).setServiceEntryPoint("/cadastro/cadastroService");
						ImpressaoController controller = new ImpressaoController(service);
						controller.go(RootLayoutPanel.get());
					} else {
						Window.alert("Usuário não possui permissão de acesso. Entre em contato com o administrador de sua congregação.");
						Window.open("http://www.watchtower.org/", "_self", "");
					}
				} else {
					Window.open(result.getLoginURL(), "_self", "");
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao tentar efetuar o login\n\n" + caught);
				Window.alert("Falha ao tentar efetuar o login\n\n" + caught);
			}
		});
		
	}

}
