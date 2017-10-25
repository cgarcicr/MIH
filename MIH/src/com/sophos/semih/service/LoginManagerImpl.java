/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sophos.semih.service;


import java.util.List;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sophos.semih.common.ApplicationSetup;
import com.sophos.semih.common.Constants;
import com.sophos.semih.dao.TMihUsuarioHome;
import com.sophos.semih.model.TMihUsuario;
import com.sophos.semih.util.Ldap;


/**
 *
 * @author miguel.altamiranda
 */
@Service
public class LoginManagerImpl implements LoginManager {
    private TMihUsuario user;
    
    /**
     * 
     * @param userName
     * @param password
     * @return El usuario autenticado 
     *         Null si la autenticacion fue fallida 
     */
	@Autowired
	private TMihUsuarioHome tMihUsuarioHome;

    @Override
	@Transactional
    public TMihUsuario login(String userName,String password) throws Exception {
    	user = new TMihUsuario();
    	user.setCodigoempleado(userName);
    	
		if((new Ldap()).doLogin(userName, password)){
			List<TMihUsuario> usuarios=tMihUsuarioHome.findByExample(user);
			if(!usuarios.isEmpty()){
				
				user=usuarios.get(0);
				// Establecer duarción de sesión inactiva
				Integer sessionTime;
				try {
					sessionTime = new Integer(ApplicationSetup.getInstance().getParamValue("TIEMPO_SESION"));
				} catch (NumberFormatException e) {
					sessionTime = 5;
				}
				FacesContext.getCurrentInstance().getExternalContext().setSessionMaxInactiveInterval(sessionTime * 60);
				
					if (user.getEstado().equals(Constants.ESTADO_ACTIVO)) {
						
						com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_LOGIN, getAuthenticatedUser().getCodigoempleado(), 
								com.sophos.semih.model.TMihLogs.class.getName(), getAuthenticatedUser(), null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
								ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
								ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
								ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
								new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
								new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
					} else {
						throw new Exception("login.usr_no_ldap");
					}
			} else {
				throw new Exception("login.usr_no_ldap");
			}
		} else {
			throw new Exception("login.usr_no_ldap");
		}
		return getAuthenticatedUser();
    }
    /**
     * 
     * @return true:Si se desconecta correctamente
     *         false: Si no se desconecta correctamente
     */
	@Override
	@Transactional
    public Boolean logout() {
        user=null;
        return true;
    }
    
    /**
     * 
     * @return El usuario autenticado 
     *         Null si no hay usuario autenticado 
     */
	@Override
	@Transactional
    public TMihUsuario getAuthenticatedUser() {
        return user;
    }
}
