package com.sophos.semih.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.common.Constants;
import com.sophos.semih.model.TMihSesion;
import com.sophos.semih.model.TMihUsuario;
import com.sophos.semih.service.SesionManager;

@ManagedBean
@ApplicationScoped
public class ControlSesionesBean extends BaseBean {
	
	public static final Character ESTADO_INACTIVO = 'I';
	public static final Character ESTADO_ACTIVO = 'A';

	private static final long serialVersionUID = -81269953130673094L;
	private BeanFactory factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	private TMihUsuario usuario;
	private List<TMihSesion> sesionsList;
	private String error;
    private int page = 1;
    private int rows;
    private TMihSesion editSesion;
    private SesionManager sesionManager;
    
	public ControlSesionesBean() {
		usuario = new TMihUsuario();
		setSesionManager((SesionManager)factory.getBean("sesionManager"));
		getSessions();
	}
	
	public void getSessions(){
		setSesionsList(new ArrayList<TMihSesion>());
		Iterator<Entry<String, HttpSession>> it = TMihUsuario.getUsersSessions().entrySet().iterator();
		while(it.hasNext()){
			Entry<String, HttpSession> e = it.next();
			HttpSession hs = e.getValue();
			TMihSesion s = new TMihSesion(((TMihUsuario)hs.getAttribute("logedUser")).getCodigoempleado(), 
						   ((TMihUsuario)hs.getAttribute("logedUser")).getNombres() + Constants.SPACE + ((TMihUsuario)hs.getAttribute("logedUser")).getApellidos(),
							hs.getId(), ESTADO_ACTIVO, new java.sql.Timestamp(hs.getCreationTime()));
			getSesionsList().add(s);
		}
	}
	
	/**
	 * Elimina la sesión seleccionada.
	 */
	public void eliminar() {
						
		try {
			TMihUsuario.getUsersSessions().get(getEditSesion().getIdUsuario()).invalidate();
			getSessions();
		} catch (Exception e) {
			error = "Error al eliminar la sesión. " + e.getMessage();
		}
	}
	

	/**
	 * Actualiza la sesión.
	 */
	public void guardar() {
		try {
			
			if (usuario.getCodigo() != null) {
				sesionManager.updateSesion(getEditSesion());
			}
			
		} catch (Exception e) {
			error = "Error al actualizar " + e.getMessage();
		}
	}


	/**
	 * @return the factory
	 */
	public BeanFactory getFactory() {
		return factory;
	}


	/**
	 * @param factory the factory to set
	 */
	public void setFactory(BeanFactory factory) {
		this.factory = factory;
	}


	/**
	 * @return the usuario
	 */
	public TMihUsuario getUsuario() {
		return usuario;
	}


	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(TMihUsuario usuario) {
		this.usuario = usuario;
	}


	/**
	 * @return the sesionsList
	 */
	public List<TMihSesion> getSesionsList() {
		return sesionsList;
	}


	/**
	 * @param sesionsList the sesionsList to set
	 */
	public void setSesionsList(List<TMihSesion> sesionsList) {
		this.sesionsList = sesionsList;
	}


	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}


	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}


	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}


	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}


	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}


	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}


	/**
	 * @return the editSesion
	 */
	public TMihSesion getEditSesion() {
		return editSesion;
	}


	/**
	 * @param editSesion the editSesion to set
	 */
	public void setEditSesion(TMihSesion editSesion) {
		this.editSesion = editSesion;
	}


	/**
	 * @return the sesionManager
	 */
	public SesionManager getSesionManager() {
		return sesionManager;
	}


	/**
	 * @param sesionManager the sesionManager to set
	 */
	public void setSesionManager(SesionManager sesionManager) {
		this.sesionManager = sesionManager;
	}
	
	public void imprimir(){
		System.out.println("La sesion es: "+editSesion.getIdSesion());
		System.out.println("El id de usuario es: "+editSesion.getIdUsuario());
	}
	
}
