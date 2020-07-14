package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {
	
	public Transfer addTransfer (Transfer transfer); // not sure if it's void 

	
	// for #5 use case: get list of transfers a user has sent or received
	List<Transfer> getListTransfers(long userId);
	
	
	
	
	Transfer getTransferByTransferId(long transferId);
	
}
