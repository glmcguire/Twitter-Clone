package com.twitter.clone.beans.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.twitter.clone.beans.dao.SayingDao;
import com.twitter.clone.model.Saying;

@Repository
@Transactional
public class SayingDaoImpl implements SayingDao {
	private Logger log = LoggerFactory.getLogger(SayingDaoImpl.class);

	@Autowired
	private SessionFactory factory;

	@Override
	public void saveSaying(Saying saying) {
		Session session = factory.getCurrentSession();

		try {
			
			session.save(saying);

		} catch (Throwable t) {
			log.warn("Could not save post...");
		}

	}

	@Override
	public List<Saying> getSayings() {
		return factory.getCurrentSession().createQuery("from Saying").list();
		// may be able to add the user here....// check later after implementing
		// everything
	}

	@Override
	public Saying getSayingUsers(Integer id) {
		return (Saying) factory.getCurrentSession().
				createQuery("select s from Saying s left join fetch s.user where s.id = :sid").
				setParameter("sid", id).uniqueResult();
	}
}
