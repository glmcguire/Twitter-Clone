package com.twitter.clone.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.twitter.clone.beans.dao.FollowDao;
import com.twitter.clone.beans.dao.UserDao;
import com.twitter.clone.model.Followee;
import com.twitter.clone.model.User;

@Component
@Scope("request")
public class UserListBean {
	
	private Logger log = LoggerFactory.getLogger(UserListBean.class);
	
	@Autowired
	private AuthenticationBean auth;

	@Autowired
	private UserDao userDao;
	@Autowired
	private FollowDao followDao;

	@Autowired
	private UserBean userBean;
	@Autowired
	private FolloweeBean followBean;

	private List<User> users;
	private User user, userName;
	private boolean isFollower;

	@PostConstruct
	public void init() {
		users = userDao.getUsers();
	}

	public String viewUserPage() {
		log.info("The view page button has been pressed!");
		if (userBean.getUser() != null) {
			if (auth.isLoggedIn()
					&& !(userBean.getUser().getName()).equals(auth
							.getUsername())) {
				
				log.info("fire the queue for logged on!");
				log.info("isFollow is what?: " + this.follower());
				if (this.follower()){
					return "view-page-success-online-follower";			
				} else {
					return "view-page-success-online";
				}
				
				// will also add another if
													// statement later for if a
													// follower or not
			} else {
				log.info("fire the queue for logged off!");
				return "view-page-success-offline";
			}

		} else {
			log.info("fire the queue for FAIL!");
			return "view-page-failure";
		}

	}
	
	public boolean follower(){
		log.info("Firing up follower method!");
		List<Followee> results = followDao.getFolloweeByUsername(auth.getUsername());
		
		int i = 0;
		while (i < results.size()){
			log.info("loop #" + i + " " + "userBean.getUser=" + userBean.getUser().getName() +
					"and results.get.getUser()=" + results.get(i).getUser().getName());
			if(userBean.getUser().getName().equals(results.get(i).getUser().getName())){
				isFollower = true;
				break;
			} else {
				isFollower = false;
			}
			i++;
		}
		return isFollower;
	}

	public List<String> getFollowsByUser(User user) {

		List<Followee> results = userDao.getFollowee();
		List<String> followees = new ArrayList<String>();

		for (int i = results.size() - 1; i >= 0; i--) {
			User userCheck = results.get(i).getUser();
			if (userCheck.equals(user)) {
				followees.add(results.get(i).getUser().getName());
			}

		}
		return followees;

	}
	
	public User getUserByName(){
		userName = userDao.getUserByName(userBean.getUser().getName());
		return userName;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
