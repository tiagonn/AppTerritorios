package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.ArrayList;
import java.util.List;

import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;
import br.com.nascisoft.apoioterritoriols.resources.client.ApoioTerritorioLSConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

public class ImpressaoViewImpl extends AbstractCadastroViewImpl implements
		ImpressaoView {
	
	private static CadastroSurdoViewUiBinderUiBinder uiBinder = 
		GWT.create(CadastroSurdoViewUiBinderUiBinder.class);
	private ImpressaoView.Presenter presenter;
	
	@UiField ListBox pesquisaImpressaoCidadeListBox;
	@UiField ListBox pesquisaImpressaoRegiaoListBox;
	@UiField ListBox pesquisaImpressaoMapaListBox;
	@UiField PushButton pesquisaImpressapImprimirButton;
	
	@UiTemplate("CadastroViewUiBinder.ui.xml")
	interface CadastroSurdoViewUiBinderUiBinder 
		extends UiBinder<Widget, ImpressaoViewImpl> {
	}

	public ImpressaoViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void initView() {
		this.selectThisTab();	
		this.limparPesquisa();
		this.cadastroSurdoTabLayoutPanel.getTabWidget(4).getParent().setVisible(this.presenter.getLoginInformation().isAdmin());
	}
	
	@Override
	public void selectThisTab() {
		this.cadastroSurdoTabLayoutPanel.selectTab(2, false);
	}
	
	private void limparPesquisa() {
		this.pesquisaImpressaoCidadeListBox.setSelectedIndex(0);
		this.pesquisaImpressaoRegiaoListBox.setSelectedIndex(0);
		this.pesquisaImpressaoRegiaoListBox.clear();
		this.pesquisaImpressaoRegiaoListBox.addItem("-- Escolha uma região --", "");
		this.pesquisaImpressaoRegiaoListBox.setEnabled(false);
		this.pesquisaImpressaoMapaListBox.clear();
		this.pesquisaImpressaoMapaListBox.addItem("-- Escolha um mapa --", "");
		this.pesquisaImpressaoMapaListBox.setSelectedIndex(0);
		this.pesquisaImpressaoMapaListBox.setEnabled(false);
		this.pesquisaImpressaoMapaListBox.setVisibleItemCount(4);
		this.pesquisaImpressapImprimirButton.setEnabled(false);
	}


	@Override
	public void setCidadeList(List<Cidade> cidades) {
		this.pesquisaImpressaoCidadeListBox.clear();
		if (cidades.size() > 1) {
			this.pesquisaImpressaoCidadeListBox.addItem("-- Escolha uma cidade --", "");
			
			this.pesquisaImpressaoRegiaoListBox.clear();
			this.pesquisaImpressaoRegiaoListBox.addItem("-- Escolha uma região --", "");
			
			this.pesquisaImpressaoMapaListBox.clear();
			this.pesquisaImpressaoMapaListBox.addItem("-- Escolha um mapa --", "");	
			this.pesquisaImpressaoMapaListBox.setVisibleItemCount(4);

		} else {
			this.presenter.onPesquisaCidadeListBoxChange(cidades.get(0).getId());
		}
		for (Cidade cidade : cidades) {
			this.pesquisaImpressaoCidadeListBox.addItem(cidade.getNome(), cidade.getId().toString());
		}	
	}
	
	@Override
	public void setRegiaoList(List<Regiao> regioes) {
		this.pesquisaImpressaoRegiaoListBox.setEnabled(true);
		this.pesquisaImpressaoRegiaoListBox.clear();
		this.pesquisaImpressaoRegiaoListBox.addItem("-- Escolha uma região --", "");

		this.pesquisaImpressaoMapaListBox.clear();
		this.pesquisaImpressaoMapaListBox.addItem("-- Escolha um mapa --", "");
		this.pesquisaImpressaoMapaListBox.setVisibleItemCount(4);
		
		for (Regiao regiao : regioes) {
			this.pesquisaImpressaoRegiaoListBox.addItem(regiao.getLetra() + " - " + regiao.getNome(), regiao.getId().toString());
		}
	}

	@Override
	public void setMapaList(List<Mapa> mapas) {
		this.pesquisaImpressaoMapaListBox.setEnabled(true);
		this.pesquisaImpressaoMapaListBox.clear();
		this.pesquisaImpressaoMapaListBox.setWidth("155px");
		for (Mapa mapa : mapas) {
			this.pesquisaImpressaoMapaListBox.addItem(mapa.getNome(), mapa.getId().toString());
		}
		this.pesquisaImpressaoMapaListBox.setVisibleItemCount(mapas.size()>15?15:mapas.size());
	}

	@Override
	public void setPresenter(ImpressaoView.Presenter presenter) {
		this.presenter = presenter;
		
	}

	@UiHandler("pesquisaImpressaoCidadeListBox")
	void onPesquisaImpressaoCidadeListBoxChange(ChangeEvent event) {
		String value = this.pesquisaImpressaoCidadeListBox.getValue(this.pesquisaImpressaoCidadeListBox.getSelectedIndex());
		if (!StringUtils.isEmpty(value)) {
			this.presenter.onPesquisaCidadeListBoxChange(Long.valueOf(value));
		} 
		
		this.pesquisaImpressaoRegiaoListBox.setEnabled(false);
		this.pesquisaImpressaoRegiaoListBox.setSelectedIndex(0);
		
		this.pesquisaImpressaoMapaListBox.setEnabled(false);
		this.pesquisaImpressaoMapaListBox.setSelectedIndex(0);
		this.pesquisaImpressaoMapaListBox.clear();
		this.pesquisaImpressaoMapaListBox.setVisibleItemCount(4);
		this.pesquisaImpressaoMapaListBox.addItem("-- Escolha um mapa --", "");
		
		this.pesquisaImpressapImprimirButton.setEnabled(false);
	}

	@UiHandler("pesquisaImpressaoRegiaoListBox")
	void onPesquisaImpressaoRegiaoListBox(ChangeEvent event) {
		if (!this.pesquisaImpressaoRegiaoListBox.getValue(this.pesquisaImpressaoRegiaoListBox.getSelectedIndex()).isEmpty()) {
			this.pesquisaImpressaoMapaListBox.setEnabled(true);
			this.pesquisaImpressapImprimirButton.setEnabled(false);
			this.presenter.onPesquisaRegiaoListBoxChange(
					Long.valueOf(pesquisaImpressaoRegiaoListBox.getValue(pesquisaImpressaoRegiaoListBox.getSelectedIndex())));
		} else {
			this.pesquisaImpressaoMapaListBox.setEnabled(false);
			this.pesquisaImpressaoMapaListBox.setSelectedIndex(0);
			this.pesquisaImpressaoMapaListBox.clear();
			this.pesquisaImpressaoMapaListBox.setVisibleItemCount(4);
			this.pesquisaImpressaoMapaListBox.addItem("-- Escolha um mapa --", "");
			this.pesquisaImpressapImprimirButton.setEnabled(false);
		}
	}
	
	@UiHandler("pesquisaImpressaoMapaListBox")
	void onPesquisaImpressaoMapaListBox(ChangeEvent event) {
		this.pesquisaImpressapImprimirButton.setEnabled(true);
	}
	
	@UiHandler("pesquisaImpressapImprimirButton")
	void onPesquisaImpressapImprimirButtonClick(ClickEvent event) {
		List<Long> mapaIDs = mapearMapasSelecionados(this.pesquisaImpressaoMapaListBox);
		if (mapaIDs.size() > 0) {
			this.presenter.abrirImpressao(mapaIDs, true);
		} else {
			this.mostrarWarning("Por favor, selecione ao menos um mapa.", ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
		}
	}
	
	private List<Long> mapearMapasSelecionados(ListBox box) {
		List<Long> lista = new ArrayList<Long>();
		for (int i = 0; i < box.getItemCount(); i++) {
			if (box.isItemSelected(i)) {
				lista.add(Long.valueOf(box.getValue(i)));
			}
		}
		return lista;
	}

}
