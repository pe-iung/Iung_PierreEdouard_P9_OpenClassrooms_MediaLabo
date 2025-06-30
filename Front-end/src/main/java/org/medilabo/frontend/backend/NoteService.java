package org.medilabo.frontend.backend;

import org.medilabo.frontend.dto.note.CreateNoteRequest;
import org.medilabo.frontend.dto.note.NoteCreatedResponse;
import org.medilabo.frontend.dto.note.NoteUpdatedResponse;
import org.medilabo.frontend.dto.note.UpdateNoteRequest;

import java.util.List;
/**
 * Service interface for managing patient notes operations.
 * Provides methods to create, read, update and delete patient notes through the API gateway.
 */
public interface NoteService {
    /**
     * Retrieves all notes for a specific patient.
     * @param patientId The ID of the patient whose notes to retrieve
     * @return List of notes associated with the patient
     */
    List<NoteCreatedResponse> getPatientNotes(Long patientId);

    /**
     * Creates a new note for a patient.
     * @param note The note creation request containing patient ID and note content
     * @return The created note response with generated ID and timestamps
     */
    NoteCreatedResponse addNote(CreateNoteRequest note);

    /**
     * Updates an existing note.
     * @param id The ID of the note to update
     * @param note The note update request containing new content
     * @return The updated note response
     */
    NoteUpdatedResponse updateNote(String id, UpdateNoteRequest note);

    /**
     * Deletes a note.
     * @param id The ID of the note to delete
     */
    void deleteNote(String id);
}
