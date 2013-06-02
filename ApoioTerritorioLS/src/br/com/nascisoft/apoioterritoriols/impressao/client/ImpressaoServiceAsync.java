package br.com.nascisoft.apoioterritoriols.impressao.client;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.AbrirMapaVO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ImpressaoServiceAsync {

	void obterSurdosCompletos(Long identificadorMapa,
			AsyncCallback<AbrirMapaVO> callback);
	
}
