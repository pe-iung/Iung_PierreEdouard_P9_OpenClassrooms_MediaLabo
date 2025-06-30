package org.medilabo.service;

import org.medilabo.dto.PatientRequest;
import org.medilabo.dto.PatientResponse;

import java.util.List;

public interface PatientService {
    List<PatientResponse> getAllPatients();

    PatientResponse getPatient(Long id);

    PatientResponse createPatient(PatientRequest patientRequest);

    PatientResponse updatePatient(Long id, PatientRequest patientRequest);

    void deletePatient(Long id);

    void deleteAll();
}
