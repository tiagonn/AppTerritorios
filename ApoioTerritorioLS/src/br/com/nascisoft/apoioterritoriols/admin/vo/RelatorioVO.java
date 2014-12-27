package br.com.nascisoft.apoioterritoriols.admin.vo;

import java.util.HashMap;
import java.util.Map;

import com.googlecode.objectify.Key;

import br.com.nascisoft.apoioterritoriols.login.entities.Cidade;
import br.com.nascisoft.apoioterritoriols.login.entities.Regiao;
import br.com.nascisoft.apoioterritoriols.login.entities.Surdo;

public class RelatorioVO extends HashMap<String, SubRelatorioVO> {

	private static final long serialVersionUID = 1L;
	
	public void adiciona(Surdo surdo) {
		SubRelatorioVO sub = this.get(String.valueOf(surdo.getRegiao().getId()));
		if (sub == null) {
			sub = new SubRelatorioVO();
			this.put(String.valueOf(surdo.getRegiao().getId()), sub);
			sub.setKeyCidade(surdo.getCidade());
			sub.setKeyRegiao(surdo.getRegiao());
		}
		
		String bairro = surdo.getBairro();
		if (bairro == null || bairro.length() == 0) {
			bairro = "Não informado";
		}

		sub.adiciona(bairro, surdo.getQtdePessoasEndereco());
	}
	
	public void acertaReferencias(Map<Key<Cidade>, Cidade> mapaCidades, Map<Key<Regiao>, Regiao> mapaRegiao) {
		
		RelatorioVO newThis = new RelatorioVO();
		
		for (String regiao : this.keySet()) {
				// acertando o nome da cidade;
			String nomeCidade = mapaCidades.get(this.get(regiao).getKeyCidade()).getNome();
			this.get(regiao).setCidade(nomeCidade);
			
				// acertando o nome da região
			String nomeRegiao = mapaRegiao.get(this.get(regiao).getKeyRegiao()).getNomeRegiaoCompleta();
			newThis.put(nomeRegiao, this.get(regiao));
		}
		
		this.clear();
		
		this.putAll(newThis);
		
	}

}
