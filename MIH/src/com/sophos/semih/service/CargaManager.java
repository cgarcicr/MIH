package com.sophos.semih.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.sophos.semih.model.TMihControlcarga;

public interface CargaManager extends Serializable {

	void insertarCarga(TMihControlcarga controlcarga);
	
	TMihControlcarga getControlcargaById(String controlcargaId);
	
	void deleteControlcarga(TMihControlcarga controlcarga);
	
	List<TMihControlcarga> getControlcargas(TMihControlcarga controlcarga);
	
	public List<TMihControlcarga> getControlcargasxUsuario();
	
	void updateControlcarga(TMihControlcarga controlcarga);
	
	String generaArchivoControl(String entidad, String archivo, String stamp, String tipoCarga, String encabezado, String separador, String separadorRuta, String contenido);

	String generaRegistroControl(String entidad, String archivo, String archivoLog, String archivoRechazos, String registro);
	
	String generaTablaTemporal(String entidad, boolean nulos);
	
	String generaSumatoria(String entidad, String campos);
	
	BigDecimal consultarCarga(String entidad, String usuario);

	int rowCount(String query);
	
}
