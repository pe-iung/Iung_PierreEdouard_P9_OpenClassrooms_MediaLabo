package org.medilabo.frontend.backend;

import org.medilabo.frontend.dto.RiskAssessmentResponse;

/**
 * Service interface for assessing diabetes risk for patients.
 * Provides methods to evaluate patient risk levels based on their medical history.
 */
public interface RiskAssessmentService {
    /**
     * Assesses the diabetes risk level for a specific patient.
     * @param patientId The ID of the patient to assess
     */
    RiskAssessmentResponse assessPatient(Long patientId);
}
