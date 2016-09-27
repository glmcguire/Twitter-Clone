package com.twitter.clone.beans;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.twitter.clone.beans.dao.UserDao;
import com.twitter.clone.model.User;

@Component
@Scope("request")
public class RegistrationBean {

	@Autowired
	private UserDao userDao;

	private User user;

	@PostConstruct
	public void init() {
		user = new User();

	}

	public String register() {
		User dbUser = userDao.registerUser(user);

		if (dbUser != null) {
			return "registration-success";
		} else {
			return "registration-failed";
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
