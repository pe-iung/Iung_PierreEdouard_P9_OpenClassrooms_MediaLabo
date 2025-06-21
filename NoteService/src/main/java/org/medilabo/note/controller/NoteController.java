package org.medilabo.note.controller;

import lombok.RequiredArgsConstructor;
import org.medilabo.note.dto.NoteDTO;
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
    public List<NoteDTO> getPatientNotes(@PathVariable Long patientId) {
        return noteService.getPatientNotes(patientId);
    }

    @PostMapping
    public NoteDTO addNote(@RequestBody NoteDTO noteDTO) {
        return noteService.addNote(noteDTO);
    }

    @PostMapping("/batch")
    public List<NoteDTO> addNotes(@RequestBody List<NoteDTO> notes) {
        return notes.stream()
                .map(noteService::addNote)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public NoteDTO updateNote(@PathVariable String id, @RequestBody NoteDTO noteDTO) {
        return noteService.updateNote(id, noteDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {
        noteService.deleteNote(id);
        return ResponseEntity.ok().build();
    }
}
