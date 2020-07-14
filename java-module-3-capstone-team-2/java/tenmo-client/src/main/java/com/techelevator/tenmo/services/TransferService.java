package com.techelevator.tenmo.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Accounts;
import com.techelevator.tenmo.models.Transfer;

import com.techelevator.tenmo.models.User;


@RequestMapping("transfer")

public class TransferService {
	
	
	// attributes
	public static String AUTH_TOKEN = "";
	private String BASE_URL;
	private RestTemplate restTemplate = new RestTemplate();
	
	User user;
	Accounts account;
	AccountService accountService;
	Transfer transfer;
	
	public TransferService(String url) {
		BASE_URL = url + "transfer/";
		accountService = new AccountService(url);
	}
	
	public Transfer doTransfer(Transfer transfer, String token) {
		
		//Transfer aTransfer = makeTransfer(transfer);
			// VALIDATION - TODO: check if the FROM and TO account exists 
		
			accountService.subtractBalance(token, transfer.getAccountFrom(), transfer.getAmount());				
			accountService.addBalance(token, transfer.getAccountTo(), transfer.getAmount());
			
			try {
				// Use restTemplate to POST a transfer, and return a Transfer object (just the body of the entity)
				restTemplate.exchange(BASE_URL, HttpMethod.POST, makeTransferEntity(transfer, token), Transfer.class).getBody();	
			} catch(Exception exception) {
				System.out.println(exception.getMessage());
			}
			return transfer;
	}
	
	
	// view transfer history (number 5 on use cases).
	
	public Transfer[] listTransfers(String token) {
		Transfer[] transfersFrom = null;
		
		try {
			transfersFrom = restTemplate.exchange(BASE_URL, HttpMethod.GET, makeAuthEntity(token), Transfer[].class).getBody();
		} catch (Exception exception) {
			System.out.println("Please try again");
		}
		return transfersFrom;
	}
	
	
	public Transfer getTransferByTransferId(long transferId, String token) {
		Transfer transfer = null;
		
		transfer = restTemplate.exchange(BASE_URL + "?transferId=" + transferId, HttpMethod.GET, makeAuthEntity(token), Transfer.class).getBody();
		
		return transfer;
	}
	
	 /**
     * Returns an {HttpEntity} with the `Authorization: Bearer:` header
     *
     * @return {HttpEntity}
     */
    private HttpEntity makeAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
	
 private HttpEntity<?> makeTransferEntity(Transfer transfer, String token) {
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(token);
    	HttpEntity<?> entity = new HttpEntity<>(transfer, headers);
    	return entity;
    }

}
