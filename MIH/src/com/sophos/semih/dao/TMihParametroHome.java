package com.sophos.semih.dao;

// Generated 30/08/2013 08:25:34 AM by Hibernate Tools 4.0.0

import static org.hibernate.criterion.Example.create;

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;

import com.sophos.semih.model.TMihParametro;

/**
 * Home object for domain model class TMihParametro.
 * @see com.sophos.semih.model.TMihParametro
 * @author Hibernate Tools
 */
public class TMihParametroHome {

	private static final Log log = LogFactory.getLog(TMihParametroHome.class);

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

	public void persist(TMihParametro transientInstance) {
		log.debug("persisting TMihParametro instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TMihParametro instance) {
		log.debug("attaching dirty TMihParametro instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(TMihParametro instance) {
		log.debug("attaching clean TMihParametro instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TMihParametro persistentInstance) {
		log.debug("deleting TMihParametro instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMihParametro merge(TMihParametro detachedInstance) {
		log.debug("merging TMihParametro instance");
		try {
			TMihParametro result = (TMihParametro) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TMihParametro findById(java.lang.String id) {
		log.debug("getting TMihParametro instance with id: " + id);
		try {
			TMihParametro instance = (TMihParametro) sessionFactory
					.getCurrentSession().get(
							"com.sophos.semih.model.TMihParametro", id);
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
	public List<TMihParametro> findByExample(TMihParametro instance) {
		log.debug("finding TMihParametro instance by example");
		try {
			List<TMihParametro> results = (List<TMihParametro>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.sophos.semih.model.TMihParametro")
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
