package com.techelevator.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.model.model.Medication;



@Service
public class MedicationSqlDAO implements MedicationDAO{
	
	private JdbcTemplate jdbcTemplate;
	
	public MedicationSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Medication getMedicationByMedId(long medIdRequest) {
		Medication medication = null;
		
		String sql = "Select * from medication where medication_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, medIdRequest);
		
		while(results.next()) {
			medication = mapRowToMedication(results);
		}
		
		return medication;
	}
	
	
	@Override
	public List<Medication> getListMedications(long userId) {
		List<Medication> listMedications = new ArrayList<>();
		
		String sql = "SELECT * " 
				+ " FROM medication "
				+ " WHERE user_id = (SELECT user_id from users where user_id = ?)";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		
		while(results.next()) {
			Medication medication = mapRowToMedication(results);
			listMedications.add(medication);
		}
		
		return listMedications;
	}
	
	// add medication 
	@Override
	public Medication addMedication(Medication medication) {
		Medication newMedication = medication;
		
		String sql = "INSERT into medication ("
					+ "user_id, " + "medication_name, " + "medication_type, " + "frequency_taken, " + "dosage_size, " + "pill_count)"
					+ " VALUES (?, ?, ?, ?, ?, ?)";
		
		
		int numRows = jdbcTemplate.update(sql, 
				
												newMedication.getUserId(),
												newMedication.getMedicationName(), 
												newMedication.getType(), 
												newMedication.getFrequencyTaken(), 
												newMedication.getDosageSize(), 
												newMedication.getPillCount());
		
		return newMedication;
	}
	
	@Override
	public boolean takeDose(Medication medication) {
		String sql = "UPDATE medication SET               user_id = ?"
											+ " , medication_name = ?"
											+ " , medication_type = ?"
											+ " , frequency_taken = ?"
											+ " , dosage_size = ?"
											+ " , pill_count = ?"
											+ " WHERE medication_id = ?";
		
		int rowsAffected = jdbcTemplate.update(sql
												, medication.getUserId()
												, medication.getMedicationName()
												, medication.getFrequencyTaken()
												, medication.getDosageSize()
												, medication.getPillCount()
												, medication.getMedicationId());
		
		return rowsAffected == 1;
		
	}
	
	// helper methods
	
	// returns a medication object from data resource
	private Medication mapRowToMedication(SqlRowSet rs) {
		Medication medication = new Medication();
		medication.setMedicationId(rs.getLong("medication_id"));
		medication.setMedicationName(rs.getString("medication_name"));
		medication.setType(rs.getString("medication_type"));
		medication.setFrequencyTaken(rs.getInt("frequency_taken"));
		medication.setDosageSize(rs.getInt("dosage_size"));
		medication.setPillCount(rs.getInt("pill_count"));
		
		return medication;
	}




	

	



}
