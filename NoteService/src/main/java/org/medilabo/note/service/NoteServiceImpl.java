package org.medilabo.note.service;

import lombok.RequiredArgsConstructor;
import org.medilabo.note.dto.CreateNoteRequest;
import org.medilabo.note.dto.NoteCreatedResponse;
import org.medilabo.note.dto.NoteDTO;
import org.medilabo.note.dto.UpdateNoteRequest;
import org.medilabo.note.exceptions.NoteNotFoundException;
import org.medilabo.note.model.Note;
import org.medilabo.note.repository.NoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<List<NoteDTO>> getPatientNotes(Long patientId) {
        List<NoteDTO> notes = noteRepository.findByPatientIdOrderByCreatedAtDesc(patientId)
                .stream()
                .map(note -> modelMapper.map(note, NoteDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(notes);
    }

    @Override
    public ResponseEntity<NoteCreatedResponse> addNote(CreateNoteRequest createNoteRequest) {
        Note note = new Note();
        note.setContent(createNoteRequest.getContent());
        note.setPatientId(createNoteRequest.getPatientId());
        Note savedNote = noteRepository.save(note);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(modelMapper.map(savedNote, NoteCreatedResponse.class));
    }

    @Override
    public ResponseEntity<NoteCreatedResponse> updateNote(String id, UpdateNoteRequest updateNoteRequest) {
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        existingNote.setContent(updateNoteRequest.getContent());
        Note updatedNote = noteRepository.save(existingNote);
        return ResponseEntity.ok(modelMapper.map(updatedNote, NoteCreatedResponse.class));
    }

    @Override
    public ResponseEntity<Void> deleteNote(String id) {
        if (!noteRepository.existsById(id)) {
            throw new NoteNotFoundException(id);
        }
        noteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
