package com.sophos.semih.dao;

// Generated 2/10/2013 05:22:11 PM by Hibernate Tools 4.0.0

import static org.hibernate.criterion.Example.create;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.InitialContext;

import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;

import com.sophos.semih.common.Constants;
import com.sophos.semih.model.TMihControlcarga;
import com.sophos.semih.model.TMihUsuario;

/**
 * Home object for domain model class TMihControlcarga.
 * @see com.sophos.semih.model.TMihControlcarga
 * @author Hibernate Tools
 */
public class TMihControlcargaHome implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1921623855308693528L;

	private static final Log log = LogFactory
			.getLog(TMihControlcargaHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("sessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(TMihControlcarga transientInstance) {
		log.debug("persisting TMihControlcarga instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TMihControlcarga instance) {
		log.debug("attaching dirty TMihControlcarga instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(TMihControlcarga instance) {
		log.debug("attaching clean TMihControlcarga instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TMihControlcarga persistentInstance) {
		log.debug("deleting TMihControlcarga instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMihControlcarga merge(TMihControlcarga detachedInstance) {
		log.debug("merging TMihControlcarga instance");
		try {
			TMihControlcarga result = (TMihControlcarga) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TMihControlcarga findById(java.lang.String id) {
		log.debug("getting TMihControlcarga instance with id: " + id);
		try {
			TMihControlcarga instance = (TMihControlcarga) sessionFactory
					.getCurrentSession().get(
							"com.sophos.semih.model.TMihControlcarga", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TMihControlcarga> findByExample(TMihControlcarga instance) {
		log.debug("finding TMihControlcarga instance by example");
		try {
			@SuppressWarnings("unchecked")
			List<TMihControlcarga> results = (List<TMihControlcarga>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.sophos.semih.model.TMihControlcarga")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	/**
	 * Permite armar el contenido del archivo de control
	 * @param entidad
	 * @param archivo
	 * @param tipoCarga
	 * @param encabezado
	 * @param separador
	 * @param contenido
	 * @return
	 */
	public String generaArchivoControl(String entidad, String archivo, String stamp, String tipoCarga, String encabezado, String separador, String separadorRuta, int usuario, String contenido) {
		
		log.debug("Parámetros de Entrada: " + entidad + " || "  + archivo + " || " + stamp + " || " + tipoCarga + " || " + encabezado + " || " + separador + " || " + separadorRuta + " || " + usuario + ".");
		
		String contenidoArchivo = "";
		
		try {
			Session session = sessionFactory.getCurrentSession();
			
			CallableStatement call;
			
			call = ((SessionImpl) session).connection().prepareCall("{call P_MIH_GENERAR_CTL(?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, entidad);
			call.setString(2, archivo);
			call.setString(3, stamp);
			call.setString(4, tipoCarga);	
			call.setString(5, encabezado);
			call.setString(6, separador);
			call.setString(7, separadorRuta);
			call.setInt(8, usuario);
			call.registerOutParameter(9, OracleTypes.VARCHAR);
			call.registerOutParameter(10, OracleTypes.NUMBER);
			call.execute();				
			
			contenidoArchivo = (String)call.getObject(9) ;
			
		}catch(SQLException e){
			log.error("error getting information for file", e);
		}
		catch (RuntimeException re) {
			log.error("error getting information for file", re);
			throw re;
		}
		return contenidoArchivo;
	}
	
	
	/**
	 * Permite armar el contenido del archivo de control
	 * @param entidad
	 * @param usuario
	 * @param archivoLog
	 * @param archivoRechazos
	 * @param registro
	 * @return
	 */
	public String generaResgistroControl(String entidad, int usuario, String archivo, String archivoLog, String archivoRechazos, String registro) {
		
		log.debug("Par�metros de Entrada: " + entidad + " || "  + usuario + " || " + archivo + " || " + archivoLog + " || " + archivoRechazos + " || " + registro + ".");
		
		String contenidoArchivo = "";
		
		try {
			Session session = sessionFactory.getCurrentSession();
			
			CallableStatement call;
			
			call = ((SessionImpl) session).connection().prepareCall("{call P_MIH_GENERAR_REG_CTL(?,?,?,?,?,?,?)}");
			call.setString(1, entidad);
			call.setInt(2, usuario);
			call.setString(3, archivo);
			call.setString(4, archivoLog);
			call.setString(5, archivoRechazos);	
			call.registerOutParameter(6, OracleTypes.VARCHAR);
			call.registerOutParameter(7, OracleTypes.NUMBER);
			
			call.execute();				
			
			contenidoArchivo = (String)call.getObject(6) ;
			
		}catch(SQLException e){
			log.error("error getting information from table", e);
		}
		catch (RuntimeException re) {
			log.error("error getting information from table", re);
			throw re;
		}
		return contenidoArchivo;
	}
	
	/**
	 * Permite armar crear una tabla temporal para la validación
	 * @param entidad
	 * @return
	 */
	public String generaTablaTemporal(String entidad, boolean nulos){
	
		log.debug("Par�metros de Entrada: " + entidad + "||" + nulos + "||" + ".");
	
		String retornoExitoso = null;
		
		int camposNulo = 0;
		
		try{
			if (nulos) {
				camposNulo = 1;
			}
			
			Session session = sessionFactory.getCurrentSession();
			
			CallableStatement call;
			
			call = ((SessionImpl) session).connection().prepareCall("{call P_MIH_TABLATEMPORAL(?,?,?)}");
			call.setString(1, entidad);
			call.setInt(2, camposNulo);
			call.registerOutParameter(3, OracleTypes.NUMBER);
			
			call.execute();				
		
		}catch(SQLException e){
			log.error("error getting information from table", e);
		}
		catch (RuntimeException re) {
			log.error("error getting information from table", re);
			throw re;
		}
		return retornoExitoso;
	}
	
	/**
	 * Permite armar el contenido del archivo de control
	 * @param entidad
	 * @return
	 */
	public String generaSumatoria(String entidad, String campos){
	
		log.debug("Par�metros de Entrada: " + entidad +  ".");
	
		String valor = null;

		try{
			Session session = sessionFactory.getCurrentSession();
			
			CallableStatement call;
			
			call = ((SessionImpl) session).connection().prepareCall("{call P_MIH_SUMATORIA(?,?,?,?)}");
			call.setString(1, entidad);
			call.setString(2, campos);
			call.registerOutParameter(3, OracleTypes.VARCHAR);
			call.registerOutParameter(4, OracleTypes.NUMBER);
			
			call.execute();				
		
			valor = (String)call.getObject(3);
			
		}catch(SQLException e){
			log.error("error getting information from table", e);
		}
		catch (RuntimeException re) {
			log.error("error getting information from table", re);
			throw re;
		}
		return valor;
	}
	
	/**
	 * Permite consultar el estado de las cargas
	 * @param entidad
	 * @usuario usuario de base de datos
	 * @return
	 */
	public BigDecimal consultarCarga(String entidad, String usuario){
	
		log.debug("Par�metros de Entrada: " + entidad + "||" + usuario + "||" + ".");
		
		BigDecimal activo = new BigDecimal(0);
		
		try{
			Session session = sessionFactory.getCurrentSession();
			
			CallableStatement call;
			
			call = ((SessionImpl) session).connection().prepareCall("{call P_MIH_MONITOR(?,?,?,?)}");
			call.setString(1, entidad);
			call.setString(2, usuario);
			call.registerOutParameter(3, OracleTypes.NUMBER);
			call.registerOutParameter(4, OracleTypes.NUMBER);
			
			call.execute();
			
			activo = (BigDecimal)call.getObject(3);
		
		}catch(SQLException e){
			log.error("error getting information from table", e);
		}
		catch (RuntimeException re) {
			log.error("error getting information from table", re);
			throw re;
		}
		return activo;
	}
	
	/**
	 * Realiza una consulta y retorna ResultSet
	 * @param query
	 * @return
	 */
	public int rowCount(String query) {
		int rowCount = 0;
		log.debug("Consulta numero registros: " + query);
		
		CallableStatement call = null;
		ResultSet rs = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			
			call = ((SessionImpl) session).connection().prepareCall("{call P_MIH_EJECUTAR_CONSULTA(?,?,?)}");
			call.registerOutParameter(1, OracleTypes.CURSOR);
			call.setString(2, query);
			call.registerOutParameter(3, OracleTypes.NUMBER);
			call.execute();
			rs = (ResultSet) call.getObject(1);
			
			while (rs.next()) {
				rowCount = rs.getInt(1);
			}
			
			return rowCount;
		}catch(SQLException e){
			log.error("Error realizando la consulta", e);
		}
		catch (RuntimeException re) {
			log.error("Error realizando la consulta", re);
			throw re;
		} 
		return 0;
	}
	
	
	/**
	 * Permite consultar las cargas a las que el usuario tiene permiso.
	 * @param usuario
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TMihControlcarga> geCargasxUsuario(TMihUsuario usuario){
		log.debug("Consultando todas las entidades a las que el usuario tiene permiso");
		try {
						
			List<TMihControlcarga> results = null;
			String strHql = "";
			
						
			if(usuario.getTMihRol().getCodigo() == Constants.COD_USR_SUPERUSR || usuario.getTMihRol().getCodigo() == Constants.COD_USR_ADM){
				strHql =  "SELECT carga "
						+ " FROM  com.sophos.semih.model.TMihControlcarga carga"
						+ " ORDER BY TO_NUMBER(carga.codigo) DESC";
				
			}else{
				
				strHql =  "SELECT carga "
						+ " FROM  com.sophos.semih.model.TMihControlcarga carga, "
						+ " com.sophos.semih.model.TMihEntxusr entUsr "
						+ " WHERE entUsr.id.codentidad = carga.TMihEntidad.codigo "
						+ " AND entUsr.id.codusuario = " + usuario.getCodigo()
						+ " UNION "
						+ " SELECT carga "
						+ " FROM com.sophos.semih.model.TMihControlcarga carga, "
						+ " com.sophos.semih.model.TMihEntidad entidad"
						+ " WHERE entidad.TMihAplicacion.TMihLineanegocio.codigo = " + usuario.getTMihLineanegocio().getCodigo()
						+ " ORDER BY TO_NUMBER(carga.codigo) DESC";
				
			}
			
			Query query = sessionFactory.getCurrentSession().createQuery(strHql);
			results = (List<TMihControlcarga>)query.list();
			log.debug("Cargas encontradas exitosamente: "+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("Se produjo un error al intentar obtener todas las cargas", re);
			throw re;
		}
	}
}
