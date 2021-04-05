package com.libman.libmanweb.dao;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.libman.libmanweb.entity.Book;
import com.libman.libmanweb.entity.LibUserBook;
import com.libman.libmanweb.entity.Users;

/**
 * @author manish
 *
 */
public interface BookDao {

	/**
	 * @param book
	 * @return
	 */
	boolean addBook(Book book);

	/**
	 * @param isbn                10 or 13 digit ISBN code, must be unique
	 * @param author              Author of the book
	 * @param title               Title of the book, must be unique
	 * @param callnumber          Call Number
	 * @param publisher           Publisher of the book
	 * @param year_of_publication Year of publication
	 * @param location            Location of the book in library
	 * @param num_of_copies       Number of copies
	 * @param current_status      Current Status
	 * @param keywords            Keywords
	 * @param image               Bytes as image
	 * @param user
	 * @return
	 */
	boolean addBook(String isbn, String author, String title, String callnumber, String publisher,
			String year_of_publication, String location, Long num_of_copies, String current_status, String keywords,
			byte[] image, Users users);	

	/**
	 * Return the book by isbn code
	 *
	 * @param isbn
	 * @return book object
	 */
	Book getBookByISBN(String isbn);

	/**
	 * @return List of all books
	 */
	List<Book> findAll();

	/**
	 *
	 * @param bookId
	 * @param userId
	 * @return The status of book request by a user
	 * @throws ParseException
	 */
	String setBookRequest(Long bookId, Long userId) throws ParseException;

	/**
	 * Search a book by any of its fields
	 *
	 * @param book
	 * @return A list of books that match the search criteria
	 */
	List<Book> searchBook(Book book);

	/**
	 * @param bookId
	 * @return Book given the bookId
	 */
	Book getBookbyId(Integer bookId);

	/**
	 * Update the availability status of the book based on the checkout summary
	 * @param bookId
	 */
	void updateBookStatus(Long bookId);

	/**
	 * @param userId
	 * @return the list of books checked out by a user
	 */
	List<Book> getBookByUserId(Long userId);

	/**
	 * @param bookId The ID of the book.
	 * @param userId The ID of the user.
	 * @return The return status of the book.
	 */
	String setBookReturn(Long bookId, Long userId);

	/**
	 * All user - book checkout logs
	 * @return
	 */
	List<LibUserBook> getAllLibUserBook();

	/**
	 * delete the book with given bookId
	 * @param id
	 * @return The return status of the books.
	 */
	boolean deleteBookByID(Long id);

	/**
	 * Renew the book
	 * check if no user is in waitList for the book
	 * check if the user is not doing more that twice consecutively
	 * @param bookId
	 * @param userId
	 * @return
	 * @throws ParseException
	 */
	String setBookRenew(Long bookId, Long userId) throws ParseException;

	/**
	 *
	 * @param book
	 * @return the date till when the book will be held for the waitlisted user
	 */
	LocalDate bookAvailabilityDueDate(Book book);

	/**
	 * Notify waitlisted user when the book becomes available
	 * @param userId
	 * @param bookId
	 */
	void waitlistMadeAvailable(Long userId, Long bookId);

	/**
	 * Executes every two minutes
	 * Waitlisted user loop execution
	 * @throws ParseException
	 */
	void waitlistCron() throws ParseException;


	String findCountAvailable();

	/**
	 * Update book details
	 *
	 * @param book
	 * @param request
	 * @return updated book
	 */
	Book updateBooks(Book book, Users user);
	
	String setBookExtend(Long bookId, Long userId) throws ParseException;


}