package com.sophos.semih.dao;

// Generated 28/08/2013 09:27:17 AM by Hibernate Tools 4.0.0

import static org.hibernate.criterion.Example.create;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import javax.naming.InitialContext;

import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;

import com.sophos.semih.model.Campo;
import com.sophos.semih.model.TMihEntidad;
import com.sophos.semih.model.TMihUsuario;
import com.sun.rowset.CachedRowSetImpl;

/**
 * Home object for domain model class TMihEntidad.
 * 
 * @see com.sophos.semih.model.TMihEntidad
 * @author Hibernate Tools
 */
/**
 * @author sebastian.duque
 *
 */
public class TMihEntidadHome implements Serializable{

	private static final long serialVersionUID = 3438820709213963439L;

	private static final Log log = LogFactory.getLog(TMihEntidadHome.class);

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

	public void persist(TMihEntidad transientInstance) {
		log.debug("persisting TMihEntidad instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}
	
	
	/** Crea la tabla en la base de datos que guardar� los datos de la entidad
	 * @param instance
	 * @throws Exception 
	 */
	public void crearTablaEntidad(String sentenciaTabla, List<String> comCampos) throws Exception{
		log.debug("creando tabla en bd para la entidad");
		Session session = sessionFactory.getCurrentSession();		
		Statement stmt = null;
		try {
			stmt = ((SessionImpl) session).connection().createStatement();
			stmt.execute(sentenciaTabla);
			for (String s : comCampos){
				stmt.execute(s);	
			}	
			System.out.println(stmt.toString());
			log.debug("tabla creada");
		} catch (Exception e) {			
			log.debug("fallo creacion de tabla: "+sentenciaTabla);
			throw new Exception("Fallo la creacion de la tabla en la base de datos.");			
		} 
	}
	
	/**
	 * Borra la tabla en la base de datos que conten�a la informaci�n de la entidad
	 * @param string
	 */
	public void borrarTablaEntidad(String sentenciaEliminar) {
		log.debug("eliminando tabla en bd para la entidad");
		Session session = sessionFactory.getCurrentSession();		
		Statement stmt = null;
		try {
			stmt = ((SessionImpl) session).connection().createStatement();
			stmt.execute(sentenciaEliminar);
			log.debug("tabla eliminada");
		} catch (Exception e) {	
			log.debug("fallo borrado de tabla: "+sentenciaEliminar);
		} 
	}

	public void attachDirty(TMihEntidad instance) {
		log.debug("attaching dirty TMihEntidad instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(TMihEntidad instance) {
		log.debug("attaching clean TMihEntidad instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TMihEntidad persistentInstance) {
		log.debug("deleting TMihEntidad instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMihEntidad merge(TMihEntidad detachedInstance) {
		log.debug("merging TMihEntidad instance");
		try {
			TMihEntidad result = (TMihEntidad) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TMihEntidad findById(int id) {
		log.debug("getting TMihEntidad instance with id: " + id);
		try {
			TMihEntidad instance = (TMihEntidad) sessionFactory
					.getCurrentSession().get(
							"com.sophos.semih.model.TMihEntidad", id);
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

	@SuppressWarnings("unchecked")
	public List<TMihEntidad> findByExample(TMihEntidad instance) {
		log.debug("finding TMihEntidad instance by example");
		try {
			List<TMihEntidad> results = (List<TMihEntidad>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.sophos.semih.model.TMihEntidad")
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
	 * Permite consultar las entidades que el usuario puede consultar.
	 * @param usuario
	 * @return
	 */
	public List<TMihEntidad> getEntidadesxUsuario(TMihUsuario usuario, Object... autorizador){
		log.debug("Consultando todas las entidades a las que el usuario tiene permiso");
		CallableStatement call = null;
		ResultSet rs = null;
		try {
			List<TMihEntidad> results = new ArrayList<TMihEntidad>(); 
			Session session = sessionFactory.getCurrentSession();
			TMihEntidad entidad;
			
			
			call = ((SessionImpl) session).connection().prepareCall("{call P_MIH_ENTIDADXUSUARIO(?,?,?,?,?)}");
			call.registerOutParameter(1, OracleTypes.CURSOR);
			call.setInt(2, usuario.getCodigo());
			if (autorizador != null && autorizador.length > 0) {
				if (autorizador[0] instanceof Integer)
					call.setInt(3, (int)autorizador[0]);
				else
					call.setInt(3, 0);
				
				if (autorizador.length == 2 ){
					
					if (autorizador[1] instanceof Integer)
						call.setInt(4, (int)autorizador[1]);
					else
						call.setInt(4, 0);
					
				}else{
					call.setInt(4, 0);					
				}
					
			}else{
				call.setInt(3, 0);
				call.setInt(4, 0);
			}
			
			call.registerOutParameter(5, OracleTypes.NUMBER);
			call.execute();
			rs = (ResultSet) call.getObject(1);				
						
			while (rs.next()) {
				entidad = new TMihEntidad();
				entidad.setCodigo(rs.getInt("CODIGO"));
				entidad.setNombre(rs.getString("NOMBRE"));
				entidad.setFecreacion(rs.getDate("FECREACION"));
				entidad.setUsrcreador(rs.getString("USRCREADOR"));
				entidad.setTvigencia(rs.getInt("TVIGENCIA"));
				entidad.setDepurar(rs.getString("DEPURAR"));
				entidad.setVacia(rs.getString("VACIA"));
				entidad.setCampoFecha(rs.getString("CAMPOFECHA"));
				entidad.setUsrsolcreacion(rs.getString("USRSOLCREACION"));
				entidad.setDescripcion(rs.getString("DESCRIPCION"));
				results.add(entidad);
			}
			
			log.debug("Entidades encontradas exitosamente: "+ results.size());
			return results;
		} catch (Exception re) {
			log.error("Se produjo un error al intentar obtener todas las entidades", re);
		} 
		
		return null;
	}
	
	/**
	 * Permite consultar los campos de la entidad especificada.
	 * @param instance	Instancia de un objeto entidad con el nombre el nombre de la entidad a buscar especificado.
	 * @return lista de campos asociados a la entidad
	 */
//	public List<Campo> consultarCampos(TMihEntidad instance) {
//		log.debug("finding campos of TMihEntidad instance by name");
//		CallableStatement call = null;
//		ResultSet rs = null;
//		try {
//			List<Campo> results = new ArrayList<Campo>(); 
//			if (instance != null && instance.getNombre() != null) {
//				
//				Session session = sessionFactory.getCurrentSession();
//				
//				call = ((SessionImpl) session).connection().prepareCall("{call P_MIH_OBTENER_EST_ENTIDAD(?,?,?)}");
//				call.registerOutParameter(1, OracleTypes.CURSOR);
//				call.setString(2, instance.getNombre());
//				call.registerOutParameter(3, OracleTypes.NUMBER);
//				call.execute();
//				rs = (ResultSet) call.getObject(1);				
//				Campo c;
//				while(rs.next()){
//					c = new Campo(rs.getString("NOMBRE_COLUMNA"), rs.getString("NOMBRE_COMPLETO"), rs.getString("COMENTARIO"), rs.getString("TIPO_DATO_COMPLETO"), rs.getString("TIPO_DATO"),  rs.getString("LONGITUD"), rs.getString("ESCALA"),  rs.getString("ESCLAVE"));
//					results.add(c);
//				}
//			}
//			return results;
//		}catch(SQLException e){
//			log.error("Error consultado campos", e);
//			return (new ArrayList<Campo>(Arrays.asList(new Campo("ACID","Account Id","Número de la cuenta del cliente","VARCHAR2(0)","VARCHAR2","30","0","S"))));
//		}
//		catch (RuntimeException re) {
//			log.error("Error consultado campos", re);
//			throw re;
//		} 
////		finally {
////			try {
////				if (!call.isClosed()) {
////					call.close();
////				}
////			} catch (SQLException e) {
////				e.printStackTrace();
////			}
////		}
//	}
	
	
	/* TEST */
	/**
	 * Permite consultar los campos de una tabla.
	 * @param query
	 * @param camposRetorno
	 * @return
	 */
	public List<Campo> consultarCampos(TMihEntidad instance) {
		log.debug("Consulta de campos de tabla: " + instance.getNombre());
		
		List<Campo> results = new ArrayList<Campo>();
		
		String query = ""+
		"SELECT  COL.COLUMN_NAME NOMBRE_COLUMNA, " +
		        "CASE WHEN INSTR(NVL(COM.COMMENTS, ' ') , '||||') = 0 THEN " +
		          "NVL(COM.COMMENTS, ' ') " +
		        "ELSE " +
		          "SUBSTR(NVL(COM.COMMENTS, ' '), " + 
		          "INSTR(NVL(COM.COMMENTS, ' ') , '||||') + 4, " + 
		          "LENGTH(NVL(COM.COMMENTS, ' ')) - (INSTR(NVL(COM.COMMENTS, ' ') , '||||') + 3)) END COMENTARIO, " +
		        "CASE WHEN INSTR(NVL(COM.COMMENTS, ' ') , '||||') = 0 THEN " +
		          "' ' " +
		        "ELSE " +
		          "SUBSTR( NVL(COM.COMMENTS, ' '), 1, INSTR(NVL(COM.COMMENTS, ' ') , '||||') - 1) END NOMBRE_COMPLETO, " +
		        "CASE WHEN COL.DATA_TYPE IN ('CHAR', 'VARCHAR', 'VARCHAR2', 'NCHAR', 'NVARCHAR2') THEN " +
		          "COL.DATA_TYPE || '(' || TO_CHAR(COL.CHAR_LENGTH) || ')' " +
		        "WHEN COL.DATA_TYPE = 'NUMBER' AND TO_CHAR(COL.DATA_PRECISION) IS NOT NULL THEN " +
		          "COL.DATA_TYPE || '(' || TO_CHAR(COL.DATA_PRECISION) || ',' || TO_CHAR(COL.DATA_SCALE) || ')' " +
		        "ELSE " +
		          "COL.DATA_TYPE " +
		        "END TIPO_DATO_COMPLETO, " +
		        "COL.DATA_TYPE TIPO_DATO, " +
		        "CASE WHEN COL.DATA_TYPE IN ('CHAR', 'VARCHAR', 'VARCHAR2', 'NCHAR', 'NVARCHAR2') THEN " +
		          "TO_CHAR(COL.CHAR_LENGTH) " +
		        "WHEN COL.DATA_TYPE = 'NUMBER' AND TO_CHAR(COL.DATA_PRECISION) IS NOT NULL THEN " +
		          "TO_CHAR(COL.DATA_PRECISION) " +
		        "ELSE " +
		          "'NA' " +
		        "END LONGITUD, " +
		        "CASE WHEN COL.DATA_TYPE = 'NUMBER' AND TO_CHAR(COL.DATA_SCALE) IS NOT NULL THEN " +
		          "TO_CHAR(COL.DATA_SCALE) " +
		        "ELSE " +
		          "'NA' " +
		        "END ESCALA, " +
		        "CASE WHEN CONS.COLUMN_NAME IS NOT NULL THEN 'S' ELSE 'N' END ESCLAVE " +
		"FROM ALL_TAB_COLUMNS COL " +
		"INNER JOIN ALL_COL_COMMENTS COM " +
		"ON COL.COLUMN_NAME = COM.COLUMN_NAME " +
		"AND COL.TABLE_NAME = COM.TABLE_NAME " +
		"LEFT JOIN ( SELECT  CONS.TABLE_NAME, " +  
		                    "CONSCOL.COLUMN_NAME " +
		            "FROM ALL_CONSTRAINTS CONS " +
		            "INNER JOIN ALL_CONS_COLUMNS CONSCOL " +
		            "ON CONS.CONSTRAINT_NAME = CONSCOL.CONSTRAINT_NAME " +
		            "AND CONS.TABLE_NAME = CONSCOL.TABLE_NAME " +
		            "AND CONS.CONSTRAINT_TYPE = 'P' " +
		            "AND CONS.TABLE_NAME = '" + instance.getNombre() + "' " +
		            "AND CONS.OWNER IN (SELECT VALOR FROM T_MIH_PARAMETRO WHERE CODIGO = 'ORACLE_SCHEMA') " +
		            "AND CONSCOL.OWNER IN (SELECT VALOR FROM T_MIH_PARAMETRO WHERE CODIGO = 'ORACLE_SCHEMA') " +
		          ") CONS " +
		"ON CONS.COLUMN_NAME =  COL.COLUMN_NAME " +
		"AND CONS.TABLE_NAME = COL.TABLE_NAME " +
		"WHERE COL.TABLE_NAME = '" + instance.getNombre() + "' " +
		"AND COL.OWNER IN (SELECT VALOR FROM T_MIH_PARAMETRO WHERE CODIGO = 'ORACLE_SCHEMA') " +
		"AND COM.OWNER IN (SELECT VALOR FROM T_MIH_PARAMETRO WHERE CODIGO = 'ORACLE_SCHEMA') " +
		"ORDER BY COL.COLUMN_ID ";
		
		
		ResultSet rs = null;
		Statement stmt = null;
		
		try {
			
			if (instance != null && instance.getNombre() != null) {
				
				Session session = sessionFactory.getCurrentSession();
				stmt = ((SessionImpl) session).connection().createStatement();
				rs = stmt.executeQuery(query);
				
				Campo c;
				while(rs.next()){
					c = new Campo(rs.getString("NOMBRE_COLUMNA"), rs.getString("NOMBRE_COMPLETO"), rs.getString("COMENTARIO"), rs.getString("TIPO_DATO_COMPLETO"), rs.getString("TIPO_DATO"),  rs.getString("LONGITUD"), rs.getString("ESCALA"),  rs.getString("ESCLAVE"));
					results.add(c);
				}
				
			}
			
			System.out.println("+++++++++++++ QUERY: " + query);
			
		}catch(SQLException e){
			e.printStackTrace();
			log.error("Error consultado campos", e);
//			return (new ArrayList<Campo>(Arrays.asList(new Campo("ACID","Account Id","Número de la cuenta del cliente","VARCHAR2(0)","VARCHAR2","30","0","S"))));
		}
		catch (RuntimeException re) {
			re.printStackTrace();
			log.error("Error consultado campos", re);
			throw re;
		} 
		return results;
	}	
		
	/**
	 * Permite realizar la consulta construida dinamicamente en el Bean
	 * @param query
	 * @param camposRetorno
	 * @return
	 */
	public ArrayList<LinkedHashMap<String, String>> consultaDinamica(String query, ArrayList<Campo> camposRetorno) {
		log.debug("Consulta dinamica: " + query);
		
		ArrayList<LinkedHashMap<String, String>> camposTabla = new ArrayList<LinkedHashMap<String, String>>();
		
		CallableStatement call = null;
		ResultSet rs = null;
		try {
			
			if ((query != null && !query.isEmpty()) && (camposRetorno != null && !camposRetorno.isEmpty()) ) {
				Session session = sessionFactory.getCurrentSession();
				
				call = ((SessionImpl) session).connection().prepareCall("{call P_MIH_EJECUTAR_CONSULTA(?,?,?)}");
				call.registerOutParameter(1, OracleTypes.CURSOR);
				call.setString(2, query);
				call.registerOutParameter(3, OracleTypes.NUMBER);
				call.execute();
				rs = (ResultSet) call.getObject(1);				
				
				while (rs.next()) {
					LinkedHashMap<String, String> valoresRegistro = new LinkedHashMap<String, String>();
					for (Campo campo : camposRetorno) {
						valoresRegistro.put(campo.getNombreCorto(), rs.getString(campo.getNombreCorto()));
					}
					camposTabla.add(valoresRegistro);
				}
			}
		}catch(SQLException e){
			log.error("Error realizando la consulta", e);
		}
		catch (RuntimeException re) {
			log.error("Error realizando la consulta", re);
			throw re;
		} 
		return camposTabla;
	}
	
	/**
	 * Realiza una consulta y retorna ResultSet
	 * @param query
	 * @return
	 */
	public CachedRowSetImpl consultaDinamica(String query) {
		log.debug("Consulta dinamica: " + query);
		
		CallableStatement call = null;
		CachedRowSetImpl crs = null;
		ResultSet rs = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			
						
			call = ((SessionImpl) session).connection().prepareCall("{call P_MIH_EJECUTAR_CONSULTA(?,?,?)}");
			call.registerOutParameter(1, OracleTypes.CURSOR);
			call.setString(2, query);
			call.registerOutParameter(3, OracleTypes.NUMBER);
			call.execute();
			rs = (ResultSet) call.getObject(1);
			
			crs = new CachedRowSetImpl();
	        crs.populate(rs);
									
			return crs;
		}catch(SQLException e){
			log.error("Error realizando la consulta", e);
		}
		catch (RuntimeException re) {
			log.error("Error realizando la consulta", re);
			throw re;
		} 
		return null;
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
	
}
