package org.medilabo.frontend.dto.note;

import lombok.Data;

@Data
public class CreateNoteRequest {

    private Long patientId;
    private String content;

}
