package org.medilabo.note.exception;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(Long id) {
        super("Note not found with id: " + id);
    }

    public NoteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
