package com.libman.libmanweb.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.libman.libmanweb.entity.UserVerificationToken;
/**
 * @author manish
 *
 */
@Repository
@Transactional
public class TokenDaoImpl implements TokenDao {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    /**
     * @param token
     * @return A User verification token.
     */
    @Override
    public UserVerificationToken findToken(String token) {
        TypedQuery<UserVerificationToken> query = em.createQuery("SELECT u from  UserVerificationToken u WHERE u.token = :value1", UserVerificationToken.class).setParameter("value1", token);
        return query.getSingleResult();
    }

    @Override
    public UserVerificationToken findTokenBUserId(Long id) {
    	TypedQuery<UserVerificationToken> query = em.createQuery("SELECT u from  UserVerificationToken u WHERE u.id = :id", UserVerificationToken.class).setParameter("id", id);
        return query.getSingleResult();
    }
    /**
     * @param userVerificationToken
     */
    @Override
    public void storeToken(UserVerificationToken userVerificationToken) {
        em.persist(userVerificationToken);
    }
}
