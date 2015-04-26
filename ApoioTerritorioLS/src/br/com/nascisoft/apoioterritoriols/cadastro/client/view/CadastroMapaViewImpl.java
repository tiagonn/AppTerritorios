package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.AbrirMapaVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.InfoWindowVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoDetailsVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;
import br.com.nascisoft.apoioterritoriols.resources.client.Resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class CadastroMapaViewImpl extends Composite implements
		CadastroMapaView {
	
	private static CadastroSurdoViewUiBinderUiBinder uiBinder = 
		GWT.create(CadastroSurdoViewUiBinderUiBinder.class);
	private Presenter presenter;
	
	@UiField TabLayoutPanel cadastroSurdoTabLayoutPanel;
	@UiField ListBox pesquisaMapaCidadeListBox;
	@UiField ListBox pesquisaMapaRegiaoListBox;
	@UiField ListBox pesquisaMapaMapaListBox;
	@UiField PushButton manterMapaSurdoAdicionarButton;
	@UiField PushButton manterMapaSurdoRemoverButton;
	@UiField PushButton manterMapaApagarMapaButton;
	@UiField LayoutPanel manterMapaMapaLayoutPanel;
	@UiField PopupPanel waitingPopUpPanel;
	@UiField PushButton pesquisaMapaAdicionarMapaButton;
	@UiField FlowPanel manterMapaSelecaoPessoasContainerFlowPanel;
	@UiField FlowPanel manterMapaSelecaoPessoasDeFlowPanel;
	@UiField FlowPanel manterMapaSelecaoPessoasParaFlowPanel;
	@UiField FlowPanel manterMapaMapaContainerFlowPanel;
	@UiField FlowPanel manterMapaSelecaoPessoasContainerDeFlowPanel;
	
	private AbrirMapaVO abrirMapaVO;
	
	private Map<Long, InfoWindowVO> mapaInfoWindow;
	private Set<Long> setPessoasDe = new HashSet<Long>();
	private Set<Long> setPessoasPara = new HashSet<Long>();
	
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
		this.cadastroSurdoTabLayoutPanel.getTabWidget(4).getParent().setVisible(this.presenter.getLoginInformation().isAdmin());
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
		this.pesquisaMapaCidadeListBox.setSelectedIndex(0);
		this.pesquisaMapaRegiaoListBox.setSelectedIndex(0);
		this.pesquisaMapaRegiaoListBox.clear();
		this.pesquisaMapaRegiaoListBox.addItem("-- Escolha uma região --", "");
		this.pesquisaMapaRegiaoListBox.setEnabled(false);
		this.pesquisaMapaMapaListBox.clear();
		this.pesquisaMapaMapaListBox.addItem("-- Escolha um mapa --", "");
		this.pesquisaMapaMapaListBox.setEnabled(false);
		this.pesquisaMapaAdicionarMapaButton.setVisible(false);
		this.manterMapaApagarMapaButton.setVisible(false);
		this.abrirMapaVO = null;
	}
	
	private void limparManter() {
		this.abrirMapaVO = null;
		this.mapaInfoWindow = new HashMap<Long, InfoWindowVO>();
		this.manterMapaApagarMapaButton.setVisible(false);
		this.manterMapaSelecaoPessoasContainerFlowPanel.setVisible(false);
		this.manterMapaMapaContainerFlowPanel.setVisible(false);
		setPessoasDe.clear();
		setPessoasPara.clear();

	}
	

	@Override
	public void setCidadeList(List<Cidade> cidades) {
		this.pesquisaMapaCidadeListBox.clear();
		if (cidades.size() > 1) {
			this.pesquisaMapaCidadeListBox.addItem("-- Escolha uma cidade --", "");
			
			this.pesquisaMapaRegiaoListBox.clear();
			this.pesquisaMapaRegiaoListBox.addItem("-- Escolha uma região --", "");
			
			this.pesquisaMapaMapaListBox.clear();
			this.pesquisaMapaMapaListBox.addItem("-- Escolha um mapa --", "");	
		} else {
			this.presenter.onPesquisaCidadeListBoxChange(cidades.get(0).getId());
		}
		for (Cidade cidade : cidades) {
			this.pesquisaMapaCidadeListBox.addItem(cidade.getNome(), cidade.getId().toString());
		}	
	}

	@Override
	public void setRegiaoList(List<Regiao> regioes) {
		this.pesquisaMapaRegiaoListBox.setEnabled(true);
		this.pesquisaMapaRegiaoListBox.clear();
		this.pesquisaMapaRegiaoListBox.addItem("-- Escolha uma região --", "");
		
		for (Regiao regiao : regioes) {
			this.pesquisaMapaRegiaoListBox.addItem(regiao.getLetra() + " - " + regiao.getNome(), regiao.getId().toString());
		}
		
		if (abrirMapaVO != null) {
			this.pesquisaMapaRegiaoListBox.setSelectedIndex(
					obterIndice(this.pesquisaMapaRegiaoListBox, this.abrirMapaVO.getRegiao().getId().toString()));
			this.pesquisaMapaAdicionarMapaButton.setVisible(true);
			this.presenter.onPesquisaRegiaoListBoxChange(this.abrirMapaVO.getRegiao().getId());
		}
	}

	@Override
	public void setMapaList(List<Mapa> mapas) {
		this.pesquisaMapaMapaListBox.setEnabled(true);
		this.pesquisaMapaMapaListBox.clear();
		this.pesquisaMapaMapaListBox.addItem("-- Escolha um mapa --", "");
		for (Mapa mapa : mapas) {
			this.pesquisaMapaMapaListBox.addItem(mapa.getNome(), mapa.getId().toString());
		}
		if (this.abrirMapaVO != null) {
			this.pesquisaMapaMapaListBox.setSelectedIndex(
					this.obterIndice(
							this.pesquisaMapaMapaListBox, 
							this.abrirMapaVO.getMapa().getId().toString()));
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

	@UiHandler("pesquisaMapaCidadeListBox")
	void onPesquisaMapaCidadeListBoxChange(ChangeEvent event) {
		String value = this.pesquisaMapaCidadeListBox.getValue(this.pesquisaMapaCidadeListBox.getSelectedIndex());
		if (!StringUtils.isEmpty(value)) {
			this.presenter.onPesquisaCidadeListBoxChange(Long.valueOf(value));
		} 
		
		this.pesquisaMapaMapaListBox.setEnabled(false);
		this.pesquisaMapaMapaListBox.setSelectedIndex(0);
		
		this.pesquisaMapaRegiaoListBox.setEnabled(false);
		this.pesquisaMapaRegiaoListBox.setSelectedIndex(0);
		limparManter();
	}
	
	@UiHandler("pesquisaMapaRegiaoListBox")
	void onPesquisaMapaRegiaoListBoxChange(ChangeEvent event) {
		if (presenter != null) {
			if (!this.pesquisaMapaRegiaoListBox.getValue(this.pesquisaMapaRegiaoListBox.getSelectedIndex()).isEmpty()) {
				this.pesquisaMapaAdicionarMapaButton.setVisible(true);
				this.presenter.onPesquisaRegiaoListBoxChange(
						Long.valueOf(
								pesquisaMapaRegiaoListBox.getValue(pesquisaMapaRegiaoListBox.getSelectedIndex())));
			} else {
				this.pesquisaMapaMapaListBox.setEnabled(false);
				this.pesquisaMapaMapaListBox.setSelectedIndex(0);
				this.pesquisaMapaAdicionarMapaButton.setVisible(false);
			}
		}
		limparManter();
	}
	
	@UiHandler("pesquisaMapaAdicionarMapaButton")
	void onPesquisaMapaAdicionarMapaButtonClick(ClickEvent event) {
		this.presenter.adicionarMapa(
				Long.valueOf(
						this.pesquisaMapaRegiaoListBox.getValue(this.pesquisaMapaRegiaoListBox.getSelectedIndex())));
	}
	

	@UiHandler("pesquisaMapaMapaListBox")
	void onPesquisaMapaMapaListBoxChange(ChangeEvent event) {
		if (presenter != null) {
			String mapa = this.pesquisaMapaMapaListBox.getValue(this.pesquisaMapaMapaListBox.getSelectedIndex());
			if (!"".equals(mapa)) {
				this.presenter.abrirMapa(Long.valueOf(mapa));
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

	@UiHandler("manterMapaSurdoAdicionarButton")
	void onManterMapaSurdoAdicionarButtonClick(ClickEvent event) {
		if (setPessoasDe.size() > 0) {
			int tamanhoMapa = this.abrirMapaVO.getCidade().getQuantidadeSurdosMapa();
			if (setPessoasDe.size() + this.manterMapaSelecaoPessoasParaFlowPanel.getWidgetCount() > tamanhoMapa) {
				Window.alert("Apenas " + tamanhoMapa + " pessoas(s) pode(m) compor um mapa. Você está tentando adicionar uma quantidade maior do que o mapa permite");
			} else {
				this.presenter.adicionarSurdosMapa(setPessoasDe, 
						Long.valueOf(this.pesquisaMapaMapaListBox.getValue(
								this.pesquisaMapaMapaListBox.getSelectedIndex())));
			}
		}
	}

	@UiHandler("manterMapaSurdoRemoverButton")
	void onManterMapaSurdoRemoverButtonClick(ClickEvent event) {
		if (this.setPessoasPara.size() > 0) {
			this.presenter.removerSurdosMapa(this.setPessoasPara);
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
	
	private void setDadosFiltros(Cidade cidade) {
		this.pesquisaMapaCidadeListBox.setSelectedIndex(
				obterIndice(this.pesquisaMapaCidadeListBox, cidade.getId().toString()));
		this.presenter.onPesquisaCidadeListBoxChange(cidade.getId());
	}

	@Override
	public void onAbrirMapa(AbrirMapaVO vo) {
		
		setDadosFiltros(vo.getCidade());
		this.abrirMapaVO = vo;
		
		HasMapOptions opt = new MapOptions();
		opt.setZoom(vo.getRegiao().getZoom());
		opt.setCenter(new LatLng(vo.getCentroRegiao().getLatitude(),vo.getCentroRegiao().getLongitude()));
		opt.setMapTypeId(new MapTypeId().getRoadmap());
		opt.setDraggable(true);
		opt.setNavigationControl(true);
		opt.setScrollwheel(true);
		MapWidget mapa = new MapWidget(opt);
		
		this.manterMapaSelecaoPessoasDeFlowPanel.clear();
		for (SurdoDetailsVO surdo : vo.getSurdosDe()) {
			CheckBox cb = new CheckBox(StringUtils.toCamelCase(surdo.getNome()));
			cb.setStyleName("mapas-manter-selecao-checkbox");
			final Long idSurdo = surdo.getId();
			cb.addValueChangeHandler(new ValueChangeHandler<Boolean>() {				
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					if (event.getValue()) {
						setPessoasDe.add(idSurdo);
						abrirMarcadorMapa(idSurdo);
					} else {
						setPessoasDe.remove(idSurdo);
						fecharMarcadorMapa(idSurdo);
					}					
				}
			});
			adicionarMarcadorSurdo(surdo, mapa, TIPO_SURDO_SEM_MAPA);
			this.manterMapaSelecaoPessoasDeFlowPanel.add(cb);
		}
		
		this.manterMapaSelecaoPessoasParaFlowPanel.clear();
		for (SurdoDetailsVO surdo : vo.getSurdosPara()) {
			CheckBox cb = new CheckBox(StringUtils.toCamelCase(surdo.getNome()));
			cb.setStyleName("mapas-manter-selecao-checkbox");
			final Long idSurdo = surdo.getId();
			cb.addValueChangeHandler(new ValueChangeHandler<Boolean>() {				
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					if (event.getValue()) {
						setPessoasPara.add(idSurdo);
						abrirMarcadorMapa(idSurdo);
					} else {
						setPessoasPara.remove(idSurdo);
						fecharMarcadorMapa(idSurdo);
					}					
				}
			});
			adicionarMarcadorSurdo(surdo, mapa, TIPO_SURDO_MAPA_ATUAL);
			this.manterMapaSelecaoPessoasParaFlowPanel.add(cb);
		}
		
		for (SurdoDetailsVO surdo : vo.getSurdosOutros()) {
			adicionarMarcadorSurdo(surdo, mapa, TIPO_SURDO_MAPA_OUTROS);
		}
		
		this.manterMapaSurdoAdicionarButton.getDownFace().setImage(new Image(Resources.INSTANCE.paraBaixo()));
		this.manterMapaSurdoAdicionarButton.getUpFace().setImage(new Image(Resources.INSTANCE.paraBaixo()));

		this.manterMapaSurdoRemoverButton.getDownFace().setImage(new Image(Resources.INSTANCE.paraCima()));
		this.manterMapaSurdoRemoverButton.getUpFace().setImage(new Image(Resources.INSTANCE.paraCima()));
		
		this.manterMapaApagarMapaButton.setVisible(true);
		this.manterMapaSelecaoPessoasContainerFlowPanel.setVisible(true);
		this.manterMapaMapaContainerFlowPanel.setVisible(true);
		
		int larguraMapa = this.manterMapaMapaContainerFlowPanel.getOffsetWidth() - 20;
		int alturaMapa = Window.getClientHeight() - this.manterMapaMapaContainerFlowPanel.getAbsoluteTop() - 60;
		String sLarguraMapa = larguraMapa + "px";
		String sAlturaMapa = alturaMapa + "px";
		
		this.manterMapaMapaLayoutPanel.clear();
		this.manterMapaMapaLayoutPanel.setSize(sLarguraMapa, sAlturaMapa);
		mapa.setSize(sLarguraMapa, sAlturaMapa);
		this.manterMapaMapaLayoutPanel.add(mapa);		
		
		int alturaPainelDe = Window.getClientHeight() 
				- this.manterMapaSelecaoPessoasContainerFlowPanel.getAbsoluteTop()
				- this.manterMapaSelecaoPessoasContainerFlowPanel.getOffsetHeight() + 20;
		
		alturaPainelDe += this.manterMapaSelecaoPessoasDeFlowPanel.getOffsetHeight();
		
		this.manterMapaSelecaoPessoasContainerDeFlowPanel.getElement().getStyle().setProperty("maxHeight", alturaPainelDe, Unit.PX);
		this.manterMapaSelecaoPessoasDeFlowPanel.getElement().getStyle().setHeight(alturaPainelDe - 50, Unit.PX);
	}
	
	private void adicionarMarcadorSurdo(SurdoDetailsVO surdo, final MapWidget mapa, int tipoSurdo) {
		HasMarkerOptions markerOpt = new MarkerOptions();
		markerOpt.setClickable(true);
		markerOpt.setVisible(true);
		HasMarkerImage icon = null;
		if (TIPO_SURDO_MAPA_ATUAL == tipoSurdo) {
			icon = new MarkerImage.Builder(Resources.INSTANCE.iconeAzul().getSafeUri().asString()).build();
		} else if (TIPO_SURDO_SEM_MAPA == tipoSurdo){
			icon = new MarkerImage.Builder(Resources.INSTANCE.iconeVermelho().getSafeUri().asString()).build();
		} else if (TIPO_SURDO_MAPA_OUTROS == tipoSurdo) {
			icon = new MarkerImage.Builder(Resources.INSTANCE.iconeVerde().getSafeUri().asString()).build();
		}
		if (icon != null) {
			markerOpt.setIcon(icon);
		}
		HasInfoWindowOptions infoOpt = new InfoWindowOptions();
		StringBuilder info = new StringBuilder();
		info.append("<span><strong>Nome:</strong> ").append(StringUtils.toCamelCase(surdo.getNome())).append("</span><br/>");
		info.append("<span><strong>Endereço:</strong> ").append(surdo.getEndereco()).append("</span><br/>");
		if (!StringUtils.isEmpty(surdo.getObservacao())) {
			info.append("<span width=80px><strong>Observação:</strong> ").append(surdo.getObservacao()).append("</span><br/>");
		}
		if (!StringUtils.isEmpty(surdo.getMapa())) {
			info.append("<span><strong>Mapa:</strong> ").append(surdo.getMapa()).append("</span><br/>");
		}		
		info.append("<span><a href=\"Cadastro.html#surdos!editar#identificadorSurdo=")
			.append(surdo.getId()).append("\">Editar surdo</a></span>");
		infoOpt.setContent(info.toString());
		infoOpt.setMaxWidth(300);
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
		
		this.mapaInfoWindow.put(surdo.getId(), new InfoWindowVO(mapa.getMap(), marker, infoWindow));
	}

	@Override
	public void onApagarMapa() {
		this.onPesquisaMapaRegiaoListBoxChange(null);
	}
	
	private void abrirMarcadorMapa(Long id) {
		InfoWindowVO vo = this.mapaInfoWindow.get(id);
		vo.getInfoWindow().open(vo.getMap(), vo.getMarker());
		
	}
	
	private void fecharMarcadorMapa(Long id) {
		InfoWindowVO vo = this.mapaInfoWindow.get(id);
		vo.getInfoWindow().close();
	}

}
