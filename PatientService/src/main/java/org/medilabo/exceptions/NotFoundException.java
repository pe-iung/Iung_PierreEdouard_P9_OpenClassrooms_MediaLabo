package org.medilabo.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class NotFoundException extends RuntimeException {
    protected NotFoundException(String message) {
        super(message);
        log.error("Not found exception = {}", message);
    }
}