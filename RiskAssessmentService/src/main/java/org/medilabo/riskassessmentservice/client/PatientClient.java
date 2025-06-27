package org.medilabo.riskassessmentservice.client;

import org.medilabo.riskassessmentservice.configuration.FeignClientConfig;
import org.medilabo.riskassessmentservice.dto.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patientClient", url = "${patient.service.url}", configuration = FeignClientConfig.class)
public interface PatientClient {
    @GetMapping("/api/patients/{id}")
    PatientDTO getPatient(@PathVariable("id") Long id);
}
