package br.com.nascisoft.apoioterritoriols.admin.server;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import br.com.nascisoft.apoioterritoriols.admin.server.dao.AdminDAO;
import br.com.nascisoft.apoioterritoriols.admin.xml.BackupType;
import br.com.nascisoft.apoioterritoriols.admin.xml.BairroType;
import br.com.nascisoft.apoioterritoriols.admin.xml.CidadeType;
import br.com.nascisoft.apoioterritoriols.admin.xml.MapaType;
import br.com.nascisoft.apoioterritoriols.admin.xml.RegiaoType;
import br.com.nascisoft.apoioterritoriols.admin.xml.SurdoType;
import br.com.nascisoft.apoioterritoriols.admin.xml.UsuarioType;
import br.com.nascisoft.apoioterritoriols.login.entities.Bairro;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.login.entities.Usuario;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.googlecode.objectify.Key;

public class RestauracaoServlet extends AbstractApoioTerritorioLSHttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger
		.getLogger(BackupServlet.class.getName());
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
			
		try {
			BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
			BlobKey blobKey = new BlobKey(req.getParameter("key"));
			String end = req.getParameter("end");
			byte[] arquivoBackup = blobstoreService.fetchData(blobKey, 0, Long.valueOf(end));
			ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(arquivoBackup));
			zip.getNextEntry();
			JAXBContext context = JAXBContext.newInstance(BackupType.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<BackupType> backup = 
					(JAXBElement<BackupType>)unmarshaller.unmarshal(
							new StreamSource(zip), BackupType.class);
			
			AdminDAO dao = new AdminDAO();
			for (UsuarioType user : backup.getValue().getUsuarios().getUsuario()) {
				dao.adicionarOuAtualizarUsuario(this.obterUsuario(user));
			}
			
			Map<Long, Long> mapaCidades = new HashMap<Long, Long>();
			for (CidadeType cidade : backup.getValue().getCidades().getCidade()) {
				mapaCidades.put(
						cidade.getIdentificador(), 
						dao.adicionarOuAtualizarCidade(this.obterCidade(cidade)));
			}
			
			Map<Long, Long> mapaRegioes = new HashMap<Long, Long>();
			for (RegiaoType regiao : backup.getValue().getRegioes().getRegiao()) {
				mapaRegioes.put(
						regiao.getIdentificador(),
						dao.adicionarOuAtualizarRegiao(
								this.obterRegiao(
										regiao, 
										mapaCidades.get(regiao.getIdentificadorCidade()))));
			}
			
			for (BairroType bairro : backup.getValue().getBairros().getBairro()) {
				dao.adicionarOuAtualizarBairro(this.obterBairro(
						bairro,
						mapaCidades.get(bairro.getIdentificadorCidade())));
			}
			
			
			Map<Long, Long> mapaMapas = new HashMap<Long, Long>();
			for (MapaType mapa : backup.getValue().getMapas().getMapa()) {
				mapaMapas.put(mapa.getIdentificador(), 
						dao.adicionarMapa(
								this.obterMapa(
										mapa,
										mapaRegioes.get(mapa.getIdentificadorRegiao()))));
			}
			
			
			for (SurdoType surdo : backup.getValue().getSurdos().getSurdo()) {	
				dao.adicionarSurdo(
						this.obterSurdo(
								surdo, 
								mapaCidades.get(surdo.getIdentificadorCidade()),
								mapaRegioes.get(surdo.getIdentificadorRegiao()),
								mapaMapas.get(surdo.getIdentificadorMapa())));
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erro ao processar arquivo xml. Exception logada mas não relançada.", e);
		}
	}
	
	private Usuario obterUsuario(UsuarioType xml) {
		Usuario retorno = new Usuario();
		
		retorno.setAdmin(xml.isAdmin());
		retorno.setEmail(xml.getEmail());
		
		return retorno;
	}
	
	private Cidade obterCidade(CidadeType xml) {
		Cidade retorno = new Cidade();
		
		retorno.setNome(xml.getNome());
		retorno.setPais(xml.getPais());
		retorno.setUF(xml.getUF());
		retorno.setLatitudeCentro(xml.getLatitudeCentro());
		retorno.setLongitudeCentro(xml.getLongitudeCentro());
		retorno.setLatitudeCentroTerritorio(xml.getLatitudeCentroTerritorio());
		retorno.setLongitudeCentroTerritorio(xml.getLongitudeCentroTerritorio());
		retorno.setQuantidadeSurdosMapa(xml.getQuantidadeSurdosMapa());
		retorno.setUtilizarBairroBuscaEndereco(xml.isUtilizarBairroBuscaEndereco());
		
		return retorno;
	}
	
	private Regiao obterRegiao(RegiaoType xml, Long identificadorCidade) {
		Regiao retorno = new Regiao();

		retorno.setNome(xml.getNome());
		retorno.setCidade(new Key<Cidade>(Cidade.class, identificadorCidade));
		retorno.setLetra(xml.getLetra());
		retorno.setZoom(xml.getNivelZoom());
		retorno.setLatitudeCentro(xml.getLatitudeCentro());
		retorno.setLongitudeCentro(xml.getLongitudeCentro());
		retorno.setCorLetra(xml.getCorLetra());
		retorno.setCorFundo(xml.getCorFundo());
		
		return retorno;		
	}
	
	private Bairro obterBairro(BairroType xml, Long identificadorCidade) {
		Bairro retorno = new Bairro();
		
		retorno.setNome(xml.getNome());
		retorno.setCidade(new Key<Cidade>(Cidade.class, identificadorCidade));
		
		return retorno;
	}
	
	private Mapa obterMapa(MapaType xml, Long identificadorRegiao) {
		Mapa retorno = new Mapa();
		
		retorno.setLetra(xml.getLetra());
		retorno.setNumero(xml.getNumero());
		retorno.setRegiao(new Key<Regiao>(Regiao.class, identificadorRegiao));
		
		return retorno;
	}
	
	private Surdo obterSurdo(SurdoType surdo, Long identificadorCidade, Long identificadorRegiao, Long identificadorMapa) {
		Surdo retorno = new Surdo();		
		
		retorno.setBairro(surdo.getBairro());
		retorno.setCep(surdo.getCep());
		retorno.setComplemento(surdo.getComplemento());
		retorno.setPublicacoesPossui(surdo.getPublicacoesPossui());
		retorno.setDvd(surdo.getDvd());
		retorno.setEstaAssociadoMapa(surdo.isEstaAssociadoMapa());
		retorno.setHorario(surdo.getHorario());
		retorno.setAnoNascimento(surdo.getAnoNascimento());
		retorno.setInstrutor(surdo.getInstrutor());
		retorno.setLatitude(surdo.getLatitude());
		retorno.setLibras(surdo.getLibras());
		retorno.setLogradouro(surdo.getLogradouro());
		retorno.setLongitude(surdo.getLongitude());
		if (identificadorMapa != null) {
			retorno.setMapa(new Key<Mapa>(Mapa.class, identificadorMapa));
		}
		retorno.setMelhorDia(surdo.getMelhorDia());
		retorno.setMsn(surdo.getMsn());
		retorno.setNome(surdo.getNome());
		retorno.setNumero(surdo.getNumero());
		retorno.setObservacao(surdo.getObservacao());
		retorno.setOnibus(surdo.getOnibus());
		retorno.setPossuiMSN(surdo.isPossuiMSN());
		retorno.setRegiao(new Key<Regiao>(Regiao.class, identificadorRegiao));
		retorno.setCidade(new Key<Cidade>(Cidade.class, identificadorCidade));
		retorno.setSexo(surdo.getSexo());
		retorno.setTelefone(surdo.getTelefone());
		retorno.setMudouSe(surdo.isMudouSe());
		retorno.setVisitarSomentePorAnciaos(surdo.isVisitarSomentePorAnciaos());
		retorno.setQtdePessoasEndereco(surdo.getQtdePessoasEndereco());
		
		return retorno;
	}

}
