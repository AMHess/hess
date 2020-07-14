package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountsDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.User;


@PreAuthorize("isAuthenticated()")  // all methods in this class can be accessed by users that are logged in (unless a method states only an Admin can use it)
@RestController
@RequestMapping("user")
public class UserController {
	
	
	//private AccountsDAO accountsDao;
	private UserDAO userDao;
	//private TransferDAO transferDAO;
	
	public UserController(UserDAO userDao) {	
	//	this.accountsDao = accountsDao;
		this.userDao = userDao;
		//this.transferDAO = transferDAO;
	}
	
	
	@RequestMapping(path = "/all", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		 return userDao.findAll();
	  }
	
//	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
//	public User findIdByUsername(username) {
//
//	}

}
