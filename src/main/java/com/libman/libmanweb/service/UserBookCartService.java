package com.libman.libmanweb.service;

import java.util.List;

import com.libman.libmanweb.entity.Book;
import com.libman.libmanweb.entity.UserBookCart;
import com.libman.libmanweb.errors.Err;

public interface UserBookCartService {

	/**
	 * Adds the given book against the user to cart
	 *
	 * @param ubc UserBookCart
	 * @return true if add successful, false if failed
	 */
	Err addUserBookToCart(UserBookCart userBookCart);

	/**
	 * To get the cart for a user
	 *
	 * @param userid int userId
	 * @return List of UserBookCart
	 */
	List<UserBookCart> getUserCart(Long userId, boolean isReturnType);

	/**
	 * Clears the books for user from cart
	 *
	 * @param userId
	 */
	void clearUserCart(Long userId, boolean isReturnType);

	/**
	 * @param userId
	 * @param isReturnType
	 * @return
	 */
	List<Book> getUserBooks(Long userId, boolean isReturnType);

}