package com.techelevator.model.controller;

import java.security.Principal;
import java.util.List;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.model.dao.MedicationDAO;
import com.techelevator.model.dao.UserDAO;
import com.techelevator.model.model.Medication;


@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("medication")

public class MedicationController {
	
	// controller interacts with DAOs
	
	private MedicationDAO medicationDao;
	private UserDAO userDao;
	
	public MedicationController(MedicationDAO medicationDao, UserDAO userDao) {
		this.medicationDao = medicationDao;
		this.userDao = userDao;
	}
	
	
	// method for getting single medication
	@RequestMapping(path = "/{medicationId}", method = RequestMethod.GET)
	public Medication getMedication(@PathVariable int medicationId, Principal principal) {
		long medIdRequest = medicationId;
		return medicationDao.getMedicationByMedId(medIdRequest);
	}
	
	// method for getting all medications by patientID
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Medication> getListMedications(Principal principal) {
		
		long userId = userDao.findIdByUsername(principal.getName());
		
		return medicationDao.getListMedications(userId);
	}
	
	// method to add a medication for current user

	@RequestMapping(method = RequestMethod.POST) 
	public Medication addMedication(@RequestBody Medication medication, Principal principal) {
			return medicationDao.addMedication(medication);
		} 
	
	
	
	@RequestMapping(path = "/{medicationId", method = RequestMethod.PUT)
	public void takeDose(@PathVariable int medicationId, @RequestBody Medication medication) {
		medicationDao.takeDose(medication);
	}
	
	
	
	
}
	









