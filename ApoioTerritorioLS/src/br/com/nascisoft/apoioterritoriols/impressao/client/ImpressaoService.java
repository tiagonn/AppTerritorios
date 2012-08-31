package br.com.nascisoft.apoioterritoriols.impressao.client;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ImpressaoService")
public interface ImpressaoService extends RemoteService {
	
	List<SurdoVO> obterSurdosCompletos(String nomeSurdo, String nomeRegiao, Long identificadorMapa);
	
}
