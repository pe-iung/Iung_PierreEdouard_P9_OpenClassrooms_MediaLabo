package org.medilabo.note.dto;

import lombok.Data;

@Data
public class CreateNoteRequest {
    private Long patientId;
    private String content;
}