package com.libman.libmanweb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author manish
 *
 */

@Embeddable
public class UserBookId implements Serializable{
	@Column(name = "book")
    protected Long bookId;

    @Column(name = "users")
    protected Long userId;

    /**
     * The default constructor
     */
    public UserBookId() {

    }

    /**
     * @param bookId
     * @param userId
     */
    public UserBookId(Long bookId, Long userId) {
        this.bookId = bookId;
        this.userId = userId;
    }
    
    /**
    *
    * @return
    */
   @Override
   public int hashCode() {
       final int prime = 31;
       int result = 1;
       result = prime * result
               + ((bookId == null) ? 0 : bookId.hashCode());
       result = prime * result
               + ((userId == null) ? 0 : userId.hashCode());
       return result;
   }

   /**
    *
    * @param obj
    * @return
    */
   @Override
   public boolean equals(Object obj) {
       if (this == obj)
           return true;
       if (obj == null)
           return false;
       if (getClass() != obj.getClass())
           return false;

       UserBookId other = (UserBookId) obj;

       if (bookId == null) {
           if (other.bookId != null)
               return false;
       } else if (!bookId.equals(other.bookId))
           return false;

       if (userId == null) {
           if (other.userId != null)
               return false;
       } else if (!userId.equals(other.userId))
           return false;

       return true;
   }
}
