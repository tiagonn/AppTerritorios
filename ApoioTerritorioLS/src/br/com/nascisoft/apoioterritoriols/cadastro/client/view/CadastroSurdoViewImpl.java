package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.client.common.ImageButtonCell;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoDetailsVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;
import br.com.nascisoft.apoioterritoriols.login.util.Validacoes;
import br.com.nascisoft.apoioterritoriols.resources.client.CellTableCustomResources;
import br.com.nascisoft.apoioterritoriols.resources.client.Resources;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
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
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.googlecode.objectify.Key;

public class CadastroSurdoViewImpl extends Composite implements CadastroSurdoView {

	private static CadastroSurdoViewUiBinderUiBinder uiBinder = GWT
			.create(CadastroSurdoViewUiBinderUiBinder.class);
	@UiField TabLayoutPanel cadastroSurdoTabLayoutPanel;
	@UiField(provided=true) CellTable<SurdoDetailsVO> pesquisaResultadoCellTable;
	@UiField Label pesquisaResultadoLabel;
	@UiField HTML manterWarningHTML;
	@UiField VerticalPanel manterSurdoPanel;
	@UiField TextBox manterNomeTextBox;
	@UiField TextBox manterLogradouroTextBox;
	@UiField TextBox manterNumeroTextBox;
	@UiField TextBox manterComplementoTextBox;
	@UiField ListBox manterCidadeListBox;
	@UiField ListBox manterRegiaoListBox;
	@UiField(provided=true) SuggestBox manterBairroSuggestBox;
	@UiField TextBox manterCEPTextBox;
	@UiField TextArea manterObservacaoTextArea;
	@UiField TextBox manterTelefoneTextBox;
	@UiField ListBox manterLibrasListBox;
	@UiField TextBox manterPublicacoesTextBox;
	@UiField ListBox manterDVDListBox;
	@UiField TextBox manterInstrutorTextBox;
	@UiField IntegerBox manterAnoNascimentoIntegerBox;
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
	@UiField CheckBox manterMudouSe;
	@UiField CheckBox manterVisitarSomentePorAnciaos;
	@UiField CheckBox manterMapaSateliteCheckBox;
	@UiField TextBox manterQtdePessoasTextBox;
	@UiField TextBox pesquisaFiltrarTextBox;
	
	MultiWordSuggestOracle bairroOracle;
	
	Long manterId;
	Key<Mapa> manterMapa;
	Double manterLongitude;
	Double manterLatitude;
	HasMarker marker;
	String manterRegiao;
	String manterCidade;
	boolean buscaEndereco = true;

	@UiTemplate("CadastroViewUiBinder.ui.xml")
	interface CadastroSurdoViewUiBinderUiBinder extends
			UiBinder<Widget, CadastroSurdoViewImpl> {
	}
	
	private Presenter presenter;
	
	private ListDataProvider<SurdoDetailsVO> resultadoPesquisa;
	private List<SurdoDetailsVO> listaResultadoPesquisa;

	public CadastroSurdoViewImpl() {
		this.bairroOracle = new MultiWordSuggestOracle();
		this.manterBairroSuggestBox = new SuggestBox(this.bairroOracle);
		CellTableCustomResources.INSTANCE.cellTableStyle().ensureInjected();
		this.pesquisaResultadoCellTable = new CellTable<SurdoDetailsVO>(15, CellTableCustomResources.INSTANCE);
		initWidget(uiBinder.createAndBindUi(this));
		this.iniciarSNListBox(this.manterLibrasListBox);
		this.iniciarSNListBox(this.manterDVDListBox);
		this.iniciarSexoListBox(this.manterSexoListBox); 
		this.resultadoPesquisa = new ListDataProvider<SurdoDetailsVO>();
		this.resultadoPesquisa.addDataDisplay(this.pesquisaResultadoCellTable);
		this.pesquisaResultadoSimplePager.setDisplay(this.pesquisaResultadoCellTable);
		
	}
	
