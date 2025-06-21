package org.medilabo.frontend.backend;

import lombok.RequiredArgsConstructor;
import org.medilabo.frontend.dto.NoteDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteClient noteClient;

    public List<NoteDTO> getPatientNotes(Long patientId) {
        return noteClient.getPatientNotes(patientId);
    }

    public NoteDTO addNote(NoteDTO note) {
        return noteClient.addNote(note);
    }

    public NoteDTO updateNote(String id, NoteDTO note) {
        return noteClient.updateNote(id, note);
    }

    public void deleteNote(String id) {
        noteClient.deleteNote(id);
    }
}
