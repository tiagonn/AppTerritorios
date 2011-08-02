package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.List;

import com.google.gwt.maps.client.base.HasLatLng;

import br.com.nascisoft.apoioterritoriols.cadastro.entities.Surdo;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoDetailsVO;

public interface CadastroSurdoView extends CadastroView {
	
	public interface Presenter extends CadastroView.Presenter {
		void onPesquisaPesquisarButtonClick(String nomeSurdo, String nomeRegiao, Long identificadorMapa);
		void onPesquisar();
		void onAdicionar();
		void adicionarOuAlterarSurdo(Surdo surdo);
		void onEditarButtonClick(Long id);
		void onEditar(Long id);
		void onApagar(Long id);
		void buscarEndereco(String logradouro, String numero, String bairro, String cep);
	}

	void setBairroList(List<String> bairros);
	void onPesquisar();
	void onEditar(Surdo surdo);
	void setResultadoPesquisa(List<SurdoDetailsVO> resultadoPesquisa);
	void onApagarSurdo(Long id);
	void onAdicionar();
	void setPresenter(Presenter presenter);
	void setPosition(HasLatLng position);

}
