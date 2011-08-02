package br.com.nascisoft.apoioterritoriols.cadastro.vo;

import java.io.Serializable;
import java.util.List;

import br.com.nascisoft.apoioterritoriols.cadastro.entities.Mapa;
import br.com.nascisoft.apoioterritoriols.cadastro.xml.Centro;

public class AbrirMapaVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Mapa mapa;
	private Centro centroRegiao;
	private List<SurdoDetailsVO> surdosDe;
	private List<SurdoDetailsVO> surdosPara;
	private List<SurdoDetailsVO> surdosOutros;
	
	
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
	public void setCentroRegiao(Centro centroRegiao) {
		this.centroRegiao = centroRegiao;
	}
	public Centro getCentroRegiao() {
		return centroRegiao;
	}
	public void setSurdosOutros(List<SurdoDetailsVO> surdosOutros) {
		this.surdosOutros = surdosOutros;
	}
	public List<SurdoDetailsVO> getSurdosOutros() {
		return surdosOutros;
	}

}
