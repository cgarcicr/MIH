package com.sophos.semih.dao;

// Generated 28/08/2013 09:27:17 AM by Hibernate Tools 4.0.0

import static org.hibernate.criterion.Example.create;

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;

import com.sophos.semih.model.EmpXml;

/**
 * Home object for domain model class EmpXml.
 * @see com.sophos.semih.model.EmpXml
 * @author Hibernate Tools
 */
public class EmpXmlHome {

	private static final Log log = LogFactory.getLog(EmpXmlHome.class);

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

	public void persist(EmpXml transientInstance) {
		log.debug("persisting EmpXml instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(EmpXml instance) {
		log.debug("attaching dirty EmpXml instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(EmpXml instance) {
		log.debug("attaching clean EmpXml instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(EmpXml persistentInstance) {
		log.debug("deleting EmpXml instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public EmpXml merge(EmpXml detachedInstance) {
		log.debug("merging EmpXml instance");
		try {
			EmpXml result = (EmpXml) sessionFactory.getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public EmpXml findById(com.sophos.semih.model.EmpXmlId id) {
		log.debug("getting EmpXml instance with id: " + id);
		try {
			EmpXml instance = (EmpXml) sessionFactory.getCurrentSession().get(
					"com.sophos.semih.model.EmpXml", id);
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
	public List<EmpXml> findByExample(EmpXml instance) {
		log.debug("finding EmpXml instance by example");
		try {
			List<EmpXml> results = (List<EmpXml>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.sophos.semih.model.EmpXml")
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
