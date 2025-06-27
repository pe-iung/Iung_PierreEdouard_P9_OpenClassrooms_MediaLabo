package org.medilabo.riskassessmentservice.dto;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class NoteDTO {
    private String id;
    private Long patientId;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
}
