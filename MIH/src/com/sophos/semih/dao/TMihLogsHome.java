package com.sophos.semih.dao;

// Generated 28/08/2013 09:27:17 AM by Hibernate Tools 4.0.0

import static org.hibernate.criterion.Example.create;

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;

import com.sophos.semih.model.TMihLogs;

/**
 * Home object for domain model class TMihLogs.
 * @see com.sophos.semih.model.TMihLogs
 * @author Hibernate Tools
 */
public class TMihLogsHome {

	private static final Log log = LogFactory.getLog(TMihLogsHome.class);

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

	public void persist(TMihLogs transientInstance) {
		log.debug("persisting TMihLogs instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TMihLogs instance) {
		log.debug("attaching dirty TMihLogs instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(TMihLogs instance) {
		log.debug("attaching clean TMihLogs instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TMihLogs persistentInstance) {
		log.debug("deleting TMihLogs instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMihLogs merge(TMihLogs detachedInstance) {
		log.debug("merging TMihLogs instance");
		try {
			TMihLogs result = (TMihLogs) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TMihLogs findById(com.sophos.semih.model.TMihLogsId id) {
		log.debug("getting TMihLogs instance with id: " + id);
		try {
			TMihLogs instance = (TMihLogs) sessionFactory.getCurrentSession()
					.get("com.sophos.semih.model.TMihLogs", id);
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
	public List<TMihLogs> findByExample(TMihLogs instance) {
		log.debug("finding TMihLogs instance by example");
		try {
			List<TMihLogs> results = (List<TMihLogs>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.sophos.semih.model.TMihLogs")
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
