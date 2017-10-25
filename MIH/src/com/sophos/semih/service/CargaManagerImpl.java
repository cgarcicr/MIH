package com.sophos.semih.service;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.common.ApplicationSetup;
import com.sophos.semih.common.Constants;
import com.sophos.semih.dao.TMihControlcargaHome;
import com.sophos.semih.model.TMihControlcarga;

@Service
public class CargaManagerImpl extends BaseManager implements CargaManager {

	private static final long serialVersionUID = 2462736169873275536L;
	private static final Log log = LogFactory.getLog(CargaManagerImpl.class);
	
	@Autowired
	private TMihControlcargaHome tMihControlcargaHome;
	
	@Override
	@Transactional
	public void insertarCarga(TMihControlcarga controlcarga){
		try {
			tMihControlcargaHome.persist(controlcarga);
			com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_INSERCION, getUser().getCodigoempleado(), 
					com.sophos.semih.model.TMihLogs.class.getName(), controlcarga, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
					ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
					ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
					ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
		} catch (Exception e) {
			log.error("Error insertarCarga()", e);
		}
	}

	@Override
	@Transactional
	public TMihControlcarga getControlcargaById(String controlcargaId) {
		try {
			TMihControlcarga controlcarga = tMihControlcargaHome.findById(controlcargaId);
			if(controlcargaId != null && !controlcargaId.equals(Constants.EMPTY_STRING) && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), controlcarga, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return controlcarga;
		} catch (Exception e) {
			log.error("Error getControlcargaById()", e);
		}
		return null;
	}

	@Override
	@Transactional
	public void deleteControlcarga(TMihControlcarga controlcarga) {
		try {
			tMihControlcargaHome.delete(controlcarga);
			com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_ELIMINACION, getUser().getCodigoempleado(), 
					com.sophos.semih.model.TMihLogs.class.getName(), controlcarga, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
					ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
					ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
					ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
					new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
		} catch (Exception e) {
			log.error("Error deleteControlcarga()", e);
		}
		
	}

	@Override
	@Transactional
	public List<TMihControlcarga> getControlcargas(TMihControlcarga controlcarga) {
		try {
			List<TMihControlcarga> controlcargas = tMihControlcargaHome.findByExample(controlcarga);
			if(controlcargas != null && controlcargas.size() > 0 && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), controlcarga, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return controlcargas;
		} catch (Exception e) {
			log.error("Error getControlcargas()", e);
		}
		return null;
	}
	
	@Override
	@Transactional
	public List<TMihControlcarga> getControlcargasxUsuario() {
		try {
			BeanFactory factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			List<TMihControlcarga> controlcargas = tMihControlcargaHome.geCargasxUsuario(((UsuarioManager) factory.getBean("usuarioManager")).getUsuarioById(getUser().getCodigo()));
			if(controlcargas != null && controlcargas.size() > 0 && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), "Resultado de la carga: " + controlcargas.size(), null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return controlcargas;
		} catch (Exception e) {
			log.error("Error getControlcargasxUsuario()", e);
		}
		return null;
	}
	
	@Override
	@Transactional
	public void updateControlcarga(TMihControlcarga controlcarga) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	@Transactional
    public String generaArchivoControl(String entidad, String archivo, String stamp, String tipoCarga, String encabezado, String separador, String separadorRuta, String contenido){
		try {
			String generacion = tMihControlcargaHome.generaArchivoControl(entidad, archivo, stamp, tipoCarga, encabezado, separador, separadorRuta, getUser().getCodigo(), contenido);
			if(generacion != null && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), contenido, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return generacion;
		} catch (Exception e) {
			log.error("Error generaArchivoControl()", e);
		}
		return null;
	}
	
	@Override
	@Transactional
    public String generaRegistroControl(String entidad, String archivo, String archivoLog, String archivoRechazos, String registro){
		try {
			String generacion = tMihControlcargaHome.generaResgistroControl(entidad, getUser().getCodigo(), archivo, archivoLog, archivoRechazos, registro);
			if(generacion != null && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), registro, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return generacion;
		} catch (Exception e) {
			log.error("Error generaRegistroControl()", e);
		}
		return null;
	}


	@Override
	@Transactional
    public String generaTablaTemporal(String entidad, boolean nulos){
		try {
			String generacion = tMihControlcargaHome.generaTablaTemporal(entidad, nulos);
			if(generacion != null && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), entidad, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return generacion;
		} catch (Exception e) {
			log.error("Error generaRegistroControl()", e);
		}
		return null;
	}
	
	
	@Override
	@Transactional
    public String generaSumatoria(String entidad, String campos){
		try {
			String generacion = tMihControlcargaHome.generaSumatoria(entidad, campos);
			if(generacion != null && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), entidad + "||" +  campos, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return generacion;
		} catch (Exception e) {
			log.error("Error generaRegistroControl()", e);
		}
		return null;
	}
	
	@Override
	@Transactional
    public BigDecimal consultarCarga(String entidad, String usuario){
		try {
			BigDecimal generacion = tMihControlcargaHome.consultarCarga(entidad, usuario);
			if(generacion.intValue() != 0 && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), entidad, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return generacion;
		} catch (Exception e) {
			log.error("Error generaRegistroControl()", e);
		}
		return null;
	}
	
	@Override
	@Transactional
    public int rowCount(String query){
		try {
			int generacion = tMihControlcargaHome.rowCount(query);
			if(generacion != 0 && getUser() != null)
				com.sophos.audit.LogService.insertLog(ApplicationSetup.getInstance().getParamValue("DATASOURCE_NAME"), null, null, Constants.FLAG_CONSULTA, getUser().getCodigoempleado(), 
						com.sophos.semih.model.TMihLogs.class.getName(), generacion, null, new Boolean(ApplicationSetup.getInstance().getParamValue("USE_WINDOWS_LOG")), 
						ApplicationSetup.getInstance().getParamValue("APP_NAME"), ApplicationSetup.getInstance().getParamValue("LOG_LEVEL"), 
						ApplicationSetup.getInstance().getParamValue("LOG_LEVEL_ID"), ApplicationSetup.getInstance().getParamValue("REMOTE_LOG_SERVER"), 
						ApplicationSetup.getInstance().getParamValue("USER_LOG_SERVER"), ApplicationSetup.getInstance().getParamValue("PASSWORD_LOG_SERVER"),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_DB_LOG")),
						new Boolean(ApplicationSetup.getInstance().getParamValue("USE_UNIX_LOG")), ApplicationSetup.getInstance().getParamValue("UNIX_PRIORITY_LOG"));
			return generacion;
		} catch (Exception e) {
			log.error("Error generaRegistroControl()", e);
		}
		return 0;
	}
	
}
