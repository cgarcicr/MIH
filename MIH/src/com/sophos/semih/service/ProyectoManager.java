package com.sophos.semih.service;

import java.io.Serializable;
import java.util.List;

import com.sophos.semih.model.TMihProyecto;

public interface ProyectoManager extends Serializable {
	void insertProyecto(TMihProyecto proyecto);
	
	TMihProyecto getProyectoById(int proyectoId);
		
	void deleteProyecto(TMihProyecto proyecto);
	
	List<TMihProyecto> getProyectos(TMihProyecto proyecto);
	
	void updateProyecto(TMihProyecto proyecto);
}
