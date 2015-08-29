package br.com.nascisoft.apoioterritoriols.resources.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {
	
	public static final Resources INSTANCE = GWT.create(Resources.class);
	
	@Source("/images/pessoa.png")
	ImageResource pessoa();
	
	@Source("/images/mapa.png")
	ImageResource mapa();
	
	@Source("/images/printer.png")
	ImageResource printer();
	
	@Source("/images/nao_visitar.png")
	ImageResource naoVisitar();
	
	@Source("/images/configuracao.png")
	ImageResource configuracao();
		
	@Source("/images/backup.png")
	ImageResource backup();
	
	@Source("/images/user.png")
	ImageResource user();
	
	@Source("/images/cidade.png")
	ImageResource cidade();
	
	@Source("/images/regiao.png")
	ImageResource regiao();
	
	@Source("/images/bairro.png")
	ImageResource bairro();
	
	@Source("/images/relatorios.png")
	ImageResource relatorios();
	
	@Source("/images/buscar.png")
	ImageResource buscar();
	
	@Source("/images/delete.png")
	ImageResource apagar();
	
	@Source("/images/edit.png")
	ImageResource editar();
	
	@Source("/images/visualizar.png")
	ImageResource visualizar();
	
	@Source("/images/adicionarPessoa.png")
	ImageResource adicionarPessoa();
	
	@Source("/images/salvar.png")
	ImageResource salvar();
	
	@Source("/images/voltar.png")
	ImageResource voltar();

	@Source("/images/adicionarMapa.png")
	ImageResource adicionarMapa();

	@Source("/images/para_baixo.png")
	ImageResource paraBaixo();
	
	@Source("/images/para_cima.png")
	ImageResource paraCima();
	
	@Source("/images/para_direita.png")
	ImageResource paraDireita();
	
	@Source("/images/para_esquerda.png")
	ImageResource paraEsquerda();
	
	@Source("/images/icone_azul.png")
	ImageResource iconeAzul();
	
	@Source("/images/icone_verde.png")
	ImageResource iconeVerde();
	
	@Source("/images/icone_vermelho.png")
	ImageResource iconeVermelho();
	
	@Source("/images/download.png")
	ImageResource download();
	
	@Source("/images/dropdown.png")
	ImageResource dropdown();
	
	@Source("/images/icone_branco_1.png")
	ImageResource iconeBranco1();
	
	@Source("/images/icone_branco_2.png")
	ImageResource iconeBranco2();
	
	@Source("/images/icone_branco_3.png")
	ImageResource iconeBranco3();
	
	@Source("/images/icone_branco_4.png")
	ImageResource iconeBranco4();
	
	@Source("/images/icone_branco_5.png")
	ImageResource iconeBranco5();
	
	@Source("/images/icone_branco_6.png")
	ImageResource iconeBranco6();
	
	@Source("/images/icone_branco_7.png")
	ImageResource iconeBranco7();
	
	@Source("/images/icone_branco_8.png")
	ImageResource iconeBranco8();
	
	@Source("/images/icone_branco_9.png")
	ImageResource iconeBranco9();
	
	@Source("/images/icone_branco_10.png")
	ImageResource iconeBranco10();
	
	
}
