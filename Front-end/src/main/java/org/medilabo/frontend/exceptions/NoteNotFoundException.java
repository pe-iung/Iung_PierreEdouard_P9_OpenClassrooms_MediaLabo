package org.medilabo.frontend.exceptions;

public class NoteNotFoundException extends UIException {
    public NoteNotFoundException(String id) {
        super(String.format("Note with ID %s was not found", id));
    }
}