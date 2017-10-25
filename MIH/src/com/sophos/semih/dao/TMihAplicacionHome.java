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

import com.sophos.semih.model.TMihAplicacion;

/**
 * Home object for domain model class TMihAplicacion.
 * @see com.sophos.semih.model.TMihAplicacion
 * @author Hibernate Tools
 */
public class TMihAplicacionHome implements Serializable{

	private static final long serialVersionUID = 678044749938986322L;

	private static final Log log = LogFactory.getLog(TMihAplicacionHome.class);

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

	public void persist(TMihAplicacion transientInstance) {
		log.debug("persisting TMihAplicacion instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TMihAplicacion instance) {
		log.debug("attaching dirty TMihAplicacion instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(TMihAplicacion instance) {
		log.debug("attaching clean TMihAplicacion instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TMihAplicacion persistentInstance) {
		log.debug("deleting TMihAplicacion instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMihAplicacion merge(TMihAplicacion detachedInstance) {
		log.debug("merging TMihAplicacion instance");
		try {
			TMihAplicacion result = (TMihAplicacion) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TMihAplicacion findById(int id) {
		log.debug("getting TMihAplicacion instance with id: " + id);
		try {
			TMihAplicacion instance = (TMihAplicacion) sessionFactory
					.getCurrentSession().get(
							"com.sophos.semih.model.TMihAplicacion", id);
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
	public List<TMihAplicacion> findByExample(TMihAplicacion instance) {
		log.debug("finding TMihAplicacion instance by example");
		try {
			List<TMihAplicacion> results = (List<TMihAplicacion>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.sophos.semih.model.TMihAplicacion")
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
	public List<TMihAplicacion> findByLN(TMihAplicacion instance) {
		log.debug("finding list of TMihAplicacion instance by lineaNegocio associated");
		try {
			List<TMihAplicacion> results;

			Criteria crit = sessionFactory.getCurrentSession().createCriteria(
					"com.sophos.semih.model.TMihAplicacion");
			Example ex = Example.create(instance);
			crit.add(ex)
					.add(Restrictions.eq("TMihLineanegocio",
							instance.getTMihLineanegocio()))
			;
			results = (List<TMihAplicacion>) crit.list();
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	
}
