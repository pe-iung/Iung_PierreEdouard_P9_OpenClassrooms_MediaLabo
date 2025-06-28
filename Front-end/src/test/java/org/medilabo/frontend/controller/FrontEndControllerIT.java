package org.medilabo.frontend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medilabo.frontend.backend.NoteServiceImpl;
import org.medilabo.frontend.backend.PatientServiceImpl;
import org.medilabo.frontend.dto.PatientDTO;
import org.medilabo.frontend.dto.note.CreateNoteRequest;
import org.medilabo.frontend.dto.note.NoteCreatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class FrontEndControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private PatientServiceImpl patientServiceImpl;

    @MockBean
    private NoteServiceImpl noteServiceImpl;

    private PatientDTO testPatient;
    private NoteCreatedResponse testNote;
    private CreateNoteRequest createNoteRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        // Setup test patient
        testPatient = new PatientDTO();
        testPatient.setId(1L);
        testPatient.setFirstName("John");
        testPatient.setLastName("Doe");
        testPatient.setDateOfBirth(new Date());
        testPatient.setGender("M");
        testPatient.setAddress("123 Test St");
        testPatient.setPhone("123-456-7890");

        // Setup test note
        testNote = new NoteCreatedResponse();
        testNote.setId("1");
        testNote.setContent("Test note content");

        // Setup create note request
        createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setPatientId(1L);
        createNoteRequest.setContent("Test note content");
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpass", roles = "USER")
    void listPatients_ShouldDisplayPatientsList() throws Exception {
        when(patientServiceImpl.getAllPatients()).thenReturn(List.of(testPatient));

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/list"))
                .andExpect(model().attributeExists("patients"));
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpass", roles = "USER")
    void createNote_ShouldRedirectToList() throws Exception {
        when(noteServiceImpl.addNote(any(CreateNoteRequest.class))).thenReturn(testNote);

        mockMvc.perform(post("/patients/1/notes")
                        .with(csrf())
                        .flashAttr("note", createNoteRequest))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/1/notes"));
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpass", roles = "USER")
    void editNoteForm_ShouldDisplayForm() throws Exception {
        when(noteServiceImpl.getPatientNotes(1L)).thenReturn(List.of(testNote));

        mockMvc.perform(get("/patients/1/notes/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("notes/form"))
                .andExpect(model().attributeExists("note"));
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpass", roles = "USER")
    void deleteNote_ShouldRedirectToList() throws Exception {
        doNothing().when(noteServiceImpl).deleteNote("1");

        mockMvc.perform(get("/patients/1/notes/1/delete")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/1/notes"));
    }
}

