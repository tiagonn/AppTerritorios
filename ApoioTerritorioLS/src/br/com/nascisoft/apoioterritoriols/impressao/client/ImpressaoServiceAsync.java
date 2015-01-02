package br.com.nascisoft.apoioterritoriols.impressao.client;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.AbrirMapaVO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ImpressaoServiceAsync {

	void obterDadosImpressao(List<Long> mapasIDs,
			AsyncCallback<List<AbrirMapaVO>> callback);
	
}
