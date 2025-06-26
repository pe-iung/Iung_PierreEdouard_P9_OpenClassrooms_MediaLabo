package org.medilabo.riskassessmentservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.medilabo.riskassessmentservice.exceptions.InvalidPatientDataException;
import org.medilabo.riskassessmentservice.exceptions.RiskNotFoundException;
import org.medilabo.riskassessmentservice.model.RiskAssessment;
import org.medilabo.riskassessmentservice.service.RiskAssessmentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk")
@RequiredArgsConstructor
@Slf4j
public class RiskController {
    private final RiskAssessmentServiceImpl riskService;

    @GetMapping("/assess/{patientId}")
    public ResponseEntity<?> assessPatient(@PathVariable Long patientId) {
        try {
            RiskAssessment assessment = riskService.assessPatientRisk(patientId);
            return ResponseEntity.ok(assessment);
        } catch (InvalidPatientDataException e) {
            log.warn("Invalid patient data: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (RiskNotFoundException e) {
            log.warn("Risk patient not found with id : {}, error {}", patientId, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }
        catch (Exception e) {
            log.error("Error assessing patient {}: {}", patientId, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error while assessing patient risk"));
        }
    }
}


