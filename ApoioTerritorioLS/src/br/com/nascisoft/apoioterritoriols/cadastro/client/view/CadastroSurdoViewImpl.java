package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.cadastro.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoDetailsVO;
import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;
import br.com.nascisoft.apoioterritoriols.login.util.Validacoes;
import br.com.nascisoft.apoioterritoriols.cadastro.xml.Regiao;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.maps.client.HasMapOptions;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.HasLatLng;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.overlay.HasMarker;
import com.google.gwt.maps.client.overlay.HasMarkerOptions;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.googlecode.objectify.Key;

public class CadastroSurdoViewImpl extends Composite implements CadastroSurdoView {

	private static CadastroSurdoViewUiBinderUiBinder uiBinder = GWT
			.create(CadastroSurdoViewUiBinderUiBinder.class);
	@UiField TabLayoutPanel cadastroSurdoTabLayoutPanel;
	@UiField TextBox pesquisaNomeTextBox;
	@UiField ListBox pesquisaRegiaoListBox;
	@UiField ListBox pesquisaMapaListBox;
	@UiField Button pesquisaPesquisarButton;
	@UiField CellTable<SurdoDetailsVO> pesquisaResultadoCellTable;
	@UiField Label pesquisaResultadoLabel;
	@UiField HTML manterWarningHTML;
	@UiField Grid manterSurdoGrid;
	@UiField TextBox manterNomeTextBox;
	@UiField TextBox manterLogradouroTextBox;
	@UiField TextBox manterNumeroTextBox;
	@UiField TextBox manterComplementoTextBox;
	@UiField ListBox manterRegiaoListBox;
	@UiField(provided=true) SuggestBox manterBairroSuggestBox;
	@UiField TextBox manterCEPTextBox;
	@UiField TextArea manterObservacaoTextArea;
	@UiField TextBox manterTelefoneTextBox;
	@UiField ListBox manterLibrasListBox;
	@UiField ListBox manterCriancaListBox;
	@UiField ListBox manterDVDListBox;
	@UiField TextBox manterInstrutorTextBox;
	@UiField TextBox manterIdadeIntegerBox;
	@UiField ListBox manterSexoListBox;
	@UiField TextBox manterHorarioTextBox;
	@UiField TextBox manterMelhorDiaTextBox;
	@UiField TextBox manterOnibusTextBox;
	@UiField TextBox manterMSNTextBox;
	@UiField InlineHyperlink manterAdicionarSurdoInlineHyperlink;
	@UiField Button manterSalvarButton;
	@UiField SimplePager pesquisaResultadoSimplePager;
	@UiField PopupPanel manterMapaPopupPanel;
	@UiField LayoutPanel manterMapaLayoutPanel;
	@UiField HTML manterWarningEnderecoHTML;
	@UiField Button manterMapaConfirmarEnderecoButton;
	@UiField Button manterMapaVoltarEnderecoButton;
	@UiField PopupPanel waitingPopUpPanel;
	@UiField CheckBox pesquisaEstaAssociadoMapaCheckBox;
	
	MultiWordSuggestOracle bairroOracle;
	
	Long manterId;
	Key<Mapa> manterMapa;
	Double manterLongitude;
	Double manterLatitude;
	HasMarker marker;
	boolean buscaEndereco = true;
	
	//TODO: Parametrizar todos os dados fixos
	private static final HasLatLng POSICAO_INICIAL = new LatLng(-22.878419,-47.070356);// endereço do salão do reino

	@UiTemplate("CadastroViewUiBinder.ui.xml")
	interface CadastroSurdoViewUiBinderUiBinder extends
			UiBinder<Widget, CadastroSurdoViewImpl> {
	}
	
	private Presenter presenter;
	
	private ListDataProvider<SurdoDetailsVO> resultadoPesquisa;

