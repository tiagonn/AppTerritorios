package br.com.nascisoft.apoioterritoriols.impressao.client;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ImpressaoServiceAsync {

	void obterSurdosCompletos(String nomeSurdo, String nomeRegiao, Long identificadorMapa,
			AsyncCallback<List<SurdoVO>> callback);
	
}
