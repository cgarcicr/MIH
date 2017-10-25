/**
 * 
 */
package com.sophos.semih.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.sophos.semih.model.Campo;
import com.sophos.semih.model.TMihEntidad;
import com.sophos.semih.model.TMihUsuario;
import com.sun.rowset.CachedRowSetImpl;

/**
 * @author SD
 *
 */
public interface EntidadManager extends Serializable {
	
	void insertEntidad(TMihEntidad entidad);
	
	TMihEntidad getEntidadById(int entidadId);
		
	void deleteEntidad(TMihEntidad entidad);
	
	List<TMihEntidad> getEntidades(TMihEntidad entidad);
	
	void updateEntidad(TMihEntidad entidad);
	
	List<TMihEntidad> getEntidadesxUsuario(Object... autorizador);
	
	List<TMihEntidad> getEntidadesxUsuario(TMihUsuario usuario, Object... autorizador);
	
	List<Campo> getCampos(TMihEntidad entidad);
		
	ArrayList<LinkedHashMap<String, String>> consultaDinamica(String query, ArrayList<Campo> camposRetorno)  throws Exception;
	
	CachedRowSetImpl consultaDinamica(String query);
	
	int rowCount(String strHql);

}