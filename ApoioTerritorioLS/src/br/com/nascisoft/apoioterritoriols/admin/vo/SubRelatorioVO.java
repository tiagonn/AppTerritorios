package br.com.nascisoft.apoioterritoriols.admin.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;

import com.googlecode.objectify.Key;

public class SubRelatorioVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, String> mapaBairros = new HashMap<String, String>();
	private int consolidadoEnderecos = 0;
	private int consolidadoPessoas = 0;
	private String cidade;
	private Key<Regiao> keyRegiao;
	private Key<Cidade> keyCidade;
	
	public Key<Regiao> getKeyRegiao() {
		return keyRegiao;
	}
	public void setKeyRegiao(Key<Regiao> keyRegiao) {
		this.keyRegiao = keyRegiao;
	}
	public Key<Cidade> getKeyCidade() {
		return keyCidade;
	}
	public void setKeyCidade(Key<Cidade> keyCidade) {
		this.keyCidade = keyCidade;
	}
	public Map<String, String> getMapaBairros() {
		return mapaBairros;
	}
	public void setBairrosNoMapa(String bairro, String quantidade) {
		if (mapaBairros == null) {
			mapaBairros = new HashMap<String, String>();
		}
		mapaBairros.put(bairro, quantidade);
	}

	public int getConsolidadoEnderecos() {
		return consolidadoEnderecos;
	}
	
	public void setConsolidadoEnderecos(int consolidado) {
		this.consolidadoEnderecos = consolidado;
	}
	
	public void adiciona(String bairro, Byte qtdePessoas) {
		
		this.consolidadoEnderecos++;
		this.setConsolidadoPessoas(this.getConsolidadoPessoas()+qtdePessoas);

		String quantidadeNoBairro = this.mapaBairros.get(bairro);
		if (quantidadeNoBairro == null) {
			quantidadeNoBairro = "0/0";
		}
		String[] quantidades = quantidadeNoBairro.split("/");
		int quantidadePessoas = Integer.valueOf(quantidades[0]);
		int quantidadeEnderecos = Integer.valueOf(quantidades[1]);
		quantidadePessoas += qtdePessoas;
		quantidadeEnderecos++;
		
		this.mapaBairros.put(bairro, quantidadePessoas+"/"+quantidadeEnderecos);

	}
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	

	public String obtemDetalhesHTML(String nomeRegiao) {
		StringBuilder ret = new StringBuilder();
		
		ret
			.append("Cidade: <strong>").append(this.cidade).append("</strong><br/>")
			.append("Região: <strong>").append(nomeRegiao).append("</strong><br/>")
			.append("Quantidade de Pessoas: ").append(this.getConsolidadoPessoas()).append("<br/>")
			.append("Quantidade de Enderecos: ").append(this.getConsolidadoEnderecos()).append("<br/><br/>")
			.append("Divisão por bairros (pessoas/enderecos): <ul>");
		
		Set<String> chaves = mapaBairros.keySet();
		List<String> chavesOrdenadas = new ArrayList<String>();
		chavesOrdenadas.addAll(chaves);
		Collections.sort(chavesOrdenadas);
		
		String chaveNaoInformado = "Não informado";
		
		for (String chave : chavesOrdenadas) {
			if (!chaveNaoInformado.equals(chave)) {
				ret.append("<li>").append(chave).append(": ").append(mapaBairros.get(chave)).append("</li>");
			}
		}
		if (mapaBairros.get(chaveNaoInformado) != null) {
			ret.append("<li>").append(chaveNaoInformado).append(": ").append(mapaBairros.get(chaveNaoInformado)).append("</li>");
		}
		ret.append("</ul>");
		
		return ret.toString();
	}
	public int getConsolidadoPessoas() {
		return consolidadoPessoas;
	}
	public void setConsolidadoPessoas(int consolidadoPessoas) {
		this.consolidadoPessoas = consolidadoPessoas;
	}

}