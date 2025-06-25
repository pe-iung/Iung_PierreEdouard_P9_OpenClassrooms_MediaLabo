package org.medilabo.frontend.backend;

import lombok.RequiredArgsConstructor;
import org.medilabo.frontend.dto.PatientDTO;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService{

    private final PatientClient patientClient;

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientClient.getAllPatients();
    }

    @Override
    public PatientDTO getPatient(Long id) {
        return patientClient.getPatient(id);
    }

    @Override
    public PatientDTO createPatient(PatientDTO patient) {
        return patientClient.createPatient(patient);
    }

    @Override
    public void updatePatient(Long id, PatientDTO patient) {
        patientClient.updatePatient(id, patient);
    }

    @Override
    public void deletePatient(Long id) {
        patientClient.deletePatient(id);
    }
}
