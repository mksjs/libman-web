package com.libman.libmanweb.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.*;

/**
 * @author manish
 *
 */
@Entity
@Table(name = "USERBOOK")
public class UserBook {
	@EmbeddedId
	@Column(name = "ID")
	private UserBookId id;
	@ManyToOne
	@JoinColumn(name = "BOOK", insertable = false, updatable = false)
	private Book book;
	@ManyToOne
	@JoinColumn(name = "USERS", insertable = false, updatable = false)
	private Users users;
	@Column(name = "CHECKOUT_DATE")
	private String checkout_date;
	// 0 or 1 or 2
	@Column(name = "RENEW_FLAG")
	private Integer renew_flag;
	@Column(name = "FINE")
	private Integer fine;

	public UserBook() {
	}

	public UserBook(Book book, Users user, LocalDateTime checkout_date, Integer renew_flag) {
		// primary key
		this.id = new UserBookId(book.getBookId(), user.getId());
		this.book = book;
		this.users = user;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		this.checkout_date = dtf.format(checkout_date);
		this.renew_flag = renew_flag;
	}

	public UserBookId getId() {
		return id;
	}

	public Book getBook() {
		return book;
	}

	public Users getUser() {
		return users;
	}

	public String getCheckout_date() {
		return checkout_date;
	}

	public Integer getRenew_flag() {
		return renew_flag;
	}

	public Integer getFine() {
		return fine;
	}

	public void setId(UserBookId id) {
		this.id = id;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public void setUser(Users user) {
		this.users = user;
	}

	public void setCheckout_date(String checkout_date) {
		this.checkout_date = checkout_date;
	}

	public void setRenew_flag(Integer renew_flag) {
		this.renew_flag = renew_flag;
	}

	public void setFine(Integer fine) {
		this.fine = fine;
	}

	public void UserBookPersist(Book b, Users u) {
		u.getCurrentBooks().add(this);
		b.getCurrentUsers().add(this);

	}
	
	public String getDueDate() throws ParseException {

        DateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date duedate = dtf.parse(this.checkout_date);

        Calendar cal = new GregorianCalendar();
        cal.setTime(duedate);
        cal.add(Calendar.DATE, 30);

        String dueDate = dtf.format(cal.getTime());
        // System.out.println("String new due date " + dueDate);

        return dueDate;
    }

    public void setCalculateFine(Date currDate) throws ParseException {




        DateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date checkDate = dtf.parse(this.getDueDate());
        System.out.println("setCalculateFine: checkoutDate: " + this.checkout_date);
        System.out.println("setCalculateFine: getDueDate: " + this.getDueDate());
        //Date checkDate = new Date();
        // Date currDate = new Date();
        //LocalDate checkDate = dtf.parse(this.checkout_date).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //LocalDate currDate = LocalDate.now();
        //Period p = Period.between(checkDate, currDate);
        long hours = (currDate.getTime() - checkDate.getTime()) / (60 * 60 * 1000);
        System.out.println("setCalculateFine: long hours: " + hours);
        if (hours <= 0) {
            this.fine = 0;
            return;
        }
        Integer intHours = (int) (long) hours;
        System.out.println("setCalculateFine: intHours: " + intHours);
        this.fine = ((intHours / 24) + 1);
    }

}
