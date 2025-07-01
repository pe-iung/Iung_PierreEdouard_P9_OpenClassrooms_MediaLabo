package org.medilabo.frontend.dto;

import lombok.Data;

//todo rename dto to homogeneity purpose
@Data
public class RiskAssessmentResponse {
    private Long patientId;
    private String patientName;
    private int age;
    private int triggerCount;
    private RiskLevel riskLevel;
}
