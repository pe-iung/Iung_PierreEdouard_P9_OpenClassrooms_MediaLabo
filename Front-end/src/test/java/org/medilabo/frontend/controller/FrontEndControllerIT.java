package org.medilabo.frontend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medilabo.frontend.backend.NoteServiceImpl;
import org.medilabo.frontend.backend.PatientServiceImpl;
import org.medilabo.frontend.dto.NoteDTO;
import org.medilabo.frontend.dto.PatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FrontEndControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientServiceImpl patientServiceImpl;

    @MockBean
    private NoteServiceImpl noteServiceImpl;

    @Autowired
    private PatientController patientController;

    @Autowired
    private NoteController noteController;

    private PatientDTO testPatient;
    private NoteDTO testNote;

    @BeforeEach
    void setUp() {
        // Setup test patient
        testPatient = new PatientDTO();
        testPatient.setId(1L);
        testPatient.setFirstName("John");
        testPatient.setLastName("Doe");
        testPatient.setDateOfBirth(new Date(1990, 1, 1));
        testPatient.setGender("M");
        testPatient.setAddress("123 Test St");
        testPatient.setPhone("123-456-7890");

        // Setup test note
        testNote = new NoteDTO();
        testNote.setId("1");
        testNote.setPatientId(1L);
        testNote.setContent("Test note content");
//        testNote.setCreatedAt(LocalDateTime.now());
//        testNote.setUpdatedAt(LocalDateTime.now());
    }

    // Patient Controller Tests
    @Test
    void listPatients_ShouldDisplayPatientsList() throws Exception {
        when(patientServiceImpl.getAllPatients()).thenReturn(List.of(testPatient));

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/list"))
                .andExpect(model().attributeExists("patients"));
    }

    @Test
    void newPatientForm_ShouldDisplayForm() throws Exception {
        mockMvc.perform(get("/patients/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/form"))
                .andExpect(model().attributeExists("patient"));
    }

    @Test
    void createPatient_ShouldRedirectToList() throws Exception {
        when(patientServiceImpl.createPatient(any(PatientDTO.class))).thenReturn(testPatient);

        mockMvc.perform(post("/patients")
                        .flashAttr("patient", testPatient))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));
    }

    @Test
    void editPatientForm_ShouldDisplayForm() throws Exception {
        when(patientServiceImpl.getPatient(1L)).thenReturn(testPatient);

        mockMvc.perform(get("/patients/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/form"))
                .andExpect(model().attributeExists("patient"));
    }

    @Test
    void updatePatient_ShouldRedirectToList() throws Exception {
        doNothing().when(patientServiceImpl).updatePatient(eq(1L), any(PatientDTO.class));

        mockMvc.perform(post("/patients/1")
                        .flashAttr("patient", testPatient))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));
    }

    @Test
    void deletePatient_ShouldRedirectToList() throws Exception {
        doNothing().when(patientServiceImpl).deletePatient(1L);

        mockMvc.perform(get("/patients/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));
    }

    // Note Controller Tests
    @Test
    void listNotes_ShouldDisplayNotesList() throws Exception {
        when(noteServiceImpl.getPatientNotes(1L)).thenReturn(List.of(testNote));

        mockMvc.perform(get("/patients/1/notes"))
                .andExpect(status().isOk())
                .andExpect(view().name("notes/list"))
                .andExpect(model().attributeExists("notes"))
                .andExpect(model().attributeExists("patientId"));
    }

    @Test
    void newNoteForm_ShouldDisplayForm() throws Exception {
        mockMvc.perform(get("/patients/1/notes/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("notes/form"))
                .andExpect(model().attributeExists("note"));
    }

    @Test
    void createNote_ShouldRedirectToList() throws Exception {
        when(noteServiceImpl.addNote(any(NoteDTO.class))).thenReturn(testNote);

        mockMvc.perform(post("/patients/1/notes")
                        .flashAttr("note", testNote))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/1/notes"));
    }

    @Test
    void editNoteForm_ShouldDisplayForm() throws Exception {
        when(noteServiceImpl.getPatientNotes(1L)).thenReturn(List.of(testNote));

        mockMvc.perform(get("/patients/1/notes/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("notes/form"))
                .andExpect(model().attributeExists("note"));
    }

    @Test
    void updateNote_ShouldRedirectToList() throws Exception {
        when(noteServiceImpl.updateNote(eq("1"), any(NoteDTO.class))).thenReturn(testNote);

        mockMvc.perform(post("/patients/1/notes/1")
                        .flashAttr("note", testNote))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/1/notes"));
    }

    @Test
    void deleteNote_ShouldRedirectToList() throws Exception {
        doNothing().when(noteServiceImpl).deleteNote("1");

        mockMvc.perform(get("/patients/1/notes/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/1/notes"));
    }
}
