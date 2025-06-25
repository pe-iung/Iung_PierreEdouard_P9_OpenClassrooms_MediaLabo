package org.medilabo.riskassessmentservice.model;

import lombok.Value;

@Value
public class RiskAssessment {
    Long patientId;
    String patientName;
    int age;
    long triggerCount;
    RiskLevel riskLevel;
}
