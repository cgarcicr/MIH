package com.sophos.semih.service;

import java.util.List;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sophos.semih.common.ApplicationSetup;
import com.sophos.semih.common.Constants;
import com.sophos.semih.dao.TMihAplicacionHome;
import com.sophos.semih.model.TMihAplicacion;

@Service
public class AplicacionManagerImpl extends BaseManager implements AplicacionManager {
	
		private static final long serialVersionUID = 8811977276521393539L;
		private static final Log log = LogFactory.getLog(AplicacionManagerImpl.class);
		
	@Autowired
	private TMihAplicacionHome tMihAplicacionHome;

	@Override
	@Transactional
	public void insertAplicacion(TMihAplicacion aplicacion) {
		try {
			tMihAplicacionHome.persist(aplicacion);
			com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_INSERCION, getUser().getCodigoempleado(), 
					com.sophos.semih.model.TMihLogs.class.getName(), aplicacion, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
					ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
					ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
					ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
		} catch (Exception e) {
			log.error("Error en insertAplicacion()", e);
		}
	}

	@Override
	@Transactional
	public TMihAplicacion getAplicacionById(int aplicacionId) {
		try {
			TMihAplicacion aplicacion = tMihAplicacionHome.findById(aplicacionId);
			if(aplicacion.getCodigo() > 0 && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), aplicacion, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return aplicacion;
		} catch (Exception e) {
			log.error("Error en getAplicacionById()", e);
		}
		return null;
	}

	@Override
	@Transactional
	public void deleteAplicacion(TMihAplicacion aplicacion) {
		try {
			tMihAplicacionHome.delete(aplicacion);
			com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_ELIMINACION, getUser().getCodigoempleado(), 
					com.sophos.semih.model.TMihLogs.class.getName(), aplicacion, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
					ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
					ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
					ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
		} catch (Exception e) {
			log.error("Error en deleteAplicacion()", e);
		}
	}

	@Override
	@Transactional
	public List<TMihAplicacion> getAplicaciones(TMihAplicacion aplicacion) {
		try {
			List<TMihAplicacion> aplicaciones = tMihAplicacionHome.findByExample(aplicacion);
			if(aplicaciones != null && aplicaciones.size() > 0 && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), aplicacion, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return aplicaciones;
		} catch (Exception e) {
			log.error("Error en getAplicaciones()", e);
		}
		return null;
	}

	@Override
	@Transactional
	public void updateAplicacion(TMihAplicacion aplicacion) {
		try {
			TMihAplicacion cur = tMihAplicacionHome.findById(aplicacion.getCodigo());
			TMihAplicacion old = (TMihAplicacion)SerializationUtils.clone(cur);
			tMihAplicacionHome.merge(aplicacion);
			com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_MODIFICACION, getUser().getCodigoempleado(), 
					com.sophos.semih.model.TMihLogs.class.getName(), cur, old, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
					ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
					ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
					ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
		} catch (Exception e) {
			log.error("Error en updateAplicacion()", e);
		}
	}
	
	@Override
	@Transactional
	public List<TMihAplicacion> getAplicacionesLN(TMihAplicacion aplicacion) {
		try {
			List<TMihAplicacion> aplicaciones =  tMihAplicacionHome.findByLN(aplicacion);
			if(aplicaciones != null && aplicaciones.size() > 0 && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), aplicacion, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return aplicaciones;
		} catch (Exception e) {
			log.error("Error en getAplicacionesLN()", e);
		}
		return null;
	}

}
