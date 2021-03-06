package com.twitter.clone.model;

// Generated May 19, 2015 11:13:01 AM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

/**
 * Person generated by hbm2java
 */
public class Person implements java.io.Serializable {

	private Integer id;
	private String firstName;
	private String lastName;
	private Set addresses = new HashSet(0);
	private Set users = new HashSet(0);

	public Person() {
	}

	public Person(String firstName, String lastName, Set addresses, Set users) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.addresses = addresses;
		this.users = users;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set getAddresses() {
		return this.addresses;
	}

	public void setAddresses(Set addresses) {
		this.addresses = addresses;
	}

	public Set getUsers() {
		return this.users;
	}

	public void setUsers(Set users) {
		this.users = users;
	}

}
