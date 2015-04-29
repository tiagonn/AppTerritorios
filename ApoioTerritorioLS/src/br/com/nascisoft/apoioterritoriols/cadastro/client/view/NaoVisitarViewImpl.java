package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.client.common.ImageButtonCell;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoNaoVisitarDetailsVO;
import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;
import br.com.nascisoft.apoioterritoriols.resources.client.CellTableCustomResources;
import br.com.nascisoft.apoioterritoriols.resources.client.Resources;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class NaoVisitarViewImpl extends AbstractCadastroViewImpl implements NaoVisitarView {
	
	private static CadastroViewUiBinderUiBinder uiBinder = 
			GWT.create(CadastroViewUiBinderUiBinder.class);
	
	private NaoVisitarView.Presenter presenter;
	private ListDataProvider<SurdoNaoVisitarDetailsVO> resultadoPesquisa;
	
	@UiField (provided=true) CellTable<SurdoNaoVisitarDetailsVO> naoVisitarResultadoCellTable;
	@UiField SimplePager naoVisitarSimplePager;	
	
	@UiTemplate("CadastroViewUiBinder.ui.xml")
	interface CadastroViewUiBinderUiBinder 
		extends UiBinder<Widget, NaoVisitarViewImpl> {
	}
	
	public NaoVisitarViewImpl() {
		CellTableCustomResources.INSTANCE.cellTableStyle().ensureInjected();
		this.naoVisitarResultadoCellTable = new CellTable<SurdoNaoVisitarDetailsVO>(15, CellTableCustomResources.INSTANCE);
		initWidget(uiBinder.createAndBindUi(this));
		this.resultadoPesquisa = new ListDataProvider<SurdoNaoVisitarDetailsVO>();
		this.resultadoPesquisa.addDataDisplay(this.naoVisitarResultadoCellTable);
		this.naoVisitarSimplePager.setDisplay(this.naoVisitarResultadoCellTable);
	}

	@Override
	public void initView() {
		this.selectThisTab();
		this.cadastroSurdoTabLayoutPanel.getTabWidget(4).getParent().setVisible(this.presenter.getLoginInformation().isAdmin());
	}

	@Override
	public void selectThisTab() {
		this.cadastroSurdoTabLayoutPanel.selectTab(3, false);
	}
	
	@Override
	public void setPresenter(NaoVisitarView.Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void setSurdosNaoVisitar(List<SurdoNaoVisitarDetailsVO> surdos) {
		this.limparResultadoPesquisa();
		this.naoVisitarResultadoCellTable.setRowCount(surdos.size());
		this.resultadoPesquisa.setList(surdos);
		
		TextColumn<SurdoNaoVisitarDetailsVO> nomeColumn = new TextColumn<SurdoNaoVisitarDetailsVO>() {
			@Override
			public String getValue(SurdoNaoVisitarDetailsVO object) {
				return StringUtils.toCamelCase(object.getNome());
			}
		};
		TextColumn<SurdoNaoVisitarDetailsVO> cidadeColumn = new TextColumn<SurdoNaoVisitarDetailsVO>() {
			@Override
			public String getValue(SurdoNaoVisitarDetailsVO object) {
				return object.getNomeCidade();
			}
		};
		TextColumn<SurdoNaoVisitarDetailsVO> regiaoColumn = new TextColumn<SurdoNaoVisitarDetailsVO>() {
			@Override
			public String getValue(SurdoNaoVisitarDetailsVO object) {
				return object.getRegiao();
			}				
		};
		TextColumn<SurdoNaoVisitarDetailsVO> motivoColumn = new TextColumn<SurdoNaoVisitarDetailsVO>() {
			@Override
			public String getValue(SurdoNaoVisitarDetailsVO object) {
				return object.getMotivo();
			}
		};
		TextColumn<SurdoNaoVisitarDetailsVO> observacaoColumn = new TextColumn<SurdoNaoVisitarDetailsVO>() {
			@Override
			public String getValue(SurdoNaoVisitarDetailsVO object) {
				return object.getObservacao();
			}
		};
		
		Column<SurdoNaoVisitarDetailsVO, String> retornarColumn = new Column<SurdoNaoVisitarDetailsVO, String>(
				new ImageButtonCell("Retornar pessoa ao cadastro", "Retornar pessoa ao cadastro")) {
			@Override
			public String getValue(SurdoNaoVisitarDetailsVO object) {
				return Resources.INSTANCE.voltar().getSafeUri().asString();
			}
		};
		retornarColumn.setFieldUpdater(new FieldUpdater<SurdoNaoVisitarDetailsVO, String>() {
			@Override
			public void update(int index, final SurdoNaoVisitarDetailsVO object, String value) {
				mostrarConfirmacao("Deseja realmente retornar esta pessoa à lista de cadastros ativos?",
					new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							presenter.onRetornarButtonClick(object.getId());
						}
					}
				);					
			}
		});
		
		this.naoVisitarResultadoCellTable.addColumn(nomeColumn, "Nome");
		this.naoVisitarResultadoCellTable.addColumn(cidadeColumn, "Cidade");
		this.naoVisitarResultadoCellTable.addColumn(regiaoColumn, "Região");
		this.naoVisitarResultadoCellTable.addColumn(motivoColumn, "Motivo");
		this.naoVisitarResultadoCellTable.addColumn(observacaoColumn, "Observação");
		this.naoVisitarResultadoCellTable.addColumn(retornarColumn);
		
	}
	
	private void limparResultadoPesquisa() {	
		// ao remover a coluna 0, o objeto passa a coluna 1 para a 0,
		// portanto sempre  é necessário remover a coluna 0.
		int j = this.naoVisitarResultadoCellTable.getColumnCount();
		for (int i = 0; i < j; i++) {
			this.naoVisitarResultadoCellTable.removeColumn(0);
		}
	}

}
