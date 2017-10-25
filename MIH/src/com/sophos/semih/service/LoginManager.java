/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sophos.semih.service;


import com.sophos.semih.model.TMihUsuario;

/**
 *
 * @author miguel.altamiranda
 */
public interface LoginManager {

    /**
     * 
     * @param userName
     * @param password
     * @return El usuario autenticado 
     *         Null si la autenticacion fue fallida 
     */
	TMihUsuario login(String userName,String password) throws Exception;

    /**
     * 
     * @return true:Si se desconecta correctamente
     *         false: Si no se desconecta correctamente
     */
    Boolean logout();
    
    /**
     * 
     * @return El usuario autenticado 
     *         Null si no hay usuario autenticado 
     */
    TMihUsuario getAuthenticatedUser();

    
}
