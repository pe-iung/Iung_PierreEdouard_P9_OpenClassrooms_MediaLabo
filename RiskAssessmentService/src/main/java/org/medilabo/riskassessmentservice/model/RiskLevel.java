package org.medilabo.riskassessmentservice.model;
/**
 * Enumeration of possible diabetes risk levels.
 * Represents the different categories of risk assessment outcomes.
 * cf service implementation ofr a deaper business rull understanding of RiskLevel calculation
 */
public enum RiskLevel {

    /**
     * No significant risk factors identified.
     */
    NONE,
    /**
     * Some risk factors present, requiring monitoring.
     */
    BORDERLINE,

    /**
     * Multiple risk factors present, requiring medical attention.
     */
    IN_DANGER,

    /**
     * High risk factors present, requiring immediate medical intervention.
     */
    EARLY_ONSET
}
