package com.twitter.clone.beans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.twitter.clone.beans.dao.SayingDao;
import com.twitter.clone.beans.dao.UserDao;
import com.twitter.clone.model.Followee;
import com.twitter.clone.model.Saying;
import com.twitter.clone.model.User;

@Component
@Scope("request")
public class SayingBean {
	private Logger log = LoggerFactory.getLogger(SayingBean.class);

	@Autowired
	private AuthenticationBean auth;

	@Autowired
	private UserDao userDao;
	@Autowired
	private SayingDao sayingDao;
	@Autowired
	private UserBean userBean;
	@Autowired
	private FollowingBean followBean;

	private User user;
	private Saying saying;
	private List<Saying> sayings;
	private List<Followee> follows;

	@PostConstruct
	public void init() {
		if (auth.isLoggedIn()) {
			String username = auth.getUsername();
			user = userDao.getUserByName(username);
			this.saying = new Saying();
			saying.setUser(user);
		}
	}

	public String savePost() {
		sayingDao.saveSaying(saying);
		saying = null;
		return null;
	}

	public List<String> getPosts() {
		log.info("Starting getPosts()");

		List<Saying> results = sayingDao.getSayings();
		List<String> sayings = new ArrayList<String>();

		for (int i = results.size() - 1; i >= 0; i--) {
			Saying say = sayingDao.getSayingUsers(results.get(i).getId());
			User users = say.getUser();

			sayings.add((results.get(i)).getSaying() + "  ~" + users.getName()
					+ "~~@" + results.get(i).getLastUpdate());
		}

		log.info("Ending get posts. sayings = " + sayings);
		return sayings;

	}

	public List<String> getPostByUser() {
		log.info("getting post by user!!!!");
		List<Saying> results = sayingDao.getSayings();
		List<String> sayings = new ArrayList<String>();

		for (int i = results.size() - 1; i >= 0; i--) {
			Saying say = sayingDao.getSayingUsers(results.get(i).getId());
			User users = say.getUser();
			log.info("user=" + users.getName() + "  and userBean="
					+ userBean.getUser().getName());

			if (userBean.getUser().getName().equals(users.getName())) {
				sayings.add((results.get(i)).getSaying() + "  ~"
						+ users.getName() + "  ~~@" + results.get(i).getLastUpdate());
			}
		}
		return sayings;
	}

	// Need to add follower arguement here || statement in if
	public List<String> getPostByUserAndFriends() {
		log.info("Starting postByUserAndFriends...");
		List<Saying> results = sayingDao.getSayings();
		List<String> sayings = new ArrayList<String>();
		List<Followee> follows = followBean.getProfileFollows();

		log.info("Starting LOOP 1...");
		for (int i = results.size() - 1; i >= 0; i--) {
			Saying say = sayingDao.getSayingUsers(results.get(i).getId());
			User users = say.getUser();
			String fuser;
			
			log.info("Starting LOOP 2...");
			log.info(auth.getUsername());
			log.info(users.getName());

			for (int n = 0; n < follows.size(); n++) {
				fuser = follows.get(n).getName();

				log.info("auth.getUsername=" + auth.getUsername());
				log.info("fuser=" + fuser);
				log.info("users.getName=" + users.getName());
				
				if (auth.getUsername().equals(fuser)
						|| users.getName().equals(fuser)) {
					sayings.add((results.get(i)).getSaying() + "  ~"
							+ users.getName() + "  ~~@" + results.get(i).getLastUpdate());
				}
			}
		}

		return checkForMultiples(sayings);
	}

	//Created to remove the duplicates from the above method...
	public List<String> checkForMultiples(List<String> multiples) {
		List<String> results = new ArrayList<String>();

		HashSet<String> set = new HashSet<>();

		for (String item : multiples) {

			if (!set.contains(item)) {
				results.add(item);
				set.add(item);
			}
		}
		return results;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public SayingDao getSayingDao() {
		return sayingDao;
	}

	public void setSayingDao(SayingDao sayingDao) {
		this.sayingDao = sayingDao;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Saying getSaying() {
		return saying;
	}

	public void setSaying(Saying saying) {
		this.saying = saying;
	}

	public List<Saying> getSayings() {
		return sayings;
	}

	public void setSayings(List<Saying> sayings) {
		this.sayings = sayings;
	}

}
