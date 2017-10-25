package com.sophos.semih.dao;

// Generated 28/08/2013 09:27:17 AM by Hibernate Tools 4.0.0

import static org.hibernate.criterion.Example.create;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;

import javax.naming.InitialContext;

import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;

import com.sophos.semih.model.TMihCatalogo;

/**
 * Home object for domain model class TMihCatalogo.
 * 
 * @see com.sophos.semih.model.TMihCatalogo
 * @author Hibernate Tools
 */
public class TMihCatalogoHome implements Serializable {
	private static final long serialVersionUID = -6117300554405497540L;

	private static final Log log = LogFactory.getLog(TMihCatalogoHome.class);

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

	public void persist(TMihCatalogo transientInstance) {
		log.debug("persisting TMihCatalogo instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TMihCatalogo instance) {
		log.debug("attaching dirty TMihCatalogo instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(TMihCatalogo instance) {
		log.debug("attaching clean TMihCatalogo instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TMihCatalogo persistentInstance) {
		log.debug("deleting TMihCatalogo instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMihCatalogo merge(TMihCatalogo detachedInstance) {
		log.debug("merging TMihCatalogo instance");
		try {
			TMihCatalogo result = (TMihCatalogo) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TMihCatalogo findById(com.sophos.semih.model.TMihCatalogoId id) {
		log.debug("getting TMihCatalogo instance with id: " + id);
		try {
			TMihCatalogo instance = (TMihCatalogo) sessionFactory
					.getCurrentSession().get(
							"com.sophos.semih.model.TMihCatalogo", id);
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
	public List<TMihCatalogo> findByExample(TMihCatalogo instance) {
		log.debug("finding TMihCatalogo instance by example");
		try {
			List<TMihCatalogo> results = (List<TMihCatalogo>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.sophos.semih.model.TMihCatalogo")
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
	public List<TMihCatalogo> findByTipoCatalogo(TMihCatalogo instance) {
		log.debug("finding list of TMihCatalogo instance by tipo catalogo");
		try {
			List<TMihCatalogo> results;

			Criteria crit = sessionFactory.getCurrentSession().createCriteria(
					"com.sophos.semih.model.TMihCatalogo");
			Example ex = Example.create(instance);
			crit.add(ex).add(
					Restrictions.eq("TMihTipocatalogo",
							instance.getTMihTipocatalogo()));
			results = (List<TMihCatalogo>) crit.list();
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public String nextCodigoCatalogo(TMihCatalogo instance) {
		log.debug("finding next codigo of TMihCatalogo instance by tipo catalogo");
		
		try {

			int maxCode = 0;
			Query queryHQL = sessionFactory.getCurrentSession().createQuery("SELECT max(cast(C.id.codigo as int)) FROM TMihCatalogo C where C.TMihTipocatalogo.codmaestro = ? ");
			queryHQL.setInteger(0, instance.getTMihTipocatalogo().getCodmaestro());
			@SuppressWarnings("unchecked")
			List<Integer> list = queryHQL.list();
			maxCode = list.get(0) != null ? list.get(0) : 0;
			return String.valueOf(maxCode+1);
			
		} catch (RuntimeException re) {	
			log.error("find next codigo catalogo failed", re);
			throw re;
		}
	}
	
	/**
	 * Permite consultar el estado de las cargas
	 * @param entidad
	 * @usuario usuario de base de datos
	 * @return
	 */
	public BigDecimal eliminarCatalogo(int tipoCatalogo, String catalogo, String tipo){
	
		log.debug("Parámetros de Entrada: " + tipoCatalogo + "||" + catalogo + "||" + tipo + "||" + ".");
		
		BigDecimal activo = new BigDecimal(0);
		
		try{
			Session session = sessionFactory.getCurrentSession();
			
			CallableStatement call;
			
			call = ((SessionImpl) session).connection().prepareCall("{call P_MIH_ELIMINACATALOGO(?,?,?,?,?)}");
			call.setInt(1, tipoCatalogo);
			call.setString(2, catalogo);
			call.setString(3, tipo);
			call.registerOutParameter(4, OracleTypes.NUMBER);
			call.registerOutParameter(5, OracleTypes.NUMBER);
			
			call.execute();
			
			activo = (BigDecimal)call.getObject(4);
		
		}catch(SQLException e){
			log.error("error getting information from table", e);
			
		}
		catch (RuntimeException re) {
			log.error("error getting information from table", re);
			throw re;
		}
		return activo;
	}

}
