package org.medilabo.frontend.backend;

import org.medilabo.frontend.dto.NoteDTO;
import org.medilabo.frontend.dto.note.CreateNoteRequest;
import org.medilabo.frontend.dto.note.NoteCreatedResponse;
import org.medilabo.frontend.dto.note.NoteUpdatedResponse;
import org.medilabo.frontend.dto.note.UpdateNoteRequest;

import java.util.List;

public interface NoteService {
    List<NoteCreatedResponse> getPatientNotes(Long patientId);

    NoteCreatedResponse addNote(CreateNoteRequest note);

    NoteUpdatedResponse updateNote(String id, UpdateNoteRequest note);

    void deleteNote(String id);
}
