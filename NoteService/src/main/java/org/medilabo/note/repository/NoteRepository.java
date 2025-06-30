package org.medilabo.note.repository;

import org.medilabo.note.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
/**
 * Repository interface for Note document operations.
 * Provides database access methods for note management.
 */
public interface NoteRepository extends MongoRepository<Note, String> {
    /**
     * Finds all notes for a specific patient, ordered by creation date.
     * @param patientId The patient's unique identifier
     * @return List of notes associated with the patient, newest first
     */
    List<Note> findByPatientIdOrderByCreatedAtDesc(Long patientId);
}
