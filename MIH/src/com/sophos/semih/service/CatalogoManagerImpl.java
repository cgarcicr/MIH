package com.sophos.semih.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sophos.semih.common.ApplicationSetup;
import com.sophos.semih.common.Constants;
import com.sophos.semih.dao.TMihCatalogoHome;
import com.sophos.semih.model.TMihCatalogo;
import com.sophos.semih.model.TMihCatalogoId;

@Service
public class CatalogoManagerImpl extends BaseManager implements CatalogoManager {
	
	private static final long serialVersionUID = 671853184693053686L;
	private static final Log log = LogFactory.getLog(CatalogoManagerImpl.class);
	
	@Autowired
	private TMihCatalogoHome tMihCatalogoHome;
	
	@Override
	@Transactional
	public void insertCatalogo(TMihCatalogo catalogo) {
		try {
			tMihCatalogoHome.persist(catalogo);
			com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_INSERCION, getUser().getCodigoempleado(), 
					com.sophos.semih.model.TMihLogs.class.getName(), catalogo, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
					ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
					ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
					ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
		} catch (Exception e) {
			log.error("Error insertCatalogo()", e);
		}
	}

	@Override
	@Transactional
	public TMihCatalogo getCatalogoById(TMihCatalogoId catalogoId) { 
		try {
			TMihCatalogo catalogo = tMihCatalogoHome.findById(catalogoId);
			if(catalogo.getId() != null && catalogo.getId().getCodigo() != null && !catalogo.getId().getCodigo().equals(Constants.EMPTY_STRING) && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), catalogo, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return catalogo;
		} catch (Exception e) {
			log.error("Error getCatalogoById()", e);
		}
		return null;
	}

	@Override
	@Transactional
	public void deleteCatalogo(TMihCatalogo catalogo) {
		try {
			tMihCatalogoHome.delete(catalogo);
			com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_ELIMINACION, getUser().getCodigoempleado(), 
					com.sophos.semih.model.TMihLogs.class.getName(), catalogo, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
					ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
					ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
					ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
		} catch (Exception e) {
			log.error("Error deleteCatalogo()", e);
		}
	}

	@Override
	@Transactional
	public List<TMihCatalogo> getCatalogos(TMihCatalogo catalogo) {		
		try {
			List<TMihCatalogo> catalogos = tMihCatalogoHome.findByExample(catalogo);
			if(catalogos != null && catalogos.size() > 0 && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), catalogo, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return catalogos;
		} catch (Exception e) {
			log.error("Error getCatalogos()", e);
		}
		return null;
	}
	
	@Override
	@Transactional
	public List<TMihCatalogo> getCatalogosTipo(TMihCatalogo catalogo) {
		try {
			List<TMihCatalogo> catalogos = tMihCatalogoHome.findByTipoCatalogo(catalogo);
			if(catalogos != null && catalogos.size() > 0 && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), catalogo, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return catalogos;
		} catch (Exception e) {
			log.error("Error getCatalogosTipo()", e);
		}
		return null;
	}

	@Override
	@Transactional
	public void updateCatalogo(TMihCatalogo catalogo) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public String getNuevoCodigo(TMihCatalogo catalogo) {
		try {
			String codigo = tMihCatalogoHome.nextCodigoCatalogo(catalogo);
			if(codigo != null && !codigo.equals(Constants.EMPTY_STRING) && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), catalogo, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return codigo;
		} catch (Exception e) {
			log.error("Error getNuevoCodigo()", e);
		}
		return null;
	}

	@Override
	@Transactional
	public BigDecimal eliminarCatalogo(int tipoCatalogo, String catalogo, String tipo) {
		try {
			BigDecimal activo = tMihCatalogoHome.eliminarCatalogo(tipoCatalogo, catalogo, tipo);
			if(activo.intValue() != 0 && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), tipoCatalogo + "||" + catalogo + "||" + tipo, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return activo;
		} catch (Exception e) {
			log.error("Error getNuevoCodigo()", e);
		}
		return null;
	}
	
}
