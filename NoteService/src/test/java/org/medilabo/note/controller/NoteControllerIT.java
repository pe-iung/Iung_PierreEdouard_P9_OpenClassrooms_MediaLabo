package org.medilabo.note.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medilabo.note.dto.NoteDTO;
import org.medilabo.note.service.NoteService;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteController.class)
@ExtendWith(MockitoExtension.class)
class NoteControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    private NoteDTO testNoteDTO;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        testNoteDTO = new NoteDTO();
        testNoteDTO.setId("1");
        testNoteDTO.setPatientId(1L);
        testNoteDTO.setContent("Test note content");
        testNoteDTO.setCreatedAt(LocalDateTime.now());
        testNoteDTO.setUpdatedAt(LocalDateTime.now());

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void getPatientNotes_ShouldReturnNotesList() throws Exception {
        List<NoteDTO> notes = List.of(testNoteDTO);
        when(noteService.getPatientNotes(1L)).thenReturn(notes);

        mockMvc.perform(get("/api/notes/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].content").value("Test note content"));
    }

    @Test
    void addNote_ShouldReturnCreatedNote() throws Exception {
        when(noteService.addNote(any())).thenReturn(testNoteDTO);

        mockMvc.perform(post("/api/notes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testNoteDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test note content"));
    }

    @Test
    void updateNote_ShouldReturnUpdatedNote() throws Exception {
        when(noteService.updateNote(eq("1"), any())).thenReturn(testNoteDTO);

        mockMvc.perform(put("/api/notes/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testNoteDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test note content"));
    }

    @Test
    void deleteNote_ShouldReturnSuccess() throws Exception {
        doNothing().when(noteService).deleteNote("1");

        mockMvc.perform(delete("/api/notes/1"))
                .andExpect(status().isOk());
    }
}
