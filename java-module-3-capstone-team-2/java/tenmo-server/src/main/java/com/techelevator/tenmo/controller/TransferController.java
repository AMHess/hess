package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountsDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;


@PreAuthorize("isAuthenticated()")  // all methods in this class can be accessed by users that are logged in (unless a method states only an Admin can use it)
@RestController
@RequestMapping("transfer")
public class TransferController {
	
	private AccountsDAO accountsDao;
	private UserDAO userDao;
	private TransferDAO transferDao;
	Principal principal;
	
	public TransferController(TransferDAO transferDao, UserDAO userDao, AccountsDAO accountsDao) {	
		this.transferDao = transferDao;
		this.userDao = userDao;
		this.accountsDao = accountsDao;
		
	}
	
	// POST to add a transfer to the transfer data table
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
	public Transfer addTransfer(@RequestBody Transfer transfer, Principal principal) { 
		return transferDao.addTransfer(transfer);
	}
	
	 
	// GET to get a list of transfers current user has sent or received.
	//	Transfers based on user ID aka the current user
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Transfer> getListTransfers(Principal principal) {
		

		long userId = userDao.findIdByUsername(principal.getName());
		
		return transferDao.getListTransfers(userId);
	}

	

	
//	@RequestMapping(method = RequestMethod.GET)
//	public Transfer getTransferByTransferId(@RequestParam long transferId) {
//		return transferDao.getTransferByTransferId(transferId);
//	}
	
	
	
	
	
}
