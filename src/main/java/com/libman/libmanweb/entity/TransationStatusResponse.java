package com.libman.libmanweb.entity;

import java.util.List;

public class TransationStatusResponse {
	private String transactionStatus;
	private List<BookStatusResponseEntityDto> BookStatusResponseEntityDtoList;
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public List<BookStatusResponseEntityDto> getBookStatusResponseEntityDtoList() {
		return BookStatusResponseEntityDtoList;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public void setBookStatusResponseEntityDtoList(List<BookStatusResponseEntityDto> bookStatusResponseEntityDtoList) {
		BookStatusResponseEntityDtoList = bookStatusResponseEntityDtoList;
	}
	
}
