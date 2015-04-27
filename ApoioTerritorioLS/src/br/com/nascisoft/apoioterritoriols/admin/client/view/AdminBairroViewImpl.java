package br.com.nascisoft.apoioterritoriols.admin.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.admin.vo.BairroVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class AdminBairroViewImpl extends Composite implements AdminBairroView {

	private static AdminViewUiBinderUiBinder uiBinder = GWT
			.create(AdminViewUiBinderUiBinder.class);
	
	private Presenter presenter;
	@UiField TabLayoutPanel adminTabLayoutPanel;
	@UiField PopupPanel waitingPopUpPanel;
	@UiField ListBox bairroCidadeListBox;
	@UiField TextBox bairroNomeTextBox;
	@UiField Button bairroAdicionarButton;
	@UiField HTML bairrosWarningHTML;
	@UiField (provided=true) CellTable<BairroVO> pesquisaBairroResultadoCellTable;
	@UiField Label pesquisaBairroResultadoLabel;
	@UiField SimplePager pesquisaBairroResultadoSimplePager;
	private ListDataProvider<BairroVO> resultadoPesquisaBairro;
	private Long bairroSelecionado;
	
	@UiTemplate("AdminViewUiBinder.ui.xml")
	interface AdminViewUiBinderUiBinder extends
			UiBinder<Widget, AdminBairroViewImpl> {
	}

	public AdminBairroViewImpl() {
		CellTableCustomResources.INSTANCE.cellTableStyle().ensureInjected();
		this.pesquisaBairroResultadoCellTable = new CellTable<BairroVO>(10, CellTableCustomResources.INSTANCE);		
		initWidget(uiBinder.createAndBindUi(this));
		this.resultadoPesquisaBairro = new ListDataProvider<BairroVO>();
		this.resultadoPesquisaBairro.addDataDisplay(this.pesquisaBairroResultadoCellTable);
		this.pesquisaBairroResultadoSimplePager.setDisplay(this.pesquisaBairroResultadoCellTable);
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
		this.presenter.buscarBairros();
	}

	@Override
	public void selectThisTab() {
		this.adminTabLayoutPanel.selectTab(4, false);
		
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.adminTabLayoutPanel.addSelectionHandler(handler);		
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@UiHandler("bairroAdicionarButton")
	void onBairroAdicionarButtonClick(ClickEvent event) {
		Validacoes validacoes = validarAdicionarUsuario();
		if (validacoes.size() > 0) {
			this.bairrosWarningHTML.setHTML(validacoes.obterValidacoesSumarizadaHTML());
		} else {
			this.presenter.adicionarOuAtualizarBairro(populaBairro());
		}
	}

	@Override
	public void setBairros(List<BairroVO> bairros) {
		this.limparResultadoPesquisa();
		this.pesquisaBairroResultadoCellTable.setRowCount(bairros.size());
		this.resultadoPesquisaBairro.setList(bairros);
		this.mostrarResultadoPesquisa();		
	}
	
	public void setCidades(List<Cidade> cidades) {
		this.bairroCidadeListBox.clear();
		for (Cidade cidade : cidades) {
			this.bairroCidadeListBox.addItem(cidade.getNome(), cidade.getId().toString());
		}
	}
	
	private BairroVO populaBairro() {
		BairroVO bairro = new BairroVO();
		
		bairro.setId(this.bairroSelecionado);
		bairro.setNome(this.bairroNomeTextBox.getText());
		bairro.setCidadeId(Long.valueOf(this.bairroCidadeListBox.getValue(this.bairroCidadeListBox.getSelectedIndex())));
		
		return bairro;
	}

	private void limparFormularios() {
		this.bairroSelecionado = null;
		this.bairroNomeTextBox.setText("");
		this.bairrosWarningHTML.setHTML("");
		this.bairroCidadeListBox.setSelectedIndex(0);
	}
	
	private void limparResultadoPesquisa() {	
		// ao remover a coluna 0, o objeto passa a coluna 1 para a 0,
		// portanto sempre  é necessário remover a coluna 0.
		int j = this.pesquisaBairroResultadoCellTable.getColumnCount();
		for (int i = 0; i < j; i++) {
			this.pesquisaBairroResultadoCellTable.removeColumn(0);
		}
		this.pesquisaBairroResultadoLabel.setText("");
		this.pesquisaBairroResultadoCellTable.setVisible(false);
		this.pesquisaBairroResultadoSimplePager.setVisible(false);
	}
	
	private void mostrarResultadoPesquisa() {
		if (this.pesquisaBairroResultadoCellTable.getRowCount() == 0) {
			this.pesquisaBairroResultadoLabel.setText("Não foram encontrados resultados para a pesquisa informada");
			this.pesquisaBairroResultadoSimplePager.setVisible(false);
		} else {
			this.pesquisaBairroResultadoLabel.setText("Foram encontrado(s) " + this.pesquisaBairroResultadoCellTable.getRowCount() + " resultado(s).");
			this.pesquisaBairroResultadoCellTable.setStyleName("surdo-tabela");
			this.pesquisaBairroResultadoCellTable.setVisible(true);
			
			TextColumn<BairroVO> nome = new TextColumn<BairroVO>() {
				@Override
				public String getValue(BairroVO object) {
					return object.getNome();
				}
			};				
			
			TextColumn<BairroVO> cidade = new TextColumn<BairroVO>() {
				@Override
				public String getValue(BairroVO object) {
					return object.getCidadeNome();
				}
			};			
			
			Delegate<Long> deletarDelegate = new Delegate<Long>() {
				@Override
				public void execute(Long object) {
					if (Window.confirm("Deseja realmente apagar esta bairro?")) {
						presenter.apagarBairro(object);
					}
				}				
			};
			ActionCell<Long> deletarCell = new ActionCell<Long>("Apagar", deletarDelegate);
			Column<BairroVO, Long> deletarColumn = new Column<BairroVO, Long>(deletarCell) {
				@Override
				public Long getValue(BairroVO object) {
					return object.getId();
				}
			};
			
			Delegate<BairroVO> editarDelegate = new Delegate<BairroVO>() {
				@Override
				public void execute(BairroVO object) {
					popularManterBairro(object);
				}				
			};
			ActionCell<BairroVO> editarCell = new ActionCell<BairroVO>("Editar", editarDelegate);
			Column<BairroVO, BairroVO> editarColumn = new Column<BairroVO, BairroVO>(editarCell) {
				@Override
				public BairroVO getValue(BairroVO object) {
					return object;
				}
			};
			
			this.pesquisaBairroResultadoCellTable.addColumn(cidade, "Cidade");
			this.pesquisaBairroResultadoCellTable.addColumn(nome, "Nome");
			this.pesquisaBairroResultadoCellTable.addColumn(editarColumn, "");
			this.pesquisaBairroResultadoCellTable.addColumn(deletarColumn, "");
			
			this.pesquisaBairroResultadoSimplePager.setVisible(true);
		}
		this.bairrosWarningHTML.setHTML("");
	}

	private void popularManterBairro(BairroVO bairro) {
		this.bairroSelecionado =  bairro.getId();
		this.bairroNomeTextBox.setText(bairro.getNome());
		this.bairroCidadeListBox.setSelectedIndex(
				obterIndice(this.bairroCidadeListBox, bairro.getCidadeId().toString()));

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
		
		if (bairroNomeTextBox.getText().isEmpty()) {
			validacoes.add("Nome precisa ser preenchido");
		}		
		
		return validacoes;
	}
}
