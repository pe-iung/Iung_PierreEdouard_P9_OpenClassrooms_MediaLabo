package org.medilabo.riskassessmentservice.service;

import org.medilabo.riskassessmentservice.dto.NoteDTO;
import org.medilabo.riskassessmentservice.dto.PatientDTO;
import org.medilabo.riskassessmentservice.exceptions.InvalidPatientDataException;
import org.medilabo.riskassessmentservice.exceptions.RiskNotFoundException;
import org.medilabo.riskassessmentservice.model.RiskAssessment;
import org.medilabo.riskassessmentservice.model.RiskLevel;

import java.util.List;
/**
 * Service interface for assessing diabetes risk levels for patients.
 * Provides methods to evaluate patient risk based on medical history and demographic data.
 */
public interface RiskAssessmentService {
    /**
     * Performs a diabetes risk assessment for a specific patient.
     * @param patientId The unique identifier of the patient to assess
     * @return RiskAssessment containing the evaluated risk level and assessment details
     * @throws InvalidPatientDataException if required patient data is missing
     * @throws RiskNotFoundException if the patient or their data cannot be found
     */
    RiskAssessment assessPatientRisk(Long patientId);

    /**
     * Counts the number of trigger terms in a patient's medical notes.
     * @param notes List of medical notes to analyze
     * @return Number of trigger terms found in the notes
     */
    long countTriggers(List<NoteDTO> notes);

    /**
     * Calculates the risk level based on patient data and trigger count.
     * @param patient Patient demographic and medical data
     * @param triggerCount Number of trigger terms found in patient's notes
     * @return Assessed RiskLevel (NONE, BORDERLINE, IN_DANGER, or EARLY_ONSET)
     */
    RiskLevel calculateRiskLevel(PatientDTO patient, long triggerCount);
}
