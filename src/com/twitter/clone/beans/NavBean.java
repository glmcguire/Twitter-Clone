package com.twitter.clone.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class NavBean {
	
	@Autowired
	private AuthenticationBean auth;

	public String home() {
		return "home";
	}

	public String login() {
		return "login";
	}

	public String register() {
		return "register";
	}

	public String profile() {
		return "profile";
	}
	
	public String profilePage(){
		if(auth.isLoggedIn()){
			return "profilePage-logged";
		} else {
			return "profilePage";
		}
		
	}
}