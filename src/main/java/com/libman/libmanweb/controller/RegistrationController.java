package com.libman.libmanweb.controller;

import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.libman.libmanweb.entity.UserVerificationToken;
import com.libman.libmanweb.entity.Users;
import com.libman.libmanweb.service.ClockService;
import com.libman.libmanweb.service.UserService;

/**
 * @author manish
 *
 */
@RestController
@RequestMapping(value = "/user")
public class RegistrationController {
	@Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    UserService uService;

    @Autowired
    ClockService clockService;
    
    @Autowired
	private Environment environment;
   
    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> registerNewUserAccount(@RequestBody Users user) {
        System.out.println("************* Received the following from the form: UID: " + user.getUid() + " UserEmail: " + user.getUseremail() + " Password: " + user.getPassword());

        try {
        	if(user.getUid() == null || user.getPassword() == null || user.getUseremail() == null) {
        		return new ResponseEntity<String>("request body is not in correct format", HttpStatus.BAD_REQUEST);
        	}
        	int indexAt = user.getUseremail().indexOf('@');
        	int indexDot = user.getUseremail().lastIndexOf('.');
        	if(indexAt == -1 || indexAt >-1 && indexDot<indexAt) {
        		return new ResponseEntity<String>("incorrect email", HttpStatus.BAD_REQUEST);
        	}
        	
        	UUID uuid = UUID. randomUUID();
        	String token = uuid. toString();
            Users added = uService.createUser(user.getUid(), user.getUseremail(), user.getPassword());
         	uService.createToken(added, token);
            System.out.println("************* The following user will be added into the database: " + added.toString());
            if(added != null ) {
            	ResponseEntity<String> response=new ResponseEntity<String>("Successfully registered \n here is token which help in future operation in account \n token : "+token + " please use this token to activate Account  url : http://localhost:8080/user/activateAccount"  , HttpStatus.OK);
    			return response;
            }
            return new ResponseEntity<String>("Something went wrong", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
        }
    }

    /**
     * @param request
     * @param user
     * @param bindingResult
     * @return the dashboard for the user to login
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String>  loginUser( @RequestBody Users user) {
       
        Users loggedInUser = uService.findUserByEmail(user.getUseremail());
        if (loggedInUser == null || !user.getPassword().equals(loggedInUser.getPassword()) || !loggedInUser.isEnabled()) {
        	
        	return new ResponseEntity<String>("Bad Credentials. No user found with this email/password combination. \r\n Is your account validation pending? If so, please re-validate account.", HttpStatus.UNAUTHORIZED);
     
        } else {
        	System.out.println("loggedInUser.getRole() "+ loggedInUser.getRole());
            if (loggedInUser.getRole().equalsIgnoreCase("USER")) {
            	ResponseEntity<String> response=new ResponseEntity<String>("Successfully logged in as USER\n"+loggedInUser.toString(), HttpStatus.OK);
    			return response;
              
            } else {
            	ResponseEntity<String> response=new ResponseEntity<String>("Successfully logged in as Librarian\n"+loggedInUser.toString(), HttpStatus.OK);
    			return response;
            }
           
        }
    }

    /**
     * @param token
     * @return The view to confirm registered token
     */
    @RequestMapping(value = "/activateAccount", method = RequestMethod.GET)
    public ResponseEntity<String>  confirmRegisteredAccount(@RequestParam("token") String token) {
        System.out.println("*********** token  = " + token);
        UserVerificationToken userVerfToken = uService.getUserToken(token);
        if (userVerfToken == null) {
        	return new ResponseEntity<String>("Wrong Token ", HttpStatus.UNAUTHORIZED);
        }

        Users user = userVerfToken.getUser();
        user.setEnabled(true);
        uService.saveValidatedUser(user);
        ResponseEntity<String> response=new ResponseEntity<String>("Account verified Successfully", HttpStatus.OK);
		return response;
    }


    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    public ResponseEntity<String>  getTokenByUserNameNPassword(@RequestBody Users user) {
      
       
        Users loggedInUser = uService.findUserByEmail(user.getUseremail());
        if (loggedInUser == null || !user.getPassword().equals(loggedInUser.getPassword()) || !loggedInUser.isEnabled()) {
        	
        	return new ResponseEntity<String>("Bad Credentials. No user found with this email/password combination. \r\n Is your account validation pending? If so, please check inbox and re-validate account.", HttpStatus.UNAUTHORIZED);
     
        }
        System.out.print(loggedInUser.getId());
        UserVerificationToken userVerificationToken = uService.getUserTokenById(loggedInUser.getId());
       
        ResponseEntity<String> response=new ResponseEntity<String>("fetched Token successfully \n token : "+ userVerificationToken.getToken() , HttpStatus.OK);
		return response;
    }

}
