package com.sophos.semih.service;

import java.io.Serializable;
import java.util.List;

import com.sophos.semih.model.TMihConsfld;
import com.sophos.semih.model.TMihConsfldId;
import com.sophos.semih.model.TMihConstbl;
import com.sophos.semih.model.TMihConstblId;
import com.sophos.semih.model.TMihConsulta;

public interface ConsultaManager extends Serializable {

	void insertarConsulta(TMihConsulta consulta);
	
	TMihConsulta getConsultaById(int consultaId);
	
	void deleteConsulta(TMihConsulta consulta);
	
	List<TMihConsulta> getConsultas(TMihConsulta consulta);
	
	List<TMihConsulta> getConsultasxusuario();
	
	int nextVal(String strHql);
	
	//Tablas de Consulta
	void insertarConstbl(TMihConstbl constbl);
	
	TMihConstbl getConstblById(TMihConstblId constblId);
	
	void deleteConstbl(TMihConstbl constbl);
	
	List<TMihConstbl> getConstbles(TMihConstbl constbl);
	
	List<TMihConstbl> getEntidades(int consulta);
	
	//Tablas de Consulta
	void insertarConsfld(TMihConsfld consfld);
	
	TMihConsfld getConsfldById(TMihConsfldId consfldId);
	
	void deleteConsfld(TMihConsfld consfld);
	
	List<TMihConsfld> getConsflds(TMihConsfld consfld);
	
	List<TMihConsfld> getCampos(int entidad, int consulta, int sectabla);
	
}
