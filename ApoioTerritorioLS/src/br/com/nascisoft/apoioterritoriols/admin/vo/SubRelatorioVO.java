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
	private Map<String, Integer> mapaBairros = new HashMap<String, Integer>();
	private int consolidado = 0;
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
	public Map<String, Integer> getMapaBairros() {
		return mapaBairros;
	}
	public void setBairrosNoMapa(String bairro, Integer quantidade) {
		if (mapaBairros == null) {
			mapaBairros = new HashMap<String, Integer>();
		}
		mapaBairros.put(bairro, quantidade);
	}

	public int getConsolidado() {
		return consolidado;
	}
	
	public void setConsolidado(int consolidado) {
		this.consolidado = consolidado;
	}
	
	public void adiciona(String bairro) {
		
		this.consolidado++;

		Integer quantidadeNoBairro = this.mapaBairros.get(bairro);
		if (quantidadeNoBairro == null) {
			quantidadeNoBairro = 0;
		}
		quantidadeNoBairro++;
		this.mapaBairros.put(bairro, quantidadeNoBairro);

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
			.append("Quantidade de surdos: ").append(this.getConsolidado()).append("<br/><br/>").append("Divisão por bairros: <ul>");
		
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

}