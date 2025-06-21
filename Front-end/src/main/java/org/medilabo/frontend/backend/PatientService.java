package org.medilabo.frontend.backend;

import lombok.RequiredArgsConstructor;
import org.medilabo.frontend.dto.PatientDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientClient patientClient;

    public List<PatientDTO> getAllPatients() {
        return patientClient.getAllPatients();
    }

    public PatientDTO getPatient(Long id) {
        return patientClient.getPatient(id);
    }

    public PatientDTO createPatient(PatientDTO patient) {
        return patientClient.createPatient(patient);
    }

    public void updatePatient(Long id, PatientDTO patient) {
        patientClient.updatePatient(id, patient);
    }

    public void deletePatient(Long id) {
        patientClient.deletePatient(id);
    }
}
