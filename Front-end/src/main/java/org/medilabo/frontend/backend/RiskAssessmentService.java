package org.medilabo.frontend.backend;

import lombok.RequiredArgsConstructor;
import org.medilabo.frontend.dto.RiskAssessmentDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiskAssessmentService {
    private final RiskAssessmentClient riskClient;

    public RiskAssessmentDTO assessPatient(Long patientId) {
        return riskClient.assessPatient(patientId);
    }
}
