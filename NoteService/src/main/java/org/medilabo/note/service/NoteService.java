package org.medilabo.note.service;

import org.medilabo.note.dto.CreateNoteRequest;
import org.medilabo.note.dto.NoteCreatedResponse;
import org.medilabo.note.dto.NoteDTO;
import org.medilabo.note.dto.UpdateNoteRequest;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface NoteService {
    ResponseEntity<List<NoteCreatedResponse>> getPatientNotes(Long patientId);
    ResponseEntity<NoteCreatedResponse> addNote(CreateNoteRequest createNoteRequest);
    ResponseEntity<NoteCreatedResponse> updateNote(String id, UpdateNoteRequest updateNoteRequest);
    ResponseEntity<Void> deleteNote(String id);
}
