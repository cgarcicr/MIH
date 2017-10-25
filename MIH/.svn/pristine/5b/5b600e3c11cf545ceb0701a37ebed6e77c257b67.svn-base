package com.sophos.semih.dao;

// Generated Dec 11, 2013 4:02:22 PM by Hibernate Tools 4.0.0

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.sophos.semih.model.TMihConsfld;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class TMihConsfld.
 * @see com.sophos.semih.model.TMihConsfld
 * @author Hibernate Tools
 */
public class TMihConsfldHome {

	private static final Log log = LogFactory.getLog(TMihConsfldHome.class);

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

	public void persist(TMihConsfld transientInstance) {
		log.debug("persisting TMihConsfld instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TMihConsfld instance) {
		log.debug("attaching dirty TMihConsfld instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(TMihConsfld instance) {
		log.debug("attaching clean TMihConsfld instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TMihConsfld persistentInstance) {
		log.debug("deleting TMihConsfld instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMihConsfld merge(TMihConsfld detachedInstance) {
		log.debug("merging TMihConsfld instance");
		try {
			TMihConsfld result = (TMihConsfld) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TMihConsfld findById(com.sophos.semih.model.TMihConsfldId id) {
		log.debug("getting TMihConsfld instance with id: " + id);
		try {
			TMihConsfld instance = (TMihConsfld) sessionFactory
					.getCurrentSession().get(
							"com.sophos.semih.model.TMihConsfld", id);
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
	public List<TMihConsfld> findByExample(TMihConsfld instance) {
		log.debug("finding TMihConsfld instance by example");
		try {
			List<TMihConsfld> results = (List<TMihConsfld>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.sophos.semih.model.TMihConsfld")
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
	public List<TMihConsfld> getCampos(int entidad, int consulta, int sectabla) {
		log.debug("getting TMihConstbl instances with consulta and entidad: " + consulta);
		try {
				String strHql = "";
				strHql =  "SELECT campo "
						+ " FROM com.sophos.semih.model.TMihConsfld campo "
						+ " WHERE campo.id.codentidad = " + entidad 
						+ " AND campo.id.codconsulta = " + consulta 
						+ " AND campo.id.sectabla = " + sectabla;
			Query query = sessionFactory.getCurrentSession().createQuery(strHql);

			return (List<TMihConsfld>) query.list();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
