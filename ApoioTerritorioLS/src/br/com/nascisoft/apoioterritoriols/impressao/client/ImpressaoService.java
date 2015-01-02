package br.com.nascisoft.apoioterritoriols.impressao.client;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.AbrirMapaVO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ImpressaoService")
public interface ImpressaoService extends RemoteService {
	
	List<AbrirMapaVO> obterDadosImpressao(List<Long> mapasIDs);
	
}
