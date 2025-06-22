package org.medilabo.frontend.backend;

import org.medilabo.frontend.dto.RiskAssessmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "riskClient", url = "${gateway.url}")
public interface RiskAssessmentClient {
    @GetMapping("/api/risk/assess/{patientId}")
    RiskAssessmentDTO assessPatient(@PathVariable("patientId") Long patientId);
}
