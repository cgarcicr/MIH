package com.sophos.semih.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sophos.semih.common.ApplicationSetup;
import com.sophos.semih.common.Constants;
import com.sophos.semih.dao.TMihTipocatalogoHome;
import com.sophos.semih.model.TMihTipocatalogo;

@Service
public class TipoCatalogoManagerImpl extends BaseManager implements TipoCatalogoManager {

	private static final long serialVersionUID = -5323963653856334895L;
	private static final Log log = LogFactory.getLog(TipoCatalogoManagerImpl.class);
	
	@Autowired
	private TMihTipocatalogoHome tMihTipocatalogoHome;
	
	@Override
	@Transactional
	public void insertTipocatalogo(TMihTipocatalogo tipoCatalogo) {		
		try {
			tMihTipocatalogoHome.persist(tipoCatalogo);
			com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_INSERCION, getUser().getCodigoempleado(), 
					com.sophos.semih.model.TMihLogs.class.getName(), tipoCatalogo, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
					ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
					ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
					ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
		} catch (Exception e) {
			log.error("Error insertTipocatalogo()", e);
		}
	}

	@Override
	@Transactional
	public TMihTipocatalogo getTipoCatalogoById(int tipoCatalogoId) {
		try {
			TMihTipocatalogo tipoCatalogo = tMihTipocatalogoHome.findById(Short.parseShort(Integer.toString(tipoCatalogoId)));
			if(tipoCatalogo != null && tipoCatalogo.getCodmaestro() > 0 && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), tipoCatalogo, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return tipoCatalogo;
		} catch (NumberFormatException e) {
			log.error("Error getTipoCatalogoById()", e);
		}
		return null;
	}

	@Override
	@Transactional
	public void deleteTipoCatalogo(TMihTipocatalogo tipoCatalogo) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public List<TMihTipocatalogo> getTiposCatalogo(TMihTipocatalogo tipoCatalogo) {
		try {
			List<TMihTipocatalogo> tiposCatalogo = tMihTipocatalogoHome.findByExample(tipoCatalogo);
			if(tiposCatalogo !=null && tiposCatalogo.size() > 0 && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), tipoCatalogo, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return tiposCatalogo;
		} catch (NumberFormatException e) {
			log.error("Error getTiposCatalogo()", e);
		}
		return null;
	}

	@Override
	@Transactional
	public void updateTipoCatalogo(TMihTipocatalogo tipoCatalogo) {
		// TODO Auto-generated method stub

	}

}
