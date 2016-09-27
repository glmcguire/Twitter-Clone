package com.twitter.clone.beans.dao;

import java.util.List;

import com.twitter.clone.model.Followee;
import com.twitter.clone.model.User;

public interface FollowDao {
	public void saveFollowee(Followee follow);
	public List<Followee> getFollowees();
	public List<Followee> getFolloweeByUsername(String username);
	public void deleteFollowee(User user);
}
