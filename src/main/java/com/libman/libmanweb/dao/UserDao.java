package com.libman.libmanweb.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.libman.libmanweb.entity.Users;

/**
 * @author manish
 *
 */
@Transactional
public interface UserDao {

	/**
	 * @param uEntity
	 * @return The user book object.
	 */
	Users createUser(Users uEntity);

	/**
	 * @param id
	 * @return The status of the remove user object.
	 */
	boolean removeUser(Long id);

	/**
	 * @return The list of all the users.
	 */
	List<Users> findAll();

	/**
	 * @param id
	 * @return The user object.
	 */
	Users getUser(Long id);

	/**
	 * @param user
	 */
	void updateUser(Users user);

	/**
	 * @param usermail
	 * @return The user object.
	 */
	Users findUserByEmail(String usermail);

}