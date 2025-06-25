package org.medilabo.riskassessmentservice.service;

import org.medilabo.riskassessmentservice.dto.NoteDTO;
import org.medilabo.riskassessmentservice.dto.PatientDTO;
import org.medilabo.riskassessmentservice.model.RiskAssessment;
import org.medilabo.riskassessmentservice.model.RiskLevel;

import java.util.List;

public interface RiskAssessmentService {
    RiskAssessment assessPatientRisk(Long patientId);

    long countTriggers(List<NoteDTO> notes);

    RiskLevel calculateRiskLevel(PatientDTO patient, long triggerCount);
}
