/**
 * 
 */
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
import com.sophos.semih.dao.TMihSesionHome;
import com.sophos.semih.model.TMihSesion;

/**
 * @author FT
 *
 */
@Service
public class SesionManagerImpl extends BaseManager implements SesionManager {
	
	private static final long serialVersionUID = -1458846997249193094L;
	private static final Log log = LogFactory.getLog(SesionManagerImpl.class);
	
	@Autowired
	private TMihSesionHome tMihSesionHome;
	
	@Override
	@Transactional
	public void insertSesion(TMihSesion sesion) {
		try {
			tMihSesionHome.persist(sesion);
		} catch (Exception e) {
			log.error("Error insertSesion()", e);
		}
	}

	@Override
	@Transactional
	public TMihSesion getSesionById(String userId) {
		try {
			TMihSesion sesion = tMihSesionHome.findById(userId);
			if(sesion.getIdUsuario() != null && !sesion.getIdUsuario().equals(Constants.EMPTY_STRING) && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), sesion, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return sesion;
		} catch (Exception e) {
			log.error("Error getSesionById()", e);
		}
		return null;
	}

	@Override
	@Transactional
	public void deleteSesion(TMihSesion sesion) {
		try {
			tMihSesionHome.delete(sesion);
			com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_ELIMINACION, getUser().getCodigoempleado(), 
					com.sophos.semih.model.TMihLogs.class.getName(), sesion, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
					ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
					ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
					ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
		} catch (Exception e) {
			log.error("Error deleteSesion()", e);
		}
	}

	@Override
	@Transactional
	public List<TMihSesion> getSesions(TMihSesion sesion) {
		try {
			List<TMihSesion> sesions = tMihSesionHome.findByExample(sesion);
			if(sesions != null && sesions.size() > 0 && getUser() != null)
			return sesions;
		} catch (Exception e) {
			log.error("Error getSesions()", e);
		}
		return null;
	}

	@Override
	@Transactional
	public void updateSesion(TMihSesion sesion) {
		try {
			TMihSesion cur = tMihSesionHome.findById(sesion.getIdUsuario());
			TMihSesion old = (TMihSesion)SerializationUtils.clone(cur);
			tMihSesionHome.merge(sesion);
			com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_MODIFICACION, getUser().getCodigoempleado(), 
					com.sophos.semih.model.TMihLogs.class.getName(), cur, old, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
					ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
					ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
					ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
		} catch (Exception e) {
			log.error("Error updateSesion()", e);
		}
	}	
	
}
