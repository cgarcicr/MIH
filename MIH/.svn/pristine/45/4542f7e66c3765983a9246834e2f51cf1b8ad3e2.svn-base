package com.sophos.semih.dao;

// Generated 9/10/2013 10:17:22 AM by Hibernate Tools 4.0.0

import static org.hibernate.criterion.Example.create;

import java.io.Serializable;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;

import com.sophos.semih.model.TMihSesion;

/**
 * Home object for domain model class TMihSesion.
 * @see com.sophos.semih.model.TMihSesion
 * @author Hibernate Tools
 */
public class TMihSesionHome implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5513745122722325414L;

	private static final Log log = LogFactory.getLog(TMihSesionHome.class);

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

	public void persist(TMihSesion transientInstance) {
		log.debug("persisting TMihSesion instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TMihSesion instance) {
		log.debug("attaching dirty TMihSesion instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(TMihSesion instance) {
		log.debug("attaching clean TMihSesion instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TMihSesion persistentInstance) {
		log.debug("deleting TMihSesion instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMihSesion merge(TMihSesion detachedInstance) {
		log.debug("merging TMihSesion instance");
		try {
			TMihSesion result = (TMihSesion) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TMihSesion findById(java.lang.String id) {
		log.debug("getting TMihSesion instance with id: " + id);
		try {
			TMihSesion instance = (TMihSesion) sessionFactory
					.getCurrentSession().get("com.sophos.semih.model.TMihSesion",
							id);
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
	public List<TMihSesion> findByExample(TMihSesion instance) {
		log.debug("finding TMihSesion instance by example");
		try {
			List<TMihSesion> results = (List<TMihSesion>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.sophos.semih.model.TMihSesion")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
