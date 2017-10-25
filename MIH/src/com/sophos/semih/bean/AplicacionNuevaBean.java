package com.sophos.semih.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.common.Constants;
import com.sophos.semih.model.TMihAplicacion;
import com.sophos.semih.model.TMihLineanegocio;
import com.sophos.semih.model.TMihProyecto;
import com.sophos.semih.service.AplicacionManager;
import com.sophos.semih.service.LineaNegocioManager;
import com.sophos.semih.service.ProyectoManager;

/**
 * Clase que soporta la plantilla de creaci�n de nueva aplicaci�n. El bean debe
 * ser llamado como 'aplicacionNueva'
 * 
 * @author sebastian.duque
 * 
 */
@ManagedBean(name = "aplicacionNueva")
@ViewScoped
public class AplicacionNuevaBean extends BaseBean {
	private static final long serialVersionUID = -7551281465209892257L;
	private String nombre;
	private String descripcion;
	private int codProyecto;
	private int codLineaNegocio;
	private Character estado;
	private String error;
	private List<TMihProyecto> proyectos;
	private List<TMihLineanegocio> lineasNegocio;
	AplicacionManager aManager;
	LineaNegocioManager lnManager;
	ProyectoManager pManager;
	transient BeanFactory factory = FacesContextUtils
			.getWebApplicationContext(FacesContext.getCurrentInstance());

	/**
	 * Constructor
	 */
	public AplicacionNuevaBean() {
		setaManager((AplicacionManager) factory.getBean("aplicacionManager"));
		setLnManager((LineaNegocioManager) factory
				.getBean("lineaNegocioManager"));
		setpManager((ProyectoManager) factory.getBean("proyectoManager"));
		
		TMihProyecto proyectoActivo = new TMihProyecto();
		proyectoActivo.setEstado(Constants.ESTADO_ACTIVO);
		setProyectos(pManager.getProyectos(proyectoActivo));
	}


	/**
	 * Metodo que controla el cambio de proyecto, para cargar las lineas de
	 * negocio que estan atadas al nuevo proyecto seleccionado.
	 * 
	 * @param e	evento
	 */
	public void cambioProyecto(AjaxBehaviorEvent e) {
		TMihLineanegocio ln = new TMihLineanegocio();
		HtmlSelectOneMenu selProyecto = (HtmlSelectOneMenu) e.getComponent();
		Object nuevoProyId = selProyecto.getValue();
		if (nuevoProyId != null){
			TMihProyecto p  = pManager.getProyectoById(Integer.parseInt(nuevoProyId.toString()));
			ln.setTMihProyecto(p);
			ln.setEstado(Constants.ESTADO_ACTIVO);
			setLineasNegocio(lnManager.getLineaNegociosProy(ln));
		} else {
			setLineasNegocio(null);
		}
	}

	/**
	 * Metodo encargado de guardar una nueva aplicacion. Recarga la pagina
	 * cuando guarda exitosamente
	 */
	public String guardar() {
		TMihAplicacion aplicacion = new TMihAplicacion();
		aplicacion.setNombre(nombre.trim());
		aplicacion.setEstado(estado);
		aplicacion.setDescripcion(descripcion);
		aplicacion.setFeregistro(new Date());
		aplicacion.setTMihLineanegocio(lnManager
				.getLineaNegocioById(codLineaNegocio));
		try {
			aManager.insertAplicacion(aplicacion);
			super.setMessage("a.guardar.exito", BaseBean.INFO, "formAplicacion:resultado", null);
			
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				super.setMessage("a.error.duplicada", BaseBean.ERROR, "formAplicacion:nuevaNombre", null);
			} else {
				error = "Error al guardar " + e.getMessage();
			}
		}
		
		return error;
	}
	
	public String limpiar(){
		super.clearMessages();
		this.limpiarValores();
		
		this.nombre = null;
		this.codProyecto = 0;
		this.codLineaNegocio = 0;
		this.estado = null;
		
		return "";
	}
	
	private void limpiarValores(){
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("formAplicacion:nuevaNombre");
		ids.add("formAplicacion:codProyecto");
		ids.add("formAplicacion:nuevaLn");
		ids.add("formAplicacion:estado");
		
		super.resetValues(ids);
		
		
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

	public int getCodLineaNegocio() {
		return codLineaNegocio;
	}

	public void setCodLineaNegocio(int codLineaNegocio) {
		this.codLineaNegocio = codLineaNegocio;
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

	public List<TMihLineanegocio> getLineasNegocio() {
		return lineasNegocio;
	}

	public void setLineasNegocio(List<TMihLineanegocio> lineasNegocio) {
		this.lineasNegocio = lineasNegocio;
	}

	public AplicacionManager getaManager() {
		return aManager;
	}

	public void setaManager(AplicacionManager aManager) {
		this.aManager = aManager;
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

	public BeanFactory getFactory() {
		return factory;
	}

	public void setFactory(BeanFactory factory) {
		this.factory = factory;
	}

}