	public CadastroSurdoViewImpl() {
		this.bairroOracle = new MultiWordSuggestOracle();
		this.manterBairroSuggestBox = new SuggestBox(this.bairroOracle);
		initWidget(uiBinder.createAndBindUi(this));
		this.iniciarSNListBox(this.manterLibrasListBox);
		this.iniciarSNListBox(this.manterCriancaListBox);
		this.iniciarSNListBox(this.manterDVDListBox);
		this.iniciarSexoListBox(this.manterSexoListBox); 
		this.resultadoPesquisa = new ListDataProvider<SurdoDetailsVO>();
		this.resultadoPesquisa.addDataDisplay(this.pesquisaResultadoCellTable);
		this.pesquisaResultadoSimplePager.setDisplay(this.pesquisaResultadoCellTable);
	}
	
	public void initView() {
		this.selectThisTab();
		this.pesquisaNomeTextBox.setText("");
		this.pesquisaRegiaoListBox.setSelectedIndex(0);
		this.pesquisaMapaListBox.setSelectedIndex(0);
		this.pesquisaEstaAssociadoMapaCheckBox.setValue(Boolean.FALSE);
		this.manterSurdoGrid.setVisible(false);
		this.manterWarningHTML.setHTML("");
		this.manterMapaPopupPanel.hide();
		this.limparResultadoPesquisa();
		this.limparManter();
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
	public void selectThisTab() {
		this.cadastroSurdoTabLayoutPanel.selectTab(0, false);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		
	}
	
	@Override
	public void setRegiaoList(List<Regiao> regioes) {
		this.pesquisaRegiaoListBox.clear();
		this.pesquisaRegiaoListBox.addItem("-- Escolha uma região --", "");

		this.manterRegiaoListBox.clear();
		this.manterRegiaoListBox.addItem("-- Escolha uma região --", "");
		
		this.pesquisaMapaListBox.clear();
		this.pesquisaMapaListBox.addItem("-- Escolha um mapa --", "");
		this.pesquisaMapaListBox.setEnabled(false);
		
		for (Regiao regiao : regioes) {
			this.pesquisaRegiaoListBox.addItem(regiao.getLetra() + " - " + regiao.getNome(), regiao.getNome());
			this.manterRegiaoListBox.addItem(regiao.getLetra() + " - " + regiao.getNome(), regiao.getNome());
		}	
	}
	
	@Override
	public void setMapaList(List<Mapa> mapas) {
		this.pesquisaMapaListBox.clear();
		this.pesquisaMapaListBox.addItem("-- Escolha um mapa --", "");
		for (Mapa mapa : mapas) {
			this.pesquisaMapaListBox.addItem(mapa.getNome(), mapa.getId().toString());
		}
	}

	@Override
	public void setBairroList(List<String> bairros) {
		for (String bairro : bairros) {
			this.bairroOracle.add(bairro);
		}
	}

	
	@UiHandler("pesquisaPesquisarButton")
	void onPesquisaPesquisarButtonClick(ClickEvent event) {
		if (this.presenter != null) {			
			this.presenter.onPesquisaPesquisarButtonClick(
					this.pesquisaNomeTextBox.getText(), 
					this.pesquisaRegiaoListBox.getValue(this.pesquisaRegiaoListBox.getSelectedIndex()),
					this.pesquisaMapaListBox.getValue(
							this.pesquisaMapaListBox.getSelectedIndex()), 
					!this.pesquisaEstaAssociadoMapaCheckBox.getValue());
		}
		this.manterSurdoGrid.setVisible(false);
	}
	
	@UiHandler("pesquisaRegiaoListBox")
	void onPesquisaRegiaoListBoxChange(ChangeEvent event) {
		if (this.presenter != null) {
			if (!this.pesquisaRegiaoListBox.getValue(this.pesquisaRegiaoListBox.getSelectedIndex()).isEmpty()
					&& !this.pesquisaEstaAssociadoMapaCheckBox.getValue()) {
				this.pesquisaMapaListBox.setEnabled(true);
				this.presenter.onPesquisaRegiaoListBoxChange(pesquisaRegiaoListBox.getValue(pesquisaRegiaoListBox.getSelectedIndex()));
			} else {
				this.pesquisaMapaListBox.setEnabled(false);
				this.pesquisaMapaListBox.setSelectedIndex(0);
			}
		}
	}	
	
	@UiHandler("pesquisaEstaAssociadoMapaCheckBox")
	void onPesquisaEstaAssociadoMapaCheckBoxValueChange(ValueChangeEvent<Boolean> event) {
		if (this.pesquisaEstaAssociadoMapaCheckBox.getValue()) {
			this.pesquisaMapaListBox.setEnabled(false);
			this.pesquisaMapaListBox.setSelectedIndex(0);
		} else if (!this.pesquisaRegiaoListBox.getValue(this.pesquisaRegiaoListBox.getSelectedIndex()).isEmpty()) {
			this.pesquisaMapaListBox.setEnabled(true);
			this.presenter.onPesquisaRegiaoListBoxChange(pesquisaRegiaoListBox.getValue(pesquisaRegiaoListBox.getSelectedIndex()));
		}
	}
		
	@Override
	public void setResultadoPesquisa(List<SurdoDetailsVO> resultadoPesquisa) {
		this.limparResultadoPesquisa();
		this.pesquisaResultadoCellTable.setRowCount(resultadoPesquisa.size());
		this.resultadoPesquisa.setList(resultadoPesquisa);
		this.mostrarResultadoPesquisa();
	}
	
	private void mostrarResultadoPesquisa() {
		if (this.pesquisaResultadoCellTable.getRowCount() == 0) {
			this.pesquisaResultadoLabel.setText("Não foram encontrados resultados para a pesquisa informada");
			this.pesquisaResultadoSimplePager.setVisible(false);
		} else {
			this.pesquisaResultadoLabel.setText("Foram encontrados " + this.pesquisaResultadoCellTable.getRowCount() + " resultados.");
			this.pesquisaResultadoCellTable.setStyleName("surdo-tabela");
			this.pesquisaResultadoCellTable.setVisible(true);
			
			TextColumn<SurdoDetailsVO> nomeColumn = new TextColumn<SurdoDetailsVO>() {
				@Override
				public String getValue(SurdoDetailsVO object) {
					return StringUtils.toCamelCase(object.getNome());
				}
			};
			TextColumn<SurdoDetailsVO> regiaoColumn = new TextColumn<SurdoDetailsVO>() {
				@Override
				public String getValue(SurdoDetailsVO object) {
					return object.getRegiao();
				}				
			};
			TextColumn<SurdoDetailsVO> mapaColumn = new TextColumn<SurdoDetailsVO>() {
				@Override
				public String getValue(SurdoDetailsVO object) {
					return object.getMapa();
				}
			};
			
			Delegate<Long> delegate = new Delegate<Long>() {
				@Override
				public void execute(Long object) {
					presenter.onEditarButtonClick(object);
				}
			};
			ActionCell<Long> actionCell = new ActionCell<Long>("Editar", delegate);
			Column<SurdoDetailsVO, Long> editColumn = new Column<SurdoDetailsVO, Long>(actionCell) {
				@Override
				public Long getValue(SurdoDetailsVO object) {
					return object.getId();
				}
			};
			
			Delegate<Long> deletarDelegate = new Delegate<Long>() {
				@Override
				public void execute(Long object) {
					if (Window.confirm("Deseja realmente apagar este surdo?")) {
						presenter.onApagar(object);
					}
				}				
			};
			ActionCell<Long> deletarCell = new ActionCell<Long>("Apagar", deletarDelegate);
			Column<SurdoDetailsVO, Long> deletarColumn = new Column<SurdoDetailsVO, Long>(deletarCell) {
				@Override
				public Long getValue(SurdoDetailsVO object) {
					return object.getId();
				}
			};

			this.pesquisaResultadoCellTable.addColumn(nomeColumn, "Nome");
			this.pesquisaResultadoCellTable.addColumn(regiaoColumn, "Região");
			this.pesquisaResultadoCellTable.addColumn(mapaColumn, "Mapa");
			this.pesquisaResultadoCellTable.addColumn(editColumn, "");
			this.pesquisaResultadoCellTable.addColumn(deletarColumn, "");
			
			this.pesquisaResultadoSimplePager.setVisible(true);
		}
		this.manterWarningHTML.setHTML("");
		this.manterSurdoGrid.setVisible(false);
	}
	
	@UiHandler("manterSalvarButton")
	void onManterSalvarButtonClick(ClickEvent event) {
		Validacoes validacoes = validaManterSurdo();
		if (validacoes.size() > 0) {
			this.manterWarningHTML.setHTML(validacoes.obterValidacoesSumarizadaHTML());
		} else {
			if (this.buscaEndereco) {
				this.presenter.buscarEndereco(
						this.manterLogradouroTextBox.getValue(), 
						this.manterNumeroTextBox.getValue(),
						this.manterBairroSuggestBox.getValue(),
						this.manterCEPTextBox.getValue());
			} else {
				HasLatLng position = new LatLng(this.manterLatitude, this.manterLongitude);
				this.setPosition(position);
			}
		}		
	}
	
	public void setPosition(HasLatLng position) {
		
		int zoom = 17;
		this.manterWarningEnderecoHTML.setVisible(false);
		if (position == null) {
			position = POSICAO_INICIAL;
			this.manterWarningEnderecoHTML.setVisible(true);
			zoom = 12;
		} 
		this.manterLatitude = position.getLatitude();
		this.manterLongitude = position.getLongitude();
		HasMapOptions opt = new MapOptions();
		opt.setZoom(zoom);
		opt.setCenter(new LatLng(this.manterLatitude,this.manterLongitude));
		opt.setMapTypeId(new MapTypeId().getRoadmap());
		opt.setDraggable(true);
		opt.setNavigationControl(true);
		opt.setScrollwheel(true);
		MapWidget mapa = new MapWidget(opt);
		mapa.setSize("635px", "480px");
		HasMarkerOptions markerOpt = new MarkerOptions();
		markerOpt.setClickable(true);
		markerOpt.setDraggable(true);
		markerOpt.setVisible(true);
		this.marker = new Marker(markerOpt);
		this.marker.setPosition(position);
		this.marker.setMap(mapa.getMap());
		manterMapaLayoutPanel.clear();
		manterMapaLayoutPanel.setSize("635px", "480px");
		manterMapaLayoutPanel.add(mapa);		
		manterMapaPopupPanel.setPopupPosition(this.pesquisaNomeTextBox.getAbsoluteLeft()+50,20);
		manterMapaPopupPanel.setVisible(true);
		manterMapaPopupPanel.show();
	}
	
	@UiHandler("manterMapaConfirmarEnderecoButton")
	void onManterMapaConfirmarEnderecoButtonClick(ClickEvent event) {
		this.manterLatitude = marker.getPosition().getLatitude();
		this.manterLongitude = marker.getPosition().getLongitude();
		Surdo surdo = populaSurdo();
		this.presenter.adicionarOuAlterarSurdo(surdo);
	}
	
	@UiHandler("manterMapaVoltarEnderecoButton")
	void onManterMapaVoltarEnderecoButtonClick(ClickEvent event) {
		manterMapaPopupPanel.hide();
	}
	
	@UiHandler(value={"manterLogradouroTextBox", "manterNumeroTextBox", "manterCEPTextBox"})
	void onEnderecoChange(ChangeEvent event) {
		this.buscaEndereco = true;
	}
	
	@UiHandler("manterBairroSuggestBox")
	void onManterBairroSuggestBoxKeyPress(KeyPressEvent event) {
		this.buscaEndereco = true;
	}

	@Override
	public void onEditar(Surdo surdo) {
		this.manterSurdoGrid.setVisible(true);
		this.limparResultadoPesquisa();
		this.populaManter(surdo);
	}
	
	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.cadastroSurdoTabLayoutPanel.addSelectionHandler(handler);
	}

