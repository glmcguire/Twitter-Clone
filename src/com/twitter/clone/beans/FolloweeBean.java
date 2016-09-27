package com.twitter.clone.beans;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.twitter.clone.model.Followee;
import com.twitter.clone.model.User;

@Component
@Scope("session")
public class FolloweeBean {
	private Followee followee;
	private List<User> users;
	private User user;

	public Followee getFollowee() {
		return followee;
	}

	public void setFollowee(Followee followee) {
		this.followee = followee;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
