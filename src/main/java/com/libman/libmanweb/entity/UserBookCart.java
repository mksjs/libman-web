package com.libman.libmanweb.entity;

import javax.persistence.*;

/**
 * @author manish
 *
 */
@Entity
@Table(name = "USERBOOKCART")
public class UserBookCart {
	@Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    

	@Column(name = "USERID")
    private Long userId;

	@Column(name = "BOOKID")
    private Long bookId;

    @Column(name = "RETURNTYPE")
    private Long returnType;
    
    public UserBookCart() {
		
    }
    
    public UserBookCart(Long userId2, Long bookId2, long l) {
		this.userId = userId2;
		this.bookId = bookId2;
		this.returnType = l;
	}

	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getBookId() {
		return bookId;
	}

	public Long getReturnType() {
		return returnType;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public void setReturnType(Long returnType) {
		this.returnType = returnType;
	}
}
