package com.techelevator.model.dao;

import java.util.List;

import com.techelevator.model.model.Medication;


public interface MedicationDAO {

	public List<Medication> getListMedications(long userId);

	public Medication addMedication(Medication medication);

	public boolean takeDose(Medication medication);

	public Medication getMedicationByMedId(long medIdRequest);
}
