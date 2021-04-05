package com.libman.libmanweb.entity;

/**
 * @author manish
 *
 */
public class LibUserBookEntityDto {
	private Long bookId;
	private Long userId;
	private String bookName;
	private String author;
	private String userName;
	private String title;
	private String isbn;
	private String userEmail;
	private String action;
	private Long noOfCopies;
	private String status;
    
	public Long getBookId() {
		return bookId;
	}
	public Long getUserId() {
		return userId;
	}
	public String getBookName() {
		return bookName;
	}
	public String getAuthor() {
		return author;
	}
	public String getUserName() {
		return userName;
	}
	public String getTitle() {
		return title;
	}
	public String getIsbn() {
		return isbn;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public String getAction() {
		return action;
	}
	public Long getNoOfCopies() {
		return noOfCopies;
	}
	public String getStatus() {
		return status;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public void setNoOfCopies(Long noOfCopies) {
		this.noOfCopies = noOfCopies;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
