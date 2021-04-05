package com.libman.libmanweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.libman.libmanweb.dao.TokenDao;
import com.libman.libmanweb.dao.UserDao;
import com.libman.libmanweb.entity.UserVerificationToken;
import com.libman.libmanweb.entity.Users;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
    UserDao userDao;

    @Autowired
    TokenDao tokenDao;
    
    @Autowired
    Environment environment;

    /**
     *
     * @param uid
     * @param useremail
     * @param password
     * @return
     */
    @Override
    public Users createUser(long uid, String useremail, String password) {
    	System.out.println("evironment " +environment.getProperty("libman.librarianEmail"));
    	String librarian =environment.getProperty("libman.librarianEmail");
        Users user = new Users();

        user.setUid(uid);
        user.setUseremail(useremail);
        user.setPassword(password);
        String role = (useremail.equals(librarian)) ? "ROLE_LIBRARIAN" : "USER";
        user.setRole(role);

        // TODO Auto-generated method stub
        return userDao.createUser(user);
    }

    /**
     * @return List of users.
     */
    @Override
    public List<Users> listUsers() {
        return userDao.findAll();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Users findUser(Long id) {
        return userDao.getUser(id);
    }

    /**
     *
     * @param user
     * @param token
     */
    @Override
    public void createToken(Users user, String token) {
        UserVerificationToken userVerificationToken = new UserVerificationToken(token, user);
        tokenDao.storeToken(userVerificationToken);
    }

    /**
     *
     * @param token
     * @return
     */
    @Override
    public UserVerificationToken getUserToken(String token) {
        return tokenDao.findToken(token);
    }
    
    @Override
    public UserVerificationToken getUserTokenById(Long id ) {
    	return tokenDao.findTokenBUserId(id);
    }
    /**
     *
     * @param user
     */
    @Override
    public void saveValidatedUser(Users user) {
        userDao.updateUser(user);
    }

    /**
     *
     * @param usermail
     * @return
     */
    @Override
    public Users findUserByEmail(String usermail) {
        Users user = userDao.findUserByEmail(usermail);
        return user;
    }
}
