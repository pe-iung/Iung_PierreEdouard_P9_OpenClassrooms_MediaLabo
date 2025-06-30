package org.medilabo.note.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class NoteCreatedResponse {
    private String Id;
    private String content;
    private Instant createdAt;
    private  Instant updatedAt;
}
