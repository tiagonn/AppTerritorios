package br.com.nascisoft.apoioterritoriols.impressao.client.view;

import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.vo.SurdoVO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ImpressaoViewImpl extends Composite implements ImpressaoView {

	private static ImpressaoViewImplUiBinder uiBinder = GWT
			.create(ImpressaoViewImplUiBinder.class);

	@UiTemplate("ImpressaoViewUiBinder.ui.xml")
	interface ImpressaoViewImplUiBinder extends
			UiBinder<Widget, ImpressaoViewImpl> {
	}
	
	private Boolean paisagem;
	private Boolean imprimirCabecalho;
	private Boolean imprimirMapa;

	public ImpressaoViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public ImpressaoViewImpl(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setConfiguracoesImpressao(Boolean paisagem,
			Boolean imprimirCabecalho, Boolean imprimirMapa) {
		this.paisagem = paisagem;
		this.imprimirCabecalho = imprimirCabecalho;
		this.imprimirMapa = imprimirMapa;
	}

	@Override
	public void abrirImpressaoMapa(List<SurdoVO> surdos) {
		// TODO Auto-generated method stub
		
	}

}
