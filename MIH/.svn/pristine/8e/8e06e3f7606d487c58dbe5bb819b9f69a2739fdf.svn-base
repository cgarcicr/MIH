package com.sophos.semih.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.sophos.semih.model.TMihCatalogo;
import com.sophos.semih.model.TMihCatalogoId;

public interface CatalogoManager extends Serializable {
	void insertCatalogo(TMihCatalogo catalogo);
	
	TMihCatalogo getCatalogoById(TMihCatalogoId catalogoId);
		
	void deleteCatalogo(TMihCatalogo catalogo);
	
	List<TMihCatalogo> getCatalogos(TMihCatalogo catalogo);
	
	void updateCatalogo(TMihCatalogo catalogo);

	List<TMihCatalogo> getCatalogosTipo(TMihCatalogo catalogo);
	
	String getNuevoCodigo(TMihCatalogo catalogo);
	
	BigDecimal eliminarCatalogo(int tipoCatalogo, String catalogo, String tipo);
	
}
