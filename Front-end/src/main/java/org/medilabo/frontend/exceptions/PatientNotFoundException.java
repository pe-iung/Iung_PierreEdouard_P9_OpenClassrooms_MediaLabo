package org.medilabo.frontend.exceptions;

public class PatientNotFoundException extends UIException {
    public PatientNotFoundException(Long id) {
        super(String.format("Patient with ID %d was not found in our system", id));
    }
}