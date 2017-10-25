package com.sophos.semih.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.naming.InitialContext;

import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;

import com.sophos.semih.model.Campo;

public class ConsultaDinamicaDAO implements Serializable{

	private static final long serialVersionUID = 7668547378259642825L;
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
	
	
	public ArrayList<LinkedHashMap<String, String>> consultaDinamica(String query, ArrayList<Campo> camposRetorno) {
		log.debug("Consulta dinamica: " + query);
		
		ArrayList<LinkedHashMap<String, String>> camposTabla = new ArrayList<LinkedHashMap<String, String>>();
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
				LinkedHashMap<String, String> valoresRegistro = new LinkedHashMap<String, String>();
				for (Campo campo : camposRetorno) {
					valoresRegistro.put(campo.getNombreCorto(), rs.getString(campo.getNombreCorto()));
				}
				camposTabla.add(valoresRegistro);
			}
		}catch(SQLException e){
			log.error("Error efectuando consultaDinamica()", e);
			try {
				throw e;
			} catch (SQLException e1) {
				log.error("Error efectuando consultaDinamica()", e1);
			}
		}
		catch (RuntimeException re) {
			log.error("Error efectuando consultaDinamica()", re);
			throw re;
		} finally {
			try {
				if (call != null && !call.isClosed()) {
					call.close();
				}
				
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				log.error("Error efectuando consultaDinamica()", e);
			}
		}
		
		return camposTabla;
	}
	
	
}
