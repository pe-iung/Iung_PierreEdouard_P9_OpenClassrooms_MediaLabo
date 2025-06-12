package org.medilabo.service;

import org.medilabo.dto.PatientRequest;

import java.util.List;

public interface PatientService {
    List<PatientRequest> getAllPatients();

    PatientRequest getPatient(String id);

    PatientRequest createPatient(PatientRequest PatientRequest);

    PatientRequest updatePatient(String id, PatientRequest patientDTO);

    void deletePatient(String id);
}
