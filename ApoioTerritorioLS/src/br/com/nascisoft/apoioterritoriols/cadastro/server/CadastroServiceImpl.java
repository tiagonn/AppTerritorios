package br.com.nascisoft.apoioterritoriols.cadastro.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.nascisoft.apoioterritoriols.admin.server.dao.AdminDAO;
import br.com.nascisoft.apoioterritoriols.cadastro.client.CadastroService;
import br.com.nascisoft.apoioterritoriols.cadastro.server.dao.CadastroDAO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.AbrirMapaVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoDetailsVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoNaoVisitarDetailsVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Bairro;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.login.server.AbstractApoioTerritorioLSService;

import com.googlecode.objectify.Key;

public class CadastroServiceImpl extends AbstractApoioTerritorioLSService implements
		CadastroService {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(CadastroServiceImpl.class.getName());
	
	private CadastroDAO dao = null;
	
	private AdminDAO adminDao = null;
	
	private CadastroDAO getDao() {
		if (dao == null) {
			dao = new CadastroDAO();
		}
		return dao;
	}
	
	private AdminDAO getAdminDao() {
		if (adminDao == null) {
			adminDao = new AdminDAO();
		}
		return adminDao;
	}
	
	@Override
	public List<Cidade> obterCidades() {
		return getAdminDao().obterCidades();
	}
	
	@Override
	public List<Regiao> obterRegioes(Long cidadeId) {
		return getAdminDao().buscarRegioes(cidadeId);
	}
	
	@Override
	public List<Bairro> obterBairros(Long cidadeId) {
		return getAdminDao().buscarBairros(cidadeId);
	}

	
	@Override
	public List<SurdoVO> obterSurdosCompletos(String nomeSurdo, Long regiaoId, Long identificadorMapa) {
		List<SurdoVO> surdos = this.obterSurdosCompletos(null, nomeSurdo, regiaoId, identificadorMapa, null);
		Collections.sort(surdos, SurdoVO.COMPARATOR_ENDERECO);
		return surdos;
	}

	private List<SurdoVO> obterSurdosCompletos(Long identificadorCidade, String nomeSurdo, Long regiaoId, Long identificadorMapa, Boolean estaAssociadoMapa) {
		List<Surdo> surdos = this.getDao().obterSurdos(identificadorCidade, nomeSurdo, regiaoId, identificadorMapa, estaAssociadoMapa);

		Set<Key<Mapa>> chavesMapa = new HashSet<Key<Mapa>>();
		Set<Key<Regiao>> chavesRegiao = new HashSet<Key<Regiao>>();
		Set<Key<Cidade>> chavesCidade = new HashSet<Key<Cidade>>();

		for (Surdo surdo : surdos) {
			if (surdo.getMapa() != null) {
				chavesMapa.add(surdo.getMapa());
			}
				chavesRegiao.add(surdo.getRegiao());
				chavesCidade.add(surdo.getCidade());
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
		
		return surdosVO;
	}
	
	@Override
	public List<SurdoDetailsVO> obterSurdos(Long identificadorCidade, String nomeSurdo, Long regiaoId, Long identificadorMapa, Boolean estaAssociadoMapa) {
		List<SurdoDetailsVO> surdos = new ArrayList<SurdoDetailsVO>();
		for (SurdoVO surdo : this.obterSurdosCompletos(identificadorCidade, nomeSurdo, regiaoId, identificadorMapa, estaAssociadoMapa)) {
			surdos.add(new SurdoDetailsVO(surdo));
		}
		
		return surdos;
	}

	@Override
	public List<Mapa> obterMapasRegiao(Long regiaoId) {
		return this.getDao().obterMapasRegiao(regiaoId);
	}

	@Override
	public Long adicionarOuAlterarSurdo(Surdo surdo) {
		String operacao = surdo.getId() != null ? " alterado" : " adicionado";
		if (surdo.isMudouSe() || surdo.isVisitarSomentePorAnciaos()) {
			surdo.setMapa(null);
		}
		surdo.setCidade(new Key<Cidade>(Cidade.class, surdo.getCidadeId()));
		surdo.setRegiao(new Key<Regiao>(Regiao.class, surdo.getRegiaoId()));
		Long id = this.getDao().adicionarOuAlterarSurdo(surdo);
		logger.log(Level.INFO, "Surdo " + id + operacao + " com sucesso");
		
		if (surdo.getMapaAnterior() != null) {
			List<Surdo> surdos = dao.obterSurdos(null, null, null, surdo.getMapaAnterior(), null);
			for (Surdo temp : surdos) {
				if (temp.getId().equals(surdo.getId())) {
					surdos.remove(temp);
				}
			}
			if (surdos.size() == 0) {
				dao.apagarMapa(surdo.getMapaAnterior());			
			}
		}
		
		return id;
	}

	@Override
	public Surdo obterSurdo(Long id) {
		return this.getDao().obterSurdo(id);
	}

	@Override
	public Long adicionarMapa(Long regiaoId) {
		Regiao regiao = this.getAdminDao().obterRegiao(regiaoId);
		return this.getDao().adicionarMapa(regiaoId, regiao.getLetra());
	}

	@Override
	public AbrirMapaVO obterInformacoesAbrirMapa(Long identificadorMapa) {
		AbrirMapaVO vo = new AbrirMapaVO();
		Mapa mapa = this.getDao().obterMapa(new Key<Mapa>(Mapa.class, identificadorMapa));
		Regiao regiao = this.getAdminDao().obterRegiao(mapa.getRegiao().getId());
		Cidade cidade = this.getAdminDao().obterCidade(regiao.getCidade().getId());
		
		vo.setMapa(mapa);
		vo.setRegiao(regiao);
		vo.setCidade(cidade);
		
		List<SurdoDetailsVO> surdosPara = this.obterSurdos(null, null, mapa.getRegiao().getId(), identificadorMapa, null);
		
		vo.setSurdosPara(surdosPara);
		
		List<SurdoDetailsVO> surdosDe = new ArrayList<SurdoDetailsVO>();
		
		for (Surdo surdo : this.getDao().obterSurdosSemMapa(mapa.getRegiao().getId())) {
			surdosDe.add(new SurdoDetailsVO(surdo, null, regiao, cidade));
		}
		
		vo.setSurdosDe(surdosDe);
		
		List<SurdoDetailsVO> surdosOutros = new ArrayList<SurdoDetailsVO>();
		
		List<Surdo> surdosOutrosMapas = this.getDao().obterSurdosOutrosMapas(mapa.getRegiao().getId(), identificadorMapa);
		
		Set<Key<Mapa>> chavesMapa = new HashSet<Key<Mapa>>();

		for (Surdo surdo : surdosOutrosMapas) {
			if (surdo.getMapa() != null) {
				chavesMapa.add(surdo.getMapa());
			} 
		}
		
		Map<Key<Mapa>, Mapa> mapas = this.getDao().obterMapas(chavesMapa);
		
		for (Surdo surdo: surdosOutrosMapas) {
			surdosOutros.add(new SurdoDetailsVO(surdo, mapas.get(surdo.getMapa()), regiao, cidade));
		}
		
		vo.setSurdosOutros(surdosOutros);

		vo.getCentroRegiao().setLatitude(regiao.getLatitudeCentro());
		vo.getCentroRegiao().setLongitude(regiao.getLongitudeCentro());
		
		return vo;
	}

	@Override
	public Long adicionarSurdosMapa(List<Long> surdos, Long identificadorMapa) {
		this.getDao().adicionarSurdoMapa(surdos, identificadorMapa);
		return identificadorMapa;
	}
	
	@Override
	public Long removerSurdosMapa(List<Long> surdos) {
		return this.getDao().removerSurdoMapa(surdos);
	}

	@Override
	public Long apagarSurdo(Long id) {
		this.getDao().apagarSurdo(id);
		return id;
	}

	@Override
	public void apagarMapa(Long identificadorMapa) {
		List<Surdo> surdos = this.getDao().obterSurdos(null, null, null, identificadorMapa, null);
		List<Long> lista = this.obterListaIds(surdos);
		this.removerSurdosMapa(lista);
		this.getDao().apagarMapa(identificadorMapa);
	}
	
	private List<Long> obterListaIds(List<Surdo> lista) {
		List<Long> retorno = new ArrayList<Long>();
		for (Surdo surdo : lista) {
			retorno.add(surdo.getId());
		}
		return retorno;
	}

	@Override
	public List<SurdoNaoVisitarDetailsVO> obterSurdosNaoVisitar() {
		List<SurdoNaoVisitarDetailsVO> retorno = new ArrayList<SurdoNaoVisitarDetailsVO>();
		List<Surdo> surdos = getDao().obterSurdosNaoVisitar();
		
		Set<Key<Regiao>> chavesRegiao = new HashSet<Key<Regiao>>();
		Set<Key<Cidade>> chavesCidade = new HashSet<Key<Cidade>>();
		
		for (Surdo surdo : surdos) {
			chavesRegiao.add(surdo.getRegiao());
			chavesCidade.add(surdo.getCidade());
		}
		
		Map<Key<Regiao>, Regiao> mapasRegiao = this.getAdminDao().obterRegioes(chavesRegiao);
		Map<Key<Cidade>, Cidade> mapasCidade = this.getAdminDao().obterCidades(chavesCidade);
		
		for (Surdo surdo : surdos) {
			retorno.add(
					new SurdoNaoVisitarDetailsVO(
							surdo, 
							mapasRegiao.get(surdo.getRegiao()),
							mapasCidade.get(surdo.getCidade())));
		}
		
		Collections.sort(retorno, SurdoNaoVisitarDetailsVO.COMPARATOR_NOME);
		
		return retorno;
	}

	@Override
	public void retornarSurdoNaoVisitar(Long id) {
		Surdo surdo = getDao().obterSurdo(id);
		surdo.setMudouSe(Boolean.FALSE);
		surdo.setVisitarSomentePorAnciaos(Boolean.FALSE);
		getDao().adicionarOuAlterarSurdo(surdo);
	}

	@Override
	public Cidade obterCidade(Long identificadorCidade) {
		return this.getAdminDao().obterCidade(identificadorCidade);
	}
}
