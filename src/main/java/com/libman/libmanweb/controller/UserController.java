package com.libman.libmanweb.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libman.libmanweb.entity.Book;
import com.libman.libmanweb.entity.BookStatusResponseEntityDto;
import com.libman.libmanweb.entity.TransationStatusResponse;
import com.libman.libmanweb.entity.UserBookCart;
import com.libman.libmanweb.entity.UserVerificationToken;
import com.libman.libmanweb.entity.Users;
import com.libman.libmanweb.errors.Err;
import com.libman.libmanweb.service.BookService;
import com.libman.libmanweb.service.ClockService;
import com.libman.libmanweb.service.UserBookCartService;
import com.libman.libmanweb.service.UserBookService;
import com.libman.libmanweb.service.UserService;

/**
 * @author manish
 *
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;
    @Autowired
    UserBookService userBookService;
    @Autowired
    UserBookCartService userBookCartService;
    @Autowired
    private ClockService clockService;
   
    

    /**
     * @param userId
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/user/{userId}/dashboard", method = RequestMethod.GET)
    public ResponseEntity<String> userDashboard(@PathVariable Long userId, @RequestParam("token") String token) {
    	Users user = userService.findUser(userId);
    	UserVerificationToken userVerifToken = userService.getUserToken(token);
    	Users userByToken = userVerifToken.getUser();
    	if(userByToken != null && user.isEnabled() && user.getId() == userByToken.getId()) {
    		ResponseEntity<String> response=new ResponseEntity<String>( user.toString() , HttpStatus.OK);
    		return response;
    	}
        return new ResponseEntity<String>("Not found any activated User"  , HttpStatus.UNAUTHORIZED);
    }

    /**
     * @return
     */
    @RequestMapping(value = "/user/showall", method = RequestMethod.GET)
    public ResponseEntity<List<Users>> showAll(@RequestParam("token") String token) {
    	UserVerificationToken userVerifToken = userService.getUserToken(token);
    	Users userByToken = userVerifToken.getUser();
    	if (userByToken.getRole().equals("ROLE_LIBRARIAN")) {
    		List<Users> users = userService.listUsers();
    		ResponseEntity<List<Users>> response=new ResponseEntity<List<Users>>( users , HttpStatus.OK);
    		return response;
    	}
    	Users user = new Users();
    	List<Users> users = new ArrayList<Users>();
    	users.add(user);
        return new ResponseEntity<List<Users>>( users , HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param userId
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/user/{userId}/books", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> showBooks(@PathVariable("userId") Long userId, @RequestParam("token") String token ) {
    	UserVerificationToken userVerifToken = userService.getUserToken(token);
    	if (userVerifToken != null) {
    		List<Book> books = bookService.listBooksOfUser(userId);
            if (books.size() < 1)
                return new ResponseEntity<List<Book>>( books , HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    	}
        
        Book book = new Book();
        List<Book> books = new ArrayList<Book>();
    	return new ResponseEntity<List<Book>>( books , HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param userId
     * @param bookId
     * @param modelAndView
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/user/{userId}/books/{bookId}", method = RequestMethod.GET)
    public ResponseEntity<String> addBookToUserCart(@PathVariable("userId") Long userId,
                                    @PathVariable("bookId") Long bookId, @RequestParam("token") String token) throws ParseException {
    	UserVerificationToken userVerifToken = userService.getUserToken(token);
    	if(userVerifToken == null) {
    		return new ResponseEntity<String>( " Unauthorized " , HttpStatus.UNAUTHORIZED);
    	}
        Err err = userBookCartService.addUserBookToCart(new UserBookCart(userId, bookId, 0l));
        String addToCartStatus;
        if (err.isAnError()) {
            addToCartStatus = err.getMessage();
    		return new ResponseEntity<String>( addToCartStatus , HttpStatus.UNAUTHORIZED);


        } else {
    		return new ResponseEntity<String>( "Book added to cart" , HttpStatus.OK);

        }
        
    }

    /**
     * @param userId
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/user/{userId}/checkout", method = RequestMethod.GET)
    public ResponseEntity<String> requestBooks(@PathVariable("userId") Long userId, @RequestParam("token") String token) throws ParseException {
    	StringBuilder emailSummary = new StringBuilder();
        emailSummary.append("Book checkout Summary!" + "\n");
    	UserVerificationToken userVerifToken = userService.getUserToken(token);
        if (userVerifToken == null ) {
        	return new ResponseEntity<String>( " Unauthorized " , HttpStatus.UNAUTHORIZED);
        }
        List<UserBookCart> userBookCartList = userBookCartService.getUserCart(userId, false);
        if (userBookCartList.size() == 0) {
        	return new ResponseEntity<String>( " Cart is Empty. Nothing to checkout " , HttpStatus.BAD_REQUEST);
        }
        List<Book> currBooks = bookService.listBooksOfUser(userId);
        if (currBooks.size() + userBookCartList.size() > 10) {
            return new ResponseEntity<String>( "Maximum 10 books can be issued at a time. Must return a book or remove from cart to issue new." , HttpStatus.BAD_REQUEST);
        }

        int userDayBookCount = userBookService.getUserDayBookCount(userId);
        if (userDayBookCount + userBookCartList.size() > 5) {
            return new ResponseEntity<String>( "Maximum 5 books can be issued in a day. Must return a book or remove from cart today or try tomorrow." , HttpStatus.BAD_REQUEST);
        }

        boolean isWaitlisted = false;
        List<BookStatusResponseEntityDto> bookStatusResponseEntityDtoList = new ArrayList<BookStatusResponseEntityDto>();
        for (UserBookCart userBookCart : userBookCartList) {
            String status = bookService.requestBook(userBookCart.getBookId(), userId);
            if (status.contains("wait")) {
                isWaitlisted = true;
            }
            BookStatusResponseEntityDto bookStatusResponseEntityDto = new BookStatusResponseEntityDto();
            bookStatusResponseEntityDto.setBookId((long)userBookCart.getBookId());
            bookStatusResponseEntityDto.setUserId((long)userBookCart.getUserId());
            bookStatusResponseEntityDto.setWaitlist(status);
            bookStatusResponseEntityDtoList.add(bookStatusResponseEntityDto);

        }
        TransationStatusResponse  transactionStausResponse = new TransationStatusResponse();
        transactionStausResponse.setTransactionStatus("Transaction successful!");
        transactionStausResponse.setBookStatusResponseEntityDtoList(bookStatusResponseEntityDtoList);
        ObjectMapper Obj = new ObjectMapper();
        String jsonStr ="something went worng";
        try {
           jsonStr = Obj.writeValueAsString(transactionStausResponse);
           userBookCartService.clearUserCart(userId, false);
        } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}     
        if(jsonStr.equals("something went worng")) {
        	return new ResponseEntity<String>( jsonStr, HttpStatus.BAD_REQUEST);
        } 
        return new ResponseEntity<String>( jsonStr, HttpStatus.OK);
        
    }

    /**
     * @param userId
     * @param bookId
     * @return
     * @throws ParseException
     */

    @RequestMapping(value = "/user/{userId}/book/{bookId}", method = RequestMethod.GET)
    public ResponseEntity<String> addBookToReturnUserCart(@PathVariable("userId") Long userId,
                                          @PathVariable("bookId") Long bookId, @RequestParam("token") String token) throws ParseException {
    	UserVerificationToken userVerifToken = userService.getUserToken(token);
    	if(userVerifToken == null) {
    		return new ResponseEntity<String>( " Unauthorized " , HttpStatus.UNAUTHORIZED);
    	}
    	Err err = userBookCartService.addUserBookToCart(new UserBookCart(userId, bookId, 1));
        String addToCartStatus;
        if (err.isAnError()) {
            addToCartStatus = err.getMessage();
        } else {
            addToCartStatus = "Book added to return cart";
        }
        List<Book> books = userBookCartService.getUserBooks(userId, true);
        return new ResponseEntity<String>( addToCartStatus, HttpStatus.OK);
    }

    /**
     * Checkout method for return cart
     * @param userId
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/user/{userId}/returnCheckout", method = RequestMethod.GET)
    public ResponseEntity<String> returnBook(@PathVariable("userId") Long userId, @RequestParam("token") String token) throws ParseException {

    	UserVerificationToken userVerifToken = userService.getUserToken(token);
    	if(userVerifToken == null) {
    		return new ResponseEntity<String>( " Unauthorized " , HttpStatus.UNAUTHORIZED);
    	}
        StringBuilder returnBookSummary = new StringBuilder();
        List<UserBookCart> userBookCartList = userBookCartService.getUserCart(userId, true);
        if (userBookCartList.size() == 0) {
            return new ResponseEntity<String>("Cart is Empty. Nothing to checkout", HttpStatus.OK);
        }



        for (UserBookCart userBookCart : userBookCartList) {
            returnBookSummary.append(bookService.returnBook(userBookCart.getBookId(), userId));
            returnBookSummary.append("\n");
        }
        returnBookSummary.append("The Return date is " + clockService.getCalendar().getTime() + "\n");
        userBookCartService.clearUserCart(userId, true);
        return new ResponseEntity<String>(returnBookSummary.toString(), HttpStatus.OK);
    }


    @RequestMapping(value = "/user/searchBookByAuthorOrTitle", method = RequestMethod.GET)
    public ResponseEntity<String> searchBookByAuthorOrTitle(@RequestParam(value ="author", required = false) String author,
    													@RequestParam(value = "title", required = false) String title ) {
    	 Book book = new Book();
       
    	 System.out.print(author);
         if (title != null && !title.isEmpty()) {
             book.setTitle(title);
         }
         if (author != null && !author.isEmpty()) {
             book.setAuthor(author);
         }
         if ((book.getAuthor() == null || book.getAuthor().isEmpty())
                         &&
                         (book.getTitle() == null || book.getTitle().isEmpty()))

         {
             System.out.println("Null check  search book !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");            
             return new ResponseEntity<String>("At least one search criteria is mandatory", HttpStatus.BAD_REQUEST);
         }
         List<Book> books = bookService.searchBookbyUser(book);
         return new ResponseEntity<String>(books.toString(), HttpStatus.OK);
         

    }


    /**
     *
     * Renew a checked out book for 7 more days
     * @param userId
     * @param bookId
     * @return The book object.
     * @throws ParseException
     */
    @RequestMapping(value = "/user/book/{bookId}/renew", method = RequestMethod.GET)
    public ResponseEntity<String> renewBook( @PathVariable("bookId") Long bookId, @RequestParam("token") String token) throws ParseException {

    	UserVerificationToken userVerifToken = userService.getUserToken(token);
    	if(userVerifToken == null) {
    		return new ResponseEntity<String>( " Unauthorized " , HttpStatus.UNAUTHORIZED);
    	}
    	Long userId = userVerifToken.getUser().getId();
        String status = bookService.renewBook(bookId, userId);

        if (status.equalsIgnoreCase("invalid book")) {
        	  return new ResponseEntity<String>(status, HttpStatus.BAD_REQUEST);
        }

        List<Book> books = bookService.listBooksOfUser(userId);
        return new ResponseEntity<String>(books.toString() + "\n  userId :"+ userId + "\n status: " + status, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/book/{bookId}/extend", method = RequestMethod.POST)
    public ResponseEntity<String> extendBook(@PathVariable("bookId") Long bookId, @RequestParam("token") String token) throws ParseException {
    	UserVerificationToken userVerifToken = userService.getUserToken(token);
    	if(userVerifToken == null || !userVerifToken.getUser().getRole().equalsIgnoreCase("Librarian")) {
    		return new ResponseEntity<String>( " Unauthorized " , HttpStatus.UNAUTHORIZED);
    	}
    	Long userId = userVerifToken.getUser().getId();
        String status = bookService.extendBook(bookId, userId);
        if (status.equalsIgnoreCase("invalid book")) {
         	  return new ResponseEntity<String>(status, HttpStatus.BAD_REQUEST);
      	 }
		return new ResponseEntity<String>(status, HttpStatus.OK);
    }
}
