package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.security.Principal;

import com.techelevator.tenmo.model.Accounts;


public interface AccountsDAO {

	//BigDecimal getCurrentBalance(int id);
	
	//double increaseBalance(int id);
	
	
	Accounts getAccountByUserId(long userId);
	
	boolean updateBalance(Accounts account);
	
}
