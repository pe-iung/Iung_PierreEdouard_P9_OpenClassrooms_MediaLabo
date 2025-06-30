package org.medilabo.note.service;

import org.medilabo.note.dto.CreateNoteRequest;
import org.medilabo.note.dto.NoteCreatedResponse;
import org.medilabo.note.dto.NoteDTO;
import org.medilabo.note.dto.UpdateNoteRequest;
import org.medilabo.note.exceptions.NoteNotFoundException;
import org.springframework.http.ResponseEntity;
import java.util.List;

/**
 * Service interface for managing patient medical notes.
 * Provides methods for creating, reading, updating, and deleting medical notes.
 */
public interface NoteService {
    /**
     * Retrieves all notes for a specific patient.
     * @param patientId The unique identifier of the patient
     * @return ResponseEntity containing a list of notes for the patient
     */
    ResponseEntity<List<NoteCreatedResponse>> getPatientNotes(Long patientId);

    /**
     * Creates a new medical note for a patient.
     * @param createNoteRequest DTO containing the note information and patient ID
     * @return ResponseEntity containing the created note details
     */
    ResponseEntity<NoteCreatedResponse> addNote(CreateNoteRequest createNoteRequest);

    /**
     * Updates an existing medical note.
     * @param id The unique identifier of the note to update
     * @param updateNoteRequest DTO containing the updated note content
     * @return ResponseEntity containing the updated note details
     * @throws NoteNotFoundException if no note is found with the given ID
     */
    ResponseEntity<NoteCreatedResponse> updateNote(String id, UpdateNoteRequest updateNoteRequest);

    /**
     * Deletes a medical note.
     * @param id The unique identifier of the note to delete
     * @return ResponseEntity with no content on successful deletion
     * @throws NoteNotFoundException if no note is found with the given ID
     */
    ResponseEntity<Void> deleteNote(String id);
}
