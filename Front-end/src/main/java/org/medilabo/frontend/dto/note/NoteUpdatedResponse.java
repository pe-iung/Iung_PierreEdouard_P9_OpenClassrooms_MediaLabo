package org.medilabo.frontend.dto.note;

import lombok.Data;

import java.time.Instant;

@Data
public class NoteUpdatedResponse {


    private String content;
    private Instant createdAt;
    private Instant updatedAt;


}
