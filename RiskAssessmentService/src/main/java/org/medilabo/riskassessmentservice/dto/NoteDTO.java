package org.medilabo.riskassessmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {
    private String id;
    private Long patientId;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
}