	public void initView() {
		this.selectThisTab();
		this.pesquisaFiltrarTextBox.setText("");
		this.manterSurdoPanel.setVisible(false);
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
	public void setCidadeList(List<Cidade> cidades) {
		this.manterCidadeListBox.clear();
		for (Cidade cidade : cidades) {
			this.manterCidadeListBox.addItem(cidade.getNome(), cidade.getId().toString());
		}	
	}	
	
	@Override
	public void setManterRegiaoList(List<Regiao> regioes) {
		this.manterRegiaoListBox.clear();
		this.manterRegiaoListBox.addItem("-- Escolha uma região --", "");
		for (Regiao regiao : regioes) {
			this.manterRegiaoListBox.addItem(regiao.getNomeRegiaoCompleta(), regiao.getId().toString());
		}
		if (this.manterRegiao != null) {
			this.manterRegiaoListBox.setSelectedIndex(obterIndice(this.manterRegiaoListBox, this.manterRegiao));
		}
 	}

	@Override
	public void setBairroList(List<String> bairros) {
		this.bairroOracle.clear();
		for (String bairro : bairros) {
			this.bairroOracle.add(bairro);
		}
	}
	
	@UiHandler("manterCidadeListBox")
	void onManterCidadeListBoxChange(ChangeEvent event) {
		if (!StringUtils.isEmpty(this.manterCidade) 
				&& !this.manterCidade.equals(this.manterCidadeListBox.getValue(this.manterCidadeListBox.getSelectedIndex()))
				&& this.manterMapa != null) {
			Window.alert("Ao alterar a cidade do surdo ele perderá a associação que tem com o mapa atual.");
		}
		this.manterCidade = this.manterCidadeListBox.getValue(this.manterCidadeListBox.getSelectedIndex());
		this.presenter.onManterCidadeListBoxChange(Long.valueOf(manterCidade));
	}
	
	@UiHandler("manterRegiaoListBox")
	void onManterRegiaoListBoxChange(ChangeEvent event) {
		if (!StringUtils.isEmpty(this.manterRegiao) 
				&& !this.manterRegiao.equals(this.manterRegiaoListBox.getValue(this.manterRegiaoListBox.getSelectedIndex()))
				&& this.manterMapa != null) {
			Window.alert("Ao alterar a região do surdo ele perderá a associação que tem com o mapa atual.");
		}
	}
	
	@UiHandler("manterSexoListBox")
	void onManterSexoListBoxChange(ChangeEvent event) {
		switch (manterSexoListBox.getValue(manterSexoListBox.getSelectedIndex())) {
		case "Homem":
			this.manterQtdePessoasTextBox.setValue("1");
			break;
		case "Mulher":
			this.manterQtdePessoasTextBox.setValue("1");
			break;
		case "Casal":
			this.manterQtdePessoasTextBox.setValue("2");
			break;
		}
	}
		
	@Override
	public void setResultadoPesquisa(List<SurdoDetailsVO> resultadoPesquisa) {
		this.limparResultadoPesquisa();
		this.pesquisaResultadoCellTable.setRowCount(resultadoPesquisa.size());
		this.resultadoPesquisa.setList(resultadoPesquisa);
		this.listaResultadoPesquisa = resultadoPesquisa;
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
			ListHandler<SurdoDetailsVO> nomeSortHandler = new ListHandler<SurdoDetailsVO>(this.resultadoPesquisa.getList());
			nomeSortHandler.setComparator(nomeColumn, new Comparator<SurdoDetailsVO>() {
				@Override
				public int compare(SurdoDetailsVO o1, SurdoDetailsVO o2) {
					if (o1 == o2) {
						return 0;
					}

					// Compare the name columns.
					if (o1 != null) {
						return (o2 != null) ? o1.getNome()
								.compareTo(o2.getNome()) : 1;
					}
					return -1;
				}
			});
			nomeColumn.setSortable(true);
			
			TextColumn<SurdoDetailsVO> cidadeColumn = new TextColumn<SurdoDetailsVO>() {
				@Override
				public String getValue(SurdoDetailsVO object) {
					return object.getNomeCidade();
				}
			};
			ListHandler<SurdoDetailsVO> cidadeSortHandler = new ListHandler<SurdoDetailsVO>(this.resultadoPesquisa.getList());
			nomeSortHandler.setComparator(cidadeColumn, new Comparator<SurdoDetailsVO>() {
				@Override
				public int compare(SurdoDetailsVO o1, SurdoDetailsVO o2) {
					if (o1 == o2) {
						return 0;
					}

					// Compare the name columns.
					if (o1 != null) {
						return (o2 != null) ? o1.getNomeCidade()
								.compareTo(o2.getNomeCidade()) : 1;
					}
					return -1;
				}
			});
			cidadeColumn.setSortable(true);
			
			TextColumn<SurdoDetailsVO> regiaoColumn = new TextColumn<SurdoDetailsVO>() {
				@Override
				public String getValue(SurdoDetailsVO object) {
					return object.getRegiao();
				}				
			};
			ListHandler<SurdoDetailsVO> regiaoSortHandler = new ListHandler<SurdoDetailsVO>(this.resultadoPesquisa.getList());
			nomeSortHandler.setComparator(regiaoColumn, new Comparator<SurdoDetailsVO>() {
				@Override
				public int compare(SurdoDetailsVO o1, SurdoDetailsVO o2) {
					if (o1 == o2) {
						return 0;
					}

					// Compare the name columns.
					if (o1 != null) {
						return (o2 != null) ? o1.getRegiao()
								.compareTo(o2.getRegiao()) : 1;
					}
					return -1;
				}
			});
			regiaoColumn.setSortable(true);
			
			TextColumn<SurdoDetailsVO> mapaColumn = new TextColumn<SurdoDetailsVO>() {
				@Override
				public String getValue(SurdoDetailsVO object) {
					return object.getMapa().substring(5);
				}
			};			
			ListHandler<SurdoDetailsVO> mapaSortHandler = new ListHandler<SurdoDetailsVO>(this.resultadoPesquisa.getList());
			nomeSortHandler.setComparator(mapaColumn, new Comparator<SurdoDetailsVO>() {
				@Override
				public int compare(SurdoDetailsVO o1, SurdoDetailsVO o2) {
					if (o1 == o2) {
						return 0;
					}

					// Compare the name columns.
					if (o1 != null) {
						if (o2 != null) {
							String mapa1 = o1.getMapa().substring(5);
							String mapa2 = o2.getMapa().substring(5);
							if (mapa1.length() == 2) {
								mapa1 = mapa1.substring(0,1) + "0" + mapa1.substring(1);
							}
							if (mapa2.length() == 2) {
								mapa2 = mapa2.substring(0,1) + "0" + mapa2.substring(1);
							}
							return mapa1.compareTo(mapa2);
						} else {
							return 1;
						}
					}
					return -1;
				}
			});
			mapaColumn.setSortable(true);
			
			TextColumn<SurdoDetailsVO> enderecoColumn = new TextColumn<SurdoDetailsVO>() {
				@Override
				public String getValue(SurdoDetailsVO object) {
					return object.getEndereco();
				}
			};
			
			Column<SurdoDetailsVO, String> editColumn = new Column<SurdoDetailsVO, String>(
					new ImageButtonCell()) {
				@Override
				public String getValue(SurdoDetailsVO object) {
					return Resources.INSTANCE.editar().getSafeUri().asString();
				}
			};
			editColumn.setFieldUpdater(new FieldUpdater<SurdoDetailsVO, String>() {
				@Override
				public void update(int index, SurdoDetailsVO object, String value) {
					presenter.onEditarButtonClick(object.getId());
				}
			});
			editColumn.setCellStyleNames("imageCell");
			
			Column<SurdoDetailsVO, String> deletarColumn = new Column<SurdoDetailsVO, String>(
					new ImageButtonCell()) {
				@Override
				public String getValue(SurdoDetailsVO object) {
					return Resources.INSTANCE.apagar().getSafeUri().asString();
				}
			};
			deletarColumn.setFieldUpdater(new FieldUpdater<SurdoDetailsVO, String>() {
				@Override
				public void update(int index, SurdoDetailsVO object, String value) {
					if (Window.confirm("Deseja realmente apagar este surdo?")) {
						presenter.onApagar(object.getId());
					}
				}
			});
			deletarColumn.setCellStyleNames("imageCell");
			
			this.pesquisaResultadoCellTable.addColumnSortHandler(nomeSortHandler);
			this.pesquisaResultadoCellTable.addColumnSortHandler(cidadeSortHandler);
			this.pesquisaResultadoCellTable.addColumnSortHandler(regiaoSortHandler);
			this.pesquisaResultadoCellTable.addColumnSortHandler(mapaSortHandler);

			this.pesquisaResultadoCellTable.addColumn(cidadeColumn, "Cidade");
			this.pesquisaResultadoCellTable.addColumn(nomeColumn, "Nome");
			this.pesquisaResultadoCellTable.addColumn(regiaoColumn, "Região");
			this.pesquisaResultadoCellTable.addColumn(mapaColumn, "Mapa");
			this.pesquisaResultadoCellTable.addColumn(enderecoColumn, "Endereço");
			this.pesquisaResultadoCellTable.addColumn(editColumn, "");
			this.pesquisaResultadoCellTable.addColumn(deletarColumn, "");
			
			this.pesquisaResultadoCellTable.getColumnSortList().push(nomeColumn);
			
			this.pesquisaResultadoSimplePager.setVisible(true);
		}
		this.manterWarningHTML.setHTML("");
		this.manterSurdoPanel.setVisible(false);
	}
	
