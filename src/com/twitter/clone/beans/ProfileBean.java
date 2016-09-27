package com.twitter.clone.beans;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.twitter.clone.beans.dao.UserDao;
import com.twitter.clone.model.User;

@Component
@Scope("session")
public class ProfileBean {
	private Logger log = LoggerFactory.getLogger(ProfileBean.class);

	@Autowired
	private AuthenticationBean auth;

	@Autowired
	private UserDao userDao;

	private User user;

	@PostConstruct
	public void init() {
		log.info("profileBean init()");
		if (auth.isLoggedIn()) {
			String username = auth.getUsername();
			user = userDao.getUserByName(username);
			// maybe get list<saying> by username here... maybe...

			log.info("profileBean init() auth.isLoggedin()");
		} else {
			user = new User();

			log.info("profileBean init() else");
		}
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
