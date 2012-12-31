package br.com.nascisoft.apoioterritoriols.admin.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;
import br.com.nascisoft.apoioterritoriols.login.util.Validacoes;

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
	@UiField CellTable<Usuario> pesquisaResultadoCellTable;
	@UiField Label pesquisaResultadoLabel;
	@UiField SimplePager pesquisaResultadoSimplePager;
	private ListDataProvider<Usuario> resultadoPesquisa;
	
	@UiTemplate("AdminViewUiBinder.ui.xml")
	interface AdminViewUiBinderUiBinder extends
			UiBinder<Widget, AdminUsuarioViewImpl> {
	}

	public AdminUsuarioViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		this.resultadoPesquisa = new ListDataProvider<Usuario>();
		this.resultadoPesquisa.addDataDisplay(this.pesquisaResultadoCellTable);
		this.pesquisaResultadoSimplePager.setDisplay(this.pesquisaResultadoCellTable);
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
		this.pesquisaResultadoCellTable.setRowCount(usuarios.size());
		this.resultadoPesquisa.setList(usuarios);
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
		int j = this.pesquisaResultadoCellTable.getColumnCount();
		for (int i = 0; i < j; i++) {
			this.pesquisaResultadoCellTable.removeColumn(0);
		}
		this.pesquisaResultadoLabel.setText("");
		this.pesquisaResultadoCellTable.setVisible(false);
		this.pesquisaResultadoSimplePager.setVisible(false);
	}
	
	private void mostrarResultadoPesquisa() {
		if (this.pesquisaResultadoCellTable.getRowCount() == 0) {
			this.pesquisaResultadoLabel.setText("Não foram encontrados resultados para a pesquisa informada");
			this.pesquisaResultadoSimplePager.setVisible(false);
		} else {
			this.pesquisaResultadoLabel.setText("Foram encontrados " + this.pesquisaResultadoCellTable.getRowCount() + " resultados.");
			this.pesquisaResultadoCellTable.setStyleName("surdo-tabela");
			this.pesquisaResultadoCellTable.setVisible(true);
			
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

			this.pesquisaResultadoCellTable.addColumn(email, "E-mail");
			this.pesquisaResultadoCellTable.addColumn(admin, "Administrador");
			this.pesquisaResultadoCellTable.addColumn(deletarColumn, "");
			
			this.pesquisaResultadoSimplePager.setVisible(true);
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
