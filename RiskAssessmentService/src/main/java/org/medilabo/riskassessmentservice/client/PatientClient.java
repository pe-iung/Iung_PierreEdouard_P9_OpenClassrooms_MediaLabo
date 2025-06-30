package org.medilabo.riskassessmentservice.client;

import org.medilabo.riskassessmentservice.configuration.FeignClientConfig;
import org.medilabo.riskassessmentservice.dto.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client for accessing the Patient service.
 * Retrieves patient demographic data for risk assessment.
 */
@FeignClient(name = "patientClient", url = "${patient.service.url}", configuration = FeignClientConfig.class)
public interface PatientClient {
    /**
     * Retrieves patient information by ID.
     * @param id The patient's unique identifier
     * @return Patient demographic and medical data
     */
    @GetMapping("/api/patients/{id}")
    PatientDTO getPatient(@PathVariable("id") Long id);
}
