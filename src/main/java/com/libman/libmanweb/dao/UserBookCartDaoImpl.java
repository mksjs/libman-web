package com.libman.libmanweb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.libman.libmanweb.entity.Book;
import com.libman.libmanweb.entity.UserBookCart;
import com.libman.libmanweb.errors.Err;

/**
 * @author manish
 *
 */
@Repository
@Transactional
public class UserBookCartDaoImpl implements UserBookCartDao {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    /**
     * Adds the given book against the user to cart
     *
     * @param userBookCart UserBookCart
     * @return Err false if add successful, true with error message if failed
     */
    @Override
    public Err addUserBookToCartIssue(UserBookCart userBookCart) {
        List<UserBookCart> userBookCartList = this.getUserCartIssue(userBookCart.getUserId());
        if (userBookCartList.size() > 4) {
            return new Err(true, "You can only add 5 books to the cart at a time");
        }
        for (UserBookCart u : userBookCartList) {
            if (u.getBookId() == userBookCart.getBookId()) {
                return new Err(true, "Book already added to cart!");
            }
        }
        entityManager.persist(userBookCart);
        return new Err();
    }

    @Override
    public Err addUserBookToCartReturn(UserBookCart userBookCart) {
        List<UserBookCart> userBookCartList = this.getUserCartReturn(userBookCart.getUserId());
        if (userBookCartList.size() > 9) {
            return new Err(true, "You can only add 10 books to the cart at a time");
        }
        for (UserBookCart userBookCart1 : userBookCartList) {
            if (userBookCart1.getBookId() == userBookCart.getBookId()) {
                return new Err(true, "Book already added to cart!");
            }
        }
        entityManager.persist(userBookCart);
        return new Err();
    }

    /**
     * To get the cart for a user
     *
     * @param userid Long userid
     * @return List of UserBookCart
     */
    @Override
    public List<UserBookCart> getUserCartIssue(Long userId) {
        String query = "Select ubc From UserBookCart ubc WHERE ubc.userId = :userid AND ubc.returnType = 0";
        Query q = entityManager.createQuery(query, UserBookCart.class);
        q.setParameter("userid", userId);
        return q.getResultList();
    }

    /**
     * To get the return cart for a user
     *
     * @param userid
     * @return
     */
    @Override
    public List<UserBookCart> getUserCartReturn(Long userId) {
        String query = "Select ubc From UserBookCart ubc WHERE ubc.userId = :userid AND ubc.returnType > 0";
        Query q = entityManager.createQuery(query, UserBookCart.class);
        q.setParameter("userid", userId);
        return q.getResultList();
    }

    /**
     * @param userBookCart
     * @return
     */
    @Override
    public boolean removeCartEntry(UserBookCart userBookCart) {
        entityManager.remove(userBookCart);
        return true;
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<Book> getUserBooksInCartIssue(Long userId) {

        List<UserBookCart> userBookCarts = this.getUserCartIssue(userId);
        List<Book> books = new ArrayList<Book>();
        for (UserBookCart userBookCart : userBookCarts) {
            books.add(entityManager.find(Book.class, userBookCart.getBookId()));

        }
        return books;
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<Book> getUserBooksInCartReturn(Long userId) {
        List<UserBookCart> userBookCarts = this.getUserCartReturn(userId);
        List<Book> books = new ArrayList<Book>();
        for (UserBookCart userBookCart : userBookCarts) {
            books.add(entityManager.find(Book.class, userBookCart.getBookId()));

        }
        return books;
    }

}
