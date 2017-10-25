package com.sophos.semih.service;

import java.io.Serializable;
import java.util.List;

import com.sophos.semih.model.TMihTipocatalogo;;

public interface TipoCatalogoManager extends Serializable {
	void insertTipocatalogo(TMihTipocatalogo tipoCatalogo);

	TMihTipocatalogo getTipoCatalogoById(int tipoCatalogoId);
		
	void deleteTipoCatalogo(TMihTipocatalogo tipoCatalogo);

	List<TMihTipocatalogo> getTiposCatalogo(TMihTipocatalogo tipoCatalogo);

	void updateTipoCatalogo(TMihTipocatalogo tipoCatalogo);
}
