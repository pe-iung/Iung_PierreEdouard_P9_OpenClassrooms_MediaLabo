package org.medilabo.note.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medilabo.note.configuration.SecurityConfig;
import org.medilabo.note.dto.CreateNoteRequest;
import org.medilabo.note.dto.NoteCreatedResponse;
import org.medilabo.note.dto.NoteDTO;
import org.medilabo.note.dto.UpdateNoteRequest;
import org.medilabo.note.service.NoteService;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

@WebMvcTest(NoteController.class)
@ExtendWith(MockitoExtension.class)
@Import(SecurityConfig.class)
@TestPropertySource(properties = {
        "api.username=testuser",
        "api.password=testpass",
        "API_USERNAME=testuser",
        "API_PASSWORD=testpass"
})
class NoteControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;


    private NoteCreatedResponse noteCreatedResponse;
    private UpdateNoteRequest updateNoteRequest;
    private CreateNoteRequest createNoteRequest;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        noteCreatedResponse = new NoteCreatedResponse();
        noteCreatedResponse.setContent("Test note content");

        createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setContent("Test note content");

        updateNoteRequest = new UpdateNoteRequest();
        updateNoteRequest.setContent("Test note content updated");

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void getPatientNotes_ShouldReturnNotesList() throws Exception {
        List<NoteCreatedResponse> notes = List.of(noteCreatedResponse);
        ResponseEntity<List<NoteCreatedResponse>> response = new ResponseEntity<>(notes, HttpStatus.OK);
        when(noteService.getPatientNotes(1L)).thenReturn(response);

        mockMvc.perform(get("/api/notes/patient/1")
                        .with(httpBasic("testuser", "testpass")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].content").value("Test note content"));
    }

    @Test
    void addNote_ShouldReturnCreatedNote() throws Exception {
        when(noteService.addNote(any())).thenReturn(ResponseEntity.ok(noteCreatedResponse));

        mockMvc.perform(post("/api/notes")
                        .with(httpBasic("testuser", "testpass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createNoteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test note content"));
    }

    @Test
    void updateNote_ShouldReturnUpdatedNote() throws Exception {
        when(noteService.updateNote(eq("1"), any())).thenReturn(ResponseEntity.ok(noteCreatedResponse));

        mockMvc.perform(put("/api/notes/1")
                        .with(httpBasic("testuser", "testpass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateNoteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test note content"));
    }

    @Test
    void deleteNote_ShouldReturnSuccess() throws Exception {
        when(noteService.deleteNote("1")).thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(delete("/api/notes/1")
                        .with(httpBasic("testuser", "testpass")))
                .andExpect(status().isNoContent());
    }
}
