/**
 * 
 */
package com.sophos.semih.service;

import java.io.Serializable;
import java.util.List;

import com.sophos.semih.model.TMihUsuario;

/**
 * @author FT
 *
 */
public interface UsuarioManager extends Serializable{
	
	void insertUsuario(TMihUsuario usuario);
	
	TMihUsuario getUsuarioById(int usuarioId);
		
	void deleteUsuario(TMihUsuario usuarioName);
	
	List<TMihUsuario> getUsuarios(TMihUsuario usuario);
	
	void updateUsuario(TMihUsuario usuario);
	
	Integer getNuevoCodigo(TMihUsuario usuario);

}