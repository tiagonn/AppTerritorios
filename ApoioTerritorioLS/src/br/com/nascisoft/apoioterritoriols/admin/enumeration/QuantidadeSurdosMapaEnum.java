package br.com.nascisoft.apoioterritoriols.admin.enumeration;

public enum QuantidadeSurdosMapaEnum {
	UM(1), 
	QUATRO(4);
	
	private Integer qtde;
	
	QuantidadeSurdosMapaEnum(Integer qtde) {
		this.qtde = qtde;
	}
	
	public Integer getQtde() {
		return qtde;
	}
}
