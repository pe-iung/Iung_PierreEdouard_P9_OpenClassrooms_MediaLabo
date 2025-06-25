package org.medilabo.frontend.controller;

import lombok.RequiredArgsConstructor;
import org.medilabo.frontend.backend.NoteServiceImpl;
import org.medilabo.frontend.dto.NoteDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients/{patientId}/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteServiceImpl noteServiceImpl;

    @GetMapping
    public String listNotes(@PathVariable Long patientId, Model model) {
        model.addAttribute("notes", noteServiceImpl.getPatientNotes(patientId));
        model.addAttribute("patientId", patientId);
        return "notes/list";
    }

    @GetMapping("/new")
    public String newNoteForm(@PathVariable Long patientId, Model model) {
        NoteDTO note = new NoteDTO();
        note.setPatientId(patientId);
        model.addAttribute("note", note);
        return "notes/form";
    }

    @PostMapping
    public String createNote(@PathVariable Long patientId, @ModelAttribute NoteDTO note) {
        note.setPatientId(patientId);
        noteServiceImpl.addNote(note);
        return "redirect:/patients/" + patientId + "/notes";
    }

    @GetMapping("/{id}/edit")
    public String editNoteForm(@PathVariable Long patientId, @PathVariable String id, Model model) {
        model.addAttribute("note", noteServiceImpl.getPatientNotes(patientId).stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Note not found")));
        return "notes/form";
    }

    @PostMapping("/{id}")
    public String updateNote(@PathVariable Long patientId, @PathVariable String id, @ModelAttribute NoteDTO note) {
        note.setPatientId(patientId);
        noteServiceImpl.updateNote(id, note);
        return "redirect:/patients/" + patientId + "/notes";
    }

    @GetMapping("/{id}/delete")
    public String deleteNote(@PathVariable Long patientId, @PathVariable String id) {
        noteServiceImpl.deleteNote(id);
        return "redirect:/patients/" + patientId + "/notes";
    }
}
