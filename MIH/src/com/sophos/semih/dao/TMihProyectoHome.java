package com.sophos.semih.dao;

// Generated 28/08/2013 09:27:17 AM by Hibernate Tools 4.0.0

import static org.hibernate.criterion.Example.create;

import java.io.Serializable;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;

import com.sophos.semih.model.TMihProyecto;

/**
 * Home object for domain model class TMihProyecto.
 * @see com.sophos.semih.model.TMihProyecto
 * @author Hibernate Tools
 */
public class TMihProyectoHome implements Serializable {
	private static final long serialVersionUID = 1314511872865782980L;

	private static final Log log = LogFactory.getLog(TMihProyectoHome.class);

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

	public void persist(TMihProyecto transientInstance) {
		log.debug("persisting TMihProyecto instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TMihProyecto instance) {
		log.debug("attaching dirty TMihProyecto instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(TMihProyecto instance) {
		log.debug("attaching clean TMihProyecto instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TMihProyecto persistentInstance) {
		log.debug("deleting TMihProyecto instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMihProyecto merge(TMihProyecto detachedInstance) {
		log.debug("merging TMihProyecto instance");
		try {
			TMihProyecto result = (TMihProyecto) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TMihProyecto findById(int id) {
		log.debug("getting TMihProyecto instance with id: " + id);
		try {
			TMihProyecto instance = (TMihProyecto) sessionFactory
					.getCurrentSession().get(
							"com.sophos.semih.model.TMihProyecto", id);
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
	public List<TMihProyecto> findByExample(TMihProyecto instance) {
		log.debug("finding TMihProyecto instance by example");
		try {
			List<TMihProyecto> results = (List<TMihProyecto>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.sophos.semih.model.TMihProyecto")
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
