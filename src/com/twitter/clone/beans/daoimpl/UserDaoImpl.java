package com.twitter.clone.beans.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.twitter.clone.beans.dao.UserDao;
import com.twitter.clone.model.Followee;
import com.twitter.clone.model.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

	private Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

	@Autowired
	private SessionFactory factory;

	@Override
	public User getUserByName(String username) {
		return (User) factory.getCurrentSession()
				.createQuery("from User u where u.name = :username")
				.setString("username", username).uniqueResult();
	}

	@Override
	public User registerUser(User user) {
		Session session = factory.getCurrentSession();
		User result = null;
		try {
			session.save(user);
			result = user;

		} catch (Throwable t) {
			log.warn("Could not register user", t);
		}

		return result;
	}

	@Override
	public List<User> getUsers() {
		return factory.getCurrentSession().createQuery("from User").list();
	}

	@Override
	public Followee getFolloweeByUsers(Integer id) {
		return (Followee) factory.getCurrentSession().
				createQuery("select f from Followee f left join fetch f.user where f.id = :fid").
				setParameter("fid", id).uniqueResult();
	}

	@Override
	public List<Followee> getFollowee() {
		return factory.getCurrentSession().createQuery("from Followee").list();
	}
	
	

}
