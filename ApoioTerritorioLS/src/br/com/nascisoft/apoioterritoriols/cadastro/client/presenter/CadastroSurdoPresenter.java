package br.com.nascisoft.apoioterritoriols.cadastro.client.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import br.com.nascisoft.apoioterritoriols.cadastro.client.CadastroServiceAsync;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.EditarSurdoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.PesquisarSurdoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.CadastroSurdoView;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoDetailsVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Bairro;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.maps.client.base.HasLatLng;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.geocoder.Geocoder;
import com.google.gwt.maps.client.geocoder.GeocoderCallback;
import com.google.gwt.maps.client.geocoder.GeocoderRequest;
import com.google.gwt.maps.client.geocoder.HasGeocoder;
import com.google.gwt.maps.client.geocoder.HasGeocoderRequest;
import com.google.gwt.maps.client.geocoder.HasGeocoderResult;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class CadastroSurdoPresenter extends AbstractCadastroPresenter implements CadastroSurdoView.Presenter {

	private final CadastroSurdoView view;
	
	public CadastroSurdoPresenter(CadastroServiceAsync service,
			HandlerManager eventBus, CadastroSurdoView view) {
		super(service, eventBus);
		this.view = view;
		this.view.setPresenter(this);
		this.populaCidades();
	}
	
	@Override
	public void initView() {
		super.initView();
	}
	
	@Override
	public void onPesquisaCidadeListBoxChange(Long cidadeId) {
		super.onPesquisaCidadeListBoxChange(cidadeId);
		populaBairros(cidadeId);
	}
	
	@Override
	void tratarCidadePopulada() {
		this.onManterCidadeListBoxChange(this.cidades.get(0).getId());
		if (this.cidades.size() == 1) {
			this.onPesquisaCidadeListBoxChange(this.cidades.get(0).getId());
		}
	}
	
	@Override
	public void onManterCidadeListBoxChange(Long cidadeId) {
		getView().showWaitingPanel();
		service.obterRegioes(cidadeId, new AsyncCallback<List<Regiao>>() {

			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter lista de regiões.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao obter lista de regiões. \n" + caught.getMessage());					
			}

			@Override
			public void onSuccess(List<Regiao> result) {
				getView().setManterRegiaoList(result);
				getView().hideWaitingPanel();
			}
		});
	}
	
	protected void populaBairros(Long cidadeId) {
		getView().showWaitingPanel();
		service.obterBairros(cidadeId, new AsyncCallback<List<Bairro>>() {
			
			@Override
			public void onSuccess(List<Bairro> result) {
				List<String> bairros = new ArrayList<String>();
				for (Bairro bairro : result) {
					bairros.add(bairro.getNome());
				}
				getView().setBairroList(bairros);
				getView().hideWaitingPanel();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter lista de bairros.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao obter lista de bairros. \n" + caught.getMessage());					
			}
		});
	}

	@Override
	public void onPesquisaPesquisarButtonClick(String identificadorCidade, String nomeSurdo, String nomeRegiao, String identificadorMapa, Boolean estaAssociadoMapa) {
		eventBus.fireEvent(new PesquisarSurdoEvent(identificadorCidade, nomeSurdo, nomeRegiao, identificadorMapa, estaAssociadoMapa));
	}
	
	@Override
	public void onPesquisaPesquisarEvent(final String identificadorCidade, final String nomeSurdo, final String regiaoId, final String identificadorMapa, final Boolean estaAssociadoMapa) {
		getView().showWaitingPanel();
		Long mapa = null;
		Long cidade = null;
		if (!StringUtils.isEmpty(identificadorCidade)) {
			cidade = Long.valueOf(identificadorCidade);
		}
		if (!StringUtils.isEmpty(identificadorMapa)) {
			mapa = Long.valueOf(identificadorMapa);
		}
		Long regiao = null;
		if (!StringUtils.isEmpty(regiaoId)) {
			regiao = Long.valueOf(regiaoId);
		}
		service.obterSurdos(cidade, nomeSurdo, regiao, mapa, estaAssociadoMapa, new AsyncCallback<List<SurdoDetailsVO>>() {

			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter lista de surdos.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao obter lista de surdos. \n" + caught.getMessage());		
			}

			@Override
			public void onSuccess(List<SurdoDetailsVO> result) {
				view.setResultadoPesquisa(result);
				Map<String, String> filtros = new HashMap<String, String>();
				filtros.put("Cidade", identificadorCidade);
				filtros.put("Nome", nomeSurdo);
				filtros.put("Regiao", regiaoId);
				filtros.put("Mapa", identificadorMapa);
				filtros.put("EstaAssociadoMapa", String.valueOf(estaAssociadoMapa));
				view.setDadosFiltro(filtros);
				getView().hideWaitingPanel();
			}
		});			
	}

	@Override
	public void adicionarOuAlterarSurdo(Surdo surdo) {
		getView().showWaitingPanel();
		service.adicionarOuAlterarSurdo(surdo, new AsyncCallback<Long>() {

			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao adicionar ou alterar surdo.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao adicionar ou alterar surdo. \n" + caught.getMessage());
			}

			@Override
			public void onSuccess(Long result) {
				getView().hideWaitingPanel();
				Window.alert("Surdo id " + result + " salvo com sucesso.");
				Map<String, String> filtros = getView().getDadosFiltro();
				String estaAssociadoMapa = filtros.get("EstaAssociadoMapa");
				Boolean estaAssociadoMapaBoolean = null;
				if (!StringUtils.isEmpty(estaAssociadoMapa) && !"null".equals(estaAssociadoMapa)) {
					estaAssociadoMapaBoolean = Boolean.valueOf(estaAssociadoMapa);
				}
				eventBus.fireEvent(new PesquisarSurdoEvent(filtros.get("Cidade"), filtros.get("Nome"), filtros.get("Regiao"), filtros.get("Mapa"), estaAssociadoMapaBoolean));
			}
		});
	}

	@Override
	public void onEditarButtonClick(Long id) {
		eventBus.fireEvent(new EditarSurdoEvent(id));
	}

	@Override
	public void onEditar(Long id) {
		getView().showWaitingPanel();
		service.obterSurdo(id, new AsyncCallback<Surdo>() {
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter surdo.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao obter surdo. \n" + caught.getMessage());
			}

			@Override
			public void onSuccess(Surdo result) {
				view.onEditar(result);			
				getView().hideWaitingPanel();
			}
		});
	}

	@Override
	public void setTabSelectionEventHandler(SelectionHandler<Integer> handler) {
		this.view.setTabSelectionEventHandler(handler);
	}

	@Override
	CadastroSurdoView getView() {
		return this.view;
	}	

	@Override
	public void onApagar(Long id) {
		getView().showWaitingPanel();
		this.service.apagarSurdo(id, new AsyncCallback<Long>() {

			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao apagar surdo.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao apagar surdo. \n" + caught.getMessage());
			}

			@Override
			public void onSuccess(Long result) {
				view.onApagarSurdo(result);
				getView().hideWaitingPanel();
			}
		});
	}

	@Override
	public void buscarEndereco(final Long identificadorCidade, final String logradouro, final String numero, final String bairro,
			final String cep) {
		getView().showWaitingPanel();
		
		
		service.obterCidade(identificadorCidade, new AsyncCallback<Cidade>() {
			
			@Override
			public void onSuccess(final Cidade cidade) {
				
				StringBuilder sb = new StringBuilder();
				String separador = ", ";
				sb.append(logradouro).append(separador).
				   append(numero).append(separador);
			
				if (!StringUtils.isEmpty(bairro) && cidade.getUtilizarBairroBuscaEndereco()) {
					sb.append(bairro).append(separador);
				}
				
				if (!StringUtils.isEmpty(cep)) {
					sb.append(cep).append(separador);
				}
				
				sb.append(cidade.getNome()).append(separador).append(cidade.getUF()).append(separador).append(cidade.getPais());
				
				HasGeocoderRequest request = new GeocoderRequest();
				request.setRegion("BR");
				request.setLanguage("pt-BR");
				request.setAddress(sb.toString());
				
				HasGeocoder geo = new Geocoder();
				geo.geocode(request, new GeocoderCallback() {			
					@Override
					public void callback(List<HasGeocoderResult> responses, String status) {
						HasLatLng centro = new LatLng(cidade.getLatitudeCentro(), cidade.getLongitudeCentro());
						if (status.equals("OK")) {
							HasGeocoderResult result = responses.get(0);
							HasLatLng retorno = result.getGeometry().getLocation();
								// em caso de não encontrar o endereço, a api do google maps está retornando o centro de Campinas, por conta da 
								// Query String que foi usada. Neste caso vou tratar como se ele não tivesse encontrado o endereço.
							view.setPosition(retorno, !retorno.equals(centro), true);
						} else {
							view.setPosition(centro, false, true);
						}				
						getView().hideWaitingPanel();
					}
				});
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao buscar cidade.\n", caught);
				getView().hideWaitingPanel();
				Window.alert("Falha ao buscar cidade. \n" + caught.getMessage());
			}
		});
		
	}

	@Override
	public void onAdicionar() {
		getView().onAdicionar();
	}

}
