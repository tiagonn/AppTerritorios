package br.com.nascisoft.apoioterritoriols.admin.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.admin.vo.RegiaoVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.util.Validacoes;
import br.com.nascisoft.apoioterritoriols.resources.client.CellTableCustomResources;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.subshell.gwt.canvas.client.colorpicker.ColorPickerDialog;
import com.subshell.gwt.canvas.client.dialog.DialogClosedEvent;
import com.subshell.gwt.canvas.client.dialog.IDialogClosedHandler;

public class AdminRegiaoViewImpl extends AbstractAdminViewImpl implements AdminRegiaoView {

	private static AdminViewUiBinderUiBinder uiBinder = GWT
			.create(AdminViewUiBinderUiBinder.class);
	
	private AdminRegiaoView.Presenter presenter;
	@UiField ListBox regiaoCidadeListBox;
	@UiField TextBox regiaoNomeTextBox;
	@UiField TextBox regiaoLetraTextBox;
	@UiField ListBox regiaoZoomListBox;
	@UiField TextBox regiaoLatitudeTextBox;
	@UiField TextBox regiaoLongitudeTextBox;
	@UiField Button regiaoAdicionarButton;
	@UiField HTML regioesWarningHTML;
	@UiField (provided=true) CellTable<RegiaoVO> pesquisaRegiaoResultadoCellTable;
	@UiField Label pesquisaRegiaoResultadoLabel;
	@UiField SimplePager pesquisaRegiaoResultadoSimplePager;
	@UiField TextBox regiaoCorLetraTextBox;
	@UiField TextBox regiaoCorFundoTextBox;
	@UiField Button regiaoCorLetraButton;
	@UiField Button regiaoCorFundoButton;
	private ListDataProvider<RegiaoVO> resultadoPesquisaRegiao;
	private Long idSelecionado;
	
	@UiTemplate("AdminViewUiBinder.ui.xml")
	interface AdminViewUiBinderUiBinder extends
			UiBinder<Widget, AdminRegiaoViewImpl> {
	}

	public AdminRegiaoViewImpl() {
		CellTableCustomResources.INSTANCE.cellTableStyle().ensureInjected();
		this.pesquisaRegiaoResultadoCellTable = new CellTable<RegiaoVO>(10, CellTableCustomResources.INSTANCE);
		initWidget(uiBinder.createAndBindUi(this));
		this.resultadoPesquisaRegiao = new ListDataProvider<RegiaoVO>();
		this.resultadoPesquisaRegiao.addDataDisplay(this.pesquisaRegiaoResultadoCellTable);
		this.pesquisaRegiaoResultadoSimplePager.setDisplay(this.pesquisaRegiaoResultadoCellTable);
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
	public void setPresenter(AdminRegiaoView.Presenter presenter) {
		this.presenter = presenter;
	}
	
	@UiHandler("regiaoAdicionarButton")
	void onRegiaoAdicionarButtonClick(ClickEvent event) {
		Validacoes validacoes = validarAdicionarUsuario();
		if (validacoes.size() > 0) {
			this.regioesWarningHTML.setHTML(validacoes.obterValidacoesSumarizadaHTML());
		} else {
			this.presenter.adicionarOuAtualizarRegiao(populaRegiao());
		}
	}

	@Override
	public void setRegioes(List<RegiaoVO> regioes) {
		this.limparResultadoPesquisa();
		this.pesquisaRegiaoResultadoCellTable.setRowCount(regioes.size());
		this.resultadoPesquisaRegiao.setList(regioes);
		this.mostrarResultadoPesquisa();		
	}
	
	public void setCidades(List<Cidade> cidades) {
		this.regiaoCidadeListBox.clear();
		for (Cidade cidade : cidades) {
			this.regiaoCidadeListBox.addItem(cidade.getNome(), cidade.getId().toString());
		}
	}
	
	private RegiaoVO populaRegiao() {
		RegiaoVO regiao = new RegiaoVO();
		
		regiao.setId(this.idSelecionado);
		regiao.setNome(this.regiaoNomeTextBox.getText());
		regiao.setLatitudeCentro(Double.valueOf(this.regiaoLatitudeTextBox.getText()));
		regiao.setLongitudeCentro(Double.valueOf(this.regiaoLongitudeTextBox.getText()));
		regiao.setLetra(this.regiaoLetraTextBox.getText());
		regiao.setZoom(Integer.valueOf(this.regiaoZoomListBox.getValue(this.regiaoZoomListBox.getSelectedIndex())));
		regiao.setCidadeId(Long.valueOf(this.regiaoCidadeListBox.getValue(this.regiaoCidadeListBox.getSelectedIndex())));
		
		return regiao;
	}

	private void limparFormularios() {
		this.idSelecionado = null;
		this.regiaoNomeTextBox.setText("");
		this.regioesWarningHTML.setHTML("");
		this.regiaoLetraTextBox.setText("");
		this.regiaoZoomListBox.clear();
		this.regiaoZoomListBox.addItem(" -- Escolha um nível de zoom -- ", "");
		this.regiaoZoomListBox.addItem("Muito aproximado", "16");
		this.regiaoZoomListBox.addItem("Aproximado", "15");
		this.regiaoZoomListBox.addItem("Médio", "14");
		this.regiaoZoomListBox.addItem("Afastado", "13");
		this.regiaoZoomListBox.addItem("Muito afastado", "12");
		this.regiaoZoomListBox.setSelectedIndex(0);
		this.regiaoLatitudeTextBox.setText("");
		this.regiaoLongitudeTextBox.setText("");
		this.regiaoCidadeListBox.setSelectedIndex(0);
		this.regiaoCorLetraTextBox.setValue("ff0000");
		this.regiaoCorLetraTextBox.getElement().getStyle().setBackgroundColor("#ff0000");
		this.regiaoCorFundoTextBox.setValue("fff339");	
		this.regiaoCorFundoTextBox.getElement().getStyle().setBackgroundColor("#fff339");	
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
			
			TextColumn<RegiaoVO> nome = new TextColumn<RegiaoVO>() {
				@Override
				public String getValue(RegiaoVO object) {
					return object.getNome();
				}
			};			
			
			TextColumn<RegiaoVO> letra = new TextColumn<RegiaoVO>() {
				@Override
				public String getValue(RegiaoVO object) {
					return object.getLetra();
				}
			};			
			
			TextColumn<RegiaoVO> cidade = new TextColumn<RegiaoVO>() {
				@Override
				public String getValue(RegiaoVO object) {
					return object.getCidadeNome();
				}
			};			
			
			Delegate<Long> deletarDelegate = new Delegate<Long>() {
				@Override
				public void execute(final Long object) {
					mostrarConfirmacao("Deseja realmente apagar esta região?",
							new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									presenter.apagarRegiao(object);
								}
							}						
						);
				}
			};
			ActionCell<Long> deletarCell = new ActionCell<Long>("Apagar", deletarDelegate);
			Column<RegiaoVO, Long> deletarColumn = new Column<RegiaoVO, Long>(deletarCell) {
				@Override
				public Long getValue(RegiaoVO object) {
					return object.getId();
				}
			};
			
