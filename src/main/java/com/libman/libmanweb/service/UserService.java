package com.libman.libmanweb.service;

import java.util.List;

import com.libman.libmanweb.entity.UserVerificationToken;
import com.libman.libmanweb.entity.Users;

public interface UserService {

	void createToken(Users user, String token);

	/**
	 *
	 * @param uid
	 * @param useremail
	 * @param password
	 * @return
	 */
	Users createUser(long uid, String useremail, String password);

	/**
	 * @return List of users.
	 */
	List<Users> listUsers();

	/**
	 * @param userId
	 * @return
	 */
	Users findUser(Long userId);

	/**
	 *
	 * @param token
	 * @return
	 */
	UserVerificationToken getUserToken(String token);

	/**
	 *
	 * @param user
	 */
	void saveValidatedUser(Users user);

	/**
	 *
	 * @param usermail
	 * @return
	 */
	Users findUserByEmail(String usermail);

	/**
	 * @param id
	 * @return
	 */
	UserVerificationToken getUserTokenById(Long id);

}
