package org.medilabo.frontend.backend;

import org.medilabo.frontend.dto.RiskAssessmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * Feign client interface for making HTTP requests to the RiskAssessment service through the API gateway.
 * Handles all risk-related API calls.
 */
@FeignClient(name = "riskClient", url = "${gateway.url}")
public interface RiskAssessmentClient {
    @GetMapping("/api/risk/assess/{patientId}")
    RiskAssessmentDTO assessPatient(@PathVariable("patientId") Long patientId);
}
