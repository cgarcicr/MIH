package com.sophos.semih.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sophos.semih.common.ApplicationSetup;
import com.sophos.semih.common.Constants;
import com.sophos.semih.dao.MantenimientoDAO;

@Service
public class MantenimientoManagerImpl extends BaseManager implements MantenimientoManager {
	
	private static final long serialVersionUID = -387061308456551849L;
	private static final Log log = LogFactory.getLog(MantenimientoManagerImpl.class);
	
	@Autowired
	private MantenimientoDAO mantenimientoDAO;
	
	@Override
	@Transactional
	public int depurarLogs(Integer dias) {
		try {
			int resultado = mantenimientoDAO.depurarLogs(dias);
			if(getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_ELIMINACION, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), "T_MIH_LOGS", null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return resultado;
		} catch (Exception e) {
			log.error("Error depurarLogs()", e);
		}
		return 1;
	}

	@Override
	@Transactional
	public int depurarInfoHistorica(String entidad) {		
		try {
			int resultado = mantenimientoDAO.depurarInfoHistorica(entidad);
			if(getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_ELIMINACION, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), "TABLAS_INFORMACION_HISTORICA", null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return resultado;
		} catch (Exception e) {
			log.error("Error depurarInfoHistorica()", e);
		}
		return 1;
	}

	@Override
	@Transactional
	public String conteoInformacionDepurar(String entidad, String campoFecha, Integer vigencia) {
		String resultado = Constants.EMPTY_STRING;
		try {
			resultado = mantenimientoDAO.conteoInformacionDepurar(entidad, campoFecha, vigencia);
			return resultado;
		} catch (Exception e) {
			log.error("Error conteoInformacionDepurar()", e);
		}
		return resultado;
	}
}
