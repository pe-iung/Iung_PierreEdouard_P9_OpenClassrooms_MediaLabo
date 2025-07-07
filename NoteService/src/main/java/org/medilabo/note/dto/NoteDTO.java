package org.medilabo.note.dto;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;


@Data
public class NoteDTO {
    private String id;
    private Long patientId;
    private String content;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
}
