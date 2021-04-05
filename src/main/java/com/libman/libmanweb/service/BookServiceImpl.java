package com.libman.libmanweb.service;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libman.libmanweb.dao.BookDao;
import com.libman.libmanweb.entity.Book;
import com.libman.libmanweb.entity.LibUserBook;
import com.libman.libmanweb.entity.Users;

/**
 * @author manish
 *
 */
@Service
public class BookServiceImpl implements BookService {

	@Autowired
    BookDao bookDao;

    @Autowired
    UserBookService userBookService;

    /**
     * @return List of all books in the library
     */
    @Override
    public List<Book> listBooks() {
        return bookDao.findAll();
    }

    /**
     *
     * @param isbn
     * @return the book with given isbn
     */
    @Override
    public Book findBook(String isbn) {
        // TODO Auto-generated method stub
        Book book = bookDao.getBookByISBN(isbn);
        return book;
    }

    /**
     *
     * @param bookId
     * @param userId
     * @return the result of requesting a book by a user
     * @throws ParseException
     */
    @Override
    public String requestBook(Long bookId, Long userId) throws ParseException {
        // TODO Auto-generated method stub
        return bookDao.setBookRequest(bookId, userId);

    }

    /**
     * @param bookId
     * @return book with given bookId
     */
    @Override
    public Book findBookById(Integer bookId) {
        return bookDao.getBookbyId(bookId);
    }

    /**
     * @param userId
     * @return books checkout by user with userId
     */
    @Override
    public List<Book> listBooksOfUser(Long userId) {

        List<Book> books = bookDao.getBookByUserId(userId);
        return books;
    }

    /**
     *
     * @param bookId
     * @param userId
     * @return status after return book by user
     */
    @Override
    public String returnBook(Long bookId, Long userId) {
        return bookDao.setBookReturn(bookId, userId);

    }

    /**
     * @param book
     * @return books satisfying the search criteria entered by the User
     */

    @Override
    public List<Book> searchBookbyUser(Book book) {

        return bookDao.searchBook(book);
    }

    /**
     *
     * @param id
     * @return boolean status after deleting a book by librarian
     */

    @Override
    public boolean deleteBookByID(Long id) {
        /*A book cannot be deleted if it’s checked out by a Normal User.*/
        if (!userBookService.checkIfEsxists(id)) {
            System.out.println("**************** Book does not exist, can delete safely. \n\r Now removing the waitlist entry for the same");

            return bookDao.deleteBookByID(id);
        } else {
            return false;
        }
    }

    /**
     *
     * @return the number of copies of a book available
     */
    @Override
    public String getAvailableBookCount() {
        return bookDao.findCountAvailable();
    }

    /**
     *
     * @param updatedbook
     * @param request
     * @return Book after updating book details
     */
    @Override
    public Book updateBooks(Book updatedbook, Users user) {
        return bookDao.updateBooks(updatedbook, user);
    }


    /**
     * @param isbn
     * @return Book based on given isbn
     */
    @Override
    public Book getBookByISBN(String isbn) {
        return bookDao.getBookByISBN(isbn);
    }

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
    @Override
    public boolean addBook(String isbn, String author, String title, String callnumber, String publisher, String year_of_publication, String location, Long num_of_copies, String current_status, String keywords, byte[] image, Users user) {
        return bookDao.addBook(isbn, author, title, callnumber, publisher, year_of_publication, location, num_of_copies, current_status, keywords, image, user);
    }

    /**
     *
     * @return all Books
     */

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    /**
     * returns the checkout log for all users
     * @return all user-book combinations
     */
    @Override
    public List<LibUserBook> getAllLibUserBook() {
        return bookDao.getAllLibUserBook();
    }

    /**
     *
     * @param bookId
     * @param userId
     * @return status after renewing a book by a user userId
     * @throws ParseException
     */
    @Override
    public String renewBook(Long bookId, Long userId) throws ParseException {
        return bookDao.setBookRenew(bookId, userId);
    }

	@Override
	public String extendBook(Long bookId, Long userId) throws ParseException {
		// TODO Auto-generated method stub
		return bookDao.setBookExtend(bookId, userId);
	}
    
    
}
