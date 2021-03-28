package com.libman.libmanweb.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * @author manish
 *
 */
@Entity
@Table(name = "LIBUSERBOOKSTATUS")
public class LibUserBook {
	@EmbeddedId

    @Column(name = "ID")
    private LibUserBookId id;
    @ManyToOne
    @JoinColumn(name = "BOOK", insertable = false, updatable = false)
    private Book book;
    @ManyToOne
    @JoinColumn(name = "USERS", insertable = false, updatable = false)
    private Users users;
    @Column(name = "ACTION")
    private String action;

    public LibUserBook() {
    }

    /** * @param b
     * @param u
     * @param action
     */
    public LibUserBook(Book b, Users u, String action) {
        // create primary key
        System.out.println("book id is ----" + b.getBookId());
        this.id = new LibUserBookId(b.getBookId(), u.getId());

        // initialize attributes
        this.book = b;
        this.users = u;
        this.action = action;
    }

	public LibUserBookId getId() {
		return id;
	}

	public Book getBook() {
		return book;
	}

	public Users getUser() {
		return users;
	}

	public void setId(LibUserBookId id) {
		this.id = id;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public void setUser(Users user) {
		this.users = user;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	@Override
    public String toString() {
        return "LibUserBook{" +
                "id=" + id +
                ", book=" + book +
                ", user=" + users +
                ", action='" + action + '\'' +
                '}';
    }
	@Embeddable
    public static class LibUserBookId implements Serializable {

        @Column(name = "book")
        protected Integer bookId;

        @Column(name = "users")
        protected Integer userId;

        public LibUserBookId() {

        }

        /**
         * @param bookId
         * @param userId
         */
        public LibUserBookId(Integer bookId, Integer userId) {
            System.out.println("book id is ----" + bookId);
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

            LibUserBookId other = (LibUserBookId) obj;

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

}
