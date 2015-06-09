package br.com.nascisoft.apoioterritoriols.cadastro.client;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.AbrirMapaVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.GeocoderResultVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoDetailsVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoNaoVisitarDetailsVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Bairro;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("CadastroService")
public interface CadastroService extends RemoteService {
	
	List<Cidade> obterCidades();
	
	Cidade obterCidade(Long identificadorCidade);

	List<Bairro> obterBairros(Long cidadeId);

	List<Regiao> obterRegioes(Long cidadeId);
	
	List<SurdoDetailsVO> obterSurdos(Long identificadorCidade, String nomeSurdo, Long regiaoId, Long identificadorMapa, Boolean estaAssociadoMapa);
	
	List<SurdoVO> obterSurdosCompletos(String nomeSurdo, Long regiaoId, Long identificadorMapa);
	
	AbrirMapaVO obterInformacoesAbrirMapa(Long identificadorMapa);
	
	List<Mapa> obterMapasRegiao(Long regiaoId);
	
	Long adicionarOuAlterarSurdo(Surdo surdo);
	
	Surdo obterSurdo(Long id);
	
	Long adicionarMapa(Long regiaoId);
	
	Long adicionarSurdosMapa(Set<Long> surdos, Long identificadorMapa);

	Long removerSurdosMapa(Set<Long> surdos);
	
	Long apagarSurdo(Long id);
	
	void apagarMapa(Long identificadorMapa);
	
	List<SurdoNaoVisitarDetailsVO> obterSurdosNaoVisitar();
	
	void retornarSurdoNaoVisitar(Long id);
	
	GeocoderResultVO buscarEndereco(String endereco) throws IOException;
	
	Boolean existePessoasNosMapas(List<Long> mapasIDs);

	SurdoVO obterSurdoCompleto(Long id);
	
}
