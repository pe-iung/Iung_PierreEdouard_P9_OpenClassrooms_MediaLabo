package org.medilabo.note.service;

import org.medilabo.note.dto.NoteDTO;
import java.util.List;

public interface NoteService {
    List<NoteDTO> getPatientNotes(Long patientId);
    NoteDTO addNote(NoteDTO noteDTO);
    NoteDTO updateNote(String id, NoteDTO noteDTO);
    void deleteNote(String id);
}

