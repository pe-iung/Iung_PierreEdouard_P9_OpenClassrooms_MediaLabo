package org.medilabo.note.repository;

import org.medilabo.note.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findByPatientIdOrderByCreatedAtDesc(Long patientId);
}
