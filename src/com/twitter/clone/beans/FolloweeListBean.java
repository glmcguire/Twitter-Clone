package com.twitter.clone.beans;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.twitter.clone.beans.dao.FollowDao;
import com.twitter.clone.model.Followee;

@Component
@Scope("request")
public class FolloweeListBean {
	
	@Autowired
	private FollowDao followDao;
	
	@Autowired
	private FolloweeBean followBean;
	
	private List<Followee> followeeList;
	
	@PostConstruct
	public void init(){
		followeeList = followDao.getFollowees();
	}
	
	public String viewFollowee(){
		if(followBean.getFollowee() != null){
			return "got-follow";
		} else {
			return "did-not-get";
		}
	}

	public FolloweeBean getFollowBean() {
		return followBean;
	}

	public void setFollowBean(FolloweeBean followBean) {
		this.followBean = followBean;
	}

	public List<Followee> getFolloweeList() {
		return followeeList;
	}

	public void setFolloweeList(List<Followee> followeeList) {
		this.followeeList = followeeList;
	}
	
	
}
