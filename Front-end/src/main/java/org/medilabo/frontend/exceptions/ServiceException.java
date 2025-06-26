package org.medilabo.frontend.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ServiceException extends RuntimeException {
    protected ServiceException(String message) {
        super(message);
        log.error("Service exception = {}", message);
    }
}