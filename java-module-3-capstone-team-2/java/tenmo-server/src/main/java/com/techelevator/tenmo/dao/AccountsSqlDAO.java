package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.security.Principal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Accounts;

@Service
public class AccountsSqlDAO implements AccountsDAO {

	private JdbcTemplate jdbcTemplate;
	
	public AccountsSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
//	public BigDecimal getCurrentBalance(int id) {
//		BigDecimal balance = jdbcTemplate.queryForObject("select balance from accounts "                              
//				                   + " where user_id = ?", BigDecimal.class, id);
//		
//		return balance;
//	}

	
	
	@Override
	public Accounts getAccountByUserId(long userId) {
		Accounts account = null;
				
		String sql = "select account_id, user_id, balance from accounts where user_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		
		while(results.next()) {
			account = mapRowToAccount(results);
	
		}
		
		return account;
	}
	
	// subtracting from the sender Balance
	// We update the balance in AccountService and pass along an updated Account object. So just need to update an account based on it's id?
	@Override
	public boolean updateBalance(Accounts account) {
		String sql = "Update accounts Set user_id = ?"			
									+ "   ,balance = ?"
									+ " where account_id = ?";
		
		int numRowsAffected = jdbcTemplate.update(sql
													, account.getUserId()
													, account.getBalance()
													, account.getAccountId());
		
		return numRowsAffected == 1;  // since accountId is the PK, only 1 or 0 rows can be updated.
	}


	
	private Accounts mapRowToAccount(SqlRowSet rs) {
		return new Accounts(rs.getLong("account_id"), rs.getLong("user_id"), rs.getBigDecimal("balance"));
	}
	

	
}
