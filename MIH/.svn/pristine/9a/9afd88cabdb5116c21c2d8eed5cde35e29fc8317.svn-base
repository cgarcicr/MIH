package com.sophos.semih.dao;

// Generated Dec 11, 2013 4:02:22 PM by Hibernate Tools 4.0.0

import static org.hibernate.criterion.Example.create;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import javax.naming.InitialContext;

import oracle.jdbc.OracleTypes;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;

import com.sophos.semih.common.Constants;
import com.sophos.semih.model.TMihConsulta;
import com.sophos.semih.model.TMihUsuario;

/**
 * Home object for domain model class TMihConsulta.
 * @see com.sophos.semih.model.TMihConsulta
 * @author Hibernate Tools
 */
public class TMihConsultaHome {

	private static final Log log = LogFactory.getLog(TMihConsultaHome.class);

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

	public void persist(TMihConsulta transientInstance) {
		log.debug("persisting TMihConsulta instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TMihConsulta instance) {
		log.debug("attaching dirty TMihConsulta instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(TMihConsulta instance) {
		log.debug("attaching clean TMihConsulta instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TMihConsulta persistentInstance) {
		log.debug("deleting TMihConsulta instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMihConsulta merge(TMihConsulta detachedInstance) {
		log.debug("merging TMihConsulta instance");
		try {
			TMihConsulta result = (TMihConsulta) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TMihConsulta findById(int id) {
		log.debug("getting TMihConsulta instance with id: " + id);
		try {
			TMihConsulta instance = (TMihConsulta) sessionFactory
					.getCurrentSession().get(
							"com.sophos.semih.model.TMihConsulta", id);
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
	public List<TMihConsulta> findByExample(TMihConsulta instance) {
		log.debug("finding TMihConsulta instance by example");
		try {
			List<TMihConsulta> results = (List<TMihConsulta>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.sophos.semih.model.TMihConsulta")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TMihConsulta> consultasxusuario(TMihUsuario usuario) {
		log.debug("finding TMihConsulta instance by user");
		try {

		String strHql = "";

		if(usuario.getTMihRol().getCodigo() == Constants.COD_USR_SUPERUSR || usuario.getTMihRol().getCodigo() == Constants.COD_USR_ADM){
		strHql = "SELECT consulta "
		+ " FROM com.sophos.semih.model.TMihConsulta consulta";

		Query query = sessionFactory.getCurrentSession().createQuery(strHql);
		return (List<TMihConsulta>) query.list();

		}else{

		List<TMihConsulta> consulta1;
		List<TMihConsulta> consulta2;
		List<TMihConsulta> consulta;

		strHql = "SELECT consulta "
		+ " FROM com.sophos.semih.model.TMihConsulta consulta, "
		+ " com.sophos.semih.model.TMihConstbl tabla, "
		+ " com.sophos.semih.model.TMihEntxusr entUsr "
		+ " WHERE entUsr.id.codentidad = tabla.id.codentidad " 
		+ " AND tabla.id.codconsulta = consulta.codigo "
		+ " AND entUsr.id.codusuario = consulta.TMihUsuario.codigo "
		+ " AND entUsr.id.codusuario = " + usuario.getCodigo();

		Query query = sessionFactory.getCurrentSession().createQuery(strHql);
		consulta1 = (List<TMihConsulta>) query.list(); 

		strHql = " SELECT consulta "
		+ " FROM com.sophos.semih.model.TMihConsulta consulta, "
		+ " com.sophos.semih.model.TMihConstbl tabla, "
		+ " com.sophos.semih.model.TMihEntidad entidad "
		+ " WHERE entidad.TMihAplicacion.TMihLineanegocio.codigo = " + usuario.getTMihLineanegocio().getCodigo()
		+ " AND tabla.id.codconsulta = consulta.codigo "
		+ " AND tabla.id.codentidad = entidad.codigo ";

		query = sessionFactory.getCurrentSession().createQuery(strHql);
		consulta2 = (List<TMihConsulta>) query.list(); 

		//Se unifican los resultados
		consulta = ListUtils.union(consulta1, consulta2);
		//Se eliminan los duplicados
		HashSet<TMihConsulta> hs = new HashSet<TMihConsulta>();
		hs.addAll(consulta);
		consulta.clear();
		consulta.addAll(hs);

		return consulta;
		}

		} catch (RuntimeException re) {
		log.error("consulta por usuario failed", re);
		throw re;
		}
	}
	
	/**
	 * Realiza una consulta y retorna ResultSet
	 * @param query
	 * @return
	 */
	public int nextVal(String query) {
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
