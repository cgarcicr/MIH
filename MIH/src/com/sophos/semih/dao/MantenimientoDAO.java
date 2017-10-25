/**
 * 
 */
package com.sophos.semih.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;

import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;

import com.sophos.semih.common.Constants;

/**
 * @author FT
 *
 */
public class MantenimientoDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 631045519215445608L;
	private static final Log log = LogFactory.getLog(MantenimientoDAO.class);
	private final SessionFactory sessionFactory = getSessionFactory();
	
	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("sessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}
	
	/**
	 * @param dias
	 * @return
	 */
	public int depurarLogs(Integer dias){
		int resultado = 0;
		Session session = sessionFactory.getCurrentSession();
		CallableStatement call = null;
		try {
			    call = ((SessionImpl) session).connection().prepareCall("{call P_MIH_LOG_DEPURACION(?,?)}");
				call.setInt(1, dias);
				call.registerOutParameter(2, OracleTypes.NUMBER);
				call.execute();
				
		} catch(SQLException e){
			log.error("Error de SQL depurando logs", e);
			return 1;
		} catch (RuntimeException re) {
			log.error("Falló borrado de registros de log", re);
			return 1;
		} finally {
			try {
				if(call != null)
					call.close();
			} catch (SQLException e) {
				log.error("Error al cerrar la conexión", e);
			}
		}
		
		System.out.println("--------------- "+resultado);
		return resultado;
	}

	/**
	 * @param dias
	 * @return
	 */
	public int depurarInfoHistorica(String entidad){
		int resultado = 0;
		Session session = sessionFactory.getCurrentSession();
		CallableStatement call = null;
		try {
			call = ((SessionImpl) session).connection().prepareCall("{call P_MIH_INFO_DEPURACION(?,?)}");
			if(entidad != null && !entidad.equals(Constants.EMPTY_STRING))
				call.setString(1, entidad);
			else
				call.setNull(1, OracleTypes.NULL);
			call.registerOutParameter(2, OracleTypes.NUMBER);
			call.execute();
			resultado = ((BigDecimal)call.getObject(2)).intValue();
			
		} catch(SQLException e){
			log.error("Error de SQL depurando información histórica", e);
			return 1;
		} catch (RuntimeException re) {
			log.error("Falló borrado de registros de información histórica", re);
			return 1;
		} finally {
			try {
				if(call != null)
					call.close();
			} catch (SQLException e) {
				log.error("Error al cerrar la conexión", e);
			}
		}
		return resultado;
	}
	
	/**
	 * @param entidad
	 * @param campoFecha
	 * @param vigencia
	 * @return
	 */
	public String conteoInformacionDepurar(String entidad, String campoFecha, Integer vigencia){
		String resultado = Constants.EMPTY_STRING;
		Session session = sessionFactory.getCurrentSession();
		CallableStatement call = null;
		try {
			call = ((SessionImpl) session).connection().prepareCall("{call P_MIH_INFO_DEPURACION_CONTEO(?,?,?,?,?)}");
			call.setString(1, entidad);
			call.setString(2, campoFecha);
			call.setInt(3, vigencia);
			call.registerOutParameter(4, OracleTypes.NUMBER);
			call.registerOutParameter(5, OracleTypes.NUMBER);
			call.execute();
			resultado = ((BigDecimal)call.getObject(4)).toString() + Constants.SEPARATOR + ((BigDecimal)call.getObject(5)).toString();
			
		} catch(SQLException e){
			log.error("Error de SQL en la consulta de información a depurar", e);
		} catch (RuntimeException re) {
			log.error("Falló consulta de vigencia de información histórica", re);
			return resultado;
		} finally {
			try {
				if(call != null)
					call.close();
			} catch (SQLException e) {
				log.error("Error al cerrar la conexión", e);
			}
		}
		return resultado;
	}
}
