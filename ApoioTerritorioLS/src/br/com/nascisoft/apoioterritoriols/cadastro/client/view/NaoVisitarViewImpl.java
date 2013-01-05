//TODO: impactar com a utilização da parametrizacão de cidade/regiao/bairro
//package br.com.nascisoft.apoioterritoriols.cadastro.client.view;
//
//import java.util.List;
//
//import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoNaoVisitarDetailsVO;
//import br.com.nascisoft.apoioterritoriols.cadastro.xml.Regiao;
//import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
//import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;
//
//import com.google.gwt.cell.client.ActionCell;
//import com.google.gwt.cell.client.ActionCell.Delegate;
//import com.google.gwt.core.client.GWT;
//import com.google.gwt.event.logical.shared.SelectionHandler;
//import com.google.gwt.uibinder.client.UiBinder;
//import com.google.gwt.uibinder.client.UiField;
//import com.google.gwt.uibinder.client.UiTemplate;
//import com.google.gwt.user.cellview.client.CellTable;
//import com.google.gwt.user.cellview.client.Column;
//import com.google.gwt.user.cellview.client.SimplePager;
//import com.google.gwt.user.cellview.client.TextColumn;
//import com.google.gwt.user.client.Window;
//import com.google.gwt.user.client.ui.Composite;
//import com.google.gwt.user.client.ui.PopupPanel;
//import com.google.gwt.user.client.ui.TabLayoutPanel;
//import com.google.gwt.user.client.ui.Widget;
//import com.google.gwt.view.client.ListDataProvider;
//
//public class NaoVisitarViewImpl extends Composite implements NaoVisitarView {
//	
//	private static CadastroViewUiBinderUiBinder uiBinder = 
//			GWT.create(CadastroViewUiBinderUiBinder.class);
//	
//	private Presenter presenter;
//	private ListDataProvider<SurdoNaoVisitarDetailsVO> resultadoPesquisa;
//	
//	@UiField PopupPanel waitingPopUpPanel;
//	@UiField TabLayoutPanel cadastroSurdoTabLayoutPanel;
//	@UiField CellTable<SurdoNaoVisitarDetailsVO> naoVisitarResultadoCellTable;
//	@UiField SimplePager naoVisitarSimplePager;	
//	
//	@UiTemplate("CadastroViewUiBinder.ui.xml")
//	interface CadastroViewUiBinderUiBinder 
//		extends UiBinder<Widget, NaoVisitarViewImpl> {
//	}
//	
//	public NaoVisitarViewImpl() {
//		initWidget(uiBinder.createAndBindUi(this));
//
//		this.resultadoPesquisa = new ListDataProvider<SurdoNaoVisitarDetailsVO>();
//		this.resultadoPesquisa.addDataDisplay(this.naoVisitarResultadoCellTable);
//		this.naoVisitarSimplePager.setDisplay(this.naoVisitarResultadoCellTable);
//	}
//
//	@Override
//	public void showWaitingPanel() {
//		waitingPopUpPanel.setVisible(true);
//		waitingPopUpPanel.show();
//	}
//
//	@Override
//	public void hideWaitingPanel() {
//		waitingPopUpPanel.hide();
//		waitingPopUpPanel.setVisible(false);
//	}
//
//	@Override
//	public void initView() {
//		this.selectThisTab();
//	}
//
//	@Override
//	public void selectThisTab() {
//		this.cadastroSurdoTabLayoutPanel.selectTab(3, false);
//	}
//
//	@Override
//	public void setMapaList(List<Mapa> mapas) {
//		// não faz nada, não é necessário para esta View, embora seja para todas as outras e
//		// apesar deste método estar implementado aqui ele não será chamado.
//		throw new RuntimeException("Método setRegiaoList não é suportado pela view NaoVisitarViewImpl");
//	}
//
//	@Override
//	public void setRegiaoList(List<Regiao> regioes) {
//		// não faz nada, não é necessário para esta View, embora seja para todas as outras e
//		// apesar deste método estar implementado aqui ele não será chamado.
//		throw new RuntimeException("Método setRegiaoList não é suportado pela view NaoVisitarViewImpl");
//	}
//
//	@Override
//	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
//		this.cadastroSurdoTabLayoutPanel.addSelectionHandler(handler);
//	}
//
//	@Override
//	public void setPresenter(Presenter presenter) {
//		this.presenter = presenter;
//	}
//	
//	@Override
//	public void setSurdosNaoVisitar(List<SurdoNaoVisitarDetailsVO> surdos) {
//		this.limparResultadoPesquisa();
//		this.naoVisitarResultadoCellTable.setRowCount(surdos.size());
//		this.resultadoPesquisa.setList(surdos);
//		
//		TextColumn<SurdoNaoVisitarDetailsVO> nomeColumn = new TextColumn<SurdoNaoVisitarDetailsVO>() {
//			@Override
//			public String getValue(SurdoNaoVisitarDetailsVO object) {
//				return StringUtils.toCamelCase(object.getNome());
//			}
//		};
//		TextColumn<SurdoNaoVisitarDetailsVO> regiaoColumn = new TextColumn<SurdoNaoVisitarDetailsVO>() {
//			@Override
//			public String getValue(SurdoNaoVisitarDetailsVO object) {
//				return object.getRegiao();
//			}				
//		};
//		TextColumn<SurdoNaoVisitarDetailsVO> motivoColumn = new TextColumn<SurdoNaoVisitarDetailsVO>() {
//			@Override
//			public String getValue(SurdoNaoVisitarDetailsVO object) {
//				return object.getMotivo();
//			}
//		};
//		
//		Delegate<Long> retornarDelegate = new Delegate<Long>() {
//			@Override
//			public void execute(Long object) {
//				if (Window.confirm("Deseja realmente retornar este surdo à lista de surdos a serem visitados?")) {
//					presenter.onRetornarButtonClick(object);
//				}
//			}
//		};
//		ActionCell<Long> retornarCell = new ActionCell<Long>("Retornar", retornarDelegate);
//		Column<SurdoNaoVisitarDetailsVO, Long> retornarColumn = new Column<SurdoNaoVisitarDetailsVO, Long>(retornarCell) {
//			@Override
//			public Long getValue(SurdoNaoVisitarDetailsVO object) {
//				return object.getId();
//			}
//		};
//		
//		Delegate<String> mostrarObservacaoDelegate = new Delegate<String>() {
//			@Override
//			public void execute(String object) {
//				Window.alert(object);
//			}				
//		};
//		ActionCell<String> mostrarObservacaoCell = new ActionCell<String>("Mostrar observação", mostrarObservacaoDelegate);
//		Column<SurdoNaoVisitarDetailsVO, String> mostrarObservacaoColumn = 
//				new Column<SurdoNaoVisitarDetailsVO, String>(mostrarObservacaoCell) {
//			@Override
//			public String getValue(SurdoNaoVisitarDetailsVO object) {
//				return object.getObservacao();
//			}
//		};
//		
//		this.naoVisitarResultadoCellTable.addColumn(nomeColumn, "Nome");
//		this.naoVisitarResultadoCellTable.addColumn(regiaoColumn, "Região");
//		this.naoVisitarResultadoCellTable.addColumn(motivoColumn, "Motivo");
//		this.naoVisitarResultadoCellTable.addColumn(mostrarObservacaoColumn);
//		this.naoVisitarResultadoCellTable.addColumn(retornarColumn);
//		
//	}
//	
//	private void limparResultadoPesquisa() {	
//		// ao remover a coluna 0, o objeto passa a coluna 1 para a 0,
//		// portanto sempre  é necessário remover a coluna 0.
//		int j = this.naoVisitarResultadoCellTable.getColumnCount();
//		for (int i = 0; i < j; i++) {
//			this.naoVisitarResultadoCellTable.removeColumn(0);
//		}
//	}
//
//}