	@UiHandler("manterSalvarButton")
	void onManterSalvarButtonClick(ClickEvent event) {
		Validacoes validacoes = validaManterSurdo();
		if (validacoes.size() > 0) {
			this.manterWarningHTML.setHTML(validacoes.obterValidacoesSumarizadaHTML());
		} else {
			if (this.buscaEndereco) {
				this.presenter.buscarEndereco(
						Long.valueOf(this.manterCidadeListBox.getValue(this.manterCidadeListBox.getSelectedIndex())),
						this.manterLogradouroTextBox.getValue(), 
						this.manterNumeroTextBox.getValue(),
						this.manterBairroSuggestBox.getValue(),
						this.manterCEPTextBox.getValue());
			} else {
				HasLatLng position = new LatLng(this.manterLatitude, this.manterLongitude);
				this.setPosition(position, true, false);
				onManterMapaConfirmarEnderecoButtonClick(event);
			}
		}		
	}
	
	public void setPosition(HasLatLng position, Boolean sucesso, Boolean mostraMapa) {
		
		int zoom = 17;
		this.manterWarningEnderecoHTML.setVisible(false);
		if (!sucesso) {
			this.manterWarningEnderecoHTML.setVisible(true);
			zoom = 12;
		} 
		this.manterLatitude = position.getLatitude();
		this.manterLongitude = position.getLongitude();
		HasMarkerOptions markerOpt = new MarkerOptions();
		markerOpt.setClickable(true);
		markerOpt.setDraggable(true);
		markerOpt.setVisible(true);
		this.marker = new Marker(markerOpt);
		this.marker.setPosition(position);

		if (mostraMapa) {		
			HasMapOptions opt = new MapOptions();
			opt.setZoom(zoom);
			opt.setCenter(new LatLng(this.manterLatitude,this.manterLongitude));
			opt.setMapTypeId(new MapTypeId().getRoadmap());
			opt.setDraggable(true);
			opt.setNavigationControl(true);
			opt.setScrollwheel(true);
			MapWidget mapa = new MapWidget(opt);
			mapa.setSize("635px", "480px");
			this.marker.setMap(mapa.getMap());
			manterMapaLayoutPanel.clear();
			manterMapaLayoutPanel.setSize("635px", "480px");
			manterMapaLayoutPanel.add(mapa);		
			manterMapaPopupPanel.setPopupPosition(this.pesquisaFiltrarTextBox.getAbsoluteLeft()+50,20);
			manterMapaPopupPanel.setVisible(true);
			manterMapaPopupPanel.show();
		}
	}
	
