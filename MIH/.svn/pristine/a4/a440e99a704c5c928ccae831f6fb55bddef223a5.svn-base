package com.sophos.semih.util;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.sophos.semih.common.Constants;
import com.sophos.semih.model.MenuItem;
import com.sophos.semih.model.TMihUsuario;

public class Security implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4740102045194663441L;

	public boolean checkMenuPerms(MenuItem menu) {
		TMihUsuario user = (TMihUsuario) getAttributeFromSession("logedUser");
//		if (user.getTMihRol()!=null
//				&& user.getTMihRol().getCodigo() == Constants.COD_USR_SUPERUSR) {
//			return true;
//		}
		if (menu.getMetodo() == null) {
			return false;
		}
		if (menu.getMetodo().equals("admUsuario")
				&& user.getAdmusuario() != null
				&& user.getAdmusuario().equals('S')) {
			return true;
		}
		if (menu.getMetodo().equals("entidades") 
				&& user.getEntidades() != null
				&& user.getEntidades().equals('S')) {
			return true;
		}
		if (menu.getMetodo().equals("carga") 
				&& user.getCarga()!=null
				&& user.getCarga().equals('S')) {
			return true;
		}
		if (menu.getMetodo().equals("parametros") 
				&& user.getParametros()!=null
				&& user.getParametros().equals('S')) {
			return true;
		}
		if (menu.getMetodo().equals("consultaMasiva") 
				&& user.getConsultamasiva()!=null
				&& user.getConsultamasiva().equals('S')) {
			return true;
		}
		if (menu.getMetodo().equals("consultaPuntual") 
				&& user.getConsultapuntual()!=null
				&& user.getConsultapuntual().equals('S')) {
			return true;
		}
		if (menu.getMetodo().equals("depuracion") 
				&& user.getDepuracion()!=null
				&& user.getDepuracion().equals('S')) {
			return true;
		}
		if (menu.getMetodo().equals("auditoria") 
				&& user.getAuditoria()!=null
				&& user.getAuditoria().equals('S')) {
			return true;
		}
		if (menu.getMetodo().equals("autorizar") 
				&& user.getAutorizar()!=null
				&& user.getAutorizar().equals('S')) {
			return true;
		}
		return false;
	}

	public void setAttributeInSession(String attributeName, Object attribute) {
		((HttpSession) FacesContext.getCurrentInstance().getExternalContext()
				.getSession(false)).setAttribute(attributeName, attribute);
	}

	public Object getAttributeFromSession(String attribute) {
		Object o;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		o = (session == null) ? null : session.getAttribute(attribute);
		return o;
	}

	public void removeAttributeFromSession(String attribute) {
		((HttpSession) FacesContext.getCurrentInstance().getExternalContext()
				.getSession(false)).removeAttribute(attribute);
	}

	public void closeSession() {
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
	}

}
