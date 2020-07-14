package com.techelevator.model.models;

public class Medication {
	
	private long medicationId;
	private int userId; // will be set by getting Principal principal and principal.getId
	private String medicationName;
	private String type; // pill, tab, liquid, injection
	private int frequencyTaken; // 1-5 times daily?
	private int dosageSize;
	private int pillCount;
	
	
	
	//ctor
	public Medication() {};
	
	public Medication(String medicationName, String type, int frequencyTaken, int dosageSize, int pillCount, int userId) {
		long medicationId;
		this.medicationName = medicationName;
		this.type = type;
		this.frequencyTaken = frequencyTaken;
		this.pillCount = pillCount;
		this.dosageSize = dosageSize;
		this.userId = userId;
		
	}

	public long getMedicationId() {
		return medicationId;
	}
	
	public void getMedicationId(long medicationId) {
		this.medicationId = medicationId;
	}

	public String getMedicationName() {
		return medicationName;
	}


	public void setMedicationName(String medicationName) {
		this.medicationName = medicationName;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getFrequencyTaken() {
		return frequencyTaken;
	}


	public void setFrequencyTaken(int pillCount) {
		this.pillCount = pillCount;
	}

	public int getPillCount() {
		return pillCount;
	}

	public int getDosageSize() {
		return dosageSize;
	}

	public void setDosageSize(int dosageSize) {
		this.dosageSize = dosageSize;
	}

	public void setPillCount(int pillCount) {
		this.pillCount = pillCount;
	}
	
	

	
	
	@Override
	public String toString() {
		return "Medication details: \n" +
				"\n Medication name: " + medicationName +
				"\n Type: " + type +
				"\n Frequency taken: " + frequencyTaken;			
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
