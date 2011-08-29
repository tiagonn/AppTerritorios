package br.com.nascisoft.apoioterritoriols.cadastro.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.LoginVO;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class CadastroEntryPoint implements EntryPoint {
	
	private static final Logger logger = Logger
		.getLogger(CadastroEntryPoint.class.getName());

	@Override
	public void onModuleLoad() {

		//TODO: feature de backup
		//TODO: ceder/revogar permissão para usuário
		//TODO: feature de mudou-se
		//TODO: feature de não visitar
		//TODO: feature de import de backup		
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginVO>() {
			
			@Override
			public void onSuccess(LoginVO result) {
				if (result.isLogado()) {
					if (result.isAutorizado()) {
						CadastroServiceAsync service = GWT.create(CadastroService.class);
						HandlerManager eventBus = new HandlerManager(null);
						CadastroController controller = new CadastroController(service, eventBus);
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
