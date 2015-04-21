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
	
	
}
