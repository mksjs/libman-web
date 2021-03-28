package com.libman.libmanweb.entity;

/**
 * @author manish
 *
 */
public class BookEntityDto {
	private int bookId;
    private String author;
    private String callNumber;
    private String currentStatus;
    private String isbn;
    private String keywords;
    private String location;
    private int numberOfCopies;
    private String publisher;
    private String title;
    private String yearOfPublication;
    
	public int getBookId() {
		return bookId;
	}
	public String getAuthor() {
		return author;
	}
	public String getCallNumber() {
		return callNumber;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public String getIsbn() {
		return isbn;
	}
	public String getKeywords() {
		return keywords;
	}
	public String getLocation() {
		return location;
	}
	public int getNumberOfCopies() {
		return numberOfCopies;
	}
	public String getPublisher() {
		return publisher;
	}
	public String getTitle() {
		return title;
	}
	public String getYearOfPublication() {
		return yearOfPublication;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setNumberOfCopies(int numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setYearOfPublication(String yearOfPublication) {
		this.yearOfPublication = yearOfPublication;
	}
}
