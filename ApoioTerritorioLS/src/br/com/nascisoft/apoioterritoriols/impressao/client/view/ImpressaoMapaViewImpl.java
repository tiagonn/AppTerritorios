package br.com.nascisoft.apoioterritoriols.impressao.client.view;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.AbrirMapaVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;
import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;
import br.com.nascisoft.apoioterritoriols.resources.client.Resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
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
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
	
	@UiField FlowPanel impressaoSimplePanel;
	
	@SuppressWarnings("unused")
	private Boolean paisagem;
	@SuppressWarnings("unused")
	private Boolean imprimirCabecalho;
	@SuppressWarnings("unused")
	private Boolean imprimirMapa;
	
	Map<Long, VerticalPanel> paineisImpressaoMapaVerticalPanel;
	Map<Long, SimplePanel> paineisImpressaoMapaSimplePanel;
	Map<Long, FlexTable> paineisImpressaoSurdoFlexTable;
	Map<Long, Label> labelsImpressaoLocalidadeLabel;
	Map<Long, Label> labelsImpressaoTerritorioLabel;
	
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
	public void abrirImpressaoMapa(List<AbrirMapaVO> mapas) {
		
		paineisImpressaoMapaVerticalPanel = new HashMap<Long, VerticalPanel>();
		paineisImpressaoMapaSimplePanel = new HashMap<Long, SimplePanel>();
		paineisImpressaoSurdoFlexTable = new HashMap<Long, FlexTable>();
		labelsImpressaoLocalidadeLabel = new HashMap<Long, Label>();
		labelsImpressaoTerritorioLabel = new HashMap<Long, Label>();
		
		FlowPanel impressaoRootPanel = new FlowPanel();
		impressaoRootPanel.setStyleName("impressao-root");
		impressaoSimplePanel.clear();
		impressaoSimplePanel.getElement().removeAllChildren();
		impressaoSimplePanel.add(impressaoRootPanel);
		impressaoSimplePanel.getElement().getStyle().setPosition(Position.RELATIVE);
		impressaoSimplePanel.getElement().getStyle().setFloat(Float.NONE);
		impressaoSimplePanel.getElement().getParentElement().getStyle().setPosition(Position.RELATIVE);
		impressaoSimplePanel.getElement().getParentElement().getStyle().setFloat(Float.NONE);
		impressaoSimplePanel.getElement().getParentElement().getParentElement().getStyle().setPosition(Position.RELATIVE);
		impressaoSimplePanel.getElement().getParentElement().getParentElement().getStyle().setFloat(Float.NONE);
		
		for (AbrirMapaVO vo : mapas) {
			
			GWT.log("Mapa sendo processado: " + vo.getSurdosImprimir().get(0).getMapa());

			VerticalPanel impressaoVerticalPanel = new VerticalPanel();
			impressaoVerticalPanel.setStyleName("impressao-panel-vertical");
			
			impressaoRootPanel.add(impressaoVerticalPanel);
			
			SimplePanel pageBreaker = new SimplePanel();
			pageBreaker.setStyleName("footer-break");
			
			impressaoRootPanel.add(pageBreaker);
			
			final Long idPrimeiraPessoa = vo.getSurdosImprimir().get(0).getId();
			
			VerticalPanel impressaoMapaVerticalPanel = new VerticalPanel();
			impressaoMapaVerticalPanel.setStyleName("impressao-panel-mapa");
			impressaoMapaVerticalPanel.getElement().getStyle().setBackgroundColor("#"+vo.getRegiao().getCorFundo());
			
			Label titulo = new Label("Cartão de Mapa de Território");
			titulo.setStyleName("impressao-cabecalho-titulo");			
			impressaoMapaVerticalPanel.add(titulo);
			
			HorizontalPanel subtituloHorizontalPanel = new HorizontalPanel();
			subtituloHorizontalPanel.setStyleName("impressao-cabecalho-subtitulo");
			impressaoMapaVerticalPanel.add(subtituloHorizontalPanel);
			
			Label localidade = new Label("Localidade: ");
			localidade.setStyleName("impressao-cabecalho-fixo");
			subtituloHorizontalPanel.add(localidade);
			
			Label impressaoLocalidadeLabel = new Label();
			impressaoLocalidadeLabel.setStyleName("impressao-cabecalho-texto");
			subtituloHorizontalPanel.add(impressaoLocalidadeLabel);
			impressaoLocalidadeLabel.getElement().getStyle().setColor("#"+vo.getRegiao().getCorLetra());
			
			labelsImpressaoLocalidadeLabel.put(idPrimeiraPessoa, impressaoLocalidadeLabel);
			
			Label numeroTerritorio = new Label("Terr. Nº ");
			numeroTerritorio.setStyleName("impressao-cabecalho-fixo");
			subtituloHorizontalPanel.add(numeroTerritorio);
			
			Label impressaoTerritorioLabel = new Label();
			impressaoTerritorioLabel.setStyleName("impressao-cabecalho-texto");
			subtituloHorizontalPanel.add(impressaoTerritorioLabel);
			impressaoTerritorioLabel.getElement().getStyle().setColor("#"+vo.getRegiao().getCorLetra());
			
			labelsImpressaoTerritorioLabel.put(idPrimeiraPessoa, impressaoTerritorioLabel);
			
			SimplePanel impressaoMapaSimplePanel = new SimplePanel();
			impressaoMapaSimplePanel.setStyleName("impressao-tabela");
			impressaoMapaVerticalPanel.add(impressaoMapaSimplePanel);
			
			paineisImpressaoMapaSimplePanel.put(idPrimeiraPessoa, impressaoMapaSimplePanel);
			
			Label instrucoes = new Label("Guarde este cartão no envelope. Tome cuidado para não o manchar, marcar ou dobrar. Cada vez que o território for coberto, queira informar disso o irmão que cuida do arquivo de territórios.");
			instrucoes.setStyleName("impressao-cabecalho-rodape");
			impressaoMapaVerticalPanel.add(instrucoes);
			
			impressaoVerticalPanel.add(impressaoMapaVerticalPanel);
			
			paineisImpressaoMapaVerticalPanel.put(idPrimeiraPessoa, impressaoMapaVerticalPanel);
			
			SimplePanel impressaoDadosSimplePanel = new SimplePanel();
			impressaoDadosSimplePanel.setStyleName("impressao-panel-dados");
			
			FlexTable impressaoSurdoFlexTable = new FlexTable();
			impressaoSurdoFlexTable.setStyleName("impressao-tabela");
			impressaoDadosSimplePanel.add(impressaoSurdoFlexTable);
			
			impressaoVerticalPanel.add(impressaoDadosSimplePanel);
			
			paineisImpressaoSurdoFlexTable.put(idPrimeiraPessoa, impressaoSurdoFlexTable);
			
			impressaoVerticalPanel.add(new HTML("<br/>"));
			
			CheckBox imprimirMapaCheckBox = new CheckBox("Imprimir Mapa");
			imprimirMapaCheckBox.setValue(true);
			imprimirMapaCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {				
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					GWT.log("Tentando obter painel de mapa " + idPrimeiraPessoa);
					GWT.log(""+paineisImpressaoMapaVerticalPanel.get(idPrimeiraPessoa));
					paineisImpressaoMapaVerticalPanel.get(idPrimeiraPessoa).setVisible(event.getValue());
				}
			});
			impressaoVerticalPanel.add(imprimirMapaCheckBox);
			
			
			CheckBox imprimirMapaSateliteCheckBox = new CheckBox("Satelite");
			imprimirMapaSateliteCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					((MapWidget)paineisImpressaoMapaSimplePanel.get(idPrimeiraPessoa).getWidget()).getMap().setMapTypeId(event.getValue()?new MapTypeId().getHybrid():new MapTypeId().getRoadmap());					
				}
			});
			impressaoVerticalPanel.add(imprimirMapaSateliteCheckBox);
						
			List<SurdoVO> surdos = vo.getSurdosImprimir();
			
			paineisImpressaoSurdoFlexTable.get(idPrimeiraPessoa).removeAllRows();
	
			int quantidadeMapa = vo.getCidade().getQuantidadeSurdosMapa();
			
			String classe = null;
			String classe1 = null;
			String classeTitulo = null;
			int zoom = 0;
			String altura = null;
			String largura = null;
			String larguraRelativa1 = null;
			String larguraRelativa2 = null;
			String cellspacing1 = null;
			String cellspacing2 = null;
			boolean mapaGrande = false;
			
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
				classeTitulo = " class=\"impressao-td-borda-inferior-pequena\"";
				zoom = 15;
				larguraRelativa1 = "\"70%\"";
				larguraRelativa2 = "\"30%\"";
				altura = ALTURA_MAPA;
				largura = LARGURA_MAPA;
				cellspacing1="\"1px\"";
				cellspacing2="\"0px\"";
				break;
				
			case 5:
				classe = " class=\"impressao-celula\"";
				classe1 = " class=\"impressao-celula-titulo\"";
				classeTitulo = " class=\"impressao-td-borda-inferior\"";
				zoom = 15;
				larguraRelativa1 = "\"60%\"";
				larguraRelativa2 = "\"40%\"";
				altura = ALTURA_MAPA_GRANDE;
				largura = LARGURA_MAPA_GRANDE;
				cellspacing1="\"5px\"";
				cellspacing2="\"1px\"";
				mapaGrande = true;
				break;
			
			case 6:
				zoom = 15;
				classe = " class=\"impressao-celula-pequena\"";
				classe1 = " class=\"impressao-celula-titulo\"";
				classeTitulo = " class=\"impressao-td-borda-inferior-pequena\"";
				altura = ALTURA_MAPA;
				largura = LARGURA_MAPA;
				
			case 10:
				zoom = 15;
				classe = " class=\"impressao-celula-pequena\"";
				classe1 = " class=\"impressao-celula-titulo\"";
				classeTitulo = " class=\"impressao-td-borda-inferior-pequena\"";
				altura = ALTURA_MAPA;
				largura = LARGURA_MAPA;
				
			default:
				break;
			}
			
			quantidadeMapa = surdos.size();
		
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
			
			paineisImpressaoMapaVerticalPanel.get(idPrimeiraPessoa).setWidth(largura);
			
			Date date = new Date();
			DateTimeFormat dtf = DateTimeFormat.getFormat("dd/MM/yyyy");
			
			for (int i = 0; i < surdos.size(); i++) {
				
				SurdoVO surdo = surdos.get(i);
				
				GWT.log("Processando pessoa " + surdo.getNome());
				
				labelsImpressaoLocalidadeLabel.get(idPrimeiraPessoa).setText(surdo.getRegiao() + " / " + surdo.getNomeCidade());
				labelsImpressaoTerritorioLabel.get(idPrimeiraPessoa).setText(surdo.getMapa().substring(5));
				
				
				
				adicionarMarcadorSurdo(
						i+1, 
						surdo, 
						mapa, 
						vo.getCidade().getQuantidadeSurdosMapa() == 1, 
						vo.getCidade().getQuantidadeSurdosMapa() == 4 || vo.getCidade().getQuantidadeSurdosMapa() == 5);
				StringBuilder html = new StringBuilder();
				
				if (quantidadeMapa == 1 
						&& vo.getCidade().getQuantidadeSurdosMapa() != 10 
						&& vo.getCidade().getQuantidadeSurdosMapa() != 6) {
				
					html.append("<table width=100% cellspacing=0 border=1>")
						.append("<tr><td width=100%>")
							.append("<table width=100% cellspacing=2 border=0>")
								.append("<tr>")
									.append("<td width=27px ").append(classe1).append(">Nome:</td>")
									.append("<td width=386px ").append(classe).append(">")
										.append(StringUtils.toCamelCase(surdo.getNome()))
										.append(" (").append(surdo.getMapa().substring(5)).append(")")
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
										.append(" Mapa impr. em: ").append(dtf.format(date).toString())
									.append("</td>")
								.append("</tr>")
							.append("</table>")
						.append("</td></tr>")
						.append("<tr><td>")
							.append("<table width=100% cellspacing=0 border=0 style=\"border-spacing:2px\">")
								.append("<tr>")
									.append("<td width=5%").append(classe1).append(">Data</td>")
									.append("<td width=8%").append(classe1).append(">Período</td>")
									.append("<td width=8%").append(classe1).append(">Encontrou?</td>")
									.append("<td width=8%").append(classe1).append(">Publicador</td>")
									.append("<td width=63%").append(classe1).append(">Resumo da conversa</td>")
								.append("</tr>")
								.append("<tr>")
								.append("<td class=\"impressao-td\">/</td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("</tr>")
								.append("<tr>")
								.append("<td class=\"impressao-td\">/</td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("</tr>")
								.append("<tr>")
								.append("<td class=\"impressao-td\">/</td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("</tr>")
								.append("<tr>")
								.append("<td class=\"impressao-td\">/</td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("</tr>")
								.append("<tr>")
								.append("<td class=\"impressao-td\">/</td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("</tr>")
								.append("<tr>")
								.append("<td class=\"impressao-td\">/</td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("</tr>")
								.append("<tr>")
								.append("<td class=\"impressao-td\">/</td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("</tr>")
								.append("<tr>")
								.append("<td class=\"impressao-td\">/</td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("</tr>")
								.append("<tr>")
								.append("<td class=\"impressao-td\">/</td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("</tr>")
								.append("<tr>")
								.append("<td class=\"impressao-td\">/</td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("<td class=\"impressao-td\"></td>")
								.append("</tr>");
					if (mapaGrande) {
						html.append("<tr>")
							.append("<td class=\"impressao-td\">/</td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td class=\"impressao-td\">/</td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td class=\"impressao-td\">/</td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td class=\"impressao-td\">/</td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td class=\"impressao-td\">/</td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td class=\"impressao-td\">/</td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("</tr>")
							.append("<tr>")
							.append("<td class=\"impressao-td\">/</td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("<td class=\"impressao-td\"></td>")
							.append("</tr>");						
					}
					
					html.append("</table>")
						.append("</td></tr>")
						.append("</table>");				
					
				} else {
										
					if (vo.getCidade().getQuantidadeSurdosMapa() == 6) {
						if (i == 0) {
							html.append("<div ").append(classeTitulo).append(">")
								.append("<div style=\"width:60%;display:inline-block\">")
								.append("<strong>").append(surdo.getMapa()).append("</strong> - Impresso em: ").append(dtf.format(date).toString())
								.append("</div>")
								.append("<div style=\"width:40%;display:inline-block\">Registro de Visitas*</div></div>");
						}
						html.append("<div ").append(classe).append(">")
								.append("<div style=\"width:60%;display: inline-block;vertical-align:middle;\">")
									.append("<p><strong>").append(i+1).append(" - ")
									.append(StringUtils.toCamelCase(surdo.getNome())).append("</strong> - ")
									.append(surdo.getEndereco()).append(" - ")
									.append(surdo.getDadosComplementoResumido()).append("</p>")
								.append("</div><div style=\"width:40%;display: inline-block;vertical-align:middle;\">")
									.append("<table width=\"100%\">")
										.append("<tr>")
											.append("<td style=\"width: 18%\" class=\"impressao-td-borda-inferior-pequena\">/</td>")
											.append("<td style=\"width: 15%\" class=\"impressao-td-borda-inferior-pequena\"></td>")
											.append("<td style=\"width: 18%\" class=\"impressao-td-borda-inferior-pequena\">/</td>")
											.append("<td style=\"width: 15%\" class=\"impressao-td-borda-inferior-pequena\"></td>")
											.append("<td style=\"width: 18%\" class=\"impressao-td-borda-inferior-pequena\">/</td>")
											.append("<td style=\"width: 15%\" class=\"impressao-td-borda-inferior-pequena\"></td>")
										.append("</tr>")
										.append("<tr>")
											.append("<td style=\"width: 18%\" class=\"impressao-td-borda-inferior-pequena\">/</td>")
											.append("<td style=\"width: 15%\" class=\"impressao-td-borda-inferior-pequena\"></td>")
											.append("<td style=\"width: 18%\" class=\"impressao-td-borda-inferior-pequena\">/</td>")
											.append("<td style=\"width: 15%\" class=\"impressao-td-borda-inferior-pequena\"></td>")
											.append("<td style=\"width: 18%\" class=\"impressao-td-borda-inferior-pequena\">/</td>")
											.append("<td style=\"width: 15%\" class=\"impressao-td-borda-inferior-pequena\"></td>")
										.append("</tr>")
										.append("<tr>")
											.append("<td style=\"width: 18%\" class=\"impressao-td-borda-inferior-pequena\">/</td>")
											.append("<td style=\"width: 15%\" class=\"impressao-td-borda-inferior-pequena\"></td>")
											.append("<td style=\"width: 18%\" class=\"impressao-td-borda-inferior-pequena\">/</td>")
											.append("<td style=\"width: 15%\" class=\"impressao-td-borda-inferior-pequena\"></td>")
											.append("<td style=\"width: 18%\" class=\"impressao-td-borda-inferior-pequena\">/</td>")
											.append("<td style=\"width: 15%\" class=\"impressao-td-borda-inferior-pequena\"></td>")
										.append("</tr>")
									.append("</table>")
								.append("</div>")
							.append("</div>");
						if (i==surdos.size()-1) {
							html.append("<div style=\"border-top: black; border-top-width: thin; border-top-style: solid; font-family: Tahoma; font-size:xx-small; text-align: center;\">")
								.append("* Por favor, preencha o registro da visita com a data e se a pessoa foi encontrada ou não. Por exemplo: 10/09 - Sim ou 21/02 - Não</div>");
						}
					} 
					else if (vo.getCidade().getQuantidadeSurdosMapa() == 10) {
						if (i == 0) {
							html.append("<div ").append(classeTitulo).append(">")
								.append("<strong>").append(surdo.getMapa()).append("</strong> - Impresso em: ").append(dtf.format(date).toString())
								.append("</div>");
						}
						html.append("<div style=\"display: inline-block;\"").append(classe).append("><strong>").append(i+1).append(" - ")
							.append(StringUtils.toCamelCase(surdo.getNome())).append("</strong> - ")
							.append(surdo.getEndereco()).append("</p>");
					} else {
	
						if (i == 0) {
							html.append("<table width=100% cellspacing=0px cellpadding=0 border=0>")
								.append("<tr><td ").append(classeTitulo).append(">")
								.append("<strong>").append(surdo.getMapa()).append("</strong> - Impresso em: ").append(dtf.format(date).toString())
								.append("</td></tr>");
						} 

						html.append("<table width=100% cellspacing=3px cellpadding=0 border=0>");
						html.append("<tr>")
								.append("<td width=").append(larguraRelativa1).append(">")
									.append("<table width=100% cellspacing=").append(cellspacing1).append(" cellpadding=0 border=0>")
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
								.append("<td width=").append(larguraRelativa2).append(">")
									.append("<table width=100% cellspacing=").append(cellspacing2).append(" cellpadding=0 border=0>")
										.append("<tr class=\"impressao-tr-pequeno\">")
											.append("<td").append(classe1).append(">Data</td>")
											.append("<td").append(classe1).append(">Detalhes da conversa</td>")
										.append("</tr>")
										.append("<tr class=\"impressao-tr-pequeno\">")
											.append("<td class=\"impressao-td\">/</td>")
											.append("<td class=\"impressao-td\"></td>")
										.append("</tr>")
										.append("<tr class=\"impressao-tr-pequeno\">")
											.append("<td class=\"impressao-td\">/</td>")
											.append("<td class=\"impressao-td\"></td>")
										.append("</tr>")
										.append("<tr class=\"impressao-tr-pequeno\">")
											.append("<td class=\"impressao-td\">/</td>")
											.append("<td class=\"impressao-td\"></td>")
										.append("</tr>")
										.append("<tr class=\"impressao-tr-pequeno\">")
											.append("<td class=\"impressao-td\">/</td>")
											.append("<td class=\"impressao-td\"></td>")
										.append("</tr>");
						if (quantidadeMapa == 5) {
							html.append("<tr class=\"impressao-tr-pequeno\">")
									.append("<td class=\"impressao-td\">/</td>")
									.append("<td class=\"impressao-td\"></td>")
								.append("</tr>");
						}
						html.append("</table>").append("</td>").append("</tr>").append("</table>");	
					}
				}
				
				paineisImpressaoSurdoFlexTable.get(idPrimeiraPessoa).setHTML(i+1, 0, html.toString());
				paineisImpressaoSurdoFlexTable.get(idPrimeiraPessoa).setBorderWidth(
						quantidadeMapa == 1 
						&& vo.getCidade().getQuantidadeSurdosMapa() != 10 
						&& vo.getCidade().getQuantidadeSurdosMapa() != 6 
							? 0 : 1);
				paineisImpressaoSurdoFlexTable.get(idPrimeiraPessoa).setCellSpacing(0);
				paineisImpressaoSurdoFlexTable.get(idPrimeiraPessoa).setWidth(largura);
			}
			
			paineisImpressaoMapaSimplePanel.get(idPrimeiraPessoa).clear();
			paineisImpressaoMapaSimplePanel.get(idPrimeiraPessoa).setSize(largura, altura);
			paineisImpressaoMapaSimplePanel.get(idPrimeiraPessoa).add(mapa);
		}
	}
	
	private void adicionarMarcadorSurdo(int surdoNro, SurdoVO surdo, MapWidget mapa, boolean mapaIndividual, boolean usarIconeHomemMulher) {
		HasMarkerOptions markerOpt = new MarkerOptions();
		markerOpt.setClickable(false);
		markerOpt.setVisible(true);
		if (!mapaIndividual) {
			HasMarkerImage icon = null;
			if (usarIconeHomemMulher) {
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
			} else {
				switch (surdoNro) {
				case 1:
					icon = new MarkerImage.Builder(Resources.INSTANCE.iconeBranco1().getSafeUri().asString()).build();
					break;
				case 2:
					icon = new MarkerImage.Builder(Resources.INSTANCE.iconeBranco2().getSafeUri().asString()).build();
					break;
				case 3:
					icon = new MarkerImage.Builder(Resources.INSTANCE.iconeBranco3().getSafeUri().asString()).build();
					break;
				case 4:
					icon = new MarkerImage.Builder(Resources.INSTANCE.iconeBranco4().getSafeUri().asString()).build();
					break;
				case 5:
					icon = new MarkerImage.Builder(Resources.INSTANCE.iconeBranco5().getSafeUri().asString()).build();
					break;
				case 6:
					icon = new MarkerImage.Builder(Resources.INSTANCE.iconeBranco6().getSafeUri().asString()).build();
					break;
				case 7:
					icon = new MarkerImage.Builder(Resources.INSTANCE.iconeBranco7().getSafeUri().asString()).build();
					break;
				case 8:
					icon = new MarkerImage.Builder(Resources.INSTANCE.iconeBranco8().getSafeUri().asString()).build();
					break;
				case 9:
					icon = new MarkerImage.Builder(Resources.INSTANCE.iconeBranco9().getSafeUri().asString()).build();
					break;
				case 10:
					icon = new MarkerImage.Builder(Resources.INSTANCE.iconeBranco10().getSafeUri().asString()).build();
					break;

				default:
					break;
				}
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
}
