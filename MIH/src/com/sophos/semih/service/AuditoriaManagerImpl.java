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
import com.sophos.semih.dao.TMihLogsHome;
import com.sophos.semih.model.TMihLogs;

@Service
public class AuditoriaManagerImpl extends BaseManager implements AuditoriaManager {
	
	private static final long serialVersionUID = -207311027882581592L;
	private static final Log logs = LogFactory.getLog(AuditoriaManagerImpl.class);
	
	@Autowired
	private TMihLogsHome tMihLogsHome;
	
	@Override
	@Transactional
	public void insertLogs(TMihLogs log) {
		try {
			tMihLogsHome.persist(log);
			com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_INSERCION, getUser().getCodigoempleado(), 
					com.sophos.semih.model.TMihLogs.class.getName(), log, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
					ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
					ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
					ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
		} catch (Exception e) {
			logs.error("Error insertLogs()", e);
		}
	}

	@Override
	@Transactional
	public TMihLogs getLogsById(TMihLogs logId) {		
		try {
			TMihLogs log = tMihLogsHome.findById(logId.getId());
			if(log.getId() != null && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), log, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return log;
		} catch (Exception e) {
			logs.error("Error getLogsById()", e);
		}
		return null;
	}

	@Override
	@Transactional
	public void deleteLogs(TMihLogs log) {
		try {
			tMihLogsHome.delete(log);
			com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_ELIMINACION, getUser().getCodigoempleado(), 
					com.sophos.semih.model.TMihLogs.class.getName(), log, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
					ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
					ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
					ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
		} catch (Exception e) {
			logs.error("Error deleteLogs()", e);
		}
	}

	@Override
	@Transactional
	public List<TMihLogs> getLogs(TMihLogs log) {
		try {
			List<TMihLogs> lineasNegocio = tMihLogsHome.findByExample(log);
			if(lineasNegocio != null && lineasNegocio.size() > 0 && getUser() != null)
			return lineasNegocio;
		} catch (Exception e) {
			logs.error("Error getLogs()", e);
		}
		return null;
	}

	@Override
	@Transactional
	public void updateLogs(TMihLogs log) {
		try {
			tMihLogsHome.merge(log);
			TMihLogs cur = tMihLogsHome.findById(log.getId());
			TMihLogs old = (TMihLogs)SerializationUtils.clone(cur);
			com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_MODIFICACION, getUser().getCodigoempleado(), 
					com.sophos.semih.model.TMihLogs.class.getName(), cur, old, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
					ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
					ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
					ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
		} catch (Exception e) {
			logs.error("Error updateLogs()", e);
		}
	}
	
}
