package org.medilabo.frontend.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FrontendServiceException extends ServiceException {
    public FrontendServiceException(String message) {
        super(message);
        log.error("Frontend service exception = {}", message);
    }
}