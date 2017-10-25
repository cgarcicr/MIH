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

import com.sophos.semih.model.TMihTipocatalogo;

/**
 * Home object for domain model class TMihTipocatalogo.
 * @see com.sophos.semih.model.TMihTipocatalogo
 * @author Hibernate Tools
 */
public class TMihTipocatalogoHome implements Serializable {

	private static final long serialVersionUID = -4672098847391708581L;

	private static final Log log = LogFactory
			.getLog(TMihTipocatalogoHome.class);

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

	public void persist(TMihTipocatalogo transientInstance) {
		log.debug("persisting TMihTipocatalogo instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TMihTipocatalogo instance) {
		log.debug("attaching dirty TMihTipocatalogo instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(TMihTipocatalogo instance) {
		log.debug("attaching clean TMihTipocatalogo instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TMihTipocatalogo persistentInstance) {
		log.debug("deleting TMihTipocatalogo instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMihTipocatalogo merge(TMihTipocatalogo detachedInstance) {
		log.debug("merging TMihTipocatalogo instance");
		try {
			TMihTipocatalogo result = (TMihTipocatalogo) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TMihTipocatalogo findById(short id) {
		log.debug("getting TMihTipocatalogo instance with id: " + id);
		try {
			TMihTipocatalogo instance = (TMihTipocatalogo) sessionFactory
					.getCurrentSession().get(
							"com.sophos.semih.model.TMihTipocatalogo", id);
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
	public List<TMihTipocatalogo> findByExample(TMihTipocatalogo instance) {
		log.debug("finding TMihTipocatalogo instance by example");
		try {
			List<TMihTipocatalogo> results = (List<TMihTipocatalogo>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.sophos.semih.model.TMihTipocatalogo")
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
