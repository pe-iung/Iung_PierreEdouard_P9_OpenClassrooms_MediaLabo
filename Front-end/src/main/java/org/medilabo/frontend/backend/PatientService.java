package org.medilabo.frontend.backend;

import jakarta.annotation.PostConstruct;
import org.medilabo.frontend.dto.PatientDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

//todo : add interface PatientService
// todo : add feign tool http
@Service
public class PatientService {
    @Value("${backend.api.username}")
    private String apiUsername;

    @Value("${backend.api.password}")
    private String apiPassword;

    @Value("${gateway.url}")
    private String gatewayUrl;

    private final RestTemplate restTemplate;

    public PatientService() {
        this.restTemplate = new RestTemplate();
    }

    @PostConstruct
    private void configureRestTemplate() {
        restTemplate.getInterceptors().add(
                new BasicAuthenticationInterceptor(apiUsername, apiPassword)
        );
    }


    public List<PatientDTO> getAllPatients() {
        PatientDTO[] patients = restTemplate.getForObject(
                gatewayUrl + "/api/patients",
                PatientDTO[].class
        );
        return Arrays.asList(patients);
    }

    public PatientDTO getPatient(Long id) {
        return restTemplate.getForObject(
                gatewayUrl + "/api/patients/" + id,
                PatientDTO.class
        );
    }

    public PatientDTO createPatient(PatientDTO patient) {
        return restTemplate.postForObject(
                gatewayUrl + "/api/patients",
                patient,
                PatientDTO.class
        );
    }

    public void updatePatient(Long id, PatientDTO patient) {
        restTemplate.put(
                gatewayUrl + "/api/patients/" + id,
                patient
        );
    }

    public void deletePatient(Long id) {
        restTemplate.delete(gatewayUrl + "/api/patients/" + id);
    }
}
