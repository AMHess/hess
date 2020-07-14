package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
	
	private long transferId; 			// set by the database
	private int transferTypeId = 2; 		// will always be send
	private int transferStatusId = 2; 		// will always be approve: 1 is approve
	private int accountFrom;
	private int accountTo;
	private BigDecimal amount;
	
	
	public Transfer() {}
	
	public Transfer(int accountFrom, int accountTo, BigDecimal amount) {
		long transferId;
		int transferTypeId;				// Not sure if these are correct. database needs this but we don't want the user to set them in the App
		int transferStatusId; 			// Not sure if these are correct
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.amount = amount;
	}

	public long getTransferId() {
		return transferId;
	}

	public void setTransferId(long transferId) {
		this.transferId = transferId;
	}

	public int getTransferTypeId() {
		return transferTypeId;
	}

//	public void setTransferTypeId(int transferTypeId) {
//		this.transferTypeId = transferTypeId;
//	}

	public int getTransferStatusId() {
		return transferStatusId;
	}

//	public void setTransferStatusId(int transferStatusId) {
//		this.transferStatusId = transferStatusId;
//	}

	public int getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(int accountFrom) {
		this.accountFrom = accountFrom;
	}

	public int getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(int accountTo) {
		this.accountTo = accountTo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	

}
