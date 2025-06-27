package org.medilabo.frontend.dto;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
public class NoteDTO {
    private String id;
    private Long patientId;
    private String content;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    public String getFormattedCreatedAt() {
        if (createdAt == null) return "";
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
                .withZone(ZoneId.systemDefault())
                .format(createdAt);
    }

    public String getFormattedUpdatedAt() {
        if (updatedAt == null) return "";
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
                .withZone(ZoneId.systemDefault())
                .format(updatedAt);
    }

}
