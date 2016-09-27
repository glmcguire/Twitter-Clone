package com.twitter.clone.beans;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.twitter.clone.beans.dao.UserDao;
import com.twitter.clone.model.User;

/**
 * This authentication bean is able to keep track whether or not 
 * a user is logged in to the website.
 * @author Gary McGuire
 *
 */

@Service
@Scope("session")
public class AuthenticationBean {
	
	@Autowired
	private UserDao userDao;
	
	private User user;
	
	/**
	 * This method checks the username and password combination 
	 * and checks to see if it matches with the database. If it is
	 * true, then it returns true and allows the user to login.
	 * @param user
	 * @return
	 */
	public boolean login(User user){
		if (user != null && user.getName() != null 
				&& user.getPassword() != null){
			
			String username = user.getName().trim();
			String password = user.getPassword().trim();
			
			if(!username.isEmpty() && !password.isEmpty()){
				User dbUser = userDao.getUserByName(username);
				if (dbUser != null && dbUser.getPassword().equals(password)){
					this.user = dbUser;
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Basic boolean which can be called upon elsewhere to see if
	 * the user is still logged in.
	 * @return
	 */
	public boolean isLoggedIn(){
		return user!= null;
		
	}
	
	/**
	 * Completes the logout process and invalidates the current session.
	 * @return
	 */
	public String logout(){
		user = null;
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "logged-out";
	}
	
	public String getUsername(){
		if(user != null){
			return user.getName();
		} else {
			return null;
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
