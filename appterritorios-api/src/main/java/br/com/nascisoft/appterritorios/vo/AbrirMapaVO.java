package br.com.nascisoft.appterritorios.vo;

import java.io.Serializable;
import java.util.List;

import br.com.nascisoft.appterritorios.entities.Cidade;
import br.com.nascisoft.appterritorios.entities.Mapa;
import br.com.nascisoft.appterritorios.entities.Regiao;

public class AbrirMapaVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Mapa mapa;
	private CentroVO centroRegiao;
	private List<SurdoDetailsVO> surdosDe;
	private List<SurdoDetailsVO> surdosPara;
	private List<SurdoDetailsVO> surdosOutros;
	private Regiao regiao;
	private Cidade cidade;
	private List<SurdoVO> surdosImprimir;
	
	
	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}
	public Mapa getMapa() {
		return mapa;
	}
	public List<SurdoDetailsVO> getSurdosDe() {
		return surdosDe;
	}
	public void setSurdosDe(List<SurdoDetailsVO> surdosDe) {
		this.surdosDe = surdosDe;
	}
	public List<SurdoDetailsVO> getSurdosPara() {
		return surdosPara;
	}
	public void setSurdosPara(List<SurdoDetailsVO> surdosPara) {
		this.surdosPara = surdosPara;
	}
	public void setCentroRegiao(CentroVO centroRegiao) {
		this.centroRegiao = centroRegiao;
	}
	public CentroVO getCentroRegiao() {
		if (centroRegiao == null) {
			centroRegiao = new CentroVO();
		}
		return centroRegiao;
	}
	public void setSurdosOutros(List<SurdoDetailsVO> surdosOutros) {
		this.surdosOutros = surdosOutros;
	}
	public List<SurdoDetailsVO> getSurdosOutros() {
		return surdosOutros;
	}
	public Regiao getRegiao() {
		return regiao;
	}
	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public List<SurdoVO> getSurdosImprimir() {
		return surdosImprimir;
	}
	public void setSurdosImprimir(List<SurdoVO> surdosImprimir) {
		this.surdosImprimir = surdosImprimir;
	}

}
