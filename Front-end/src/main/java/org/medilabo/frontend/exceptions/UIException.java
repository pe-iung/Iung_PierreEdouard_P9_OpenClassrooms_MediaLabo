package org.medilabo.frontend.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UIException extends RuntimeException {
    public UIException(String message) {
        super(message);
        log.error("UI exception = {}", message);
    }
}