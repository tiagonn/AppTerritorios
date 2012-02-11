package br.com.nascisoft.apoioterritoriols.cadastro.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.login.client.LoginService;
import br.com.nascisoft.apoioterritoriols.login.client.LoginServiceAsync;
import br.com.nascisoft.apoioterritoriols.login.vo.LoginVO;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class CadastroEntryPoint implements EntryPoint {
	
	private static final Logger logger = Logger
		.getLogger(CadastroEntryPoint.class.getName());

	@Override
	public void onModuleLoad() {

		//TODO: feature de backup
		//TODO: passar a suportar cidade
		//TODO: ceder/revogar permissão para usuário
		//TODO: feature de mudou-se
		//TODO: feature de não visitar
		//TODO: feature de import de backup		
		//TODO: Nome da região na impressão
		//TODO: Ao salvar edição de surdo sem alterar endereço não abrir popup de confirmação de endereço.
		//TODO: Ao salvar uma edição ou adição de surdo, usar os mesmos critérios de busca anterior.
		//TODO: Ao permanecer o mouse em cima de um surdo mostrar informações detalhadas dele.
		//TODO: Na aba mapas, mostrar mais informações no popup do surdo no mapa, e colocar um link para entrar na edição deste surdo.
		//TODO: Ao terminar de imprimir um mapa, voltar para a aba de impressão mantendo a seleção anterior da região.
		//TODO: Estudar diminuir o limite para 3 surdos por mapa.
		//TODO: Listar a letra das regiões, principalmente no combo de regiões, e ordená-las pela letra.
		//TODO: Ação inicial ao abrir cadastro de surdos já buscando todos os surdos sem aplicação de filtro, e trocar ação Pesquisar por Filtrar

		LoginServiceAsync loginService = GWT.create(LoginService.class);
		((ServiceDefTarget)loginService).setServiceEntryPoint("/login/LoginService");
		loginService.login(GWT.getHostPageBaseURL()+"Cadastro.html", new AsyncCallback<LoginVO>() {
			
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
