package com.libman.libmanweb.dao;

import com.libman.libmanweb.entity.UserVerificationToken;

/**
 * @author manish
 *
 */
public interface TokenDao {

	/**
	 * @param userVerificationToken
	 */
	void storeToken(UserVerificationToken userVerificationToken);

	/**
	 * @param token
	 * @return A User verification token.
	 */
	UserVerificationToken findToken(String token);

	/**
	 * @param id
	 * @return
	 */
	UserVerificationToken findTokenBUserId(Long id);

}