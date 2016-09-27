package com.twitter.clone.beans.dao;

import java.util.List;

import com.twitter.clone.model.Saying;

public interface SayingDao {
	public void saveSaying(Saying saying);
	public List<Saying> getSayings();
	public Saying getSayingUsers(Integer id);
}
