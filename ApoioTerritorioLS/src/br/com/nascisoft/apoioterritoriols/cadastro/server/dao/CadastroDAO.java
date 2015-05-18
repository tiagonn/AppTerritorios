package br.com.nascisoft.apoioterritoriols.cadastro.server.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.login.server.dao.BaseDAO;

import com.googlecode.objectify.Key;

public class CadastroDAO extends BaseDAO {
	
	public static final CadastroDAO INSTANCE = new CadastroDAO(); 
	
//	private static final Logger logger = Logger.getLogger(CadastroDAO.class.getName());
	
	public Long adicionarOuAlterarSurdo(Surdo surdo) {
		return ofy().save().entity(surdo).now().getId();
	}
	
	public List<Surdo> obterSurdos(Long identificadorCidade, String nomeSurdo, Long regiaoId, Long identificadorMapa, Boolean estaAssociadoMapa) {

		Map<String, Object> mapa = new HashMap<String, Object>();
		if (identificadorCidade != null) {
			mapa.put("cidade", Key.create(Cidade.class, identificadorCidade));
		}
		if (nomeSurdo != null && nomeSurdo.length() > 0) {
			mapa.put("nome >=", nomeSurdo.toUpperCase());
			mapa.put("nome <", nomeSurdo.toUpperCase() + '\uFFFD');
		}
		if (regiaoId != null) {
			mapa.put("regiao", Key.create(Regiao.class, regiaoId));
		}
		if (identificadorMapa != null) {
			mapa.put("mapa", Key.create(Mapa.class, identificadorMapa));
		}
		if (estaAssociadoMapa != null) {
			mapa.put("estaAssociadoMapa", estaAssociadoMapa);
		}
		mapa.put("mudouSe", Boolean.FALSE);
		mapa.put("visitarSomentePorAnciaos", Boolean.FALSE);
		
		return aplicarFiltro(mapa, Surdo.class, "nome");
	}
	
	public Mapa obterMapa(Key<Mapa> chave) {
		return ofy().load().key(chave).now();
	}
	
	public Map<Key<Mapa>, Mapa> obterMapas(Collection<Key<Mapa>> chave) {
		return ofy().load().keys(chave);
	}
	
	public Surdo obterSurdo(Long id) {
		return ofy().load().type(Surdo.class).id(id).now();
	}
	
	public Long adicionarMapa(Long regiaoId, String letra) {
		List<Mapa> mapas = this.obterMapasRegiao(regiaoId);
		Integer i = 1;
		for (Mapa mapa : mapas) {
			if (i.equals(mapa.getNumero())) {
				i++;
			} else {
				break;
			}
		}
		
		Mapa mapa = new Mapa();
		mapa.setNumero(i);
		mapa.setLetra(letra);
		mapa.setRegiao(Key.create(Regiao.class, regiaoId));
		
		return ofy().save().entity(mapa).now().getId();		
	}
	
	public List<Mapa> obterMapasRegiao(Long regiaoId) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		if (regiaoId != null) {
			mapa.put("regiao", Key.create(Regiao.class, regiaoId));
		}
		return aplicarFiltro(mapa, Mapa.class, "numero");
	}
	
	public List<Surdo> obterSurdosSemMapa(Long regiaoId) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		if (regiaoId != null) {
			mapa.put("regiao", Key.create(Regiao.class, regiaoId));
		}
		mapa.put("estaAssociadoMapa", Boolean.FALSE);
		mapa.put("mudouSe", Boolean.FALSE);
		mapa.put("visitarSomentePorAnciaos", Boolean.FALSE);

		return aplicarFiltro(mapa, Surdo.class, "nome");
	}
	
	public List<Surdo> obterSurdosOutrosMapas(Long regiaoId, Long identificadorMapa) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		if (regiaoId != null) {
			mapa.put("regiao", Key.create(Regiao.class, regiaoId));
		}
		if (identificadorMapa != null) {
			mapa.put("mapa !=", Key.create(Mapa.class, identificadorMapa));
		}
		mapa.put("estaAssociadoMapa", Boolean.TRUE);
		mapa.put("mudouSe", Boolean.FALSE);
		mapa.put("visitarSomentePorAnciaos", Boolean.FALSE);
		
		return aplicarFiltro(mapa, Surdo.class, null);
	}
	
	public void adicionarSurdoMapa(Set<Long> surdos, Long identificadorMapa) {
		for (Long id : surdos) {
			Surdo surdo = ofy().load().type(Surdo.class).id(id).now();
			surdo.setMapa(Key.create(Mapa.class, identificadorMapa));
			ofy().save().entity(surdo);
		}
	}
	
	public Long removerSurdoMapa(Set<Long> surdos) {
		Long retorno = null;
		for (Long id : surdos) {
			Surdo surdo = ofy().load().type(Surdo.class).id(id).now();
			if (retorno == null) {
				retorno = surdo.getMapa().getId();
			}
			surdo.setMapa(null);
			ofy().save().entity(surdo);
		}
		return retorno;
	}
	
	public void apagarSurdo(Long id) {
		ofy().delete().type(Surdo.class).id(id);
	}
	
	public void apagarMapa(Long identificadorMapa) {
		ofy().delete().type(Mapa.class).id(identificadorMapa);
	}
	
	public List<Surdo> obterSurdosNaoVisitar() {
		List<Surdo> retorno = new ArrayList<Surdo>();
		
		retorno.addAll(ofy().load().type(Surdo.class).filter("mudouSe", Boolean.TRUE).list());
		retorno.addAll(ofy().load().type(Surdo.class).filter("visitarSomentePorAnciaos", Boolean.TRUE).list());
		
		return retorno;
	}
}
