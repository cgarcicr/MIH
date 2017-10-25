package com.sophos.semih.dao;

// Generated 28/08/2013 09:27:17 AM by Hibernate Tools 4.0.0

import static org.hibernate.criterion.Example.create;

import java.io.Serializable;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import com.sophos.semih.model.TMihLineanegocio;

/**
 * Home object for domain model class TMihLineanegocio.
 * 
 * @see com.sophos.semih.model.TMihLineanegocio
 * @author Hibernate Tools
 */
public class TMihLineanegocioHome implements Serializable{
	private static final long serialVersionUID = 6988646491205032870L;

	private static final Log log = LogFactory
			.getLog(TMihLineanegocioHome.class);

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

	public void persist(TMihLineanegocio transientInstance) {
		log.debug("persisting TMihLineanegocio instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TMihLineanegocio instance) {
		log.debug("attaching dirty TMihLineanegocio instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(TMihLineanegocio instance) {
		log.debug("attaching clean TMihLineanegocio instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TMihLineanegocio persistentInstance) {
		log.debug("deleting TMihLineanegocio instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMihLineanegocio merge(TMihLineanegocio detachedInstance) {
		log.debug("merging TMihLineanegocio instance");
		try {
			TMihLineanegocio result = (TMihLineanegocio) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TMihLineanegocio findById(int id) {
		log.debug("getting TMihLineanegocio instance with id: " + id);
		try {
			TMihLineanegocio instance = (TMihLineanegocio) sessionFactory
					.getCurrentSession().get(
							"com.sophos.semih.model.TMihLineanegocio", id);
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

	public List<TMihLineanegocio> findByExample(TMihLineanegocio instance) {
		log.debug("finding TMihLineanegocio instance by example");
		try {
			@SuppressWarnings("unchecked")
			List<TMihLineanegocio> results = (List<TMihLineanegocio>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.sophos.semih.model.TMihLineanegocio")
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
	public List<TMihLineanegocio> findByProject(TMihLineanegocio instance) {
		log.debug("finding list of TMihAplicacion instance by proyecto associated");
		try {
			List<TMihLineanegocio> results;

			Criteria crit = sessionFactory.getCurrentSession().createCriteria(
					"com.sophos.semih.model.TMihLineanegocio");
			Example ex = Example.create(instance);
			crit.add(ex)
					.add(Restrictions.eq("TMihProyecto",
							instance.getTMihProyecto()))
			;
			results = (List<TMihLineanegocio>) crit.list();
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