	@Override
	public void onApagarSurdo(Long id) {
		for (int i = 0; i < this.resultadoPesquisa.getList().size();i++) {
			SurdoDetailsVO vo = this.resultadoPesquisa.getList().get(i);
			if (vo.getId().equals(id)) {
				this.resultadoPesquisa.getList().remove(i);
				break;
			}
		}
		this.pesquisaResultadoCellTable.setRowCount(this.resultadoPesquisa.getList().size());
		this.limparResultadoPesquisa();
		this.mostrarResultadoPesquisa();
	}

	private Validacoes validaManterSurdo() {
		//TODO: Validar tamanhos máximos (por conta da impressão - validar na UI diretamente)
		Validacoes validacoes = new Validacoes();
		
		if (manterNomeTextBox.getText().isEmpty()) {
			validacoes.add("Nome precisa ser preenchido");
		}
		if (manterLogradouroTextBox.getText().isEmpty()) {
			validacoes.add("Logradouro precisa ser preenchido");
		}
		if (manterNumeroTextBox.getText().isEmpty()) {
			validacoes.add("Número precisa ser preenchido");
		}
		if (manterRegiaoListBox.getSelectedIndex() == 0) {
			validacoes.add("Região precisa ser preenchida");
		}
		
		return validacoes;
	} 
	
