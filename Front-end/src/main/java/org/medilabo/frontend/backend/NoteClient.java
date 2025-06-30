package org.medilabo.frontend.backend;

import org.medilabo.frontend.dto.note.CreateNoteRequest;
import org.medilabo.frontend.dto.note.NoteCreatedResponse;
import org.medilabo.frontend.dto.note.NoteUpdatedResponse;
import org.medilabo.frontend.dto.note.UpdateNoteRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Feign client interface for making HTTP requests to the Note service through the API gateway.
 * Handles all note-related API calls.
 */
@FeignClient(name = "noteClient", url = "${gateway.url}")
public interface NoteClient {

    @GetMapping("/api/notes/patient/{patientId}")
    List<NoteCreatedResponse> getPatientNotes(@PathVariable("patientId") Long patientId);

    @PostMapping("/api/notes")
    NoteCreatedResponse addNote(@RequestBody CreateNoteRequest note);

    @PutMapping("/api/notes/{id}")
    NoteUpdatedResponse updateNote(@PathVariable("id") String id, @RequestBody UpdateNoteRequest note);

    @DeleteMapping("/api/notes/{id}")
    void deleteNote(@PathVariable("id") String id);
}
