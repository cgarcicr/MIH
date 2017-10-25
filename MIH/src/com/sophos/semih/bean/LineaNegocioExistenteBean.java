package com.sophos.semih.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.model.TMihLineanegocio;
import com.sophos.semih.service.LineaNegocioManager;

/**
 * Clase que soporta la tabla de lineas de negocio existentes. El bean debe ser
 * llamado como 'lineaNegocioExistente'
 * 
 * @author sebastian.duque
 * 
 */
@ManagedBean(name = "lineaNegocioExistente")
@ViewScoped
public class LineaNegocioExistenteBean extends BaseBean {

	private static final long serialVersionUID = 863204900360921123L;
	private List<TMihLineanegocio> lineasNegocio;
	private String error;
	LineaNegocioManager manager;
	transient BeanFactory factory = FacesContextUtils
			.getWebApplicationContext(FacesContext.getCurrentInstance());
	private TMihLineanegocio lnEditada;

	public LineaNegocioExistenteBean() {
		setManager((LineaNegocioManager) factory.getBean("lineaNegocioManager"));
		setLineasNegocio(manager.getLineaNegocios(new TMihLineanegocio()));
	}

	/**
	 * Elimina el proyecto seleccionado, siempre y cuando no existan lineas de
	 * negocio asociadas al mismo.
	 */
	public void eliminar() {
		try {
			manager.deleteLineaNegocio(lnEditada);			
			setLineasNegocio(manager.getLineaNegocios(new TMihLineanegocio()));
		} catch (DataIntegrityViolationException e) {
//			error = "Existen aplicaciones atadas a esta línea de negocio";
//			error = super.getMessage("ln.eliminar.error", null);
						
			FacesContext context = FacesContext.getCurrentInstance();	         
	        context.addMessage(null, new FacesMessage("Mensaje",super.getMessage("ln.eliminar.error", null)) );
	        
			
		} catch (Exception e) {
			error = "Error al eliminar. Intente nuevamente.";
		}
	}

	/**
	 * Actualiza los detalles del proyecto seleccionado.
	 */
	public void editar() {
		try {
			lnEditada.setNombre(lnEditada.getNombre().trim());
			manager.updateLineaNegocio(lnEditada);
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		} catch (Exception e) {
			error = "Error al editar la linea de negocio. Verifique que no exista otra con el mismo nombre. ";
		}
	}
	
	/**
	 * Permite refrescar los valores de la vista.
	 */
	public void refrescarValores(){
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("form:editarNombre");
		ids.add("form:editarProy");
		ids.add("form:estado");
		ids.add("form:descripcion");
		super.resetValues(ids);
	}

	// Getters y Setters
	public LineaNegocioManager getManager() {
		return manager;
	}

	public void setManager(LineaNegocioManager manager) {
		this.manager = manager;
	}

	public BeanFactory getFactory() {
		return factory;
	}

	public void setFactory(BeanFactory factory) {
		this.factory = factory;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<TMihLineanegocio> getLineasNegocio() {
		return lineasNegocio;
	}

	public void setLineasNegocio(List<TMihLineanegocio> lineasNegocio) {
		this.lineasNegocio = lineasNegocio;
	}

	public TMihLineanegocio getLnEditada() {
		return lnEditada;
	}

	public void setLnEditada(TMihLineanegocio lnEditada) {
		this.lnEditada = lnEditada;
	}

}
