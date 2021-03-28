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
    private int id;

    

	@Column(name = "USERID")
    private int userId;

	@Column(name = "BOOKID")
    private int bookId;

    @Column(name = "RETURNTYPE")
    private int returnType;
    
    public UserBookCart() {
		
    }
    
    public UserBookCart(int userId, int bookId, int returnType) {
		this.userId = userId;
		this.bookId = bookId;
		this.returnType = returnType;
	}

	public int getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}

	public int getBookId() {
		return bookId;
	}

	public int getReturnType() {
		return returnType;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public void setReturnType(int returnType) {
		this.returnType = returnType;
	}
}
