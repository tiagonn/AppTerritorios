package br.com.nascisoft.apoioterritoriols.cadastro.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;


public class Centro implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double latitude;
	private Double longitude;
	
	@XmlElement
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	@XmlElement
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
}

