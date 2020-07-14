package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountsDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Accounts;

@PreAuthorize("isAuthenticated()")  // all methods in this class can be accessed by users that are logged in (unless a method states only an Admin can use it)
@RestController
@RequestMapping("accounts")
public class AccountController {
	
	// Controller to interact with Account DAO
	
	private AccountsDAO accountsDao;
	private UserDAO userDao;
	private TransferDAO transferDao;
	
	public AccountController(AccountsDAO accountsDao, UserDAO userDao, TransferDAO transferDao) {	
		this.accountsDao = accountsDao;
		this.userDao = userDao;
		this.transferDao = transferDao;
	}
	

    // method for getting current balance by username
	// this is going to handle the path accounts/balance     
    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    
    // principal is the current user
    public BigDecimal findCurrentBalance(Principal principal) throws UsernameNotFoundException {
    		long userId = getCurrentUserId(principal);				// get the userId of the current user
    		return accountsDao.getAccountByUserId(userId).getBalance();   // call the AccountDAO get get balance based on current user's account
    }
	
    @RequestMapping(path = "/{sendTo}", method = RequestMethod.GET)
    public Accounts getAccountWithUserId(@PathVariable int sendTo) { 
    	long userId = sendTo;
    	return accountsDao.getAccountByUserId(userId);
    }
    

    // PUT method to update the balance 
    @RequestMapping(path = "/{accountId}", method = RequestMethod.PUT)
    public void updateAccount(@PathVariable int accountId, @RequestBody Accounts account) {
    	// actually have to update the database with the new account object
    	accountsDao.updateBalance(account);
    }
    
    
    
    //Helper methods
    
    // get current user by id
	// finds the user by username and returns the id
	private Long getCurrentUserId(Principal principal) {		
		long userId;		
		userId = userDao.findIdByUsername(principal.getName());
		return userId;
		
	}
	
}