	@UiHandler("manterMapaConfirmarEnderecoButton")
	void onManterMapaConfirmarEnderecoButtonClick(ClickEvent event) {
		this.manterLatitude = marker.getPosition().getLatitude();
		this.manterLongitude = marker.getPosition().getLongitude();
		this.manterMapaPopupPanel.hide();
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
	
	@UiHandler(value={"manterMudouSe", "manterVisitarSomentePorAnciaos"})
	void onNaoVisitarChangeValue(ValueChangeEvent<Boolean> event) {
		if (event.getValue()) {
			Window.alert("Ao selecionar mudou-se ou visitar somente por anciãos, este surdo será removido " +
					"da listagem e do mapa que ele está associado, se é que ele está associado a algum mapa, " + 
					"até que ele seja desmarcado como não visitar na aba própria para este fim.\n\n" +
					"Por favor, detalhe porque você está marcando este surdo para não ser visitado no campo observação.");
			if ("manterMudouSe".equals(((CheckBox)event.getSource()).getName())) {
				this.manterVisitarSomentePorAnciaos.setValue(Boolean.FALSE);
			} else {
				this.manterMudouSe.setValue(Boolean.FALSE);
			}
		}
	}

	@Override
	public void onEditar(Surdo surdo) {
		this.manterSurdoPanel.setVisible(true);
		this.limparResultadoPesquisa();
		this.populaManter(surdo);
		this.presenter.onManterCidadeListBoxChange(Long.valueOf(this.manterCidade));
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
		if (manterQtdePessoasTextBox.getValue() == null ||
				manterQtdePessoasTextBox.getValue().length() == 0) {
			validacoes.add("Quantidade de pessoas no endereço precisa ser preenchida");
		} else {
			try {
				Byte.valueOf(this.manterQtdePessoasTextBox.getValue());
			} catch (NumberFormatException ex) {
				validacoes.add("Quantidade de pessoas no endereço precisa ser um valor numérico");
			}
		}
		
		return validacoes;
	} 
	
	private void limparResultadoPesquisa() {	
		if (this.presenter.getLoginInformation().isAdmin()) {
			boolean existeAdmin = true;
			try {
				this.cadastroSurdoTabLayoutPanel.getTabWidget(4);
			} catch (AssertionError ex) {
				existeAdmin = false;
			} catch (IndexOutOfBoundsException ex) {
				existeAdmin = false;
			}
			if (!existeAdmin) {
				StringBuilder sb = new StringBuilder("<a href=\"/Admin.html\"><img src=\"");
				sb.append(Resources.INSTANCE.configuracao().getSafeUri().asString())
					.append("\" title=\"Configurações\" alt=\"Configurações\"/></a>");
				this.cadastroSurdoTabLayoutPanel.add(new HTML(""), new HTML(sb.toString()));
			}
		}

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
		this.manterRegiao = null;
		this.manterCidade = null;
		this.manterNomeTextBox.setText("");         
		this.manterLogradouroTextBox.setText("");   
		this.manterNumeroTextBox.setText("");       
		this.manterComplementoTextBox.setText("");  
		this.manterRegiaoListBox.setSelectedIndex(0);       
		this.manterCidadeListBox.setSelectedIndex(0);       
		this.manterBairroSuggestBox.setText("");       
		this.manterCEPTextBox.setText("");          
		this.manterObservacaoTextArea.setText("");  
		this.manterTelefoneTextBox.setText("");     
		this.manterLibrasListBox.setSelectedIndex(0);       
		this.manterDVDListBox.setSelectedIndex(0);          
		this.manterInstrutorTextBox.setText("");    
		this.manterPublicacoesTextBox.setText("");    
		this.manterAnoNascimentoIntegerBox.setText("");     
		this.manterSexoListBox.setSelectedIndex(0);         
		this.manterHorarioTextBox.setText("");      
		this.manterMelhorDiaTextBox.setText("");    
		this.manterOnibusTextBox.setText("");       
		this.manterMSNTextBox.setText("");
		this.manterMudouSe.setValue(Boolean.FALSE);
		this.manterVisitarSomentePorAnciaos.setValue(Boolean.FALSE);
		this.manterMapaSateliteCheckBox.setValue(false);
		this.manterQtdePessoasTextBox.setText("1");
	}
	
	private Surdo populaSurdo() {
		Surdo surdo = new Surdo();
		surdo.setId(this.manterId);
		surdo.setNome(this.manterNomeTextBox.getText().toUpperCase());
		surdo.setLogradouro(this.manterLogradouroTextBox.getText());
		surdo.setNumero(this.manterNumeroTextBox.getText());
		surdo.setComplemento(this.manterComplementoTextBox.getText());
		surdo.setRegiaoId(Long.valueOf(this.manterRegiaoListBox.getValue(this.manterRegiaoListBox.getSelectedIndex())));
		surdo.setBairro(this.manterBairroSuggestBox.getText());
		surdo.setCep(this.manterCEPTextBox.getText());
		surdo.setObservacao(this.manterObservacaoTextArea.getText());
		surdo.setTelefone(this.manterTelefoneTextBox.getText());
		surdo.setLibras(this.manterLibrasListBox.getValue(this.manterLibrasListBox.getSelectedIndex()));
		surdo.setPublicacoesPossui(this.manterPublicacoesTextBox.getText());
		surdo.setDvd(this.manterDVDListBox.getValue(this.manterDVDListBox.getSelectedIndex()));
		surdo.setInstrutor(this.manterInstrutorTextBox.getText());
		surdo.setAnoNascimento(this.manterAnoNascimentoIntegerBox.getValue());
		surdo.setSexo(this.manterSexoListBox.getValue(this.manterSexoListBox.getSelectedIndex()));
		surdo.setHorario(this.manterHorarioTextBox.getText());
		surdo.setMelhorDia(this.manterMelhorDiaTextBox.getText());
		surdo.setOnibus(this.manterOnibusTextBox.getText());
		surdo.setMsn(this.manterMSNTextBox.getText());
		if (this.manterRegiao != null && this.manterRegiao.equals(surdo.getRegiaoId().toString())) {
			surdo.setMapa(this.manterMapa);
		} else if (this.manterMapa != null){
			surdo.setMapaAnterior(this.manterMapa.getId());
		}
		surdo.setLongitude(this.manterLongitude);
		surdo.setLatitude(this.manterLatitude);
		surdo.setMudouSe(this.manterMudouSe.getValue());
		surdo.setVisitarSomentePorAnciaos(this.manterVisitarSomentePorAnciaos.getValue());
		if ((surdo.isMudouSe() || surdo.isVisitarSomentePorAnciaos())
				&& this.manterMapa != null) {
			surdo.setMapaAnterior(this.manterMapa.getId());
		}
		surdo.setCidadeId(Long.valueOf(this.manterCidadeListBox.getValue(this.manterCidadeListBox.getSelectedIndex())));
		surdo.setQtdePessoasEndereco(
				this.manterQtdePessoasTextBox.getValue() != null && this.manterQtdePessoasTextBox.getValue().length()>0 
				? Byte.valueOf(this.manterQtdePessoasTextBox.getValue())
						:null);
		return surdo;
	}
	
	private void populaManter(Surdo surdo) {
		this.buscaEndereco = false;
		this.manterId = surdo.getId();
		this.manterRegiao = String.valueOf(surdo.getRegiao().getId());
		this.manterCidade = String.valueOf(surdo.getCidade().getId());
		this.manterNomeTextBox.setText(StringUtils.toCamelCase(surdo.getNome()));
		this.manterLogradouroTextBox.setText(surdo.getLogradouro());
		this.manterNumeroTextBox.setText(surdo.getNumero());
		this.manterComplementoTextBox.setText(surdo.getComplemento());
		this.manterRegiaoListBox.setSelectedIndex(
				obterIndice(this.manterRegiaoListBox, this.manterRegiao));
		this.manterBairroSuggestBox.setText(surdo.getBairro());
		this.manterCEPTextBox.setText(surdo.getCep());
		this.manterObservacaoTextArea.setText(surdo.getObservacao());
		this.manterTelefoneTextBox.setText(surdo.getTelefone());
		this.manterLibrasListBox.setSelectedIndex(
				obterIndice(this.manterLibrasListBox, surdo.getLibras()));
		this.manterPublicacoesTextBox.setText(surdo.getPublicacoesPossui());
		this.manterDVDListBox.setSelectedIndex(
				obterIndice(this.manterDVDListBox, surdo.getDvd()));
		this.manterInstrutorTextBox.setText(surdo.getInstrutor());
		this.manterAnoNascimentoIntegerBox.setValue(surdo.getAnoNascimento());
		this.manterSexoListBox.setSelectedIndex(
				obterIndice(this.manterSexoListBox, surdo.getSexo()));
		this.manterHorarioTextBox.setText(surdo.getHorario());
		this.manterMelhorDiaTextBox.setText(surdo.getMelhorDia());
		this.manterOnibusTextBox.setText(surdo.getOnibus());
		this.manterMSNTextBox.setText(surdo.getMsn());
		this.manterMapa = surdo.getMapa();
		this.manterLongitude = surdo.getLongitude();
		this.manterLatitude = surdo.getLatitude();
		this.manterMudouSe.setValue(surdo.isMudouSe());
		this.manterVisitarSomentePorAnciaos.setValue(surdo.isVisitarSomentePorAnciaos());
		this.manterCidadeListBox.setSelectedIndex(obterIndice(this.manterCidadeListBox, this.manterCidade));
		this.manterQtdePessoasTextBox.setValue(surdo.getQtdePessoasEndereco()!=null?surdo.getQtdePessoasEndereco().toString():"1");
	}
	
	private void iniciarSNListBox(ListBox snListBox) {
		snListBox.addItem("-- Escolha uma opção --", "");
		snListBox.addItem("Sim");
		snListBox.addItem("Não");
	}
	
	private void iniciarSexoListBox(ListBox sexoListBox){
		sexoListBox.addItem("-- Escolha uma opção --", "");
		sexoListBox.addItem("Homem");
		sexoListBox.addItem("Mulher");
		sexoListBox.addItem("Casal");
		sexoListBox.addItem("Família");
		sexoListBox.addItem("Amigos/Grupo");
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
		this.manterSurdoPanel.setVisible(true);
		this.limparResultadoPesquisa();
		this.limparManter();
		if (this.manterCidadeListBox != null && this.manterCidadeListBox.getItemCount() > 0) {
			this.presenter.onManterCidadeListBoxChange(
					Long.valueOf(this.manterCidadeListBox.getValue(this.manterCidadeListBox.getSelectedIndex())));
		} 
	}
	
	@UiHandler("manterMapaSateliteCheckBox")
	void onImprimirMapaSateliteCheckBoxValueChange(ValueChangeEvent<Boolean> event) {
		if (event.getValue()) {
			((MapWidget)this.manterMapaLayoutPanel.getWidget(0)).getMap().setMapTypeId(new MapTypeId().getHybrid());
		} else {
			((MapWidget)this.manterMapaLayoutPanel.getWidget(0)).getMap().setMapTypeId(new MapTypeId().getRoadmap());
		}
	}
	
	@UiHandler("pesquisaFiltrarTextBox")
	void onPesquisaFiltrarTextBoxKeyUpEvent(KeyUpEvent event) {
		this.limparResultadoPesquisa();
		if (this.pesquisaFiltrarTextBox.getValue().length()>0) {
			List<SurdoDetailsVO> resultado = filtrarResultadoPesquisa(this.pesquisaFiltrarTextBox.getValue());
			this.resultadoPesquisa.setList(resultado);
			this.pesquisaResultadoCellTable.setRowCount(resultado.size());
		} else {
			this.resultadoPesquisa.setList(this.listaResultadoPesquisa);
			this.pesquisaResultadoCellTable.setRowCount(this.listaResultadoPesquisa.size());
		}
		this.mostrarResultadoPesquisa();
	}
	
	private List<SurdoDetailsVO> filtrarResultadoPesquisa(String query) {
		List<SurdoDetailsVO> result = new ArrayList<SurdoDetailsVO>();
		for (SurdoDetailsVO vo : this.listaResultadoPesquisa) {
			if ( (vo.getNome().toUpperCase().indexOf(query.toUpperCase()) != -1)
					|| (vo.getNomeCidade().toUpperCase().indexOf(query.toUpperCase()) != -1)
					|| (vo.getRegiao().toUpperCase().indexOf(query.toUpperCase()) != -1) 
					|| (vo.getMapa().toUpperCase().indexOf(query.toUpperCase()) != -1)
					|| (vo.getEndereco().toUpperCase().indexOf(query.toUpperCase()) != -1)
					) {
				result.add(vo);
			}
		}
		return result;
	}
	
}
