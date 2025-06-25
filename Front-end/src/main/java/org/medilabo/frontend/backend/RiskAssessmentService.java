package org.medilabo.frontend.backend;

import org.medilabo.frontend.dto.RiskAssessmentDTO;

public interface RiskAssessmentService {
    RiskAssessmentDTO assessPatient(Long patientId);
}
