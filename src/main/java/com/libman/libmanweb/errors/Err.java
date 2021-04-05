package com.libman.libmanweb.errors;

/**
 * @author manish
 *
 */
public class Err {
	boolean anError;
    String message;

	public Err(boolean anError, String message) {
		// TODO Auto-generated constructor stub
		 this.anError = anError;
	     this.message = message;
	}

	public Err() {
		// TODO Auto-generated constructor stub
		this.anError = false;
        this.message = "";
	}

	public boolean isAnError() {
		return anError;
	}

	public String getMessage() {
		return message;
	}

	public void setAnError(boolean anError) {
		this.anError = anError;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
