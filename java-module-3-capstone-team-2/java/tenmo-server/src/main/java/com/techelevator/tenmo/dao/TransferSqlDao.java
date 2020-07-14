package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Transfer;

@Service
public class TransferSqlDao implements TransferDAO {

	private JdbcTemplate jdbcTemplate;

	public TransferSqlDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Transfer addTransfer(Transfer transfer) {
		// boolean transferCreated = false;

		Transfer aTransfer = transfer;

		String addTransferSQL = "INSERT into transfers ("			
				+ " transfer_type_id," + " transfer_status_id," + " account_from," + " account_to," + " amount)"
				+ " VALUES (?, ?, ?, ?, ?)";

		int numRowsAffected = jdbcTemplate.update(addTransferSQL,				
				aTransfer.getTransferTypeId(), aTransfer.getTransferStatusId(), aTransfer.getAccountFrom(),
				aTransfer.getAccountTo(), aTransfer.getAmount());
		return aTransfer;

	}

	// gets a list of transfers based on the userId. Current user can only see their
	// transfers
	@Override
	public List<Transfer> getListTransfers(long userId) {

		List<Transfer> listTransfers = new ArrayList<>();
		
		String sqlFrom = "SELECT * "
				+ "FROM transfers "
				+ "INNER JOIN accounts ON accounts.account_id = transfers.account_from OR accounts.account_id = transfers.account_to "
				+ "WHERE user_id = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFrom, userId);
	

		while (results.next()) {
			Transfer aTransfer = mapRowToTransfer(results);
			listTransfers.add(aTransfer);
		}

		return listTransfers;
	}
	


	public Transfer getTransferByTransferId(long transferId) {
		Transfer transfer = null;
		
		String sql = "SELECT * FROM transfers WHERE transfer_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
		
		while (results.next()) {
			transfer = mapRowToTransfer(results);
		}
		
		return transfer;
	}
	
//	
//	//helper methods
//	
	private Transfer mapRowToTransfer(SqlRowSet rs) {
		Transfer transfer = new Transfer();
		transfer.setTransferId(rs.getLong("transfer_id"));
		transfer.setAccountFrom(rs.getInt("account_from")); 
		transfer.setAccountTo(rs.getInt("account_to"));
		transfer.setAmount(rs.getBigDecimal("amount"));
		return transfer;
		
	}
//	

}
