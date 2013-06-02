package br.com.nascisoft.apoioterritoriols.cadastro.client;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.AbrirMapaVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoDetailsVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoNaoVisitarDetailsVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Bairro;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CadastroServiceAsync {

	void obterSurdos(Long identificadorCidade, String nomeSurdo, Long regiaoId,
			Long identificadorMapa, Boolean estaAssociadoMapa,
			AsyncCallback<List<SurdoDetailsVO>> callback);

	void obterSurdosCompletos(String nomeSurdo, Long regiaoId,
			Long identificadorMapa, AsyncCallback<List<SurdoVO>> callback);

	void obterMapasRegiao(Long regiaoId, AsyncCallback<List<Mapa>> callback);

	void adicionarOuAlterarSurdo(Surdo surdo, AsyncCallback<Long> callback);

	void obterSurdo(Long id, AsyncCallback<Surdo> callback);

	void adicionarMapa(Long regiaoId, AsyncCallback<Long> callback);

	void adicionarSurdosMapa(List<Long> surdos, Long identificadorMapa,
			AsyncCallback<Long> callback);
	
	void removerSurdosMapa(List<Long> surdos, AsyncCallback<Long> callback);

	void obterInformacoesAbrirMapa(Long identificadorMapa,
			AsyncCallback<AbrirMapaVO> callback);

	void apagarSurdo(Long id, AsyncCallback<Long> callback);

	void apagarMapa(Long identificadorMapa, AsyncCallback<Void> callback);

	void obterSurdosNaoVisitar(
			AsyncCallback<List<SurdoNaoVisitarDetailsVO>> callback);

	void retornarSurdoNaoVisitar(Long id, AsyncCallback<Void> callback);

	void obterCidades(AsyncCallback<List<Cidade>> callback);

	void obterBairros(Long cidadeId, AsyncCallback<List<Bairro>> callback);

	void obterRegioes(Long cidadeId, AsyncCallback<List<Regiao>> callback);

	void obterCidade(Long identificadorCidade, AsyncCallback<Cidade> callback);

}
