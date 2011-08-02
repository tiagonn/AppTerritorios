package br.com.nascisoft.apoioterritoriols.cadastro.client;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.cadastro.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.AbrirMapaVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoDetailsVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("CadastroService")
public interface CadastroService extends RemoteService {

	List<String> obterBairrosCampinas();

	List<String> obterRegioesCampinas();
	
	List<SurdoDetailsVO> obterSurdos(String nomeSurdo, String nomeRegiao, Long identificadorMapa);
	
	List<SurdoVO> obterSurdosCompletos(String nomeSurdo, String nomeRegiao, Long identificadorMapa);
	
	AbrirMapaVO obterInformacoesAbrirMapa(Long identificadorMapa);
	
	List<Mapa> obterMapasRegiao(String nomeRegiao);
	
	Long adicionarOuAlterarSurdo(Surdo surdo);
	
	Surdo obterSurdo(Long id);
	
	Long adicionarMapa(String nomeRegiao);
	
	Long adicionarSurdosMapa(List<Long> surdos, Long identificadorMapa);

	Long removerSurdosMapa(List<Long> surdos);
	
	Long apagarSurdo(Long id);
	
	void apagarMapa(Long identificadorMapa);
	
}
