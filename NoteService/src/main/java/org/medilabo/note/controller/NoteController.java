package org.medilabo.note.controller;

import lombok.RequiredArgsConstructor;
import org.medilabo.note.dto.CreateNoteRequest;
import org.medilabo.note.dto.NoteCreatedResponse;
import org.medilabo.note.dto.NoteDTO;
import org.medilabo.note.dto.UpdateNoteRequest;
import org.medilabo.note.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<NoteCreatedResponse>> getPatientNotes(@PathVariable Long patientId) {
        return noteService.getPatientNotes(patientId);
    }

    @PostMapping
    public ResponseEntity<NoteCreatedResponse> addNote(@RequestBody CreateNoteRequest createNoteRequest) {
        return noteService.addNote(createNoteRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteCreatedResponse> updateNote(@PathVariable String id, @RequestBody UpdateNoteRequest updateNoteRequest) {
        return noteService.updateNote(id, updateNoteRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {
        return noteService.deleteNote(id);
    }
}
