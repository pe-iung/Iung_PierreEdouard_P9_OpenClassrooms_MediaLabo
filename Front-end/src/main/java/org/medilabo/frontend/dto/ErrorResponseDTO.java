package org.medilabo.frontend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ErrorResponseDTO {
    private String code;
    private String message;
    private LocalDateTime timestamp;
}
