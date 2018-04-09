package br.com.nascisoft.apoioterritoriols.cadastro.client.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import br.com.nascisoft.apoioterritoriols.cadastro.client.CadastroServiceAsync;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.EditarSurdoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.event.PesquisarSurdoEvent;
import br.com.nascisoft.apoioterritoriols.cadastro.client.view.CadastroSurdoView;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoDetailsVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Bairro;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.login.util.StringUtils;
import br.com.nascisoft.apoioterritoriols.login.vo.LoginVO;
import br.com.nascisoft.apoioterritoriols.resources.client.ApoioTerritorioLSConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.maps.client.base.HasLatLng;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.geocoder.Geocoder;
import com.google.gwt.maps.client.geocoder.GeocoderCallback;
import com.google.gwt.maps.client.geocoder.GeocoderRequest;
import com.google.gwt.maps.client.geocoder.HasGeocoderResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class CadastroSurdoPresenter extends AbstractCadastroPresenter implements CadastroSurdoView.Presenter {

	private final CadastroSurdoView view;
	
	public CadastroSurdoPresenter(CadastroServiceAsync service,
			HandlerManager eventBus, CadastroSurdoView view, LoginVO login) {
		super(service, eventBus, login);
		this.view = view;
		this.view.setPresenter(this);
		this.populaCidades();
	}
	
	@Override
	public void initView() {
		super.initView();
	}
	
	
	@Override
	public void onManterCidadeListBoxChange(final Long cidadeId) {
		getView().showWaitingPanel();
		GWT.log("Entrando em onManterCidadeListBoxChange, id da cidade: " +cidadeId);
		service.obterRegioes(cidadeId, new AsyncCallback<List<Regiao>>() {

			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter lista de regiões.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning("Falha ao obter lista de regiões. \n" + caught.getMessage(), 
						ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
			}

			@Override
			public void onSuccess(List<Regiao> result) {
				GWT.log("Sucesso ao obter regiões, regiões encontradas: " + result.size());
				getView().setManterRegiaoList(result);
				populaBairros(cidadeId);
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
				getView().mostrarWarning("Falha ao obter lista de bairros. \n" + caught.getMessage(), ApoioTerritorioLSConstants.INSTANCE.warningTimeout());					
			}
		});
	}

	@Override
	public void onPesquisaPesquisarEvent(final String identificadorCidade,
			final String nomeSurdo, final String regiaoId,
			final String identificadorMapa, final Boolean estaAssociadoMapa,
			final Boolean dispararPesquisa) {
		
		if (dispararPesquisa) {
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
					logger.log(Level.SEVERE, "Falha ao obter lista de pessoas.\n", caught);
					getView().hideWaitingPanel();
					getView().mostrarWarning("Falha ao obter lista de pessoas. \n" + caught.getMessage(), ApoioTerritorioLSConstants.INSTANCE.warningTimeout());		
				}
	
				@Override
				public void onSuccess(List<SurdoDetailsVO> result) {
					view.setResultadoPesquisa(result);
					getView().hideWaitingPanel();
				}
			});			
		} else {
			getView().setResultadoPesquisa(null);
		}
	}

	@Override
	public void adicionarOuAlterarSurdo(Surdo surdo, List<SurdoDetailsVO> surdos) {
		getView().showWaitingPanel();
		service.adicionarOuAlterarSurdo(surdo, surdos, new AsyncCallback<List<SurdoDetailsVO>>() {

			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao adicionar ou alterar pessoa.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning("Falha ao adicionar ou alterar pessoa. \n" + caught.getMessage(), ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
			}

			@Override
			public void onSuccess(List<SurdoDetailsVO> result) {
				getView().hideWaitingPanel();
				getView().mostrarWarning("Pessoa salva com sucesso.", ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
				getView().setResultadoPesquisa(result);
				eventBus.fireEvent(new PesquisarSurdoEvent("", "", "", "", null, false));
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
				logger.log(Level.SEVERE, "Falha ao obter pessoa.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning("Falha ao obter pessoa. \n" + caught.getMessage(), ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
			}
			
			@Override
			public void onSuccess(Surdo result) {
				view.onEditar(result);			
				getView().hideWaitingPanel();
			}
		});
	}

	@Override
	public void onVisualizar(Long id) {
		getView().showWaitingPanel();
		service.obterSurdoCompleto(id, new AsyncCallback<SurdoVO>() {
			@Override
			public void onFailure(Throwable caught) {
				logger.log(Level.SEVERE, "Falha ao obter pessoa.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning("Falha ao obter pessoa. \n" + caught.getMessage(), ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
			}

			@Override
			public void onSuccess(SurdoVO result) {
				view.onVisualizar(result);			
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
				logger.log(Level.SEVERE, "Falha ao apagar pessoa.\n", caught);
				getView().hideWaitingPanel();
				getView().mostrarWarning("Falha ao apagar pessoa. \n" + caught.getMessage(), ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
			}

			@Override
			public void onSuccess(Long result) {
				view.onApagarSurdo(result);
				getView().hideWaitingPanel();
				getView().mostrarWarning("Pessoa " + result + " apagada com sucesso. ", ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
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
				
				sb.append(cidade.getNome()).append(separador).append(cidade.getUF()).append(separador).append(cidade.getPais()).append(separador);
				
				Geocoder geocoder = new Geocoder();
				GeocoderRequest request = new GeocoderRequest();
				logger.info("Endereço de pesquisa: " + sb.toString());
				request.setAddress(sb.toString());
				request.setRegion("AR");
				geocoder.geocode(request, new GeocoderCallback() {
					@Override
					public void callback(List<HasGeocoderResult> responses, String status) {
						HasLatLng centro = roundLatLng(new LatLng(cidade.getLatitudeCentro(), cidade.getLongitudeCentro()));
						if ("OK".equals(status)) {
								// pegando a primeira resposta
							HasLatLng result = responses.get(0).getGeometry().getLocation();
							HasLatLng retorno = roundLatLng(new LatLng(result.getLatitude(), result.getLongitude()));
							boolean naoEncontrouEndereco = retorno.equals(centro);
							if (naoEncontrouEndereco) {
								retorno = new LatLng(cidade.getLatitudeCentroTerritorio(), cidade.getLongitudeCentroTerritorio());
							} else {
								retorno = new LatLng(result.getLatitude(), result.getLongitude());
							}
							view.setPosition(retorno, !naoEncontrouEndereco, true);
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
				getView().mostrarWarning("Falha ao buscar cidade. \n" + caught.getMessage(), ApoioTerritorioLSConstants.INSTANCE.warningTimeout());
			}
		});
		
	}
	
	private HasLatLng roundLatLng(HasLatLng latlng) {
		return new LatLng(Math.floor(latlng.getLatitude()*1000)/1000, Math.floor(latlng.getLongitude()*1000)/1000);
	}

	@Override
	public void onAdicionar() {
		getView().onAdicionar();
	}

	@Override
	public void onManterVoltarClick() {
		getView().setResultadoPesquisa(null);
		eventBus.fireEvent(new PesquisarSurdoEvent("", "", "", "", null, false));
	}

}
