package com.sophos.semih.service;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import com.sophos.semih.model.TMihUsuario;

public class BaseManager implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8340645285244249677L;
	private TMihUsuario user;
	
	/**
	 * @return the user
	 */
	public TMihUsuario getUser() {
		if(FacesContext.getCurrentInstance() != null)
			user = (TMihUsuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("logedUser");
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(TMihUsuario user) {
		this.user = user;
	}
}
