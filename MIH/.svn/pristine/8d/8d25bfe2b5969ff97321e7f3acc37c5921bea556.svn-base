package com.sophos.semih.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.model.TMihLogs;
import com.sophos.semih.service.AuditoriaManager;

/**
 * Bean que guarda el estado de la vista de logs
 * 
 * @author FT
 * 
 */
@ManagedBean
@ViewScoped
public class AuditoriaBean extends BaseBean {

	private static final long serialVersionUID = -1920514222492581823L;
	
	private String error;
	private BeanFactory factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	private TMihLogs log;
	private List<TMihLogs> logs;
	private AuditoriaManager manager;
	private String tablaFilter;
	private String usuarioFilter;
	private String descripcionFilter;
	private String fechaFilter;
	private String accionFilter;

	public AuditoriaBean() {
		setManager((AuditoriaManager) factory.getBean("auditoriaManager"));
		setLogs(manager.getLogs(new TMihLogs()));
	}

	// Getters y Setters
	public AuditoriaManager getManager() {
		return manager;
	}

	public void setManager(AuditoriaManager manager) {
		this.manager = manager;
	}

	public TMihLogs getLog() {
		return log;
	}

	public void setLog(TMihLogs log) {
		this.log = log;
	}

	public List<TMihLogs> getLogs() {
		return logs;
	}

	public void setLogs(List<TMihLogs> logs) {
		this.logs = logs;
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

	/**
	 * @return the tablaFilter
	 */
	public String getTablaFilter() {
		return tablaFilter;
	}

	/**
	 * @param tablaFilter the tablaFilter to set
	 */
	public void setTablaFilter(String tablaFilter) {
		this.tablaFilter = tablaFilter;
	}

	/**
	 * @return the usuarioFilter
	 */
	public String getUsuarioFilter() {
		return usuarioFilter;
	}

	/**
	 * @param usuarioFilter the usuarioFilter to set
	 */
	public void setUsuarioFilter(String usuarioFilter) {
		this.usuarioFilter = usuarioFilter;
	}

	/**
	 * @return the descripcionFilter
	 */
	public String getDescripcionFilter() {
		return descripcionFilter;
	}

	/**
	 * @param descripcionFilter the descripcionFilter to set
	 */
	public void setDescripcionFilter(String descripcionFilter) {
		this.descripcionFilter = descripcionFilter;
	}

	/**
	 * @return the fechaFilter
	 */
	public String getFechaFilter() {
		return fechaFilter;
	}

	/**
	 * @param fechaFilter the fechaFilter to set
	 */
	public void setFechaFilter(String fechaFilter) {
		this.fechaFilter = fechaFilter;
	}

	/**
	 * @return the accionFilter
	 */
	public String getAccionFilter() {
		return accionFilter;
	}

	/**
	 * @param accionFilter the accionFilter to set
	 */
	public void setAccionFilter(String accionFilter) {
		this.accionFilter = accionFilter;
	}

}