	private void limparResultadoPesquisa() {	
		// ao remover a coluna 0, o objeto passa a coluna 1 para a 0,
		// portanto sempre  é necessário remover a coluna 0.
		int j = this.pesquisaResultadoCellTable.getColumnCount();
		for (int i = 0; i < j; i++) {
			this.pesquisaResultadoCellTable.removeColumn(0);
		}
		this.pesquisaResultadoLabel.setText("");
		this.pesquisaResultadoCellTable.setVisible(false);
		this.pesquisaResultadoSimplePager.setVisible(false);
	}
	
	private void limparManter() {
		this.buscaEndereco = true;
		this.manterId = null;
		this.manterMapa = null;
		this.manterLongitude = null;
		this.manterLatitude = null;
		this.manterNomeTextBox.setText("");         
		this.manterLogradouroTextBox.setText("");   
		this.manterNumeroTextBox.setText("");       
		this.manterComplementoTextBox.setText("");  
		this.manterRegiaoListBox.setSelectedIndex(0);       
		this.manterBairroSuggestBox.setText("");       
		this.manterCEPTextBox.setText("");          
		this.manterObservacaoTextArea.setText("");  
		this.manterTelefoneTextBox.setText("");     
		this.manterLibrasListBox.setSelectedIndex(0);       
		this.manterCriancaListBox.setSelectedIndex(0);      
		this.manterDVDListBox.setSelectedIndex(0);          
		this.manterInstrutorTextBox.setText("");    
		this.manterIdadeIntegerBox.setText("");     
		this.manterSexoListBox.setSelectedIndex(0);         
		this.manterHorarioTextBox.setText("");      
		this.manterMelhorDiaTextBox.setText("");    
		this.manterOnibusTextBox.setText("");       
		this.manterMSNTextBox.setText("");
	}
	
