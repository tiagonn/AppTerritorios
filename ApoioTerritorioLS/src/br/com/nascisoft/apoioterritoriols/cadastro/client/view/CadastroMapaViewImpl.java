package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.ArrayList;
import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.cadastro.util.StringUtils;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.AbrirMapaVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoDetailsVO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.maps.client.HasMapOptions;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.HasInfoWindow;
import com.google.gwt.maps.client.base.HasInfoWindowOptions;
import com.google.gwt.maps.client.base.InfoWindow;
import com.google.gwt.maps.client.base.InfoWindowOptions;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.event.Event;
import com.google.gwt.maps.client.event.EventCallback;
import com.google.gwt.maps.client.overlay.HasMarker;
import com.google.gwt.maps.client.overlay.HasMarkerImage;
import com.google.gwt.maps.client.overlay.HasMarkerOptions;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerImage;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class CadastroMapaViewImpl extends Composite implements
		CadastroMapaView {
	
	private static CadastroSurdoViewUiBinderUiBinder uiBinder = 
		GWT.create(CadastroSurdoViewUiBinderUiBinder.class);
	private Presenter presenter;
	
	@UiField TabLayoutPanel cadastroSurdoTabLayoutPanel;
	@UiField ListBox pesquisaMapaRegiaoListBox;
	@UiField ListBox pesquisaMapaMapaListBox;
	@UiField Label manterMapaSurdoNomeMapaLabel;
	@UiField Grid manterMapaSurdoGrid;
	@UiField ListBox manterMapaSurdoDeListBox;
	@UiField ListBox manterMapaSurdoParaListBox;
	@UiField Button manterMapaSurdoAdicionarButton;
	@UiField Button manterMapaSurdoRemoverButton;
	@UiField Button manterMapaApagarMapaButton;
	@UiField LayoutPanel manterMapaMapaLayoutPanel;
	@UiField HorizontalPanel manterMapaLegendaHorizontalPanel;
	@UiField PopupPanel waitingPopUpPanel;
	
	private Long identificadorMapaAtual;
	
	private static final String OPERACAO_ADICIONAR_MAPA = "adicionarMapa";
	private static final int TIPO_SURDO_SEM_MAPA = 0;
	private static final int TIPO_SURDO_MAPA_ATUAL = 1;
	private static final int TIPO_SURDO_MAPA_OUTROS = 2;
	
	@UiTemplate("CadastroViewUiBinder.ui.xml")
	interface CadastroSurdoViewUiBinderUiBinder 
		extends UiBinder<Widget, CadastroMapaViewImpl> {
	}

	public CadastroMapaViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void initView() {
		this.selectThisTab();	
		this.limparPesquisa();
		this.limparManter();
	}
	
	@Override
	public void selectThisTab() {
		this.cadastroSurdoTabLayoutPanel.selectTab(1, false);
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
	
	
	private void limparPesquisa() {
		this.pesquisaMapaRegiaoListBox.setSelectedIndex(0);
		this.pesquisaMapaMapaListBox.clear();
		this.pesquisaMapaMapaListBox.addItem("-- Escolha um mapa --", "");
		this.pesquisaMapaMapaListBox.setSelectedIndex(0);
		this.pesquisaMapaMapaListBox.setEnabled(false);
		this.identificadorMapaAtual = null;
	}
	
	private void limparManter() {
		this.manterMapaSurdoGrid.setVisible(false);
		this.manterMapaSurdoNomeMapaLabel.setText("");
		this.identificadorMapaAtual = null;
		this.manterMapaMapaLayoutPanel.clear();
		this.manterMapaMapaLayoutPanel.setVisible(false);
		this.manterMapaLegendaHorizontalPanel.setVisible(false);
	}

	@Override
	public void setRegiaoList(List<String> regioes) {
		this.pesquisaMapaRegiaoListBox.clear();
		this.pesquisaMapaRegiaoListBox.addItem("-- Escolha uma região --", "");

		this.pesquisaMapaMapaListBox.clear();
		this.pesquisaMapaMapaListBox.addItem("-- Escolha um mapa --", "");
		
		for (String regiao : regioes) {
			this.pesquisaMapaRegiaoListBox.addItem(regiao);
		}
	}

	@Override
	public void setMapaList(List<Mapa> mapas) {
		this.pesquisaMapaMapaListBox.clear();
		this.pesquisaMapaMapaListBox.addItem("-- Escolha um mapa --", "");
		this.pesquisaMapaMapaListBox.addItem("Adicionar novo mapa na Região", OPERACAO_ADICIONAR_MAPA);
		for (Mapa mapa : mapas) {
			this.pesquisaMapaMapaListBox.addItem(mapa.getNome(), mapa.getId().toString());
		}
		if (this.identificadorMapaAtual != null) {
			this.pesquisaMapaMapaListBox.setSelectedIndex(
					this.obterIndice(
							this.pesquisaMapaMapaListBox, 
							this.identificadorMapaAtual.toString()));
		}
	}

	@Override
	public void setPresenter(
			br.com.nascisoft.apoioterritoriols.cadastro.client.view.CadastroMapaView.Presenter presenter) {
		this.presenter = presenter;
		
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.cadastroSurdoTabLayoutPanel.addSelectionHandler(handler);
	}

	@UiHandler("pesquisaMapaRegiaoListBox")
	void onPesquisaMapaRegiaoListBoxChange(ChangeEvent event) {
		if (presenter != null) {
			if (!this.pesquisaMapaRegiaoListBox.getValue(this.pesquisaMapaRegiaoListBox.getSelectedIndex()).isEmpty()) {
				this.pesquisaMapaMapaListBox.setEnabled(true);
				this.presenter.onPesquisaRegiaoListBoxChange(pesquisaMapaRegiaoListBox.getValue(pesquisaMapaRegiaoListBox.getSelectedIndex()));
			} else {
				this.pesquisaMapaMapaListBox.setEnabled(false);
				this.pesquisaMapaMapaListBox.setSelectedIndex(0);
			}
		}
		limparManter();
	}
	
	@UiHandler("pesquisaMapaMapaListBox")
	void onPesquisaMapaMapaListBoxChange(ChangeEvent event) {
		if (presenter != null) {
			String mapa = this.pesquisaMapaMapaListBox.getValue(this.pesquisaMapaMapaListBox.getSelectedIndex());
			if (!"".equals(mapa)) {
				if (OPERACAO_ADICIONAR_MAPA.equals(mapa)) {
					this.presenter.adicionarMapa(
							this.pesquisaMapaRegiaoListBox.getValue(this.pesquisaMapaRegiaoListBox.getSelectedIndex()));
				} else {
					this.presenter.abrirMapa(Long.valueOf(mapa));
				}
			} else {
				limparManter();
			}
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
	
	//TODO: alterar componente de multi-select box para box com labels selecionados ou avaliar outra possibilidade
	//TODO: ao selecionar um surdo, fazer a infowindow dele aparecer no mapa.

	@UiHandler("manterMapaSurdoAdicionarButton")
	void onManterMapaSurdoAdicionarButtonClick(ClickEvent event) {
		if (this.presenter != null) {
			List<Long> lista = mapearSurdosSelecionados(this.manterMapaSurdoDeListBox);
			if (lista.size() > 0) {
				if (lista.size() + this.manterMapaSurdoParaListBox.getItemCount() > 4) {
					Window.alert("Apenas 4 surdos podem compor um mapa. Voce esta tentando adicionar uma quantidade maior do que o mapa permite");
				} else {
					this.presenter.adicionarSurdosMapa(lista, 
							Long.valueOf(this.pesquisaMapaMapaListBox.getValue(
									this.pesquisaMapaMapaListBox.getSelectedIndex())));
				}
			}
		}
	}
	//TODO: Ao clicar no surdo, fazer aparecer a sua infoWindow

	@UiHandler("manterMapaSurdoRemoverButton")
	void onManterMapaSurdoRemoverButtonClick(ClickEvent event) {
		if (this.presenter != null) {
			List<Long> lista = mapearSurdosSelecionados(this.manterMapaSurdoParaListBox);
			if (lista.size() > 0) {
				this.presenter.removerSurdosMapa(lista);
			}
		}
	}

	@UiHandler("manterMapaApagarMapaButton")
	void onManterMapaApagarMapaButtonClick(ClickEvent event) {
		if (this.presenter != null) {
			if (Window.confirm("Deseja realmente apagar este mapa? Todos os surdos associados a esse ficarão sem mapa associados.")) {
				this.presenter.apagarMapa(Long.valueOf(this.pesquisaMapaMapaListBox.getValue(
						this.pesquisaMapaMapaListBox.getSelectedIndex())));
			}
		}
	}
	
	private List<Long> mapearSurdosSelecionados(ListBox box) {
		List<Long> lista = new ArrayList<Long>();
		for (int i = 0; i < box.getItemCount(); i++) {
			if (box.isItemSelected(i)) {
				lista.add(Long.valueOf(box.getValue(i)));
			}
		}
		return lista;
	}

	@Override
	public void onAbrirMapa(AbrirMapaVO vo) {
		
		this.pesquisaMapaRegiaoListBox.setSelectedIndex(obterIndice(this.pesquisaMapaRegiaoListBox, vo.getMapa().getRegiao()));
		this.onPesquisaMapaRegiaoListBoxChange(null);
		
		this.pesquisaMapaMapaListBox.setSelectedIndex(obterIndice(this.pesquisaMapaMapaListBox, vo.getMapa().getId().toString()));
		
		this.identificadorMapaAtual = vo.getMapa().getId();
		
		HasMapOptions opt = new MapOptions();
		opt.setZoom(14);
		opt.setCenter(new LatLng(vo.getCentroRegiao().getLatitude(),vo.getCentroRegiao().getLongitude()));
		opt.setMapTypeId(new MapTypeId().getRoadmap());
		opt.setDraggable(true);
		opt.setNavigationControl(true);
		opt.setScrollwheel(true);
		MapWidget mapa = new MapWidget(opt);
		mapa.setSize("750px", "400px");

		this.manterMapaSurdoParaListBox.clear();
		for (SurdoDetailsVO surdo : vo.getSurdosPara()) {
			this.manterMapaSurdoParaListBox.addItem(StringUtils.toCamelCase(surdo.getNome()), surdo.getId().toString());
			adicionarMarcadorSurdo(surdo, mapa, TIPO_SURDO_MAPA_ATUAL);
		}	
		this.manterMapaSurdoDeListBox.clear();
		for (SurdoDetailsVO surdo : vo.getSurdosDe()) {
			this.manterMapaSurdoDeListBox.addItem(StringUtils.toCamelCase(surdo.getNome()), surdo.getId().toString());
			adicionarMarcadorSurdo(surdo, mapa, TIPO_SURDO_SEM_MAPA);
		}
		for (SurdoDetailsVO surdo : vo.getSurdosOutros()) {
			adicionarMarcadorSurdo(surdo, mapa, TIPO_SURDO_MAPA_OUTROS);
		}
		this.manterMapaSurdoGrid.setVisible(true);
		this.manterMapaSurdoNomeMapaLabel.setText(vo.getMapa().getNome());
		this.manterMapaSurdoNomeMapaLabel.setVisible(true);
		
		this.manterMapaMapaLayoutPanel.setSize("750px", "400px");
		this.manterMapaMapaLayoutPanel.setVisible(true);
		this.manterMapaMapaLayoutPanel.add(mapa);
		
		this.manterMapaLegendaHorizontalPanel.setVisible(true);
	}
	
	private void adicionarMarcadorSurdo(SurdoDetailsVO surdo, final MapWidget mapa, int tipoSurdo) {
		HasMarkerOptions markerOpt = new MarkerOptions();
		markerOpt.setClickable(true);
		markerOpt.setVisible(true);
		HasMarkerImage icon = null;
		if (TIPO_SURDO_MAPA_ATUAL == tipoSurdo) {
			icon = new MarkerImage.Builder("images/icone_azul.png").build();
		} else if (TIPO_SURDO_SEM_MAPA == tipoSurdo){
			icon = new MarkerImage.Builder("images/icone_vermelho.png").build();
		} else if (TIPO_SURDO_MAPA_OUTROS == tipoSurdo) {
			icon = new MarkerImage.Builder("images/icone_verde.png").build();
		}
		if (icon != null) {
			markerOpt.setIcon(icon);
		}
		HasInfoWindowOptions infoOpt = new InfoWindowOptions();
		StringBuilder info = new StringBuilder();
		info.append("<span><strong>Nome:</strong> ").append(StringUtils.toCamelCase(surdo.getNome()));
		if (!StringUtils.isEmpty(surdo.getMapa())) {
			info.append("<br/><br/><span><strong>Mapa:</strong> ").append(surdo.getMapa());
		}		
		infoOpt.setContent(info.toString());
		final HasInfoWindow infoWindow = new InfoWindow(infoOpt);
		final HasMarker marker = new Marker(markerOpt);
		marker.setPosition(new LatLng(surdo.getLatitude(), surdo.getLongitude()));
		marker.setMap(mapa.getMap());
		
		Event.addListener(marker, "click", new EventCallback() {			
			@Override
			public void callback() {
				infoWindow.open(mapa.getMap(), marker);				
			}
		});
	}

	@Override
	public void onApagarMapa() {
		this.onPesquisaMapaRegiaoListBoxChange(null);
	}
}
