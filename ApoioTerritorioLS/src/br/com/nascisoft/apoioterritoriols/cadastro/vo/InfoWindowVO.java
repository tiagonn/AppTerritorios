package br.com.nascisoft.apoioterritoriols.cadastro.vo;

import com.google.gwt.maps.client.HasMap;
import com.google.gwt.maps.client.base.HasInfoWindow;
import com.google.gwt.maps.client.overlay.HasMarker;

public class InfoWindowVO {
	
	public InfoWindowVO() {
	}

	public InfoWindowVO(HasMap map, HasMarker marker, HasInfoWindow infoWindow) {
		this.map = map;
		this.marker = marker;
		this.infoWindow = infoWindow;
	}
	
	private HasMap map;
	private HasMarker marker;
	private HasInfoWindow infoWindow;
	
	public HasMap getMap() {
		return map;
	}

	public void setMap(HasMap map) {
		this.map = map;
	}

	public HasMarker getMarker() {
		return marker;
	}

	public void setMarker(HasMarker marker) {
		this.marker = marker;
	}

	public HasInfoWindow getInfoWindow() {
		return infoWindow;
	}

	public void setInfoWindow(HasInfoWindow infoWindow) {
		this.infoWindow = infoWindow;
	}
	
}