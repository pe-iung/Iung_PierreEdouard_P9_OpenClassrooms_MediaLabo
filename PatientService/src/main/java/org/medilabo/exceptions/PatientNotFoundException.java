package org.medilabo.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PatientNotFoundException extends NotFoundException {
    public PatientNotFoundException(String message) {
        super(message);
        log.error("Patient not found exception = {}", message);
    }
}
