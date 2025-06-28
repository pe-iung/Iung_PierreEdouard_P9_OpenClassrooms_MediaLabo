package org.medilabo.frontend.dto.note;

import lombok.Data;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
public class NoteCreatedResponse {
    private String Id;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;

    public String getFormattedCreatedAt() {
        if (createdAt == null) return "";
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
                .withZone(ZoneId.systemDefault())
                .format(createdAt);
    }
}
