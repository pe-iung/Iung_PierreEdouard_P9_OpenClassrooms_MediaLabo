package org.medilabo.frontend.backend;

import org.medilabo.frontend.dto.NoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "noteClient", url = "${gateway.url}")
public interface NoteClient {

    @GetMapping("/api/notes/patient/{patientId}")
    List<NoteDTO> getPatientNotes(@PathVariable("patientId") Long patientId);

    @PostMapping("/api/notes")
    NoteDTO addNote(@RequestBody NoteDTO note);

    @PutMapping("/api/notes/{id}")
    NoteDTO updateNote(@PathVariable("id") String id, @RequestBody NoteDTO note);

    @DeleteMapping("/api/notes/{id}")
    void deleteNote(@PathVariable("id") String id);
}
