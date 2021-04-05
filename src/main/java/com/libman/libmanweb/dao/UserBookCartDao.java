package com.libman.libmanweb.dao;

import java.util.List;

import com.libman.libmanweb.entity.Book;
import com.libman.libmanweb.entity.UserBookCart;
import com.libman.libmanweb.errors.Err;

/**
 * @author manish
 *
 */
public interface UserBookCartDao {

	/**
	 * Adds the given book against the user to cart
	 *
	 * @param userBookCart UserBookCart
	 * @return Err false if add successful, true with error message if failed
	 */
	Err addUserBookToCartIssue(UserBookCart userBookCart);

	Err addUserBookToCartReturn(UserBookCart userBookCart);

	/**
	 * To get the cart for a user
	 *
	 * @param userid Long userid
	 * @return List of UserBookCart
	 */
	List<UserBookCart> getUserCartIssue(Long userId);

	/**
	 * To get the return cart for a user
	 *
	 * @param userid
	 * @return
	 */
	List<UserBookCart> getUserCartReturn(Long userId);

	/**
	 * @param userBookCart
	 * @return
	 */
	boolean removeCartEntry(UserBookCart userBookCart);

	/**
	 * @param userId
	 * @return
	 */
	List<Book> getUserBooksInCartIssue(Long userId);

	/**
	 * @param userId
	 * @return
	 */
	List<Book> getUserBooksInCartReturn(Long userId);
	

}