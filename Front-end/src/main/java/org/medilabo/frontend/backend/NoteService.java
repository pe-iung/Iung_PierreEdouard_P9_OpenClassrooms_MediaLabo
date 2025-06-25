package org.medilabo.frontend.backend;

import org.medilabo.frontend.dto.NoteDTO;

import java.util.List;

public interface NoteService {
    List<NoteDTO> getPatientNotes(Long patientId);

    NoteDTO addNote(NoteDTO note);

    NoteDTO updateNote(String id, NoteDTO note);

    void deleteNote(String id);
}