			Delegate<RegiaoVO> editarDelegate = new Delegate<RegiaoVO>() {
				@Override
				public void execute(RegiaoVO object) {
					popularManterRegiao(object);
				}				
			};
			ActionCell<RegiaoVO> editarCell = new ActionCell<RegiaoVO>("Editar", editarDelegate);
			Column<RegiaoVO, RegiaoVO> editarColumn = new Column<RegiaoVO, RegiaoVO>(editarCell) {
				@Override
				public RegiaoVO getValue(RegiaoVO object) {
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

	private void popularManterRegiao(RegiaoVO regiao) {
		this.idSelecionado = regiao.getId();
		this.regiaoNomeTextBox.setText(regiao.getNome());
		this.regiaoLatitudeTextBox.setText(regiao.getLatitudeCentro().toString());
		this.regiaoLongitudeTextBox.setText(regiao.getLongitudeCentro().toString());
		this.regiaoLetraTextBox.setText(regiao.getLetra());
		this.regiaoZoomListBox.setSelectedIndex(obterIndice(this.regiaoZoomListBox, regiao.getZoom().toString()));
		this.regiaoCidadeListBox.setSelectedIndex(
				obterIndice(this.regiaoCidadeListBox, regiao.getCidadeId().toString()));

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
		
		if (regiaoNomeTextBox.getText().isEmpty()) {
			validacoes.add("Nome precisa ser preenchido");
		}		
		if (regiaoLetraTextBox.getText().isEmpty()) {
			validacoes.add("Identificador/Letra precisa ser preenchido");
		}	
		if (regiaoZoomListBox.getSelectedIndex() == 0) {
			validacoes.add("Nível de zoom precisa ser preenchido");
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
	
	@UiHandler("regiaoCorFundoTextBox")
	void onRegiaoCorFundoTextBoxValueChange(ChangeEvent event) {
		this.regiaoCorFundoTextBox.getElement().getStyle().setBackgroundColor("#"+this.regiaoCorFundoTextBox.getValue());
		setBoxTextColor(this.regiaoCorFundoTextBox.getValue(), this.regiaoCorFundoTextBox);
	}
	
	@UiHandler("regiaoCorLetraTextBox")
	void onRegiaoCorLetraTextBoxValueChange(ChangeEvent event) {
		this.regiaoCorLetraTextBox.getElement().getStyle().setBackgroundColor("#"+this.regiaoCorLetraTextBox.getValue());
		setBoxTextColor(this.regiaoCorLetraTextBox.getValue(), this.regiaoCorLetraTextBox);
	}
	
	@UiHandler("regiaoCorLetraButton")
	void onRegiaoCorLetraButtonClick(ClickEvent event) {
		ColorPickerDialog dlg = pickColor(regiaoCorLetraTextBox);
		dlg.setPopupPosition(event.getClientX(), event.getClientY());
	}
	
	@UiHandler("regiaoCorFundoButton")
	void onRegiaoCorFundoButtonClick(ClickEvent event) {
		ColorPickerDialog dlg = pickColor(regiaoCorFundoTextBox);
		dlg.setPopupPosition(event.getClientX(), event.getClientY());
	}
	
	private ColorPickerDialog pickColor(final TextBox box) {
		final ColorPickerDialog dlg = new ColorPickerDialog();
		dlg.setColor(box.getText());
		dlg.addDialogClosedHandler(new IDialogClosedHandler() {
			public void dialogClosed(DialogClosedEvent event) {
				if (!event.isCanceled()) {
					box.setText(dlg.getColor());
					box.getElement().getStyle().setBackgroundColor("#"+dlg.getColor());	
					setBoxTextColor(dlg.getColor(), box);
				}
			}
		});
		dlg.center();
		return dlg;
	}
	
	private void setBoxTextColor(String color, TextBox box) {
		try {
			if (Integer.valueOf(color.substring(0,1)) < 7) {
				box.getElement().getStyle().setColor("#ffffff");
			} else {
				box.getElement().getStyle().setColor("#000000");
			}
		} catch (NumberFormatException ex) {
			box.getElement().getStyle().setColor("#000000");
		}
	}
	
}
