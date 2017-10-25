package com.sophos.semih.bean;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.model.TMihProyecto;
import com.sophos.semih.service.ProyectoManager;

/**
 * Clase que soporta la plantilla de creaci�n de nuevo proyecto. El bean debe
 * ser llamado como 'proyectoNuevo'
 * 
 * @author sebastian.duque
 * 
 */
@ManagedBean(name = "proyectoNuevo")
@ViewScoped
public class ProyectoNuevoBean extends BaseBean {

	private static final long serialVersionUID = 8339144256028780940L;
	private String nombre;
	private String descripcion;
	private Character estado;
	private String error;
	ProyectoManager manager;
	TMihProyecto proyecto;
	transient BeanFactory factory = FacesContextUtils
			.getWebApplicationContext(FacesContext.getCurrentInstance());

	public ProyectoNuevoBean() {
		manager = (ProyectoManager) factory.getBean("proyectoManager");
	}

	/**
	 * M�todo encargado de guardar un nuevo proyecto. Recarga la pagina cuando
	 * guarda exitosamente
	 */
	public String guardar() {
		error = "";
		proyecto = new TMihProyecto();
		proyecto.setNombre(nombre.trim());
		proyecto.setEstado(estado);
		proyecto.setDescripcion(descripcion);
		proyecto.setFeregistro(new Date());

		try {
			manager.insertProyecto(proyecto);
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				super.setMessage("proyectos.repetido", BaseBean.ERROR, "formProyecto:nuevoNombre", null);
			} else {
				super.setMessage("proyectos.error.guardar", BaseBean.ERROR, "formProyecto:nuevoNombre", null);
			}
		}
		return error;
	}
	
	/**
	 * Limpia los mensajes
	 * @return
	 */
	public String clear(){
		super.clearMessages();
		return null;
	}

	// Getters y Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Character getEstado() {
		return estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

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
}
