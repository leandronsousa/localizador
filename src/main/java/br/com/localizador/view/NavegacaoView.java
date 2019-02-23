package br.com.localizador.view;

import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class NavegacaoView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -183938349211125403L;

	private boolean mapView;

	public HttpServletRequest getRequest() { 
		FacesContext ctx = getFacesContext();
		ExternalContext exc = ctx.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) exc.getRequest();
		return request;
	}
	
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	public boolean isMapView() {
		return mapView;
	}

	public void setMapView(boolean mapView) {
		this.mapView = mapView;
	}
	
}
