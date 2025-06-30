package org.medilabo.frontend.backend;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.medilabo.frontend.dto.note.CreateNoteRequest;
import org.medilabo.frontend.dto.note.NoteCreatedResponse;
import org.medilabo.frontend.dto.note.NoteUpdatedResponse;
import org.medilabo.frontend.dto.note.UpdateNoteRequest;
import org.medilabo.frontend.exceptions.FrontendServiceException;
import org.medilabo.frontend.exceptions.NoteNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteClient noteClient;

    @Override
    public List<NoteCreatedResponse> getPatientNotes(Long patientId) {
        try {
            return noteClient.getPatientNotes(patientId);
        } catch (Exception e) {
            log.error("Error fetching notes: {}", e.getMessage());
            throw new FrontendServiceException("Unable to fetch notes list");
        }
    }

    @Override
    public NoteCreatedResponse addNote(CreateNoteRequest note) {
        try {
            log.info("note added toString = {} :",note.toString());
            return noteClient.addNote(note);
        } catch (Exception e) {
            log.error("Error adding note: {}", e.getMessage());
            throw new FrontendServiceException("Unable to add note");
        }
    }

    @Override
    public NoteUpdatedResponse updateNote(String id, UpdateNoteRequest note) {
        try {
            return noteClient.updateNote(id, note);
        } catch (FeignException.NotFound e) {
            log.error("Note not found with id = {}", id);
            throw new NoteNotFoundException(id);
        } catch (Exception e) {
            log.error("Error updating note: {}", e.getMessage());
            throw new FrontendServiceException("Unable to update note");
        }
    }

    @Override
    public void deleteNote(String id) {
        try {
            noteClient.deleteNote(id);
        } catch (FeignException.NotFound e) {
            log.error("Note not found with id = {}", id);
            throw new NoteNotFoundException(id);
        } catch (Exception e) {
            log.error("Error deleting note: {}", e.getMessage());
            throw new FrontendServiceException("Unable to delete note");
        }
    }
}
