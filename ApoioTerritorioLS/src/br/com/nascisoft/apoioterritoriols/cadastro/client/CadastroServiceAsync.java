package br.com.nascisoft.apoioterritoriols.cadastro.client;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.cadastro.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.AbrirMapaVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoDetailsVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CadastroServiceAsync {
	
	void obterBairrosCampinas(AsyncCallback<List<String>> callback);

	void obterSurdos(String nomeSurdo, String nomeRegiao, Long identificadorMapa, Boolean estaAssociadoMapa, 
			AsyncCallback<List<SurdoDetailsVO>> callback);

	void obterSurdosCompletos(String nomeSurdo, String nomeRegiao, Long identificadorMapa,
			AsyncCallback<List<SurdoVO>> callback);

	void obterMapasRegiao(String nomeRegiao,
			AsyncCallback<List<Mapa>> callback);

	void adicionarOuAlterarSurdo(Surdo surdo, AsyncCallback<Long> callback);

	void obterSurdo(Long id, AsyncCallback<Surdo> callback);

	void adicionarMapa(String nomeRegiao, AsyncCallback<Long> callback);

	void obterRegioesCampinas(AsyncCallback<List<String>> callback);

	void adicionarSurdosMapa(List<Long> surdos, Long identificadorMapa,
			AsyncCallback<Long> callback);
	
	void removerSurdosMapa(List<Long> surdos, AsyncCallback<Long> callback);

	void obterInformacoesAbrirMapa(Long identificadorMapa,
			AsyncCallback<AbrirMapaVO> callback);

	void apagarSurdo(Long id, AsyncCallback<Long> callback);

	void apagarMapa(Long identificadorMapa, AsyncCallback<Void> callback);

}
