package com.libman.libmanweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libman.libmanweb.dao.UserBookDao;

@Service
public class UserBookServiceImpl implements UserBookService {

	@Autowired
    UserBookDao userBookDao;
    /**
     * Returns number of books the user is holding on a particular day
     *
     * @param userId
     * @return number of books issued by user on current date
     */
    @Override
    public int getUserDayBookCount(Long userId) {
        return userBookDao.getUserDayBookCount(userId);
    }

    /**
     * @param bookid
     * @return
     */
	@Override
	public boolean checkIfEsxists(Long bookId) {
		// TODO Auto-generated method stub
		return userBookDao.exists(bookId);
	}

}
