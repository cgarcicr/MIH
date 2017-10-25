package com.sophos.semih.bean;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.common.Constants;
import com.sophos.semih.model.TMihLineanegocio;
import com.sophos.semih.model.TMihProyecto;
import com.sophos.semih.service.LineaNegocioManager;
import com.sophos.semih.service.ProyectoManager;

/**
 * Clase que soporta la plantilla de creaci�n de nueva linea de negocio. El bean
 * debe ser llamado como 'lineaNegocioNueva'
 * 
 * @author sebastian.duque
 * 
 */
@ManagedBean(name = "lineaNegocioNueva")
@ViewScoped
public class LineaNegocioNuevaBean extends BaseBean {

	private static final long serialVersionUID = -5896219942919092145L;
	private String nombre;
	private String descripcion;
	private int codProyecto;
	private Character estado;
	private String error;
	private List<TMihProyecto> proyectos;
	private LineaNegocioManager lnManager;
	private ProyectoManager pManager;
	private TMihLineanegocio lineaNegocio;
	private BeanFactory factory;

	public LineaNegocioNuevaBean() {
		factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		setpManager((ProyectoManager) factory.getBean("proyectoManager"));
		setLnManager((LineaNegocioManager) factory.getBean("lineaNegocioManager"));		
		TMihProyecto proyectoActivo = new TMihProyecto();
		proyectoActivo.setEstado(Constants.ESTADO_ACTIVO);
		this.proyectos = pManager.getProyectos(proyectoActivo);
	}

	/**
	 * Metodo encargado de guardar una nueva linea de negocio.
	 * Recarga la pagina cuando guarda exitosamente 
	 */
	public String guardar() {
		lineaNegocio = new TMihLineanegocio();
		lineaNegocio.setNombre(nombre.trim());
		lineaNegocio.setEstado(estado);
		lineaNegocio.setDescripcion(descripcion);		
		lineaNegocio.setFeregistro(new Date());		
		lineaNegocio.setTMihProyecto(pManager.getProyectoById(codProyecto));	
		try {
			lnManager.insertLineaNegocio(lineaNegocio);
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				super.setMessage("ln.duplicado.error", BaseBean.ERROR, null, nombre);
			} else {
				error = "Error al guardar " + e.getMessage();
			}
		}
		return error;
	}
	
	public String clean(){
		super.clearMessages();
		this.nombre = null;
		this.codProyecto = 0;
		this.estado = null;
		this.descripcion = null;
		
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

	public int getCodProyecto() {
		return codProyecto;
	}

	public void setCodProyecto(int codProyecto) {
		this.codProyecto = codProyecto;
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

	public List<TMihProyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<TMihProyecto> proyectos) {
		this.proyectos = proyectos;
	}

	public BeanFactory getFactory() {
		return factory;
	}

	public void setFactory(BeanFactory factory) {
		this.factory = factory;
	}

	public LineaNegocioManager getLnManager() {
		return lnManager;
	}

	public void setLnManager(LineaNegocioManager lnManager) {
		this.lnManager = lnManager;
	}

	public ProyectoManager getpManager() {
		return pManager;
	}

	public void setpManager(ProyectoManager pManager) {
		this.pManager = pManager;
	}
}
