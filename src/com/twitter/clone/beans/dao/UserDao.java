package com.twitter.clone.beans.dao;

import java.util.List;

import com.twitter.clone.model.Followee;
import com.twitter.clone.model.User;



public interface UserDao {
	public User getUserByName(String username);
	public User registerUser(User user);
	public List<User> getUsers();
	public Followee getFolloweeByUsers(Integer id);
	public List<Followee> getFollowee();
}
