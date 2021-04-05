package com.libman.libmanweb.service;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.scheduling.annotation.EnableScheduling;

import com.libman.libmanweb.entity.Book;
import com.libman.libmanweb.entity.LibUserBook;
import com.libman.libmanweb.entity.Users;

/**
 * @author manish
 *
 */
@EnableScheduling
public interface BookService {

	/**
	 * @return List of all books in the library
	 */
	List<Book> listBooks();

	/**
	 *
	 * @param isbn
	 * @return the book with given isbn
	 */
	Book findBook(String isbn);

	/**
	 *
	 * @param bookId
	 * @param userId
	 * @return the result of requesting a book by a user
	 * @throws ParseException
	 */
	String requestBook(Long bookId, Long userId) throws ParseException;

	/**
	 * @param bookId
	 * @return book with given bookId
	 */
	Book findBookById(Integer bookId);

	/**
	 * @param userId
	 * @return books checkout by user with userId
	 */
	List<Book> listBooksOfUser(Long userId);

	/**
	 *
	 * @param long1
	 * @param userId
	 * @return status after return book by user
	 */
	String returnBook(Long bookId, Long userId);

	/**
	 * @param book
	 * @return books satisfying the search criteria entered by the patron
	 */
	List<Book> searchBookbyUser(Book book);

	/**
	 *
	 * @param id
	 * @return boolean status after deleting a book by librarian
	 */
	boolean deleteBookByID(Long id);

	/**
	 *
	 * @return the number of copies of a book available
	 */
	String getAvailableBookCount();


	/**
	 *
	 * @param updatedbook
	 * @param request
	 * @return Book after updating book details
	 */
	Book updateBooks(Book updatedbook, Users user);

	/**
	 * @param isbn
	 * @return Book based on given isbn
	 */
	Book getBookByISBN(String isbn);

	/**
	 *
	 * @return all Books
	 */
	List<Book> findAll();

	/**
	 * returns the checkout log for all users
	 * @return all user-book combinations
	 */
	List<LibUserBook> getAllLibUserBook();

	/**
	 * Add a book to database
	 *
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
	 * @return true if add successful, false if failed
	 */
	boolean addBook(String isbn, String author, String title, String callnumber, String publisher,
			String year_of_publication, String location, Long num_of_copies, String current_status, String keywords,
			byte[] image, Users user);

	/**
	 *
	 * @param bookId
	 * @param userId
	 * @return status after renewing a book by a user userId
	 * @throws ParseException
	 */
	String renewBook(Long bookId, Long userId) throws ParseException;

	String extendBook(Long bookId, Long userId) throws ParseException;

}