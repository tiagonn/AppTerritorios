package br.com.nascisoft.apoioterritoriols.admin.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;
import br.com.nascisoft.apoioterritoriols.login.util.Validacoes;
import br.com.nascisoft.apoioterritoriols.resources.client.CellTableCustomResources;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class AdminUsuarioViewImpl extends Composite implements AdminUsuarioView {

	private static AdminViewUiBinderUiBinder uiBinder = GWT
			.create(AdminViewUiBinderUiBinder.class);
	
	private Presenter presenter;
	@UiField TabLayoutPanel adminTabLayoutPanel;
	@UiField PopupPanel waitingPopUpPanel;
	@UiField TextBox usuarioEmailTextBox;
	@UiField CheckBox usuarioAdministradorCheckBox;
	@UiField Button usuarioAdicionarButton;
	@UiField HTML usuariosWarningHTML;
	@UiField (provided=true) CellTable<Usuario> pesquisaUsuarioResultadoCellTable;
	@UiField Label pesquisaUsuarioResultadoLabel;
	@UiField SimplePager pesquisaUsuarioResultadoSimplePager;
	private ListDataProvider<Usuario> resultadoPesquisaUsuario;
	
	@UiTemplate("AdminViewUiBinder.ui.xml")
	interface AdminViewUiBinderUiBinder extends
			UiBinder<Widget, AdminUsuarioViewImpl> {
	}

	public AdminUsuarioViewImpl() {
		CellTableCustomResources.INSTANCE.cellTableStyle().ensureInjected();
		this.pesquisaUsuarioResultadoCellTable = new CellTable<Usuario>(10, CellTableCustomResources.INSTANCE);
		initWidget(uiBinder.createAndBindUi(this));
		this.resultadoPesquisaUsuario = new ListDataProvider<Usuario>();
		this.resultadoPesquisaUsuario.addDataDisplay(this.pesquisaUsuarioResultadoCellTable);
		this.pesquisaUsuarioResultadoSimplePager.setDisplay(this.pesquisaUsuarioResultadoCellTable);
	}

	@Override
	public void showWaitingPanel() {
		waitingPopUpPanel.setVisible(true);
		waitingPopUpPanel.show();		
	}

	@Override
	public void hideWaitingPanel() {
		waitingPopUpPanel.hide();
		waitingPopUpPanel.setVisible(false);
	}

	@Override
	public void initView() {
		this.selectThisTab();
		this.limparFormularios();
		this.limparResultadoPesquisa();
		this.presenter.buscarUsuarios();
	}

	@Override
	public void selectThisTab() {
		this.adminTabLayoutPanel.selectTab(1, false);
		
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.adminTabLayoutPanel.addSelectionHandler(handler);		
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@UiHandler("usuarioAdicionarButton")
	void onUsuarioAdicionarButtonClick(ClickEvent event) {
		Validacoes validacoes = validarAdicionarUsuario();
		if (validacoes.size() > 0) {
			this.usuariosWarningHTML.setHTML(validacoes.obterValidacoesSumarizadaHTML());
		} else {
			this.presenter.adicionarOuAtualizarUsuario(this.usuarioEmailTextBox.getText(), this.usuarioAdministradorCheckBox.getValue());
		}
	}

	@Override
	public void setUsuarios(List<Usuario> usuarios) {
		this.limparResultadoPesquisa();
		this.pesquisaUsuarioResultadoCellTable.setRowCount(usuarios.size());
		this.resultadoPesquisaUsuario.setList(usuarios);
		this.mostrarResultadoPesquisa();		
	}

	private void limparFormularios() {
		this.usuarioAdministradorCheckBox.setValue(false);
		this.usuarioEmailTextBox.setText("");
		this.usuariosWarningHTML.setHTML("");
	}
	
	private void limparResultadoPesquisa() {	
		// ao remover a coluna 0, o objeto passa a coluna 1 para a 0,
		// portanto sempre  é necessário remover a coluna 0.
		int j = this.pesquisaUsuarioResultadoCellTable.getColumnCount();
		for (int i = 0; i < j; i++) {
			this.pesquisaUsuarioResultadoCellTable.removeColumn(0);
		}
		this.pesquisaUsuarioResultadoLabel.setText("");
		this.pesquisaUsuarioResultadoCellTable.setVisible(false);
		this.pesquisaUsuarioResultadoSimplePager.setVisible(false);
	}
	
	private void mostrarResultadoPesquisa() {
		if (this.pesquisaUsuarioResultadoCellTable.getRowCount() == 0) {
			this.pesquisaUsuarioResultadoLabel.setText("Não foram encontrados resultados para a pesquisa informada");
			this.pesquisaUsuarioResultadoSimplePager.setVisible(false);
		} else {
			this.pesquisaUsuarioResultadoLabel.setText("Foram encontrado(s) " + this.pesquisaUsuarioResultadoCellTable.getRowCount() + " resultado(s).");
			this.pesquisaUsuarioResultadoCellTable.setStyleName("surdo-tabela");
			this.pesquisaUsuarioResultadoCellTable.setVisible(true);
			
			TextColumn<Usuario> email = new TextColumn<Usuario>() {
				@Override
				public String getValue(Usuario object) {
					return object.getEmail();
				}
			};
			TextColumn<Usuario> admin = new TextColumn<Usuario>() {
				@Override
				public String getValue(Usuario object) {
					return object.getAdmin() ? "Sim" : "Não";
				}				
			};
			
			
			Delegate<String> deletarDelegate = new Delegate<String>() {
				@Override
				public void execute(String object) {
					if (Window.confirm("Deseja realmente apagar este usuário?")) {
						presenter.apagarUsuario(object);
					}
				}				
			};
			ActionCell<String> deletarCell = new ActionCell<String>("Apagar", deletarDelegate);
			Column<Usuario, String> deletarColumn = new Column<Usuario, String>(deletarCell) {
				@Override
				public String getValue(Usuario object) {
					return object.getEmail();
				}
			};

			this.pesquisaUsuarioResultadoCellTable.addColumn(email, "E-mail");
			this.pesquisaUsuarioResultadoCellTable.addColumn(admin, "Administrador");
			this.pesquisaUsuarioResultadoCellTable.addColumn(deletarColumn, "");
			
			this.pesquisaUsuarioResultadoSimplePager.setVisible(true);
		}
		this.usuariosWarningHTML.setHTML("");
	}
	
	private Validacoes validarAdicionarUsuario() {
		Validacoes validacoes = new Validacoes();
		
		if (usuarioEmailTextBox.getText().isEmpty()) {
			validacoes.add("E-mail precisa ser preenchido");
		}		
		
		return validacoes;
	}
}
