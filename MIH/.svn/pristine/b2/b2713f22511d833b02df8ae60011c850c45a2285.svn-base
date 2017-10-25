package com.sophos.semih.dao;

// Generated Dec 11, 2013 4:02:22 PM by Hibernate Tools 4.0.0

import static org.hibernate.criterion.Example.create;

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.sophos.semih.model.TMihConstbl;

/**
 * Home object for domain model class TMihConstbl.
 * @see com.sophos.semih.model.TMihConstbl
 * @author Hibernate Tools
 */
public class TMihConstblHome {

	private static final Log log = LogFactory.getLog(TMihConstblHome.class);

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

	public void persist(TMihConstbl transientInstance) {
		log.debug("persisting TMihConstbl instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TMihConstbl instance) {
		log.debug("attaching dirty TMihConstbl instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(TMihConstbl instance) {
		log.debug("attaching clean TMihConstbl instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TMihConstbl persistentInstance) {
		log.debug("deleting TMihConstbl instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMihConstbl merge(TMihConstbl detachedInstance) {
		log.debug("merging TMihConstbl instance");
		try {
			TMihConstbl result = (TMihConstbl) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TMihConstbl findById(com.sophos.semih.model.TMihConstblId id) {
		log.debug("getting TMihConstbl instance with id: " + id);
		try {
			TMihConstbl instance = (TMihConstbl) sessionFactory
					.getCurrentSession().get(
							"com.sophos.semih.model.TMihConstbl", id);
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
	public List<TMihConstbl> findByExample(TMihConstbl instance) {
		log.debug("finding TMihConstbl instance by example");
		try {
			List<TMihConstbl> results = (List<TMihConstbl>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.sophos.semih.model.TMihConstbl")
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
	public List<TMihConstbl> getEntidades(int consulta) {
		log.debug("getting TMihConstbl instances with consulta and entidad: " + consulta);
		try {

				String strHql = "";

				strHql =  "SELECT tabla "
						+ " FROM com.sophos.semih.model.TMihConstbl tabla "
						+ " WHERE tabla.id.codconsulta = " + consulta ;

			Query query = sessionFactory.getCurrentSession().createQuery(strHql);

			return (List<TMihConstbl>) query.list();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
}
