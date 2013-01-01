package br.com.nascisoft.apoioterritoriols.admin.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class AdminRegiaoViewImpl extends Composite implements AdminRegiaoView {

	private static AdminViewUiBinderUiBinder uiBinder = GWT
			.create(AdminViewUiBinderUiBinder.class);
	
	private Presenter presenter;
	@UiField TabLayoutPanel adminTabLayoutPanel;
	@UiField PopupPanel waitingPopUpPanel;
	@UiField ListBox regiaoCidadeListBox;
	@UiField TextBox regiaoNomeTextBox;
	@UiField TextBox regiaoLetraTextBox;
	@UiField TextBox regiaoLatitudeTextBox;
	@UiField TextBox regiaoLongitudeTextBox;
	@UiField Button regiaoAdicionarButton;
	@UiField HTML regioesWarningHTML;
	@UiField CellTable<Regiao> pesquisaRegiaoResultadoCellTable;
	@UiField Label pesquisaRegiaoResultadoLabel;
	@UiField SimplePager pesquisaRegiaoResultadoSimplePager;
	private ListDataProvider<Regiao> resultadoPesquisaRegiao;
	
	@UiTemplate("AdminViewUiBinder.ui.xml")
	interface AdminViewUiBinderUiBinder extends
			UiBinder<Widget, AdminRegiaoViewImpl> {
	}

	public AdminRegiaoViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		this.resultadoPesquisaRegiao = new ListDataProvider<Regiao>();
		this.resultadoPesquisaRegiao.addDataDisplay(this.pesquisaRegiaoResultadoCellTable);
		this.pesquisaRegiaoResultadoSimplePager.setDisplay(this.pesquisaRegiaoResultadoCellTable);
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
		this.presenter.buscarCidades();
		this.presenter.buscarRegioes();
	}

	@Override
	public void selectThisTab() {
		this.adminTabLayoutPanel.selectTab(3, false);
		
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.adminTabLayoutPanel.addSelectionHandler(handler);		
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@UiHandler("regiaoAdicionarButton")
	void onRegiaoAdicionarButtonClick(ClickEvent event) {
		Validacoes validacoes = validarAdicionarUsuario();
		if (validacoes.size() > 0) {
			this.regioesWarningHTML.setHTML(validacoes.obterValidacoesSumarizadaHTML());
		} else {
			this.presenter.adicionarOuAtualizarRegiao(populaRegiao(), this.regiaoCidadeListBox.getValue(this.regiaoCidadeListBox.getSelectedIndex()));
		}
	}

	@Override
	public void setRegioes(List<Regiao> regioes) {
		this.limparResultadoPesquisa();
		this.pesquisaRegiaoResultadoCellTable.setRowCount(regioes.size());
		this.resultadoPesquisaRegiao.setList(regioes);
		this.mostrarResultadoPesquisa();		
	}
	
	public void setCidades(List<Cidade> cidades) {
		this.regiaoCidadeListBox.clear();
		this.regiaoCidadeListBox.addItem("-- Escolha uma opção --", "");
		for (Cidade cidade : cidades) {
			this.regiaoCidadeListBox.addItem(cidade.getNome());
		}
	}
	
	private Regiao populaRegiao() {
		Regiao regiao = new Regiao();
		
		regiao.setNome(this.regiaoNomeTextBox.getText());
		regiao.setLatitudeCentro(Double.valueOf(this.regiaoLatitudeTextBox.getText()));
		regiao.setLongitudeCentro(Double.valueOf(this.regiaoLongitudeTextBox.getText()));
		regiao.setLetra(this.regiaoLetraTextBox.getText());
		
		return regiao;
	}

	private void limparFormularios() {
		this.regiaoNomeTextBox.setText("");
		this.regioesWarningHTML.setHTML("");
		this.regiaoLetraTextBox.setText("");
		this.regiaoLatitudeTextBox.setText("");
		this.regiaoLongitudeTextBox.setText("");
		this.regiaoCidadeListBox.setSelectedIndex(0);
	}
	
	private void limparResultadoPesquisa() {	
		// ao remover a coluna 0, o objeto passa a coluna 1 para a 0,
		// portanto sempre  é necessário remover a coluna 0.
		int j = this.pesquisaRegiaoResultadoCellTable.getColumnCount();
		for (int i = 0; i < j; i++) {
			this.pesquisaRegiaoResultadoCellTable.removeColumn(0);
		}
		this.pesquisaRegiaoResultadoLabel.setText("");
		this.pesquisaRegiaoResultadoCellTable.setVisible(false);
		this.pesquisaRegiaoResultadoSimplePager.setVisible(false);
	}
	
	private void mostrarResultadoPesquisa() {
		if (this.pesquisaRegiaoResultadoCellTable.getRowCount() == 0) {
			this.pesquisaRegiaoResultadoLabel.setText("Não foram encontrados resultados para a pesquisa informada");
			this.pesquisaRegiaoResultadoSimplePager.setVisible(false);
		} else {
			this.pesquisaRegiaoResultadoLabel.setText("Foram encontrado(s) " + this.pesquisaRegiaoResultadoCellTable.getRowCount() + " resultado(s).");
			this.pesquisaRegiaoResultadoCellTable.setStyleName("surdo-tabela");
			this.pesquisaRegiaoResultadoCellTable.setVisible(true);
			
			TextColumn<Regiao> nome = new TextColumn<Regiao>() {
				@Override
				public String getValue(Regiao object) {
					return object.getNome();
				}
			};			
			
			TextColumn<Regiao> letra = new TextColumn<Regiao>() {
				@Override
				public String getValue(Regiao object) {
					return object.getLetra();
				}
			};			
			
			TextColumn<Regiao> cidade = new TextColumn<Regiao>() {
				@Override
				public String getValue(Regiao object) {
					return object.getCidade().getName();
				}
			};			
			
			Delegate<String> deletarDelegate = new Delegate<String>() {
				@Override
				public void execute(String object) {
					if (Window.confirm("Deseja realmente apagar esta regiao?")) {
						presenter.apagarRegiao(object);
					}
				}				
			};
			ActionCell<String> deletarCell = new ActionCell<String>("Apagar", deletarDelegate);
			Column<Regiao, String> deletarColumn = new Column<Regiao, String>(deletarCell) {
				@Override
				public String getValue(Regiao object) {
					return object.getNome();
				}
			};
			
			Delegate<Regiao> editarDelegate = new Delegate<Regiao>() {
				@Override
				public void execute(Regiao object) {
					popularManterRegiao(object);
				}				
			};
			ActionCell<Regiao> editarCell = new ActionCell<Regiao>("Editar", editarDelegate);
			Column<Regiao, Regiao> editarColumn = new Column<Regiao, Regiao>(editarCell) {
				@Override
				public Regiao getValue(Regiao object) {
					return object;
				}
			};
			
			this.pesquisaRegiaoResultadoCellTable.addColumn(cidade, "Cidade");
			this.pesquisaRegiaoResultadoCellTable.addColumn(nome, "Nome");
			this.pesquisaRegiaoResultadoCellTable.addColumn(letra, "Letra/Identificador");
			this.pesquisaRegiaoResultadoCellTable.addColumn(editarColumn, "");
			this.pesquisaRegiaoResultadoCellTable.addColumn(deletarColumn, "");
			
			this.pesquisaRegiaoResultadoSimplePager.setVisible(true);
		}
		this.regioesWarningHTML.setHTML("");
	}

	private void popularManterRegiao(Regiao regiao) {
		this.regiaoNomeTextBox.setText(regiao.getNome());
		this.regiaoLatitudeTextBox.setText(regiao.getLatitudeCentro().toString());
		this.regiaoLongitudeTextBox.setText(regiao.getLongitudeCentro().toString());
		this.regiaoLetraTextBox.setText(regiao.getLetra());
		this.regiaoCidadeListBox.setSelectedIndex(
				obterIndice(this.regiaoCidadeListBox, regiao.getCidade().getName()));

	}
	
	private int obterIndice(ListBox list, String valor) {
		for (int i = 0; i < list.getItemCount(); i++) {
			if (list.getValue(i).equals(valor)) {
				return i;
			}
		}
		return 0;
	}
	
	private Validacoes validarAdicionarUsuario() {
		Validacoes validacoes = new Validacoes();
		

		if (this.regiaoCidadeListBox.getSelectedIndex() == 0) {
			validacoes.add("Uma cidade precisa ser selecionada.");
		}
		if (regiaoNomeTextBox.getText().isEmpty()) {
			validacoes.add("Nome precisa ser preenchido");
		}		
		if (regiaoLetraTextBox.getText().isEmpty()) {
			validacoes.add("Identificador/Letra precisa ser preenchido");
		}	
		if (regiaoLatitudeTextBox.getText().isEmpty()) {
			validacoes.add("Latitude precisa ser preenchido");
		} else {
			try {
				Double.valueOf(this.regiaoLatitudeTextBox.getText());
			} catch (NumberFormatException ex) {
				validacoes.add("Latitude precisa ser numérico");
			}
		}
		if (regiaoLongitudeTextBox.getText().isEmpty()) {
			validacoes.add("Longitude precisa ser preenchido");
		} else {
			try {
				Double.valueOf(this.regiaoLongitudeTextBox.getText());
			} catch (NumberFormatException ex) {
				validacoes.add("Longitude precisa ser numérico");
			}
		}

		
		return validacoes;
	}
}
