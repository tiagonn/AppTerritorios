package br.com.nascisoft.apoioterritoriols.cadastro.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoDetailsVO;
import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;
import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;

import com.google.gwt.maps.client.base.HasLatLng;

public interface CadastroSurdoView extends CadastroView {
	
	public interface Presenter extends CadastroView.Presenter {
		void onPesquisaPesquisarButtonClick(String identificadorCidade, String nomeSurdo, String nomeRegiao, String identificadorMapa, Boolean estaAssociadoMapa);
		void onPesquisaPesquisarEvent(String identificadorCidade, String nomeSurdo, String nomeRegiao, String identificadorMapa, Boolean estaAssociadoMapa);
		void adicionarOuAlterarSurdo(Surdo surdo);
		void onEditarButtonClick(Long id);
		void onEditar(Long id);
		void onApagar(Long id);
		void buscarEndereco(Long identificadorCidade, String logradouro, String numero, String bairro, String cep);
		void onAdicionar();
		void onManterCidadeListBoxChange(Long cidadeId);
		void onVisualizar(Long id);
		void populaCidades();
	}
	void setCidadeList(List<Cidade> lista);
	void setPresenter(Presenter presenter);
	void setManterRegiaoList(List<Regiao> regioes);
	void setBairroList(List<String> bairros);
	void setResultadoPesquisa(List<SurdoDetailsVO> resultadoPesquisa);
	void onAdicionar();
	void setPosition(HasLatLng position, Boolean sucesso, Boolean mostraMapa);
	void onEditar(Surdo surdo);
	void onApagarSurdo(Long id);
	void onVisualizar(SurdoVO surdo);

}
