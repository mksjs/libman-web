package com.libman.libmanweb.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author manish
 *
 */
@Entity
@Table(name = "USERVERIFICATIONTOKEN")
public class UserVerificationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String token;
	
	@OneToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "ID")
	private Users users;

	public UserVerificationToken() {
		
	}

	public UserVerificationToken(String token, Users user) {
		this.token = token;
		this.users = user;
	}

	public Long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public Users getUser() {
		return users;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setUser(Users user) {
		this.users = user;
	}
	
}
