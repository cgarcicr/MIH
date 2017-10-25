package com.sophos.semih.service;

import java.io.Serializable;
import java.util.List;

import com.sophos.semih.model.TMihLineanegocio;

public interface LineaNegocioManager extends Serializable {
	void insertLineaNegocio(TMihLineanegocio lineaNegocio);
	
	TMihLineanegocio getLineaNegocioById(int lineaNegocioId);
		
	void deleteLineaNegocio(TMihLineanegocio lineaNegocio);
	
	List<TMihLineanegocio> getLineaNegocios(TMihLineanegocio lineaNegocio);
	
	void updateLineaNegocio(TMihLineanegocio lineaNegocio);
	
	List<TMihLineanegocio> getLineaNegociosProy(TMihLineanegocio lineaNegocio);
}
