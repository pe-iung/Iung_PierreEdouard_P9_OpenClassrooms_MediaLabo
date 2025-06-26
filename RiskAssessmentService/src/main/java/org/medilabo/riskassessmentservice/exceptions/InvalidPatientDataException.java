package org.medilabo.riskassessmentservice.exceptions;

public class InvalidPatientDataException extends RuntimeException {
    public InvalidPatientDataException(Long patientId, String field) {
        super(String.format("Cannot assess risk for patient %d: %s is required", patientId, field));
    }
}
