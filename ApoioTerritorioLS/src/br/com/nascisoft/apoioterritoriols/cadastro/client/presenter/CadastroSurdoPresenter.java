package br.com.nascisoft.apoioterritoriols.cadastro.client.presenter;

import java.util.List;
import java.util.logging.Level;

import br.com.nascisoft.apoioterritoriols.cadastro.client.CadastroServiceAsync;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.AbrirCadastroSurdoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.EditarSurdoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.PesquisarSurdoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.CadastroSurdoView;
import br.com.nascisoft.apoioterritoriols.cadastro.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoDetailsVO;
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
	
	//TODO: Parametrizar todos os dados fixos
	private static final HasLatLng LATITUDE_CENTRO_CAMPINAS = new LatLng(-22.9071048, -47.06323910000003);
	
	public CadastroSurdoPresenter(CadastroServiceAsync service,
			HandlerManager eventBus, CadastroSurdoView view) {
		super(service, eventBus);
		this.view = view;
		this.view.setPresenter(this);
		populaRegioes();
		populaBairros();
	}
	
	@Override
	public void initView() {
		super.initView();
	}
	
	private static List<String> bairros = null;
	
	protected void populaBairros() {
		if (bairros == null) {
			getView().showWaitingPanel();
			service.obterBairrosCampinas(new AsyncCallback<List<String>>() {
				
				@Override
				public void onSuccess(List<String> result) {
					bairros = result;					
					getView().setBairroList(bairros);
					getView().hideWaitingPanel();
				}
				
				@Override
				public void onFailure(Throwable caught) {
					logger.log(Level.SEVERE, "Falha ao obter lista de bairros.\n", caught);
					Window.alert("Falha ao obter lista de bairros. \n" + caught.getMessage());					
				}
			});
		} else {
			this.view.setBairroList(bairros);
		}
	}

	@Override
	public void onPesquisaPesquisarButtonClick(String nomeSurdo, String nomeRegiao, String identificadorMapa, Boolean estaAssociadoMapa) {
		eventBus.fireEvent(new PesquisarSurdoEvent(nomeSurdo, nomeRegiao, identificadorMapa, estaAssociadoMapa));
	}
	
	@Override
	public void onPesquisaPesquisarEvent(String nomeSurdo, String nomeRegiao, String identificadorMapa, Boolean estaAssociadoMapa) {
		getView().showWaitingPanel();
		Long mapa = null;
		if (!StringUtils.isEmpty(identificadorMapa)) {
			mapa = Long.valueOf(identificadorMapa);
		}
		service.obterSurdos(nomeSurdo, nomeRegiao, mapa, estaAssociadoMapa, new AsyncCallback<List<SurdoDetailsVO>>() {

			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter lista de surdos.\n", caught);
				Window.alert("Falha ao obter lista de surdos. \n" + caught.getMessage());		
			}

			@Override
			public void onSuccess(List<SurdoDetailsVO> result) {
				getView().hideWaitingPanel();
				view.setResultadoPesquisa(result);
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
				Window.alert("Falha ao adicionar ou alterar surdo. \n" + caught.getMessage());
			}

			@Override
			public void onSuccess(Long result) {
				getView().hideWaitingPanel();
				Window.alert("Surdo id " + result + " salvo com sucesso.");
				eventBus.fireEvent(new AbrirCadastroSurdoEvent());
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
	public void buscarEndereco(String logradouro, String numero, String bairro,
			String cep) {
		getView().showWaitingPanel();
		StringBuilder sb = new StringBuilder();
		String separador = ", ";
		sb.append(logradouro).append(separador).
		   append(numero).append(separador);
	
		if (!StringUtils.isEmpty(bairro)) {
			sb.append(bairro).append(separador);
		}
		
		if (!StringUtils.isEmpty(cep)) {
			sb.append(cep).append(separador);
		}
		
		//TODO: Parametrizar todos os dados fixos
		sb.append("Campinas, Sao Paulo, Brasil");
		
		HasGeocoderRequest request = new GeocoderRequest();
		request.setRegion("BR");
		request.setLanguage("pt-BR");
		request.setAddress(sb.toString());
		
		HasGeocoder geo = new Geocoder();
		geo.geocode(request, new GeocoderCallback() {			
			@Override
			public void callback(List<HasGeocoderResult> responses, String status) {
				if (status.equals("OK")) {
					HasGeocoderResult result = responses.get(0);
					HasLatLng retorno = result.getGeometry().getLocation();
						// em caso de não encontrar o endereço, a api do google maps está retornando o centro de Campinas, por conta da 
						// Query String que foi usada. Neste caso vou tratar como se ele não tivesse encontrado o endereço.
					if (retorno.equals(LATITUDE_CENTRO_CAMPINAS)) {
						retorno = null;
					}
					view.setPosition(retorno);
				} else {
					view.setPosition(null);
				}				
				getView().hideWaitingPanel();
			}
		});
		
	}

	@Override
	public void onAdicionar() {
		getView().onAdicionar();
	}

}
