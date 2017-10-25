package com.sophos.semih.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.common.Constants;
import com.sophos.semih.model.TMihAplicacion;
import com.sophos.semih.model.TMihLineanegocio;
import com.sophos.semih.model.TMihProyecto;
import com.sophos.semih.service.AplicacionManager;
import com.sophos.semih.service.LineaNegocioManager;
import com.sophos.semih.service.ProyectoManager;

/**
 * Clase que soporta la tabla de aplicaciones existentes. El bean debe ser
 * llamado como 'aplicacionExistente'
 * 
 * @author sebastian.duque
 * 
 */
@ManagedBean(name = "aplicacionExistente")
@ViewScoped
public class AplicacionExistenteBean extends BaseBean  {

	private static final long serialVersionUID = 863204900360921123L;
	private List<TMihProyecto> proyectos;
	private List<TMihLineanegocio> lineasNegocio;
	private List<TMihAplicacion> aplicaciones;
	private String error;
	ProyectoManager pManager;
	LineaNegocioManager lnManager;
	AplicacionManager aManager;
	transient BeanFactory factory = FacesContextUtils
			.getWebApplicationContext(FacesContext.getCurrentInstance());
	private TMihAplicacion appEditada;

	/**
	 * Constructor
	 */
	public AplicacionExistenteBean() {
		setaManager((AplicacionManager) factory.getBean("aplicacionManager"));
		setLnManager((LineaNegocioManager) factory
				.getBean("lineaNegocioManager"));
		setpManager((ProyectoManager) factory.getBean("proyectoManager"));
		setAplicaciones(aManager.getAplicaciones(new TMihAplicacion()));
		setLineasNegocio(lnManager.getLineaNegocios(new TMihLineanegocio()));
		TMihProyecto proyectoActivo = new TMihProyecto();
		proyectoActivo.setEstado(Constants.ESTADO_ACTIVO);		
		setProyectos(pManager.getProyectos(proyectoActivo));
	}

	/**
	 * Metodo que controla el cambio de proyecto, para cargar las lineas de
	 * negocio que estan atadas al nuevo proyecto seleccionado.
	 * 
	 * @param e
	 *            evento de cambio en la lista de proyectos
	 */
	public void cambioProyecto(ValueChangeEvent e) {		
		Object nuevoProy = e.getNewValue();
		if (nuevoProy != null) {
			TMihProyecto p = pManager.getProyectoById(Integer
					.parseInt(nuevoProy.toString()));
			cambioProyecto(p);
		}
	}

	/**
	 * Carga la lista de Líneas de Negocio según el proyecto
	 * @param p Proyecto que se usará como filtro en la búsqueda
	 */
	private void cambioProyecto(TMihProyecto p) {
		if (p != null) {
			TMihLineanegocio ln = new TMihLineanegocio();
			ln.setTMihProyecto(p);
			ln.setEstado(Constants.ESTADO_ACTIVO);
			setLineasNegocio(lnManager.getLineaNegociosProy(ln));
		} else {
			setLineasNegocio(null);
		}
	}
	
	public void editarApp(){
		TMihProyecto p = appEditada.getTMihLineanegocio().getTMihProyecto();
		if (p != null) {			
			cambioProyecto(p);
		}
	}

	/**
	 * Elimina la aplicacion seleccionada, siempre y cuando no haya entidades
	 * atadas a la misma.
	 */
	public void eliminar() {
		try {
			aManager.deleteAplicacion(appEditada);
			setAplicaciones(aManager.getAplicaciones(new TMihAplicacion()));
		} catch (DataIntegrityViolationException e) {
			error = "La aplicación no puede ser eliminada porque existe información asociada.";
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,null, "La aplicación no puede ser eliminada porque existe información asociada") );
		} catch (Exception e) {
			error = "Error al eliminar " + e.getMessage();
		}
	}

	/**
	 * Actualiza los detalles de la aplicacion seleccionada.
	 */
	public void editar() {
		try {
			appEditada.setNombre(appEditada.getNombre().trim());
			aManager.updateAplicacion(appEditada);
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		} catch (Exception e) {
			error = "Error al guardar " + e.getMessage();
		}
	}

	
	/**
	 * Permite refrescar los valores de la vista.
	 */
	public void refrescarValores(){
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("form:editarNombre");
		ids.add("form:proyecto");
		ids.add("form:selectApp");
		ids.add("form:estado");
		ids.add("form:descripcion");
		super.resetValues(ids);
	}
	
	// Getters y Setters
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

	public List<TMihAplicacion> getAplicaciones() {
		return aplicaciones;
	}

	public void setAplicaciones(List<TMihAplicacion> aplicaciones) {
		this.aplicaciones = aplicaciones;
	}

	public TMihAplicacion getappEditada() {
		return appEditada;
	}

	public void setappEditada(TMihAplicacion appEditada) {
		this.appEditada = appEditada;
	}

	public List<TMihProyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<TMihProyecto> proyectos) {
		this.proyectos = proyectos;
	}

	public List<TMihLineanegocio> getLineasNegocio() {
		return lineasNegocio;
	}

	public void setLineasNegocio(List<TMihLineanegocio> lineasNegocio) {
		this.lineasNegocio = lineasNegocio;
	}

	public ProyectoManager getpManager() {
		return pManager;
	}

	public void setpManager(ProyectoManager pManager) {
		this.pManager = pManager;
	}

	public LineaNegocioManager getLnManager() {
		return lnManager;
	}

	public void setLnManager(LineaNegocioManager lnManager) {
		this.lnManager = lnManager;
	}

	public AplicacionManager getaManager() {
		return aManager;
	}

	public void setaManager(AplicacionManager aManager) {
		this.aManager = aManager;
	}

	public TMihAplicacion getAppEditada() {
		return appEditada;
	}

	public void setAppEditada(TMihAplicacion appEditada) {
		this.appEditada = appEditada;
	}

}
