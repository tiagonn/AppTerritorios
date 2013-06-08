package br.com.nascisoft.apoioterritoriols.impressao.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.AbrirMapaVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;
import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ImpressaoMapaViewImpl extends Composite implements ImpressaoMapaView {

	private static ImpressaoViewImplUiBinder uiBinder = GWT
			.create(ImpressaoViewImplUiBinder.class);

	@UiTemplate("ImpressaoViewUiBinder.ui.xml")
	interface ImpressaoViewImplUiBinder extends
			UiBinder<Widget, ImpressaoMapaViewImpl> {
	}
	
	@UiField SimplePanel impressaoMapaSimplePanel;
	@UiField FlexTable impressaoSurdoFlexTable;
	@UiField Label impressaoLocalidadeLabel;
	@UiField Label impressaoTerritorioLabel;
	@UiField VerticalPanel impressaoMapaVerticalPanel;
	@UiField CheckBox imprimirMapaCheckBox;
	
	
	@SuppressWarnings("unused")
	private Boolean paisagem;
	@SuppressWarnings("unused")
	private Boolean imprimirCabecalho;
	@SuppressWarnings("unused")
	private Boolean imprimirMapa;
	
	private final static String ALTURA_MAPA = "280px";
	private final static String LARGURA_MAPA = "540px";

	private final static String ALTURA_MAPA_GRANDE = "455px";
	private final static String LARGURA_MAPA_GRANDE = "745px";


	public ImpressaoMapaViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setConfiguracoesImpressao(Boolean paisagem,
			Boolean imprimirCabecalho, Boolean imprimirMapa) {
		this.paisagem = paisagem;
		this.imprimirCabecalho = imprimirCabecalho;
		this.imprimirMapa = imprimirMapa;

	}

	@Override
	public void abrirImpressaoMapa(AbrirMapaVO vo) {
		
		List<SurdoVO> surdos = vo.getSurdosImprimir();
		
		this.impressaoSurdoFlexTable.removeAllRows();

		int quantidadeMapa = vo.getCidade().getQuantidadeSurdosMapa();
		
		String classe = null;
		String classe1 = null;
		int zoom = 0;
		String altura = null;
		String largura = null;
		String cellspacing = null;
		String resumo = null;
		
		switch (quantidadeMapa) {
		case 1:
			classe = " class=\"impressao-celula\"";
			classe1 = " class=\"impressao-celula-titulo\"";
			zoom = 16;
			altura = ALTURA_MAPA;
			largura = LARGURA_MAPA;
			break;

		case 4:
			classe = " class=\"impressao-celula-pequena\"";
			classe1 = " class=\"impressao-celula-titulo-pequena\"";
			zoom = 15;
			altura = ALTURA_MAPA;
			largura = LARGURA_MAPA;
			cellspacing="1px";
			resumo = "________________________";
			break;
			
		case 5:
			classe = " class=\"impressao-celula\"";
			classe1 = " class=\"impressao-celula-titulo\"";
			zoom = 15;
			altura = ALTURA_MAPA_GRANDE;
			largura = LARGURA_MAPA_GRANDE;
			cellspacing="5px";
			resumo = "_________________________________";
			break;
			
		default:
			break;
		}
	
		HasMapOptions opt = new MapOptions();
		opt.setZoom(zoom);
		opt.setMapTypeId(new MapTypeId().getRoadmap());
		opt.setDraggable(true);
		opt.setNavigationControl(false);
		opt.setScrollwheel(true);
		opt.setDisableDefaultUI(true);
		opt.setCenter(this.obterCentroMapa(surdos));
		MapWidget mapa = new MapWidget(opt);
		mapa.setSize(largura, altura);
		
		this.impressaoMapaVerticalPanel.setWidth(largura);
		
		for (int i = 0; i < surdos.size(); i++) {
			
			SurdoVO surdo = surdos.get(i);
			
			this.impressaoLocalidadeLabel.setText(surdo.getRegiao() + " / " + surdo.getNomeCidade());
			this.impressaoTerritorioLabel.setText(surdo.getMapa().substring(5));
			
			
			
			adicionarMarcadorSurdo(i+1, surdo, mapa, quantidadeMapa == 1);
			StringBuilder html = new StringBuilder();
			
			if (quantidadeMapa == 1) {
			
				html.append("<table width=100% cellspacing=0 border=1>")
					.append("<tr><td width=100%>")
						.append("<table width=100% cellspacing=2 border=0>")
							.append("<tr>")
								.append("<td width=27px ").append(classe1).append(">Nome:</td>")
								.append("<td width=386px ").append(classe).append(">")
									.append(StringUtils.toCamelCase(surdo.getNome()))
								.append("</td>")
								.append("<td width=27px ").append(classe1).append(">Tel:</td>")
								.append("<td width=100px ").append(classe).append(">")
									.append(surdo.getTelefone())
								.append("</td>")
							.append("</tr>")
							.append("<tr>")
								.append("<td ").append(classe1).append(">End:</td>")
								.append("<td ").append(classe).append(">").append(surdo.getEndereco()).append("</td>")
								.append("<td ").append(classe1).append(">Instr:</td>")
								.append("<td ").append(classe).append(">")
								.append(surdo.getInstrutor())
								.append("</td>")
							.append("</tr>")
							.append("<tr>")
								.append("<td colspan=4 ").append(classe)
									.append("><strong>Observações: </strong>")
									.append(surdo.getObservacaoConsolidada())
								.append("</td>")
							.append("</tr>")
						.append("</table>")
					.append("</td></tr>")
					.append("<tr><td>")
						.append("<table width=100% cellspacing=0 border=0>")
							.append("<tr>")
								.append("<td").append(classe1).append(">Data</td>")
								.append("<td").append(classe1).append(">Período</td>")
								.append("<td").append(classe1).append(">Encontrou?</td>")
								.append("<td").append(classe1).append(">Publicador</td>")
								.append("<td").append(classe1).append(">Resumo da conversa</td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td>__/__</td>")
							.append("<td>______</td>")
							.append("<td>________</td>")
							.append("<td>________</td>")
							.append("<td>_______________________________________</td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td>__/__</td>")
							.append("<td>______</td>")
							.append("<td>________</td>")
							.append("<td>________</td>")
							.append("<td>_______________________________________</td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td>__/__</td>")
							.append("<td>______</td>")
							.append("<td>________</td>")
							.append("<td>________</td>")
							.append("<td>_______________________________________</td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td>__/__</td>")
							.append("<td>______</td>")
							.append("<td>________</td>")
							.append("<td>________</td>")
							.append("<td>_______________________________________</td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td>__/__</td>")
							.append("<td>______</td>")
							.append("<td>________</td>")
							.append("<td>________</td>")
							.append("<td>_______________________________________</td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td>__/__</td>")
							.append("<td>______</td>")
							.append("<td>________</td>")
							.append("<td>________</td>")
							.append("<td>_______________________________________</td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td>__/__</td>")
							.append("<td>______</td>")
							.append("<td>________</td>")
							.append("<td>________</td>")
							.append("<td>_______________________________________</td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td>__/__</td>")
							.append("<td>______</td>")
							.append("<td>________</td>")
							.append("<td>________</td>")
							.append("<td>_______________________________________</td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td>__/__</td>")
							.append("<td>______</td>")
							.append("<td>________</td>")
							.append("<td>________</td>")
							.append("<td>_______________________________________</td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td>__/__</td>")
							.append("<td>______</td>")
							.append("<td>________</td>")
							.append("<td>________</td>")
							.append("<td>_______________________________________</td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td>__/__</td>")
							.append("<td>______</td>")
							.append("<td>________</td>")
							.append("<td>________</td>")
							.append("<td>_______________________________________</td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td>__/__</td>")
							.append("<td>______</td>")
							.append("<td>________</td>")
							.append("<td>________</td>")
							.append("<td>_______________________________________</td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td>__/__</td>")
							.append("<td>______</td>")
							.append("<td>________</td>")
							.append("<td>________</td>")
							.append("<td>_______________________________________</td>")
							.append("</tr>")
						.append("</table>")
					.append("</td></tr>")
					.append("</table>");				
				
			} else {
				
				html.append("<table width=100% cellspacing=3px cellpadding=0 border=0>")
					.append("<tr>")
						.append("<td width=70%>")
							.append("<table width=100% cellspacing=").append(cellspacing).append(" cellpadding=0 border=0>")
								.append("<tr>")
									.append("<td").append(classe).append("><strong>Nome:</strong> ").append(StringUtils.toCamelCase(surdo.getNome())).append("</td>")
								.append("</tr>")
								.append("<tr>")
									.append("<td").append(classe).append("><strong>End:</strong> ").append(surdo.getEndereco()).append("</td>")
								.append("</tr>")
								.append("<tr>")
									.append("<td").append(classe).append("><strong>Obs:</strong> ").append(surdo.getObservacaoConsolidadaResumida()).append("</td>")
								.append("</tr>")
							.append("</table>")
						.append("</td>")
						.append("<td width=30%>")
							.append("<table width=100% cellspacing=0 cellpadding=0 border=0>")
								.append("<tr>")
									.append("<td").append(classe1).append(">Data</td>")
									.append("<td").append(classe1).append(">Detalhes da conversa</td>")
								.append("</tr>")
								.append("<tr>")
									.append("<td ").append(classe).append(">__/__</td>")
									.append("<td ").append(classe).append(">").append(resumo).append("</td>")
								.append("</tr>")
								.append("<tr>")
									.append("<td").append(classe).append(">__/__</td>")
									.append("<td").append(classe).append(">").append(resumo).append("</td>")
								.append("</tr>")
								.append("<tr>")
									.append("<td").append(classe).append(">__/__</td>")
									.append("<td").append(classe).append(">").append(resumo).append("</td>")
								.append("</tr>")
								.append("<tr>")
									.append("<td").append(classe).append(">__/__</td>")
									.append("<td").append(classe).append(">").append(resumo).append("</td>")
								.append("</tr>")
								.append("<tr>")
									.append("<td").append(classe).append(">__/__</td>")
									.append("<td").append(classe).append(">").append(resumo).append("</td>")
								.append("</tr>");
				if (quantidadeMapa == 5) {
							html.append("<tr>")
									.append("<td").append(classe).append(">__/__</td>")
									.append("<td").append(classe).append(">").append(resumo).append("</td>")
								.append("</tr>");
				}
						html.append("</table>")
						.append("</td>")
					.append("</tr>")
					.append("</table>");		
			}
			
			this.impressaoSurdoFlexTable.setHTML(i+1, 0, html.toString());
			this.impressaoSurdoFlexTable.setBorderWidth(quantidadeMapa == 1 ? 0 : 1);
			this.impressaoSurdoFlexTable.setCellSpacing(0);
			this.impressaoSurdoFlexTable.setWidth(largura);
		}
		
		this.impressaoMapaSimplePanel.clear();
		this.impressaoMapaSimplePanel.setSize(largura, altura);
		this.impressaoMapaSimplePanel.add(mapa);
	}
	
	private void adicionarMarcadorSurdo(int surdoNro, SurdoVO surdo, MapWidget mapa, boolean mapaIndividual) {
		HasMarkerOptions markerOpt = new MarkerOptions();
		markerOpt.setClickable(false);
		markerOpt.setVisible(true);
		if (!mapaIndividual) {
			HasMarkerImage icon = null;
			if (!StringUtils.isEmpty(surdo.getSexo())) {
				if ("Homem".equals(surdo.getSexo())) {
					icon = new MarkerImage.Builder("images/icone_homem_"+surdoNro+".png").build();
				} else if ("Mulher".equals(surdo.getSexo())) {
					icon = new MarkerImage.Builder("images/icone_mulher_"+surdoNro+".png").build();
				} else {
					icon = new MarkerImage.Builder("images/icone_branco_"+surdoNro+".png").build();
				}
			} else {
				icon = new MarkerImage.Builder("images/icone_branco_"+surdoNro+".png").build();
			}
			markerOpt.setIcon(icon);
		}
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
	
	
	@UiHandler("imprimirMapaCheckBox")
	void onImprimirMapaCheckBoxValueChange(ValueChangeEvent<Boolean> event) {
		this.impressaoMapaVerticalPanel.setVisible(event.getValue());
	}

	@UiHandler("imprimirMapaSateliteCheckBox")
	void onImprimirMapaSateliteCheckBoxValueChange(ValueChangeEvent<Boolean> event) {
		if (event.getValue()) {
			((MapWidget)this.impressaoMapaSimplePanel.getWidget()).getMap().setMapTypeId(new MapTypeId().getHybrid());
		} else {
			((MapWidget)this.impressaoMapaSimplePanel.getWidget()).getMap().setMapTypeId(new MapTypeId().getRoadmap());
		}
	}
}
