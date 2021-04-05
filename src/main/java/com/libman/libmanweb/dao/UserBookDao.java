package com.libman.libmanweb.dao;

import com.libman.libmanweb.entity.UserBook;

/**
 * @author manish
 *
 */
public interface UserBookDao {

	/**
	 * Returns number of books the user is holding on a particular day
	 *
	 * @param userId
	 * @return number of books issued by user on current date
	 */
	int getUserDayBookCount(Long userId);

	/**
	 * @param booId
	 * @return The exists flag.
	 */
	boolean exists(Long bookId);

	boolean removeUserBook(UserBook userBook);

}