package org.medilabo.frontend.backend;

import org.medilabo.frontend.dto.patient.PatientResponse;
import org.medilabo.frontend.dto.patient.UpsertPatientRequest;

import java.util.List;
/**
 * Service interface for managing patient operations.
 * Provides methods to create, read, update and delete patients through the API gateway.
 */
public interface PatientService {

    /**
     * Retrieves all patients in the system.
     * @return List of all patients
     */
    List<PatientResponse> getAllPatients();

    /**
     * Retrieves a specific patient by ID.
     * @param id The ID of the patient to retrieve
     * @return The patient details
     */
    PatientResponse getPatient(Long id);

    /**
     * Creates a new patient.
     * @param patient The patient creation request
     * @return The created patient response
     */
    PatientResponse createPatient(UpsertPatientRequest patient);

    /**
     * Updates an existing patient.
     * @param id The ID of the patient to update
     * @param patient The patient update request
     */
    void updatePatient(Long id, UpsertPatientRequest patient);

    /**
     * Deletes a patient.
     * @param id The ID of the patient to delete
     */
    void deletePatient(Long id);
}
