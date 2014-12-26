package br.com.nascisoft.apoioterritoriols.cadastro.vo;

import java.io.Serializable;

public class GeocoderResultVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Double lat;
	private Double lng;
	private String status;
	
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
