package org.medilabo.frontend.dto;

import lombok.Data;

@Data
public class RiskAssessmentResponse {
    private Long patientId;
    private String patientName;
    private int age;
    private int triggerCount;
    private RiskLevel riskLevel;
}