	private Surdo populaSurdo() {
		Surdo surdo = new Surdo();
		surdo.setId(this.manterId);
		surdo.setNome(this.manterNomeTextBox.getText().toUpperCase());
		surdo.setLogradouro(this.manterLogradouroTextBox.getText());
		surdo.setNumero(this.manterNumeroTextBox.getText());
		surdo.setComplemento(this.manterComplementoTextBox.getText());
		surdo.setRegiao(this.manterRegiaoListBox.getValue(this.manterRegiaoListBox.getSelectedIndex()));
		surdo.setBairro(this.manterBairroSuggestBox.getText());
		surdo.setCep(this.manterCEPTextBox.getText());
		surdo.setObservacao(this.manterObservacaoTextArea.getText());
		surdo.setTelefone(this.manterTelefoneTextBox.getText());
		surdo.setLibras(this.manterLibrasListBox.getValue(this.manterLibrasListBox.getSelectedIndex()));
		surdo.setCrianca(this.manterCriancaListBox.getValue(this.manterCriancaListBox.getSelectedIndex()));
		surdo.setDvd(this.manterDVDListBox.getValue(this.manterDVDListBox.getSelectedIndex()));
		surdo.setInstrutor(this.manterInstrutorTextBox.getText());
		surdo.setIdade(this.manterIdadeIntegerBox.getText());
		surdo.setSexo(this.manterSexoListBox.getValue(this.manterSexoListBox.getSelectedIndex()));
		surdo.setHorario(this.manterHorarioTextBox.getText());
		surdo.setMelhorDia(this.manterMelhorDiaTextBox.getText());
		surdo.setOnibus(this.manterOnibusTextBox.getText());
		surdo.setMsn(this.manterMSNTextBox.getText());
		surdo.setMapa(this.manterMapa);
		surdo.setLongitude(this.manterLongitude);
		surdo.setLatitude(this.manterLatitude);
		return surdo;
	}
	
