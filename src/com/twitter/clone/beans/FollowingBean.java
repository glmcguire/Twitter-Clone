package com.twitter.clone.beans;

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

/**
 * This bean manages the followers and followee's... allowing them to be updated
 * and maintained in the person_db schema
 * 
 * @author Gary McGuire
 *
 */
@Component
@Scope("request")
public class FollowingBean {
	private Logger log = LoggerFactory.getLogger(SayingBean.class);

	@Autowired
	private AuthenticationBean auth;

	@Autowired
	private UserDao userDao;
	@Autowired
	private FollowDao followDao;
	@Autowired
	private UserBean userBean;

	private User user;
	private Followee followee;
	private List<Followee> followers = null;

	/**
	 * Initializes the above variables, setting the current logged in user to
	 * the local instance of user as well as setting it to the followee
	 * instance.
	 */
	@PostConstruct
	public void init() {
		if (auth.isLoggedIn()) {
			String username = auth.getUsername();
			user = userDao.getUserByName(username);
			this.followee = new Followee();
			followee.setName(username);
		}
	}

	/**
	 * Saves the followee to the logged in user, returning a redirect string
	 * upon completion.
	 * 
	 * @return
	 */
	public String saveFollow() {

		followee.setUser(userBean.getUser());
		followDao.saveFollowee(followee);

		return "redirect-to-unfollow";
	}

	/**
	 * Deletes a followee from a user, also returning a redirect string upon
	 * completion.
	 * 
	 * @return
	 */
	public String deleteFollow() {
		followee.setUser(userBean.getUser());
		log.info("followwee.name=" + followee.getName());
		log.info("followee.id=" + followee.getId());
		log.info("followee.user=" + followee.getUser().getName());
		followDao.deleteFollowee(followee.getUser());

		return "redirect-to-follow";
	}

	/**
	 * Returns all the people that the logged in user is following.
	 * 
	 * @return
	 */
	public List<Followee> getUsersFollows() {
		if (userBean.getUser() != null) {
			followers = followDao.getFolloweeByUsername(userBean.getUser()
					.getName());
		}
		return followers;

	}

	/**
	 * Returns all the people that a user is following on their public profile.
	 * 
	 * @return
	 */
	public List<Followee> getProfileFollows() {
		if (user != null) {
			followers = followDao.getFolloweeByUsername(user.getName());
		}
		return followers;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Followee getFollowee() {
		return followee;
	}

	public void setFollowee(Followee followee) {
		this.followee = followee;
	}

	public List<Followee> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Followee> followers) {
		this.followers = followers;
	}

}
