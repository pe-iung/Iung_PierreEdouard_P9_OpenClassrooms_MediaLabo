package org.medilabo.frontend.backend;

import lombok.RequiredArgsConstructor;
import org.medilabo.frontend.dto.NoteDTO;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{

    private final NoteClient noteClient;

    @Override
    public List<NoteDTO> getPatientNotes(Long patientId) {
        return noteClient.getPatientNotes(patientId);
    }
    @Override
    public NoteDTO addNote(NoteDTO note) {
        return noteClient.addNote(note);
    }
    @Override
    public NoteDTO updateNote(String id, NoteDTO note) {
        return noteClient.updateNote(id, note);
    }
    @Override
    public void deleteNote(String id) {
        noteClient.deleteNote(id);
    }
}
