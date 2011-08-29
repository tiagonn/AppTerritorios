package br.com.nascisoft.apoioterritoriols.cadastro.server;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import br.com.nascisoft.apoioterritoriols.cadastro.client.CadastroService;
import br.com.nascisoft.apoioterritoriols.cadastro.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.cadastro.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.cadastro.server.dao.CadastroDAO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.AbrirMapaVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoDetailsVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;
import br.com.nascisoft.apoioterritoriols.cadastro.xml.Bairros;
import br.com.nascisoft.apoioterritoriols.cadastro.xml.Centro;
import br.com.nascisoft.apoioterritoriols.cadastro.xml.Regiao;
import br.com.nascisoft.apoioterritoriols.cadastro.xml.Regioes;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;

public class CadastroServiceImpl extends RemoteServiceServlet implements
		CadastroService {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(CadastroServiceImpl.class.getName());

	private static List<String> bairros = null;
	
	private static Regioes regioes = null;
	
	private CadastroDAO dao = null;
	
	private CadastroDAO getDao() {
		if (dao == null) {
			dao = new CadastroDAO();
		}
		return dao;
	}

	@Override
	public List<String> obterBairrosCampinas() {
		if (CadastroServiceImpl.bairros == null) {
			try {
				JAXBContext context = JAXBContext.newInstance(Bairros.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				CadastroServiceImpl.bairros = ((Bairros) unmarshaller
						.unmarshal(new File(
								"WEB-INF/classes/META-INF/BairrosCampinas.xml")))
						.getBairro();
			} catch (Exception ex) {
				logger.log(Level.SEVERE, "Erro ao recuperar lista de bairros",
						ex);
				throw new RuntimeException("Erro ao recuperar lista de bairros.", ex);
			}
		}
		return CadastroServiceImpl.bairros;
	}
	
	private void initRegioes() {
		if (CadastroServiceImpl.regioes == null) {
			try {
				JAXBContext context = JAXBContext.newInstance(Regioes.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				CadastroServiceImpl.regioes = ((Regioes) unmarshaller
						.unmarshal(new File(
								"WEB-INF/classes/META-INF/RegioesCampinas.xml")));
				
			} catch (Exception ex) {
				logger.log(Level.SEVERE, "Erro ao recuperar lista de regiões",
						ex);
				throw new RuntimeException("Erro ao recuperar lista de regiões.", ex);
			}
		}
	}
	
	private Regiao obterRegiao(String nomeRegiao) {
		initRegioes();
		Regiao retorno = null;
		for (Regiao regiao : CadastroServiceImpl.regioes.getRegiao()) {
			if (regiao.getNome().equals(nomeRegiao)) {
				retorno = regiao;
				break;
			}
		}
		return retorno;
	}

	@Override
	public List<String> obterRegioesCampinas() {
		initRegioes();
		List<String> retorno = new ArrayList<String>();
		for (Regiao regiao : regioes.getRegiao()) {
			retorno.add(regiao.getNome());
		}
		return retorno;
	}
	
	@Override
	public List<SurdoVO> obterSurdosCompletos(String nomeSurdo, String nomeRegiao, Long identificadorMapa) {
		List<SurdoVO> surdos = this.obterSurdosCompletos(nomeSurdo, nomeRegiao, identificadorMapa, null);
		Collections.sort(surdos, SurdoVO.COMPARATOR_ENDERECO);
		return surdos;
	}

	private List<SurdoVO> obterSurdosCompletos(String nomeSurdo, String nomeRegiao, Long identificadorMapa, Boolean estaAssociadoMapa) {
		List<SurdoVO> surdos = new ArrayList<SurdoVO>();
		for (Surdo surdo : this.getDao().obterSurdos(nomeSurdo, nomeRegiao, identificadorMapa, estaAssociadoMapa)) {
			Mapa mapa = null;
			if (surdo.getMapa() != null) {
				mapa = this.getDao().obterMapa(surdo.getMapa());
			} 
			surdos.add(new SurdoVO(surdo, mapa));
		}
		return surdos;
	}
	
	@Override
	public List<SurdoDetailsVO> obterSurdos(String nomeSurdo, String nomeRegiao, Long identificadorMapa, Boolean estaAssociadoMapa) {
		List<SurdoDetailsVO> surdos = new ArrayList<SurdoDetailsVO>();
		for (SurdoVO surdo : this.obterSurdosCompletos(nomeSurdo, nomeRegiao, identificadorMapa, estaAssociadoMapa)) {
			surdos.add(new SurdoDetailsVO(surdo));
		}
		
		return surdos;
	}

	@Override
	public List<Mapa> obterMapasRegiao(String nomeRegiao) {
		return this.getDao().obterMapasRegiao(nomeRegiao);
	}

	@Override
	public Long adicionarOuAlterarSurdo(Surdo surdo) {
		String operacao = surdo.getId() != null ? " alterado" : " adicionado";
		Long id = this.getDao().adicionarOuAlterarSurdo(surdo);
		logger.log(Level.INFO, "Surdo " + id + operacao + " com sucesso");
		return id;
	}

	@Override
	public Surdo obterSurdo(Long id) {
		return this.getDao().obterSurdo(id);
	}

	@Override
	public Long adicionarMapa(String nomeRegiao) {
		Regiao regiao = obterRegiao(nomeRegiao);
		return this.getDao().adicionarMapa(nomeRegiao, regiao.getLetra());
	}

	@Override
	public AbrirMapaVO obterInformacoesAbrirMapa(Long identificadorMapa) {
		AbrirMapaVO vo = new AbrirMapaVO();
		Mapa mapa = this.getDao().obterMapa(new Key<Mapa>(Mapa.class, identificadorMapa));
		
		vo.setMapa(mapa);
		
		List<SurdoDetailsVO> surdosPara = this.obterSurdos(null, mapa.getRegiao(), identificadorMapa, null);
		
		vo.setSurdosPara(surdosPara);
		
		List<SurdoDetailsVO> surdosDe = new ArrayList<SurdoDetailsVO>();
		
		for (Surdo surdo : this.getDao().obterSurdosSemMapa(mapa.getRegiao())) {
			surdosDe.add(new SurdoDetailsVO(surdo, null));
		}
		
		vo.setSurdosDe(surdosDe);
		
		List<SurdoDetailsVO> surdosOutros = new ArrayList<SurdoDetailsVO>();
		for (Surdo surdo: this.getDao().obterSurdosOutrosMapas(mapa.getRegiao(), identificadorMapa)) {
			surdosOutros.add(new SurdoDetailsVO(surdo, this.getDao().obterMapa(surdo.getMapa())));
		}
		
		vo.setSurdosOutros(surdosOutros);
		
		vo.setCentroRegiao(this.obterCentroRegiao(mapa.getRegiao()));
		
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
		List<Surdo> surdos = this.getDao().obterSurdos(null, null, identificadorMapa, null);
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
	
	private Centro obterCentroRegiao(String nomeRegiao) {
		Centro centro = null;
		for (Regiao regiao : CadastroServiceImpl.regioes.getRegiao()) {
			if (regiao.getNome().equals(nomeRegiao)) {
				centro = regiao.getCentro();
				break;
			}
		}
		return centro;
	}

}
