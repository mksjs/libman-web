package com.libman.libmanweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libman.libmanweb.dao.UserBookCartDao;
import com.libman.libmanweb.entity.Book;
import com.libman.libmanweb.entity.UserBookCart;
import com.libman.libmanweb.errors.Err;

@Service
public class UserBookCartServiceImpl implements UserBookCartService {
	@Autowired
    UserBookCartDao userBookCartDao;

    /**
     * Adds the given book against the user to cart
     *
     * @param ubc UserBookCart
     * @return true if add successful, false if failed
     */
    @Override
    public Err addUserBookToCart(UserBookCart userBookCart) {
        if (userBookCart.getReturnType() > 0) {
            return userBookCartDao.addUserBookToCartReturn(userBookCart);
        } else {
            return userBookCartDao.addUserBookToCartIssue(userBookCart);
        }
    }

    /**
     * To get the cart for a user
     *
     * @param userid int userId
     * @return List of UserBookCart
     */
    @Override
    public List<UserBookCart> getUserCart(Long userId, boolean isReturnType) {
        if (isReturnType) {
            return userBookCartDao.getUserCartReturn(userId);
        } else {
            return userBookCartDao.getUserCartIssue(userId);
        }

    }

    /**
     * Clears the books for user from cart
     *
     * @param userId
     */
    @Override
    public void clearUserCart(Long userId, boolean isReturnType) {
        List<UserBookCart> userBookCartList;
        if (isReturnType) {
            userBookCartList = userBookCartDao.getUserCartReturn(userId);
        } else {
            userBookCartList = userBookCartDao.getUserCartIssue(userId);
        }
        for (UserBookCart userBookCart : userBookCartList) {
            userBookCartDao.removeCartEntry(userBookCart);
        }
    }

    /**
     * @param userId
     * @param isReturnType
     * @return
     */
    @Override
    public List<Book> getUserBooks(Long userId, boolean isReturnType) {
        List<Book> books;
        if (isReturnType) {
            books = userBookCartDao.getUserBooksInCartReturn(userId);
        } else {
            books = userBookCartDao.getUserBooksInCartIssue(userId);
        }
        return books;
    }
}
