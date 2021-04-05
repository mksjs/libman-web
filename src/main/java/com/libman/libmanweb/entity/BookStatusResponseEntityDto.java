package com.libman.libmanweb.entity;

public class BookStatusResponseEntityDto {
	private Long userId;
	private Long bookId;
	private String waitlist;
	public Long getUserId() {
		return userId;
	}
	public Long getBookId() {
		return bookId;
	}
	public String getWaitlist() {
		return waitlist;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public void setWaitlist(String waitlist) {
		this.waitlist = waitlist;
	}
	
}
