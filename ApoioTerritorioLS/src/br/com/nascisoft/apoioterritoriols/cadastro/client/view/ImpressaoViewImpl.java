package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.cadastro.util.StringUtils;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;
import br.com.nascisoft.apoioterritoriols.cadastro.xml.Regiao;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.maps.client.HasMapOptions;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.HasLatLng;
import com.google.gwt.maps.client.base.LatLng;
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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
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
	@UiField SimplePanel impressaoSimplePanel;
	@UiField FlexTable impressaoSurdoFlexTable;
	@UiField FlexTable impressaoSurdoFlexTableInvertida;
	@UiField LayoutPanel impressaoMapaLayoutPanel;
	@UiField Button impressaoVoltarButton;
	@UiField PopupPanel waitingPopUpPanel;
	@UiField Image impressaoReverseImage;
	
	private Boolean paisagem = true;
	private Long identificadorMapaAtual = null;
	private final static String ALTURA_MAPA = "475px";
	private final static String LARGURA_MAPA = "800px";
	
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
		this.limparImpressao();
	}
	
	@Override
	public void selectThisTab() {
		this.cadastroSurdoTabLayoutPanel.selectTab(2, false);
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
		this.pesquisaImpressaoRegiaoListBox.setSelectedIndex(0);
		this.pesquisaImpressaoMapaListBox.clear();
		this.pesquisaImpressaoMapaListBox.addItem("-- Escolha um mapa --", "");
		this.pesquisaImpressaoMapaListBox.setSelectedIndex(0);
		this.pesquisaImpressaoMapaListBox.setEnabled(false);
	}
	
	private void limparImpressao() {
		this.impressaoSimplePanel.setVisible(false);
		this.impressaoSurdoFlexTable.removeAllRows();
		this.impressaoSurdoFlexTableInvertida.removeAllRows();
		this.identificadorMapaAtual = null;
	}

	@Override
	public void setRegiaoList(List<Regiao> regioes) {
		this.pesquisaImpressaoRegiaoListBox.clear();
		this.pesquisaImpressaoRegiaoListBox.addItem("-- Escolha uma região --", "");

		this.pesquisaImpressaoMapaListBox.clear();
		this.pesquisaImpressaoMapaListBox.addItem("-- Escolha um mapa --", "");
		
		for (Regiao regiao : regioes) {
			this.pesquisaImpressaoRegiaoListBox.addItem(regiao.getLetra() + " - " + regiao.getNome(), regiao.getNome());
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
	void onPesquisaImpressaoRegiaoListBox(ChangeEvent event) {
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
	void onPesquisaImpressaoMapaListBox(ChangeEvent event) {
		if (presenter != null) {
			String mapa = this.pesquisaImpressaoMapaListBox.getValue(this.pesquisaImpressaoMapaListBox.getSelectedIndex());
			this.identificadorMapaAtual = Long.valueOf(mapa);
			if (!"".equals(mapa)) {
				this.presenter.abrirImpressao(identificadorMapaAtual, paisagem);
			}
			else {
				limparImpressao();
			}
		}
	}

	@Override
	public void onAbrirImpressao(Long identificadorMapa, List<SurdoVO> surdos, Boolean paisagem) {
		this.paisagem = paisagem;
		this.identificadorMapaAtual = identificadorMapa;
		
		String classe = " class=\"impressao-celula\"";
		String classe1= " class=\"impressao-celula-titulo\"";
		
		HasMapOptions opt = new MapOptions();
		opt.setZoom(16);
		opt.setMapTypeId(new MapTypeId().getRoadmap());
		opt.setDraggable(true);
		opt.setNavigationControl(false);
		opt.setScrollwheel(true);
		opt.setDisableDefaultUI(true);
		opt.setCenter(this.obterCentroMapa(surdos));
		MapWidget mapa = new MapWidget(opt);
		defineTamanhoMapa(mapa);
		
		for (int i = 0; i < surdos.size();i++) {
			if (i == 0) {
				this.impressaoSurdoFlexTable.setHTML(0, 0, surdos.get(i).getMapa());
				this.impressaoSurdoFlexTable.getCellFormatter().addStyleName(0,0,"impressao-tabela-centro");
				this.impressaoSurdoFlexTableInvertida.setHTML(0, 0, surdos.get(i).getMapa());
				this.impressaoSurdoFlexTableInvertida.getCellFormatter().addStyleName(0,0,"impressao-tabela-centro");
			}
			SurdoVO surdo = surdos.get(i);
			
			adicionarMarcadorSurdo(i+1, surdo, mapa);
			
			StringBuilder html = new StringBuilder();
			html.append("<table width=\"100%\" cellspacing=0>")
					.append("<tr>")
						.append("<td width=\"40px\"").append(classe1).append(">Nome:</td>")
						.append("<td width=\"340px\"").append(classe).append(">").append(StringUtils.toCamelCase(surdo.getNome())).append("</td>")
						.append("<td width=\"140px\" ").append(classe1).append(">Data última visita:</td>")
						.append("<td width=\"205px\" ").append(classe1).append(">Falou com o surdo:</td>")
						.append("<td width=\"60px\"").append(classe1).append(">Libras:</td>")
						.append("<td width=\"15px\" ").append(classe).append(">").append(StringUtils.primeiraLetra(surdo.getLibras())).append("</td>")
					.append("</tr>")
					.append("<tr>")
						.append("<td").append(classe1).append(">End:</td>")
						.append("<td").append(classe).append(">")
							.append(surdo.getEndereco())
						.append("</td>")
						.append("<td colspan=\"2\"").append(classe1).append(">_______ ______________________________</td>")
						.append("<td").append(classe1).append(">DVD:</td>")
						.append("<td").append(classe).append(">").append(StringUtils.primeiraLetra(surdo.getDvd())).append("</td>")
					.append("</tr>")
					.append("<tr>")
						.append("<td").append(classe1).append(">Bairro:</td>")
						.append("<td").append(classe).append(">").append(surdo.getBairro()).append("</td>")
						.append("<td colspan=\"2\"").append(classe1).append(">_______ ______________________________</td>")
						.append("<td").append(classe1).append(">Sexo:</td>")
						.append("<td").append(classe).append(">").append(StringUtils.primeiraLetra(surdo.getSexo())).append("</td>")
					.append("</tr>")
					.append("<tr>")
						.append("<td").append(classe1).append(">Tel:</td>")
						.append("<td").append(classe).append(">").append(surdo.getTelefone()).append("</td>")
						.append("<td colspan=\"2\"").append(classe1).append(">_______ ______________________________</td>")
						.append("<td").append(classe1).append(">Criança:</td>")
						.append("<td").append(classe).append(">").append(StringUtils.primeiraLetra(surdo.getCrianca())).append("</td>")
					.append("</tr>")
					.append("<tr>")
						.append("<td").append(classe1).append(">Obs:</td>")
						.append("<td colspan=\"5\" ").append(classe).append(">").append(surdo.getObservacao()).append("</td>")
					.append("</tr>")
					.append("<tr>")
						.append("<td colspan=\"6\"><table width=\"100%\" cellspacing=0><tr>")
						.append("<td width=\"120px\"").append(classe).append("><strong>Idade:</strong> ").append(surdo.getIdade()).append("</td>")
						.append("<td width=\"180px\"").append(classe).append("><strong>Melhor dia:</strong> ").append(surdo.getMelhorDia()).append("</td>")
						.append("<td width=\"180px\"").append(classe).append("><strong>Horário:</strong> ").append(surdo.getHorario()).append("</td>")
						.append("<td width=\"120px\"").append(classe).append("><strong>Ônibus:</strong> ").append(surdo.getOnibus()).append("</td>")
						.append("<td width=\"200px\"").append(classe).append("><strong>Instrutor:</strong> ").append(surdo.getInstrutor()).append("</td>")
					.append("</tr></table></td></tr>")
				.append("</table>");
						
			this.impressaoSurdoFlexTable.setHTML(i+1, 0, html.toString());
			this.impressaoSurdoFlexTable.setBorderWidth(1);
			this.impressaoSurdoFlexTable.setCellSpacing(0);
			this.impressaoSurdoFlexTableInvertida.setHTML(i+1, 0, html.toString());
			this.impressaoSurdoFlexTableInvertida.setBorderWidth(1);
			this.impressaoSurdoFlexTableInvertida.setCellSpacing(0);
		}
		
		this.impressaoSurdoFlexTableInvertida.setStyleName("impressao-tabela-invertida-"+surdos.size());
		
		defineTamanhoMapa(this.impressaoMapaLayoutPanel);
		this.impressaoMapaLayoutPanel.setVisible(true);
		this.impressaoMapaLayoutPanel.add(mapa);
		
		//TODO: bug de impressão de div no IE
		
		this.impressaoSimplePanel.setVisible(true);
	}
	
	private void defineTamanhoMapa(Widget obj) {
		if (paisagem) {
			obj.setSize(LARGURA_MAPA, ALTURA_MAPA);
		} else {			
			obj.setSize(ALTURA_MAPA, LARGURA_MAPA);
		}
		this.impressaoSurdoFlexTable.setVisible(paisagem);
		this.impressaoSurdoFlexTableInvertida.setVisible(!paisagem);
	}
	
	private void adicionarMarcadorSurdo(int surdoNro, SurdoVO surdo, MapWidget mapa) {
		HasMarkerOptions markerOpt = new MarkerOptions();
		markerOpt.setClickable(false);
		markerOpt.setVisible(true);
		HasMarkerImage icon = null;
		if (!StringUtils.isEmpty(surdo.getSexo())) {
			if ("Masculino".equals(surdo.getSexo())) {
				icon = new MarkerImage.Builder("images/icone_homem_"+surdoNro+".png").build();
			} else if ("Feminino".equals(surdo.getSexo())) {
				icon = new MarkerImage.Builder("images/icone_mulher_"+surdoNro+".png").build();
			} 
		} else {
			icon = new MarkerImage.Builder("images/icone_branco_"+surdoNro+".png").build();
		}
		markerOpt.setIcon(icon);
		HasMarker marker = new Marker(markerOpt);
		marker.setPosition(new LatLng(surdo.getLatitude(), surdo.getLongitude()));
		marker.setMap(mapa.getMap());
	}
	
	private HasLatLng obterCentroMapa(List<SurdoVO> surdos) {
		Double latitude = 0d;
		Double longitude = 0d;
		for (SurdoVO surdo : surdos) {
			latitude += surdo.getLatitude();
			longitude += surdo.getLongitude();
		}
		
		return new LatLng(latitude/surdos.size(), longitude/surdos.size());
	}
	
	@UiHandler("impressaoVoltarButton")
	public void onImpressaovoltarButtonClick(ClickEvent event) {
		this.presenter.onVoltar();
	}
	

	@UiHandler("impressaoReverseImage")
	void onImpressaoReverseImageClick(ClickEvent event) {
		if (presenter != null) {
			this.presenter.abrirImpressao(identificadorMapaAtual, !paisagem);
		}
	}
}
