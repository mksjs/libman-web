package com.libman.libmanweb.service;

public interface UserBookService {

	/**
	 * @param bookid
	 * @return
	 */
	boolean checkIfEsxists(Long bookId);

	/**
	 * Returns number of books the user is holding on a particular day
	 *
	 * @param userId
	 * @return number of books issued by user on current date
	 */
	int getUserDayBookCount(Long userId);

	

}