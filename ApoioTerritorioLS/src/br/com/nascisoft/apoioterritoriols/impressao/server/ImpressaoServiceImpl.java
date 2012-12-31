package br.com.nascisoft.apoioterritoriols.impressao.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.cadastro.server.dao.CadastroDAO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;
import br.com.nascisoft.apoioterritoriols.impressao.client.ImpressaoService;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.login.server.AbstractApoioTerritorioLSService;

import com.googlecode.objectify.Key;

public class ImpressaoServiceImpl extends AbstractApoioTerritorioLSService implements
		ImpressaoService {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(ImpressaoServiceImpl.class.getName());
	
	private CadastroDAO dao = null;
	
	private CadastroDAO getDao() {
		if (dao == null) {
			dao = new CadastroDAO();
		}
		return dao;
	}
	
	@Override
	public List<SurdoVO> obterSurdosCompletos(String nomeSurdo, String nomeRegiao, Long identificadorMapa) {
		logger.log(Level.INFO, "Obtendo surdos para impressao");
		List<SurdoVO> surdos = this.obterSurdosCompletos(nomeSurdo, nomeRegiao, identificadorMapa, null);
		Collections.sort(surdos, SurdoVO.COMPARATOR_ENDERECO);
		return surdos;
	}

	private List<SurdoVO> obterSurdosCompletos(String nomeSurdo, String nomeRegiao, Long identificadorMapa, Boolean estaAssociadoMapa) {
		List<Surdo> surdos = this.getDao().obterSurdos(nomeSurdo, nomeRegiao, identificadorMapa, estaAssociadoMapa);
		Map<Long, Key<Mapa>> chavesMapa = new HashMap<Long, Key<Mapa>>();
		for (Surdo surdo : surdos) {
			if (surdo.getMapa() != null) {
				chavesMapa.put(surdo.getId(), surdo.getMapa());
			} 
		}
		
		Map<Key<Mapa>, Mapa> mapas = this.getDao().obterMapas(chavesMapa.values());
		
		List<SurdoVO> surdosVO = new ArrayList<SurdoVO>();
		for (Surdo surdo : surdos) {
			surdosVO.add(new SurdoVO(surdo, mapas.get(surdo.getMapa())));
		}
		return surdosVO;
	}
	
}
