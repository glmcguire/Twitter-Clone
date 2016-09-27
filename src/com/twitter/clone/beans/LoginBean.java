package com.twitter.clone.beans;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.twitter.clone.model.User;

/**
 * This Login bean is used to authenticate the user's login.
 * 
 * @author Gary McGuire
 *
 */
@Component
@Scope("request")
public class LoginBean {

	private static final Logger log = LoggerFactory.getLogger(LoginBean.class);
	@Autowired
	private AuthenticationBean authentication;

	private User user;
	private Boolean loginFailed;

	@PostConstruct
	public void init() {
		user = new User();
	}

	public String login() {
		if (authentication.login(user)) {
			loginFailed = false;
			log.info("login-success going through");
			return "login-success";
		} else {
			loginFailed = true;
			log.info("login-fail going through");
			return "login-failure";
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getLoginFailed() {
		return loginFailed;
	}

	public void setLoginFailed(Boolean loginFailed) {
		this.loginFailed = loginFailed;
	}
}
