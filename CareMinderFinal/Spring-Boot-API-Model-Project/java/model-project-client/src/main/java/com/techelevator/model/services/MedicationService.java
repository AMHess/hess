package com.techelevator.model.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.techelevator.model.models.Medication;



@RequestMapping("medication")
public class MedicationService {
	
	//attributes
	public static String AUTH_TOKEN = "";
	private String BASE_URL;
	private RestTemplate restTemplate = new RestTemplate();
	

	// constructor
	public MedicationService(String url) {
		BASE_URL = url + "medication/";
	}
	
	
	// methods to include: CRUD
	
	// getMedicationsByPatientId - get a list of medications
	// getMedication (MedicationId) - get details on one specific med
	// updateMedication (MedicationId)
	// takeDose(medicationId) - would decrement pillCount. Can set reminder when pill count gets < 10
	// addMedication  - medicationId will be set by postgreSQL when returned in confirmation
	// deleteMedication - will need to think through access-level.
	
	
	// getMedicationsByPatientId - get a list of medications
	public Medication[] getAllMedications(String token) {
		Medication[] allMedications = null;		
		allMedications = restTemplate.exchange(BASE_URL, HttpMethod.GET, makeAuthEntity(token), Medication[].class).getBody();
		return allMedications;
	}
	
	
	// getMedication (MedicationId) - get details on one specific med
	public Medication getMedication(int medicationId, String token) {
		Medication medication;
		medication = restTemplate.exchange(BASE_URL + medicationId, HttpMethod.GET, makeAuthEntity(token), Medication.class).getBody();		
		return medication;			
	}
	
	// add a medication - takes in a medication object and auth token
	public Medication addMedication(Medication medication, String token) {
		
		// validate that medication does not overlap
		
		restTemplate.exchange(BASE_URL, HttpMethod.POST, makeMedicationEntity(medication, token), Medication.class).getBody();
	
		return medication;
	}

	
//	// updateMedication (MedicationId)
//	public Medication updateMedication(int medicationId, String token) {
//		Medication medication = null;
//		medication = restTemplate.exchange(BASE_URL + medicationId, HttpMethod.PUT, makeMedicationEntity(medication, token), Medication.class).getBody();		
//		return medication;
//	}
	
	// may need to pull this out to its own separate class
	public Medication takeDose(int medicationId, String token) {
		
		// get the current user's specific medication based on medicationId
		Medication medication = getMedication(medicationId, token);
		
		// if pillCount <= 0, alert user that they are out	
		if(medication.getPillCount() <= 0) {
			System.out.println("Contact Pharmacy Immediately. Currently out of " + medication.getMedicationName() + "doses");
		} 
		
		// set the pillCount to : current pill count - # of pills required for a dosage
		medication.setPillCount(medication.getPillCount() - medication.getDosageSize());
		
		try {
			restTemplate.exchange(BASE_URL + medicationId, HttpMethod.PUT, makeMedicationEntity(medication, token), Medication.class).getBody();
		} catch (Exception exception) {
			exception.getMessage();
		}
		
			if(medication.getPillCount() <= 10) {
				System.out.println("Please contact your doctor to reorder your " + medication.getMedicationName());
									
		} else {
			System.out.println("Your dose has been logged. Your current quantity remaining for " + medication.getMedicationName() + " is " + medication.getPillCount());
		}
		
		return medication;
	}
	
	
	
	
	
	
	
	
	// returns an http entity that includes auth bearer token
	// Used in restTemplate HTTP method requests
	private HttpEntity makeAuthEntity(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}

	// used for PUT and POST requests to pass Medication object and token
	private HttpEntity<?> makeMedicationEntity(Medication medication, String token){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<?> entity = new HttpEntity(medication, headers);
		return entity;
	}

}
