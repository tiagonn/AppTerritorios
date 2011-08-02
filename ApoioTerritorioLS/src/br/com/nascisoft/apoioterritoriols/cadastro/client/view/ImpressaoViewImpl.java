package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.cadastro.util.StringUtils;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class ImpressaoViewImpl extends Composite implements
		ImpressaoView {
	
	private static CadastroSurdoViewUiBinderUiBinder uiBinder = 
		GWT.create(CadastroSurdoViewUiBinderUiBinder.class);
	private Presenter presenter;
	
	@UiField TabLayoutPanel cadastroSurdoTabLayoutPanel;
	@UiField ListBox pesquisaImpressaoRegiaoListBox;
	@UiField ListBox pesquisaImpressaoMapaListBox;	
	@UiField DecoratedPopupPanel impressaoDecoratedPopupPanel;
	@UiField FlexTable impressaoSurdoFlexTable;
	@UiField LayoutPanel impressaoMapaLayoutPanel;
	@UiField Button impressaoVoltarButton;
	
	@UiTemplate("CadastroViewUiBinder.ui.xml")
	interface CadastroSurdoViewUiBinderUiBinder 
		extends UiBinder<Widget, ImpressaoViewImpl> {
	}

	public ImpressaoViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void initView() {
		this.cadastroSurdoTabLayoutPanel.selectTab(2);	
		this.limparPesquisa();
		this.limparImpressao();
	}
	
	private void limparPesquisa() {
		this.pesquisaImpressaoRegiaoListBox.setSelectedIndex(0);
		this.pesquisaImpressaoMapaListBox.clear();
		this.pesquisaImpressaoMapaListBox.setSelectedIndex(0);
		this.pesquisaImpressaoMapaListBox.setEnabled(false);
	}
	
	private void limparImpressao() {
		this.impressaoDecoratedPopupPanel.hide();
		this.impressaoSurdoFlexTable.removeAllRows();
	}

	@Override
	public void setRegiaoList(List<String> regioes) {
		this.pesquisaImpressaoRegiaoListBox.clear();
		this.pesquisaImpressaoRegiaoListBox.addItem("-- Escolha uma regiao --", "");

		this.pesquisaImpressaoMapaListBox.clear();
		this.pesquisaImpressaoMapaListBox.addItem("-- Escolha um mapa --", "");
		
		for (String regiao : regioes) {
			this.pesquisaImpressaoRegiaoListBox.addItem(regiao);
		}
	}

	@Override
	public void setMapaList(List<Mapa> mapas) {
		this.pesquisaImpressaoMapaListBox.clear();
		this.pesquisaImpressaoMapaListBox.addItem("-- Escolha um mapa --", "");
		for (Mapa mapa : mapas) {
			this.pesquisaImpressaoMapaListBox.addItem(mapa.getNome(), mapa.getId().toString());
		}
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.cadastroSurdoTabLayoutPanel.addSelectionHandler(handler);
	}

	@UiHandler("pesquisaImpressaoRegiaoListBox")
	void onPesquisaMapaRegiaoListBoxChange(ChangeEvent event) {
		if (presenter != null) {
			if (!this.pesquisaImpressaoRegiaoListBox.getValue(this.pesquisaImpressaoRegiaoListBox.getSelectedIndex()).isEmpty()) {
				this.pesquisaImpressaoMapaListBox.setEnabled(true);
				this.presenter.onPesquisaRegiaoListBoxChange(pesquisaImpressaoRegiaoListBox.getValue(pesquisaImpressaoRegiaoListBox.getSelectedIndex()));
			} else {
				this.pesquisaImpressaoMapaListBox.setEnabled(false);
				this.pesquisaImpressaoMapaListBox.setSelectedIndex(0);
			}
		}
		limparImpressao();
	}
	
	@UiHandler("pesquisaImpressaoMapaListBox")
	void pesquisaImpressaoMapaListBox(ChangeEvent event) {
		if (presenter != null) {
			String mapa = this.pesquisaImpressaoMapaListBox.getValue(this.pesquisaImpressaoMapaListBox.getSelectedIndex());
			if (!"".equals(mapa)) {
				this.presenter.abrirImpressao(Long.valueOf(mapa));
			}
			else {
				limparImpressao();
			}
		}
	}

	@Override
	public void onAbrirImpressao(List<SurdoVO> surdos) {
		
		for (SurdoVO surdo : surdos) {
			StringBuilder html = new StringBuilder();
			html.append("<table>")
					.append("<tr>")
						.append("<td>Nome:</td>")
						.append("<td>").append(StringUtils.toCamelCase(surdo.getNome())).append("</td>");
//						.append("<td>").append("")
			//TODO: Terminar de criar a tabela HTML que vai em uma linha da flex table
		}
		
		//TODO: Terminar de criar usando o layoutpanel o mapa
		
		this.impressaoDecoratedPopupPanel.setAnimationEnabled(true);
		this.impressaoDecoratedPopupPanel.setAutoHideEnabled(true);
		this.impressaoDecoratedPopupPanel.setGlassEnabled(true);
		this.impressaoDecoratedPopupPanel.setTitle("Impressao de mapas");
		this.impressaoDecoratedPopupPanel.setVisible(true);
		this.impressaoDecoratedPopupPanel.show();		
	}
	
	@UiHandler("impressaoVoltarButton")
	public void onImpressaovoltarButtonClick(ClickEvent event) {
		this.limparImpressao();
	}
}
