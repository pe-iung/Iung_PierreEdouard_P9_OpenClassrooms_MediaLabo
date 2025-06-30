package org.medilabo.frontend.backend;

import org.medilabo.frontend.dto.patient.PatientResponse;
import org.medilabo.frontend.dto.patient.UpsertPatientRequest;

import java.util.List;

public interface PatientService {
    List<PatientResponse> getAllPatients();

    PatientResponse getPatient(Long id);

    PatientResponse createPatient(UpsertPatientRequest patient);

    void updatePatient(Long id, UpsertPatientRequest patient);

    void deletePatient(Long id);
}
