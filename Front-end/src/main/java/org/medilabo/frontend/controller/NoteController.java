package org.medilabo.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.medilabo.frontend.backend.NoteServiceImpl;
import org.medilabo.frontend.backend.PatientServiceImpl;
import org.medilabo.frontend.dto.note.CreateNoteRequest;
import org.medilabo.frontend.dto.note.UpdateNoteRequest;
import org.medilabo.frontend.exceptions.NoteNotFoundException;
import org.medilabo.frontend.exceptions.PatientNotFoundException;
import org.medilabo.frontend.exceptions.UIException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/patients/{patientId}/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteServiceImpl noteServiceImpl;
    private final PatientServiceImpl patientServiceImpl;

    @GetMapping
    public String listNotes(@PathVariable Long patientId, Model model, RedirectAttributes redirectAttributes) {
        try {
            patientServiceImpl.getPatient(patientId);
            model.addAttribute("notes", noteServiceImpl.getPatientNotes(patientId));
            model.addAttribute("patientId", patientId);
            return "notes/list";
        } catch (PatientNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patients";
        } catch (UIException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patients";
        }
    }

    @GetMapping("/new")
    public String newNoteForm(@PathVariable Long patientId, Model model) {
        CreateNoteRequest note = new CreateNoteRequest();
        note.setPatientId(patientId);
        model.addAttribute("note", note);
        return "notes/form";
    }

    @PostMapping
    public String createNote(@PathVariable Long patientId, @ModelAttribute CreateNoteRequest note, RedirectAttributes redirectAttributes) {
        try {
            note.setPatientId(patientId);
            noteServiceImpl.addNote(note);
            redirectAttributes.addFlashAttribute("success", "Note created successfully");
            return "redirect:/patients/" + patientId + "/notes";
        } catch (UIException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patients/" + patientId + "/notes";
        }
    }

    @GetMapping("/{id}/edit")
    public String editNoteForm(@PathVariable Long patientId, @PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("note", noteServiceImpl.getPatientNotes(patientId).stream()
                    .filter(n -> n.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new NoteNotFoundException(id)));
            model.addAttribute("patientId", patientId);
            model.addAttribute("noteId", id);
            return "notes/form";
        } catch (NoteNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patients/" + patientId + "/notes";
        }
    }

    @PostMapping("/{id}")
    public String updateNote(@PathVariable Long patientId, @PathVariable String id, @ModelAttribute UpdateNoteRequest note, RedirectAttributes redirectAttributes) {
        try {
            //note.setPatientId(patientId);
            noteServiceImpl.updateNote(id, note);
            redirectAttributes.addFlashAttribute("success", "Note updated successfully");
            return "redirect:/patients/" + patientId + "/notes";
        } catch (NoteNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patients/" + patientId + "/notes";
        } catch (UIException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patients/" + patientId + "/notes";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteNote(@PathVariable Long patientId, @PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            noteServiceImpl.deleteNote(id);
            redirectAttributes.addFlashAttribute("success", "Note deleted successfully");
            return "redirect:/patients/" + patientId + "/notes";
        } catch (NoteNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patients/" + patientId + "/notes";
        } catch (UIException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patients/" + patientId + "/notes";
        }
    }
}