	private void populaManter(Surdo surdo) {
		this.buscaEndereco = false;
		this.manterId = surdo.getId();
		this.manterNomeTextBox.setText(StringUtils.toCamelCase(surdo.getNome()));
		this.manterLogradouroTextBox.setText(surdo.getLogradouro());
		this.manterNumeroTextBox.setText(surdo.getNumero());
		this.manterComplementoTextBox.setText(surdo.getComplemento());
		this.manterRegiaoListBox.setSelectedIndex(
				obterIndice(this.manterRegiaoListBox, surdo.getRegiao()));
		this.manterBairroSuggestBox.setText(surdo.getBairro());
		this.manterCEPTextBox.setText(surdo.getCep());
		this.manterObservacaoTextArea.setText(surdo.getObservacao());
		this.manterTelefoneTextBox.setText(surdo.getTelefone());
		this.manterLibrasListBox.setSelectedIndex(
				obterIndice(this.manterLibrasListBox, surdo.getLibras()));
		this.manterCriancaListBox.setSelectedIndex(
				obterIndice(this.manterCriancaListBox, surdo.getCrianca()));
		this.manterDVDListBox.setSelectedIndex(
				obterIndice(this.manterDVDListBox, surdo.getDvd()));
		this.manterInstrutorTextBox.setText(surdo.getInstrutor());
		this.manterIdadeIntegerBox.setText(surdo.getIdade());
		this.manterSexoListBox.setSelectedIndex(
				obterIndice(this.manterSexoListBox, surdo.getSexo()));
		this.manterHorarioTextBox.setText(surdo.getHorario());
		this.manterMelhorDiaTextBox.setText(surdo.getMelhorDia());
		this.manterOnibusTextBox.setText(surdo.getOnibus());
		this.manterMSNTextBox.setText(surdo.getMsn());
		this.manterMapa = surdo.getMapa();
		this.manterLongitude = surdo.getLongitude();
		this.manterLatitude = surdo.getLatitude();
	}
	
	private void iniciarSNListBox(ListBox snListBox) {
		snListBox.addItem("-- Escolha uma opção --", "");
		snListBox.addItem("Sim");
		snListBox.addItem("Não");
	}
	
	private void iniciarSexoListBox(ListBox sexoListBox){
		sexoListBox.addItem("-- Escolha uma opção --", "");
		sexoListBox.addItem("Masculino");
		sexoListBox.addItem("Feminino");
	}
	
	private int obterIndice(ListBox list, String valor) {
		for (int i = 0; i < list.getItemCount(); i++) {
			if (list.getValue(i).equals(valor)) {
				return i;
			}
		}
		return 0;
	}

	@Override
	public void onAdicionar() {
		this.manterSurdoGrid.setVisible(true);
		this.limparResultadoPesquisa();
		this.limparManter();
	}	
	
}
