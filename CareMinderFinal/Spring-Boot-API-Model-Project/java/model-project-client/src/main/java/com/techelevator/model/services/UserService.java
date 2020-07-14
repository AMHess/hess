package com.techelevator.model.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.techelevator.model.models.User;



@RequestMapping("user")
public class UserService {
	
	//attributes
	public static String AUTH_TOKEN = "";
	private String BASE_URL;
	private RestTemplate restTemplate = new RestTemplate();
	
	//constructor
	public UserService(String url) {
		BASE_URL = url + "user/";
	}
	
	
	
	// methods to include in here: CRUD
	//getAllPatients - only admin level access?
	// getPatientByPatientId
	// addPatient is handled at register in App. Won't need that here
	// deletePatient - restrict access to ADMIN level
	
	// patientId will be the current user that's logged in
	public User getUserByUserId(int userId, String token) {
		User user = null;
		user = restTemplate.exchange(BASE_URL + userId, HttpMethod.GET, makeAuthEntity(token), User.class).getBody();
		return user;
	}
	
	
	
	
	
	
	// helper methods:
	// makeAuthEntity
	//makePatientEntity - not sure if will need currently
	
	
	// returns an http entity that includes auth bearer token
	// Used in restTemplate HTTP method requests
	private HttpEntity makeAuthEntity(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
}
