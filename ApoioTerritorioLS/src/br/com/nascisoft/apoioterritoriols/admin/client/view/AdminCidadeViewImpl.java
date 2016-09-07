package br.com.nascisoft.apoioterritoriols.admin.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.admin.enumeration.QuantidadeSurdosMapaEnum;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.util.Validacoes;
import br.com.nascisoft.apoioterritoriols.resources.client.CellTableCustomResources;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class AdminCidadeViewImpl extends AbstractAdminViewImpl implements AdminCidadeView {

	private static AdminViewUiBinderUiBinder uiBinder = GWT
			.create(AdminViewUiBinderUiBinder.class);
	
	private AdminCidadeView.Presenter presenter;
	@UiField TextBox cidadeNomeTextBox;
	@UiField TextBox cidadeUFTextBox;
	@UiField TextBox cidadePaisTextBox;
	@UiField ListBox cidadeQuantidadeSurdosMapaListBox;
	@UiField TextBox cidadeLatitudeTextBox;
	@UiField TextBox cidadeLongitudeTextBox;
	@UiField CheckBox cidadeUtilizarMesmaLatitudeCheckBox;
	@UiField TextBox cidadeLatitudeTerritorioTextBox;
	@UiField TextBox cidadeLongitudeTerritorioTextBox;
	@UiField CheckBox cidadeUtilizarBairroBuscaEnderecoCheckBox;
	@UiField Button cidadeAdicionarButton;
	@UiField HTML cidadesWarningHTML;
	@UiField (provided=true) CellTable<Cidade> pesquisaCidadeResultadoCellTable;
	@UiField Label pesquisaCidadeResultadoLabel;
	@UiField SimplePager pesquisaCidadeResultadoSimplePager;
	private ListDataProvider<Cidade> resultadoPesquisaCidade;
	private Long idSelecionado;
	
	@UiTemplate("AdminViewUiBinder.ui.xml")
	interface AdminViewUiBinderUiBinder extends
			UiBinder<Widget, AdminCidadeViewImpl> {
	}

	public AdminCidadeViewImpl() {
		CellTableCustomResources.INSTANCE.cellTableStyle().ensureInjected();
		this.pesquisaCidadeResultadoCellTable = new CellTable<Cidade>(10, CellTableCustomResources.INSTANCE);
		initWidget(uiBinder.createAndBindUi(this));
		this.resultadoPesquisaCidade = new ListDataProvider<Cidade>();
		this.resultadoPesquisaCidade.addDataDisplay(this.pesquisaCidadeResultadoCellTable);
		this.pesquisaCidadeResultadoSimplePager.setDisplay(this.pesquisaCidadeResultadoCellTable);
		this.iniciarQuantidadeSurdosMapaListBox();
	}

	@Override
	public void initView() {
		this.selectThisTab();
		this.limparFormularios();
		this.limparResultadoPesquisa();
		this.presenter.buscarCidades();
	}

	@Override
	public void selectThisTab() {
		this.adminTabLayoutPanel.selectTab(2, false);
		
	}

	@Override
	public void setPresenter(AdminCidadeView.Presenter presenter) {
		this.presenter = presenter;
	}
	
	@UiHandler("cidadeAdicionarButton")
	void onCidadeAdicionarButtonClick(ClickEvent event) {
		Validacoes validacoes = validarAdicionarUsuario();
		if (validacoes.size() > 0) {
			this.cidadesWarningHTML.setHTML(validacoes.obterValidacoesSumarizadaHTML());
		} else {
			this.presenter.adicionarOuAtualizarCidade(populaCidade());
		}
	}

	@Override
	public void setCidades(List<Cidade> cidades) {
		this.limparResultadoPesquisa();
		this.pesquisaCidadeResultadoCellTable.setRowCount(cidades.size());
		this.resultadoPesquisaCidade.setList(cidades);
		this.mostrarResultadoPesquisa();		
	}
	
	private Cidade populaCidade() {
		Cidade cidade = new Cidade();
		
		cidade.setId(idSelecionado);
		cidade.setNome(this.cidadeNomeTextBox.getText());
		cidade.setUF(this.cidadeUFTextBox.getText());
		cidade.setPais(this.cidadePaisTextBox.getText());
		cidade.setLatitudeCentro(Double.valueOf(this.cidadeLatitudeTextBox.getText()));
		cidade.setLongitudeCentro(Double.valueOf(this.cidadeLongitudeTextBox.getText()));
		if (this.cidadeUtilizarMesmaLatitudeCheckBox.getValue()) {
			cidade.setLatitudeCentroTerritorio(Double.valueOf(this.cidadeLatitudeTextBox.getText()));
			cidade.setLongitudeCentroTerritorio(Double.valueOf(this.cidadeLongitudeTextBox.getText()));
		} else {
			cidade.setLatitudeCentroTerritorio(Double.valueOf(this.cidadeLatitudeTerritorioTextBox.getText()));
			cidade.setLongitudeCentroTerritorio(Double.valueOf(this.cidadeLongitudeTerritorioTextBox.getText()));
		}
		cidade.setUtilizarBairroBuscaEndereco(this.cidadeUtilizarBairroBuscaEnderecoCheckBox.getValue());
		cidade.setQuantidadeSurdosMapa(
				Integer.valueOf(this.cidadeQuantidadeSurdosMapaListBox.getValue(
						this.cidadeQuantidadeSurdosMapaListBox.getSelectedIndex())));
		
		return cidade;
	}

	private void limparFormularios() {
		this.idSelecionado = null;
		this.cidadeUtilizarBairroBuscaEnderecoCheckBox.setValue(false);
		this.cidadeNomeTextBox.setText("");
		this.cidadesWarningHTML.setHTML("");
		this.cidadeLatitudeTextBox.setText("");
		this.cidadeLongitudeTextBox.setText("");
		this.cidadeUtilizarMesmaLatitudeCheckBox.setValue(true);
		this.cidadeLatitudeTerritorioTextBox.setText("");
		this.cidadeLongitudeTerritorioTextBox.setText("");
		this.cidadeLatitudeTerritorioTextBox.setEnabled(false);
		this.cidadeLongitudeTerritorioTextBox.setEnabled(false);
		this.cidadeUFTextBox.setText("");
		this.cidadePaisTextBox.setText("");
		this.cidadeQuantidadeSurdosMapaListBox.setSelectedIndex(0);
	}
	
	private void iniciarQuantidadeSurdosMapaListBox() {
		this.cidadeQuantidadeSurdosMapaListBox.addItem("-- Escolha uma opção --", "");
		this.cidadeQuantidadeSurdosMapaListBox.addItem(String.valueOf(QuantidadeSurdosMapaEnum.UM.getQtde()));
		this.cidadeQuantidadeSurdosMapaListBox.addItem(String.valueOf(QuantidadeSurdosMapaEnum.QUATRO.getQtde()));
		this.cidadeQuantidadeSurdosMapaListBox.addItem("5 - Grande", String.valueOf(QuantidadeSurdosMapaEnum.CINCO.getQtde()));
		this.cidadeQuantidadeSurdosMapaListBox.addItem(String.valueOf(QuantidadeSurdosMapaEnum.SEIS.getQtde()));
		this.cidadeQuantidadeSurdosMapaListBox.addItem(String.valueOf(QuantidadeSurdosMapaEnum.DEZ.getQtde()));
	}
	
	private void limparResultadoPesquisa() {	
		// ao remover a coluna 0, o objeto passa a coluna 1 para a 0,
		// portanto sempre  é necessário remover a coluna 0.
		int j = this.pesquisaCidadeResultadoCellTable.getColumnCount();
		for (int i = 0; i < j; i++) {
			this.pesquisaCidadeResultadoCellTable.removeColumn(0);
		}
		this.pesquisaCidadeResultadoLabel.setText("");
		this.pesquisaCidadeResultadoCellTable.setVisible(false);
		this.pesquisaCidadeResultadoSimplePager.setVisible(false);
	}
	
	private void mostrarResultadoPesquisa() {
		if (this.pesquisaCidadeResultadoCellTable.getRowCount() == 0) {
			this.pesquisaCidadeResultadoLabel.setText("Não foram encontrados resultados para a pesquisa informada");
			this.pesquisaCidadeResultadoSimplePager.setVisible(false);
		} else {
			this.pesquisaCidadeResultadoLabel.setText("Foram encontrado(s) " + this.pesquisaCidadeResultadoCellTable.getRowCount() + " resultado(s).");
			this.pesquisaCidadeResultadoCellTable.setVisible(true);
			this.pesquisaCidadeResultadoCellTable.getElement().getStyle().setWidth(100, Unit.PCT);
			TextColumn<Cidade> nome = new TextColumn<Cidade>() {
				@Override
				public String getValue(Cidade object) {
					return object.getNome();
				}
			};			
			
			Delegate<Long> deletarDelegate = new Delegate<Long>() {
				@Override
				public void execute(final Long object) {
					mostrarConfirmacao("Deseja realmente apagar esta cidade?",
						new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								presenter.apagarCidade(object);
							}
						}
					);
				}				
			};
			ActionCell<Long> deletarCell = new ActionCell<Long>("Apagar", deletarDelegate);
			Column<Cidade, Long> deletarColumn = new Column<Cidade, Long>(deletarCell) {
				@Override
				public Long getValue(Cidade object) {
					return object.getId();
				}
			};
			
			Delegate<Cidade> editarDelegate = new Delegate<Cidade>() {
				@Override
				public void execute(Cidade object) {
					popularManterCidade(object);
				}				
			};
			ActionCell<Cidade> editarCell = new ActionCell<Cidade>("Editar", editarDelegate);
			Column<Cidade, Cidade> editarColumn = new Column<Cidade, Cidade>(editarCell) {
				@Override
				public Cidade getValue(Cidade object) {
					return object;
				}
			};

			this.pesquisaCidadeResultadoCellTable.addColumn(nome, "Nome");
			this.pesquisaCidadeResultadoCellTable.addColumn(editarColumn, "");
			this.pesquisaCidadeResultadoCellTable.addColumn(deletarColumn, "");
			
			this.pesquisaCidadeResultadoSimplePager.setVisible(true);
		}
		this.cidadesWarningHTML.setHTML("");
	}

	private void popularManterCidade(Cidade cidade) {
		this.idSelecionado = cidade.getId();
		this.cidadeNomeTextBox.setText(cidade.getNome());
		this.cidadeLatitudeTextBox.setText(cidade.getLatitudeCentro().toString());
		this.cidadeLongitudeTextBox.setText(cidade.getLongitudeCentro().toString());
		this.cidadeUFTextBox.setText(cidade.getUF());
		this.cidadePaisTextBox.setText(cidade.getPais());
		this.cidadeUtilizarBairroBuscaEnderecoCheckBox.setValue(cidade.getUtilizarBairroBuscaEndereco());
		this.cidadeQuantidadeSurdosMapaListBox.setSelectedIndex(
				obterIndice(this.cidadeQuantidadeSurdosMapaListBox, cidade.getQuantidadeSurdosMapa().toString()));
		if (cidade.getLatitudeCentro().equals(cidade.getLatitudeCentroTerritorio()) && cidade.getLongitudeCentro().equals(cidade.getLongitudeCentroTerritorio())) {
			this.cidadeUtilizarMesmaLatitudeCheckBox.setValue(true);
			this.cidadeLatitudeTerritorioTextBox.setEnabled(false);
			this.cidadeLongitudeTerritorioTextBox.setEnabled(false);
			this.cidadeLatitudeTerritorioTextBox.setValue("");
			this.cidadeLongitudeTerritorioTextBox.setValue("");
		} else {
			this.cidadeUtilizarMesmaLatitudeCheckBox.setValue(false);
			this.cidadeLatitudeTerritorioTextBox.setEnabled(true);
			this.cidadeLongitudeTerritorioTextBox.setEnabled(true);
			this.cidadeLatitudeTerritorioTextBox.setText(cidade.getLatitudeCentroTerritorio().toString());
			this.cidadeLongitudeTerritorioTextBox.setText(cidade.getLongitudeCentroTerritorio().toString());
		}

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
		
		if (cidadeNomeTextBox.getText().isEmpty()) {
			validacoes.add("Nome precisa ser preenchido");
		}		
		if (cidadeUFTextBox.getText().isEmpty()) {
			validacoes.add("UF precisa ser preenchido");
		}		
		if (cidadePaisTextBox.getText().isEmpty()) {
			validacoes.add("País precisa ser preenchido");
		}		
		if (cidadeLatitudeTextBox.getText().isEmpty()) {
			validacoes.add("Latitude do centro da cidade precisa ser preenchido");
		} else {
			try {
				Double.valueOf(this.cidadeLatitudeTextBox.getText());
			} catch (NumberFormatException ex) {
				validacoes.add("Latitude do centro da cidade precisa ser numérico");
			}
		}
		if (cidadeLongitudeTextBox.getText().isEmpty()) {
			validacoes.add("Longitude do centro da cidade precisa ser preenchido");
		} else {
			try {
				Double.valueOf(this.cidadeLongitudeTextBox.getText());
			} catch (NumberFormatException ex) {
				validacoes.add("Longitude do centro da cidade precisa ser numérico");
			}
		}
		if (!cidadeUtilizarMesmaLatitudeCheckBox.getValue()) {
			if (cidadeLatitudeTerritorioTextBox.getText().isEmpty()) {
				validacoes.add("Latitude do centro do território precisa ser preenchido");
			} else {
				try {
					Double.valueOf(this.cidadeLatitudeTerritorioTextBox.getText());
				} catch (NumberFormatException ex) {
					validacoes.add("Latitude do centro do território precisa ser numérico");
				}
			}
			if (cidadeLongitudeTerritorioTextBox.getText().isEmpty()) {
				validacoes.add("Longitude do centro do território precisa ser preenchido");
			} else {
				try {
					Double.valueOf(this.cidadeLongitudeTerritorioTextBox.getText());
				} catch (NumberFormatException ex) {
					validacoes.add("Longitude do centro do território precisa ser numérico");
				}
			}
		}
		if (this.cidadeQuantidadeSurdosMapaListBox.getSelectedIndex() == 0) {
			validacoes.add("Quantidade de surdos por mapa precisa ser selecionado.");
		}

		
		return validacoes;
	}
	
	@UiHandler("cidadeUtilizarMesmaLatitudeCheckBox")
	void onCidadeUtilizarMesmaLatitudeCheckBoxValueChange(ValueChangeEvent<Boolean> event) {
		this.cidadeLatitudeTerritorioTextBox.setEnabled(!event.getValue());
		this.cidadeLongitudeTerritorioTextBox.setEnabled(!event.getValue());
		if (event.getValue()) {
			this.cidadeLatitudeTerritorioTextBox.setText("");
			this.cidadeLongitudeTerritorioTextBox.setText("");
		} 
	}
}
