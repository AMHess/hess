package com.techelevator.model.models;

public class Caregiver {
	
	// pojo for caregiver class. Requires id, and username
	
	private long id;
	private String username;
	private int userId;
	
	public Caregiver() {
		
	}
	
	public Caregiver(long id, String username, int userId) {
		this.id = id;
		this.username = username;
		this.userId = userId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUserName(String username) {
		this.username = username;
	}


	public long getId() {
		return id;
	}

	public String getUserName() {
		return username;
	}


	@Override
	public String toString() {
		return "Patient details: \n" +
				"\n ID: " + id +
				"\n Name: " + username;
				
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
