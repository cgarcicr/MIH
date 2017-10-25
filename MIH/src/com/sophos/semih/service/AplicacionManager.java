package com.sophos.semih.service;

import java.io.Serializable;
import java.util.List;

import com.sophos.semih.model.TMihAplicacion;

public interface AplicacionManager extends Serializable{
	void insertAplicacion(TMihAplicacion aplicacion);
	
	TMihAplicacion getAplicacionById(int aplicacionId);
		
	void deleteAplicacion(TMihAplicacion aplicacion);
	
	List<TMihAplicacion> getAplicaciones(TMihAplicacion aplicacion);
	
	void updateAplicacion(TMihAplicacion aplicacion);
	
	List<TMihAplicacion> getAplicacionesLN(TMihAplicacion aplicacion);
}
