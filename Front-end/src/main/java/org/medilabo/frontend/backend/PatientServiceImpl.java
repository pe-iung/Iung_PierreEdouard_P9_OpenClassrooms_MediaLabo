package org.medilabo.frontend.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.medilabo.frontend.dto.patient.PatientResponse;
import org.medilabo.frontend.dto.patient.UpsertPatientRequest;
import org.medilabo.frontend.exceptions.FrontendServiceException;
import org.medilabo.frontend.exceptions.PatientNotFoundException;
import org.medilabo.frontend.exceptions.UIException;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService{

    private final PatientClient patientClient;
    private final ObjectMapper objectMapper;

    @Override
    public List<PatientResponse> getAllPatients() {
        try {
            return patientClient.getAllPatients();
        } catch (FeignException.NotFound e) {
            log.error("Error fetching patients: {}", e.getMessage());
            throw new UIException("Unable to fetch patients list");
        } catch (Exception e) {
            log.error("Unexpected error while fetching patients", e);
            throw new FrontendServiceException("An unexpected error occurred while fetching patients");
        }
    }

    @Override
    public PatientResponse getPatient(Long id) {
        try {
            return patientClient.getPatient(id);
        } catch (FeignException.NotFound e) {

                log.error("Error PatientNotFound with id = {}", id);
                throw new PatientNotFoundException(id);

        } catch (Exception e) {
            log.error("Error fetching patient details", e);
            throw new FrontendServiceException("Error fetching patient details");
        }
    }

    @Override
    public PatientResponse createPatient(UpsertPatientRequest patient) {
        return patientClient.createPatient(patient);
    }

    @Override
    public void updatePatient(Long id, UpsertPatientRequest patient) {
        patientClient.updatePatient(id, patient);
    }

    @Override
    public void deletePatient(Long id) {
        patientClient.deletePatient(id);
    }
}
