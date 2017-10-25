package com.sophos.semih.dao;

// Generated 28/08/2013 09:27:17 AM by Hibernate Tools 4.0.0

import static org.hibernate.criterion.Example.create;

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;

import com.sophos.semih.model.TMihUsuario;

/**
 * Home object for domain model class TMihUsuario.
 * @see com.sophos.semih.model.TMihUsuario
 * @author Hibernate Tools
 */
public class TMihUsuarioHome {

	private static final Log log = LogFactory.getLog(TMihUsuarioHome.class);

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
	
	public void setSessionFactory(SessionFactory sessionFactory)
    {
        
    }

	public void persist(TMihUsuario transientInstance) {
		log.debug("persisting TMihUsuario instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TMihUsuario instance) {
		log.debug("attaching dirty TMihUsuario instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(TMihUsuario instance) {
		log.debug("attaching clean TMihUsuario instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TMihUsuario persistentInstance) {
		log.debug("deleting TMihUsuario instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMihUsuario merge(TMihUsuario detachedInstance) {
		log.debug("merging TMihUsuario instance");
		try {
			TMihUsuario result = (TMihUsuario) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TMihUsuario findById(int id) {
		log.debug("getting TMihUsuario instance with id: " + id);
		try {
			TMihUsuario instance = (TMihUsuario) sessionFactory
					.getCurrentSession().get(
							"com.sophos.semih.model.TMihUsuario", id);
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
	public List<TMihUsuario> findByExample(TMihUsuario instance) {
		log.debug("finding TMihUsuario instance by example");
		try {
			List<TMihUsuario> results = (List<TMihUsuario>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.sophos.semih.model.TMihUsuario")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public Integer nextCodigoUsuario(TMihUsuario instance) {
		log.debug("finding next codigo of TMihUsuario");
		try {

			Criteria crit = sessionFactory.getCurrentSession()
					.createCriteria("com.sophos.semih.model.TMihUsuario")
					.setProjection(Projections.max("codigo"));
			Integer resultado = (Integer) crit.uniqueResult();
			int maxCodigo = (resultado == null) ? new Integer(0) : resultado;			
			return maxCodigo+1;
		} catch (RuntimeException re) {
			log.error("find next codigo usuario failed", re);
			throw re;
		}
	}
}
