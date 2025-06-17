package org.medilabo.service;

import org.medilabo.dto.PatientRequest;

import java.util.List;

public interface PatientService {
    List<PatientRequest> getAllPatients();

    PatientRequest getPatient(Long id);

    PatientRequest createPatient(PatientRequest PatientRequest);

    PatientRequest updatePatient(Long id, PatientRequest patientDTO);

    void deletePatient(Long id);

    void deleteAll();
}
