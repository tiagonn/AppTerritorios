package br.com.nascisoft.apoioterritoriols.admin.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.sf.jsr107cache.CacheManager;
import br.com.nascisoft.apoioterritoriols.admin.server.dao.AdminDAO;
import br.com.nascisoft.apoioterritoriols.admin.xml.BackupType;
import br.com.nascisoft.apoioterritoriols.admin.xml.MapaType;
import br.com.nascisoft.apoioterritoriols.admin.xml.SurdoType;
import br.com.nascisoft.apoioterritoriols.cadastro.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.cadastro.entities.Surdo;

import com.googlecode.objectify.Key;

public class RestauracaoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger
		.getLogger(BackupServlet.class.getName());
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		byte[] arquivoBackup = (byte[]) CacheManager.getInstance().getCache("ARQUIVO_UPLOAD").get("BACKUP");
				
		try {
			ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(arquivoBackup));
			zip.getNextEntry();
			JAXBContext context = JAXBContext.newInstance(BackupType.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			BackupType backup = (BackupType) unmarshaller.unmarshal(zip);
			
			AdminDAO dao = new AdminDAO();
			Map<Long, Long> mapaMapas = new HashMap<Long, Long>();
			
			for (MapaType mapa : backup.getMapas().getMapa()) {
				mapaMapas.put(mapa.getId(), dao.adicionarMapa(this.obterMapa(mapa)));
			}
			for (SurdoType surdo : backup.getSurdos().getSurdo()) {	
				dao.adicionarSurdo(this.obterSurdo(surdo, mapaMapas.get(surdo.getMapa())));
			}
		} catch (JAXBException e) {
			logger.log(Level.SEVERE, "Erro ao processar arquivo xml", e);
			throw new RuntimeException("Erro ao processar arquivo xml", e);
		}
	}
	
	private Mapa obterMapa(MapaType mapa) {
		Mapa retorno = new Mapa();
		retorno.setLetra(mapa.getLetra());
		retorno.setNumero(mapa.getNumero());
		retorno.setRegiao(mapa.getRegiao());
		return retorno;
	}
	
	private Surdo obterSurdo(SurdoType surdo, Long mapaId) {
		Surdo retorno = new Surdo();		
		
		retorno.setBairro(surdo.getBairro());
		retorno.setCep(surdo.getCep());
		retorno.setComplemento(surdo.getComplemento());
		retorno.setCrianca(surdo.getCrianca());
		retorno.setDvd(surdo.getDvd());
		retorno.setEstaAssociadoMapa(surdo.isEstaAssociadoMapa());
		retorno.setHorario(surdo.getHorario());
		retorno.setIdade(surdo.getIdade());
		retorno.setInstrutor(surdo.getInstrutor());
		retorno.setLatitude(surdo.getLatitude());
		retorno.setLibras(surdo.getLibras());
		retorno.setLogradouro(surdo.getLogradouro());
		retorno.setLongitude(surdo.getLongitude());
		if (mapaId != null) {
			retorno.setMapa(new Key<Mapa>(Mapa.class, mapaId));
		}
		retorno.setMelhorDia(surdo.getMelhorDia());
		retorno.setMsn(surdo.getMsn());
		retorno.setNome(surdo.getNome());
		retorno.setNumero(surdo.getNumero());
		retorno.setObservacao(surdo.getObservacao());
		retorno.setOnibus(surdo.getOnibus());
		retorno.setPossuiMSN(surdo.isPossuiMSN());
		retorno.setRegiao(surdo.getRegiao());
		retorno.setSexo(surdo.getSexo());
		retorno.setTelefone(surdo.getTelefone());
		retorno.setMudouSe(surdo.isMudouSe());
		retorno.setVisitarSomentePorAnciaos(surdo.isVisitarSomentePorAnciaos());
		
		return retorno;
	}

}
