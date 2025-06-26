package org.medilabo.riskassessmentservice.exceptions;

public class RiskNotFoundException extends RuntimeException {
    public RiskNotFoundException(Long patientId) {
        super(String.format("Risk assessment for patient ID %d was not found", patientId));
    }
}