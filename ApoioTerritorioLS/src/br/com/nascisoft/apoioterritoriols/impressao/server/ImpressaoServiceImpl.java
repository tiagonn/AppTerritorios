package br.com.nascisoft.apoioterritoriols.impressao.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.admin.server.dao.AdminDAO;
import br.com.nascisoft.apoioterritoriols.cadastro.server.dao.CadastroDAO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.AbrirMapaVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;
import br.com.nascisoft.apoioterritoriols.impressao.client.ImpressaoService;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.login.server.AbstractApoioTerritorioLSService;

import com.googlecode.objectify.Key;

public class ImpressaoServiceImpl extends AbstractApoioTerritorioLSService implements
		ImpressaoService {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(ImpressaoServiceImpl.class.getName());
	
	private CadastroDAO getDao() {
		return CadastroDAO.INSTANCE;
	}
	
	private AdminDAO getAdminDao() {
		return AdminDAO.INSTANCE;
	}
	
	@Override
	public List<AbrirMapaVO> obterDadosImpressao(List<Long> mapasIDs) {
		List<AbrirMapaVO> retorno = new ArrayList<AbrirMapaVO>();
		for (Long mapaID : mapasIDs) {
			retorno.add(this.obterSurdosCompletos(mapaID));
		}
		return retorno;
	};
	
	private AbrirMapaVO obterSurdosCompletos(Long identificadorMapa) {
		logger.log(Level.INFO, "Obtendo surdos para impressao");
		
		List<Surdo> surdos = this.getDao().obterSurdos(null, null, null, identificadorMapa, null);
		
		Set<Key<Mapa>> chavesMapa = new HashSet<Key<Mapa>>();
		Set<Key<Regiao>> chavesRegiao = new HashSet<Key<Regiao>>();
		Set<Key<Cidade>> chavesCidade = new HashSet<Key<Cidade>>();
		
		for (Surdo surdo : surdos) {
			if (surdo.getMapa() != null) {
				chavesMapa.add(surdo.getMapa());
				chavesRegiao.add(surdo.getRegiao());
				chavesCidade.add(surdo.getCidade());
			} 
		}
		
		Map<Key<Mapa>, Mapa> mapas = this.getDao().obterMapas(chavesMapa);
		Map<Key<Regiao>, Regiao> mapasRegiao = this.getAdminDao().obterRegioes(chavesRegiao);
		Map<Key<Cidade>, Cidade> mapasCidade = this.getAdminDao().obterCidades(chavesCidade);
		
		List<SurdoVO> surdosVO = new ArrayList<SurdoVO>();
		for (Surdo surdo : surdos) {
			surdosVO.add(new SurdoVO(
					surdo, 
					mapas.get(surdo.getMapa()), 
					mapasRegiao.get(surdo.getRegiao()),
					mapasCidade.get(surdo.getCidade())));
		}
		
		Collections.sort(surdosVO, SurdoVO.COMPARATOR_LATITUDE);
		
		AbrirMapaVO vo = new AbrirMapaVO();
		
		vo.setSurdosImprimir(surdosVO);
		vo.setCidade(mapasCidade.get(surdos.get(0).getCidade()));
		vo.setRegiao(mapasRegiao.get(surdos.get(0).getRegiao()));
		
		return vo;
	}

		
}
