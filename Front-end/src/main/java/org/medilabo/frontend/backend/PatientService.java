package org.medilabo.frontend.backend;

import org.medilabo.frontend.dto.PatientDTO;

import java.util.List;

public interface PatientService {
    List<PatientDTO> getAllPatients();

    PatientDTO getPatient(Long id);

    PatientDTO createPatient(PatientDTO patient);

    void updatePatient(Long id, PatientDTO patient);

    void deletePatient(Long id);
}
