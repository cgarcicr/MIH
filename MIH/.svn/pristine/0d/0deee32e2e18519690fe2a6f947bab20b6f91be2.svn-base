package com.sophos.semih.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.model.TMihProyecto;
import com.sophos.semih.service.ProyectoManager;

/**
 * Clase que soporta la tabla de proyectos existentes.
 * El bean debe ser llamado como 'proyectoExistente'
 * @author sebastian.duque
 *  
 */
@ManagedBean(name="proyectoExistente")
@ViewScoped
public class ProyectoExistenteBean extends BaseBean {

	private static final long serialVersionUID = 863204900360921123L;	
	private List<TMihProyecto> proyectos;
	private String error;
	ProyectoManager manager;
	transient BeanFactory factory = FacesContextUtils
			.getWebApplicationContext(FacesContext.getCurrentInstance());	
	private TMihProyecto proyectoEditado;

	public ProyectoExistenteBean() {
		proyectoEditado = new TMihProyecto();
		setManager((ProyectoManager) factory.getBean("proyectoManager"));
		setProyectos(manager.getProyectos(new TMihProyecto()));
	}
	
	/**
	 * Elimina el proyecto seleccionado, siempre y cuando no
	 * existan lineas de negocio asociadas al mismo.
	 */
	public void eliminar() {
		try {
			manager.deleteProyecto(proyectoEditado);
			setProyectos(manager.getProyectos(new TMihProyecto()));
		} catch (DataIntegrityViolationException e) {
//			error = "El proyecto no puede ser eliminado, ya que existen líneas de negocios asociadas a este proyecto.";	
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Advertencia",  "El proyecto no puede ser eliminado, ya que existen líneas de negocios asociadas a este proyecto.") );
		} catch (Exception e) {
			error = "Error al eliminar " + e.getMessage();			
		}
	}
	
	/**
	 * Actualiza los detalles del proyecto seleccionado.
	 */
	public void editar() {
		try {
			proyectoEditado.setNombre(proyectoEditado.getNombre().trim());
			manager.updateProyecto(proyectoEditado);
		} catch (Exception e) {
			error = "Error al guardar " + e.getMessage();
		}
	}
	
	/**
	 * Permite limpiar los datos que se van a amostrar en la vista.
	 * @return
	 */
	public String limpiar(){
		super.clearMessages();
		this.refrescarValores();
		
		this.proyectoEditado.setCodigo(0);
		this.proyectoEditado.setNombre(null);
		this.proyectoEditado.setEstado(null);
		this.proyectoEditado.setDescripcion(null);
		return "";
	}
	
	/**
	 * Permite refrescar los valores de la vista.
	 */
	public void refrescarValores(){
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("formEdit:editNombre");
		ids.add("formEdit:estado");
		ids.add("formEdit:descripcion");
		super.resetValues(ids);
	}

	// Getters y Setters
	public ProyectoManager getManager() {
		return manager;
	}
	public void setManager(ProyectoManager manager) {
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
	public List<TMihProyecto> getProyectos() {
		return proyectos;
	}
	public void setProyectos(List<TMihProyecto> proyectos) {
		this.proyectos = proyectos;
	}	
	public TMihProyecto getProyectoEditado() {
		return proyectoEditado;
	}
	public void setProyectoEditado(TMihProyecto proyectoEditado) {
		this.proyectoEditado = proyectoEditado;
	}
}
