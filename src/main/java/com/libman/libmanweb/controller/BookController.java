package com.libman.libmanweb.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.service.spi.ServiceException; import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.libman.libmanweb.entity.Book;
import com.libman.libmanweb.entity.BookEntityDto;
import com.libman.libmanweb.entity.LibUserBook;
import com.libman.libmanweb.entity.LibUserBookEntityDto;
import com.libman.libmanweb.entity.UserVerificationToken;
import com.libman.libmanweb.entity.Users;
import com.libman.libmanweb.errors.Errors;
import com.libman.libmanweb.service.BookService;
import com.libman.libmanweb.service.ClockService;
import com.libman.libmanweb.service.UserService;

/**
 * @author manish
 *
 */
@RestController
@RequestMapping("/book")
public class BookController {
	
    @Autowired
    BookService bookService;

    @Autowired
    ClockService clockService;
    
    @Autowired
    UserService userService;

    private String isbn = "";


    /**
     * @param isbn
     * @param response
     * @return
     */
    @RequestMapping(value = "/{isbn}", method = RequestMethod.GET)
    public ResponseEntity<String> getBook(@PathVariable("isbn") String isbn ) {
        Book book = bookService.getBookByISBN(isbn);
        /**
         * If ID is not found in database
         */
        if (book == null) {

            return new ResponseEntity<String> ("Book not found by given Isbn" , HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(book.toString(), HttpStatus.OK);
    }

    /**
     * @param book
     * @param modelAndView
     * @param request
     * @param response
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     * @throws ServiceException
     */
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    ResponseEntity<String> addBookviaForm(@RequestBody Book book, @RequestParam("token") String token ) {
        System.out.println("book" + book);
        /**
         * Save value to database.
         */
        UserVerificationToken userVerificationToken = userService.getUserToken(token);
        Users user = userVerificationToken.getUser();
        if(user == null || !user.getRole().equalsIgnoreCase("ROLE_LIBRARIAN")) {
        	return new ResponseEntity<String>("Wrong access token ", HttpStatus.UNAUTHORIZED);
        }
        System.out.println("user is - " + user.toString());
        book.setIsbn(book.getIsbn());
        ResponseEntity<String> responseEntity = addNewBook(book, book.getTitle(), book.getAuthor(), book.getYear_of_publication(), book.getPublisher(),  user);

        return responseEntity;
    }
    
    /**
     * @param book
     * @param title
     * @param author
     * @param year_of_publication
     * @param publisher
     * @param response
     * @param user
     * @return 
     */
    private ResponseEntity<String> addNewBook(Book book, String title, String author, String year_of_publication, String publisher, Users user) {
        try {
            bookService.addBook(book.getIsbn(), author, title, book.getCallnumber(), publisher, year_of_publication, book.getLocation(), book.getNum_of_copies(), book.getCurrent_status(), book.getKeywords(), book.getImage(), user);
        }
        /**
         * If Unique key number is tried to repeat
         */ 
        catch (PersistenceException pe) {
            pe.printStackTrace();
            return new ResponseEntity<String> ("duplicate Isbn found !!!!! so, Book can't add with duplicate isbn ", HttpStatus.BAD_REQUEST);
        }
		return  new ResponseEntity<String> ("Book added successfully ", HttpStatus.CREATED);
    }

    /**
     * @param book1
     * @param modelAndView
     * @return The HTTP response-body to be sent to the calling javascript method.
     */
    @RequestMapping(value = "/searchAllBooks", method = RequestMethod.GET)
    public ResponseEntity<List<BookEntityDto> > searchAllBooks() {
        List<Book> books = bookService.findAll();
        List<BookEntityDto> booksList = new ArrayList<>();
        for (Book book : books) {
        	BookEntityDto bookEntityDto = new BookEntityDto();
            bookEntityDto.setTitle(book.getTitle());
            bookEntityDto.setAuthor(book.getAuthor());
            bookEntityDto.setBookId(book.getBookId());
            bookEntityDto.setCallNumber(book.getCallnumber());
            bookEntityDto.setCurrentStatus(book.getCurrent_status());
            bookEntityDto.setNumberOfCopies(book.getNum_of_copies());
            bookEntityDto.setPublisher(book.getPublisher());
            bookEntityDto.setYearOfPublication(book.getYear_of_publication());
            bookEntityDto.setKeywords(book.getKeywords());
            bookEntityDto.setIsbn(book.getIsbn());
            bookEntityDto.setLocation(book.getLocation());
            booksList.add(bookEntityDto);
        }
        return new ResponseEntity<List<BookEntityDto> >(booksList, HttpStatus.OK);
    }

   

 


    /**
     * @param book         The book object to be updated.
     * @param modelAndView
     * @param request      The model and view
     * @return
     */
    @Transactional
    @RequestMapping(value = "/updatebook", method = RequestMethod.POST)
    public ResponseEntity<String> updateBooks( @RequestBody Book book, @RequestParam("token") String token) {
		  UserVerificationToken userVerificationToken = userService.getUserToken(token);
	      Users user = userVerificationToken.getUser();
	      if(user == null || !user.getRole().equalsIgnoreCase("ROLE_LIBRARIAN")) {
	      	return new ResponseEntity<String>("Wrong access token ", HttpStatus.UNAUTHORIZED);
	      }
	      Book updatedbook = bookService.updateBooks(book,user );
	      System.out.println("Update called !!!");
	      return new ResponseEntity<String>(updatedbook.toString(), HttpStatus.OK);
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/deletebook/{bookId}", method = RequestMethod.GET)
    public ResponseEntity<String> deleteBook(@PathVariable("bookId") Long id, @RequestParam("token") String token) {
        try {
        	UserVerificationToken userVerificationToken = userService.getUserToken(token);
	  	      Users user = userVerificationToken.getUser();
	  	      if(user == null || !user.getRole().equalsIgnoreCase("ROLE_LIBRARIAN")) {
	  	      	return new ResponseEntity<String>("Wrong access token ", HttpStatus.UNAUTHORIZED);
	  	      }
            System.out.println("User requested to delete this book: " + id);
            if (bookService.deleteBookByID(id)) {
                System.out.println("Book Deleted Sucessfully!!");
                return new ResponseEntity<String>("Book Deleted Sucessfully!!", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Unable to delete book, book is checked out already", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("Unable to delete book at this time. Please try later", HttpStatus.BAD_REQUEST);
        }
    }
}
