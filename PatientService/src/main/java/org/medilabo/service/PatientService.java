package org.medilabo.service;

import org.medilabo.dto.PatientRequest;
import org.medilabo.dto.PatientResponse;
import org.medilabo.exceptions.PatientNotFoundException;

import java.util.List;

/**
 * Service interface for managing patient operations.
 * Provides core business logic for patient management including CRUD operations.
 */
public interface PatientService {
    /**
     * Retrieves all patients from the database.
     * @return List of all patients mapped to PatientResponse DTOs
     */
    List<PatientResponse> getAllPatients();

    /**
     * Retrieves a specific patient by their ID.
     * @param id The unique identifier of the patient
     * @return PatientResponse containing the patient's details
     * @throws PatientNotFoundException if no patient is found with the given ID
     */
    PatientResponse getPatient(Long id);

    /**
     * Creates a new patient in the system.
     * @param patientRequest DTO containing the new patient's information
     * @return PatientResponse containing the created patient's details including generated ID
     */
    PatientResponse createPatient(PatientRequest patientRequest);

    /**
     * Updates an existing patient's information.
     * @param id The unique identifier of the patient to update
     * @param patientRequest DTO containing the updated patient information
     * @return PatientResponse containing the updated patient's details
     * @throws PatientNotFoundException if no patient is found with the given ID
     */
    PatientResponse updatePatient(Long id, PatientRequest patientRequest);

    /**
     * Deletes a patient from the system.
     * @param id The unique identifier of the patient to delete
     * @throws PatientNotFoundException if no patient is found with the given ID
     */
    void deletePatient(Long id);

    /**
     * Deletes all patients from the system.
     * Used primarily for testing and system cleanup.
     */
    void deleteAll();
}
