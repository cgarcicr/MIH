package com.sophos.semih.dao;

// Generated 28/08/2013 09:27:17 AM by Hibernate Tools 4.0.0

import static org.hibernate.criterion.Example.create;

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;

import com.sophos.semih.model.TMihRol;

/**
 * Home object for domain model class TMihRol.
 * @see com.sophos.semih.model.TMihRol
 * @author Hibernate Tools
 */
public class TMihRolHome {

	private static final Log log = LogFactory.getLog(TMihRolHome.class);

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

	public void persist(TMihRol transientInstance) {
		log.debug("persisting TMihRol instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TMihRol instance) {
		log.debug("attaching dirty TMihRol instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(TMihRol instance) {
		log.debug("attaching clean TMihRol instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TMihRol persistentInstance) {
		log.debug("deleting TMihRol instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMihRol merge(TMihRol detachedInstance) {
		log.debug("merging TMihRol instance");
		try {
			TMihRol result = (TMihRol) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TMihRol findById(int id) {
		log.debug("getting TMihRol instance with id: " + id);
		try {
			TMihRol instance = (TMihRol) sessionFactory.getCurrentSession()
					.get("com.sophos.semih.model.TMihRol", id);
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
	public List<TMihRol> findByExample(TMihRol instance) {
		log.debug("finding TMihRol instance by example");
		try {
			List<TMihRol> results = (List<TMihRol>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.sophos.semih.model.TMihRol")
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
