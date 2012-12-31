package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.List;
import java.util.Map;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoDetailsVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;

import com.google.gwt.maps.client.base.HasLatLng;

public interface CadastroSurdoView extends CadastroView {
	
	public interface Presenter extends CadastroView.Presenter {
		void onPesquisaPesquisarButtonClick(String nomeSurdo, String nomeRegiao, String identificadorMapa, Boolean estaAssociadoMapa);
		void onPesquisaPesquisarEvent(String nomeSurdo, String nomeRegiao, String identificadorMapa, Boolean estaAssociadoMapa);
		void adicionarOuAlterarSurdo(Surdo surdo);
		void onEditarButtonClick(Long id);
		void onEditar(Long id);
		void onApagar(Long id);
		void buscarEndereco(String logradouro, String numero, String bairro, String cep);
		void onAdicionar();
	}

	void setPresenter(Presenter presenter);
	void setBairroList(List<String> bairros);
	void setResultadoPesquisa(List<SurdoDetailsVO> resultadoPesquisa);
	void onAdicionar();
	void setPosition(HasLatLng position, Boolean mostraMapa);
	void onEditar(Surdo surdo);
	void onApagarSurdo(Long id);
	Map<String, String> getDadosFiltro();
	void setDadosFiltro(Map<String, String> filtros);

}
