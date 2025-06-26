package org.medilabo.note.exceptions;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(String id) {
        super(String.format("Note with ID %s was not found", id));
    }
}
