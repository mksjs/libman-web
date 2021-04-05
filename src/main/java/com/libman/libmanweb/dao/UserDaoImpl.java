package com.libman.libmanweb.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.libman.libmanweb.entity.Users;

/**
 * @author manish
 *
 */
@Transactional
@Repository
public class UserDaoImpl implements UserDao {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    /**
     * @param uEntity
     * @return The user book object.
     */
    @Override
    public Users createUser(Users uEntity) {
        em.persist(uEntity);
        return uEntity;
    }

    /**
     * @param id
     * @return The status of the remove user object.
     */
    @Override
    public boolean removeUser(Long id) {
        Users user = em.find(Users.class, id);
        if (user == null) return false;
        em.remove(user);
        return true;
    }

    /**
     * @return The list of all the users.
     */
    @Override
    public List<Users> findAll() {
        List<Users> users = (List<Users>) em.createQuery("select u from Users u", Users.class).getResultList();
        return users;
    }

    /**
     * @param id
     * @return The user object.
     */
    @Override
    public Users getUser(Long id) {
        Users user = em.find(Users.class, id);
        return user;
    }

    /**
     * @param user
     */
    @Override
    public void updateUser(Users user) {
        em.merge(user);
    }

    /**
     * @param usermail
     * @return The user object.
     */
    @Override
    public Users findUserByEmail(String usermail) {
        System.out.println("Email to Query:" + usermail);
        Query query = em.createQuery("select id from Users u where u.useremail = :useremail");
        query.setParameter("useremail", usermail);
        List userIds = query.getResultList();
        System.out.println(userIds);
        if (userIds.size() > 0) {
            Users user = em.find(Users.class, userIds.get(0));
            return user;
        }
        return null;
    }
}
