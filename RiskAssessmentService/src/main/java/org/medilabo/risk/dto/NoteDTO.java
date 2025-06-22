package org.medilabo.risk.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NoteDTO {
    private String id;
    private Long patientId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
