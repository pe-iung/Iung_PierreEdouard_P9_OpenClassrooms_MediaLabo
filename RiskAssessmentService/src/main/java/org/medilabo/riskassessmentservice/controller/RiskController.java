package org.medilabo.riskassessmentservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.medilabo.riskassessmentservice.model.RiskAssessment;
import org.medilabo.riskassessmentservice.service.RiskAssessmentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk")
@RequiredArgsConstructor
@Slf4j
public class RiskController {
    private final RiskAssessmentServiceImpl riskService;

    @GetMapping("/assess/{patientId}")
    public ResponseEntity<RiskAssessment> assessPatient(
            @PathVariable Long patientId) {
        try {
            log.info("inside Risk controller assessing patient {}:", patientId );
            return ResponseEntity.ok(

                    riskService.assessPatientRisk(patientId)
            );
        } catch (IllegalArgumentException e) {
            log.error("Bad request error assessing patient {}: {}", patientId, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error assessing patient {}: {}", patientId, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
