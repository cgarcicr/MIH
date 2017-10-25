package com.sophos.semih.dao;

// Generated 28/08/2013 10:01:37 AM by Hibernate Tools 4.0.0

import static org.hibernate.criterion.Example.create;

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;

import com.sophos.semih.model.TMihEntxusr;

/**
 * Home object for domain model class TMihEntxusr.
 * @see com.sophos.semih.model.TMihEntxusr
 * @author Hibernate Tools
 */
public class TMihEntxusrHome {

	private static final Log log = LogFactory.getLog(TMihEntxusrHome.class);

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

	public void persist(TMihEntxusr transientInstance) {
		log.debug("persisting TMihEntxusr instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TMihEntxusr instance) {
		log.debug("attaching dirty TMihEntxusr instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(TMihEntxusr instance) {
		log.debug("attaching clean TMihEntxusr instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TMihEntxusr persistentInstance) {
		log.debug("deleting TMihEntxusr instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMihEntxusr merge(TMihEntxusr detachedInstance) {
		log.debug("merging TMihEntxusr instance");
		try {
			TMihEntxusr result = (TMihEntxusr) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TMihEntxusr findById(com.sophos.semih.model.TMihEntxusrId id) {
		log.debug("getting TMihEntxusr instance with id: " + id);
		try {
			TMihEntxusr instance = (TMihEntxusr) sessionFactory
					.getCurrentSession().get(
							"com.sophos.semih.model.TMihEntxusr", id);
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
	public List<TMihEntxusr> findByExample(TMihEntxusr instance) {
		log.debug("finding TMihEntxusr instance by example");
		try {
			List<TMihEntxusr> results = (List<TMihEntxusr>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.sophos.semih.model.TMihEntxusr")
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
