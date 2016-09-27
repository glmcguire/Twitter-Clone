package com.twitter.clone.beans.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.twitter.clone.beans.dao.FollowDao;
import com.twitter.clone.model.Followee;
import com.twitter.clone.model.User;

@Repository
@Transactional
public class FollowDaoImpl implements FollowDao {

	private Logger log = LoggerFactory.getLogger(SayingDaoImpl.class);

	@Autowired
	private SessionFactory factory;

	@Override
	public void saveFollowee(Followee follow) {
		Session session = factory.getCurrentSession();

		try {

			session.save(follow);

		} catch (Throwable t) {
			log.warn("Could not save followee...");
		}

	}

	@Override
	public List<Followee> getFollowees() {
		return factory.getCurrentSession().createQuery("from Followee").list();
	}

	@Override
	public List<Followee> getFolloweeByUsername(String username) {
		return factory.getCurrentSession()
				.createQuery("select f from Followee f left join fetch f.user where f.name = :username")
				.setParameter("username", username).list();
	}

	@Override
	public void deleteFollowee(User user) {
		log.info("starting delete follower method...");
		Session session = factory.getCurrentSession();
		log.info("just got current session!...");
		try {
			Followee follow = (Followee) session
					.createQuery("select f from Followee f left join fetch f.user where f.user = :userid")
					.setParameter("userid", user).uniqueResult();
			log.info("follow.name here is=" + follow.getName());
			session.delete(follow);

		} catch (Throwable t) {
			log.warn("Could not delete followee...");
		}

	}

}
