package org.medilabo.note.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medilabo.note.dto.CreateNoteRequest;
import org.medilabo.note.dto.NoteCreatedResponse;
import org.medilabo.note.dto.UpdateNoteRequest;
import org.medilabo.note.model.Note;
import org.medilabo.note.repository.NoteRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

    @InjectMocks
    private NoteServiceImpl noteService;

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private ModelMapper modelMapper;

    private Note testNote;
    private NoteCreatedResponse testNoteResponse;
    private CreateNoteRequest createNoteRequest;
    private UpdateNoteRequest updateNoteRequest;

    @BeforeEach
    void setUp() {
        // Setup test note
        testNote = new Note();
        testNote.setId("1");
        testNote.setPatientId(1L);
        testNote.setContent("Test note");
        testNote.setCreatedAt(Instant.now());
        testNote.setUpdatedAt(Instant.now());

        // Setup test response
        testNoteResponse = new NoteCreatedResponse();
        testNoteResponse.setContent("Test note");
        testNoteResponse.setCreatedAt(testNote.getCreatedAt());
        testNoteResponse.setUpdatedAt(testNote.getUpdatedAt());

        // Setup create request
        createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setPatientId(1L);
        createNoteRequest.setContent("Test note");

        // Setup update request
        updateNoteRequest = new UpdateNoteRequest();
        updateNoteRequest.setContent("Updated test note");
    }

    @Test
    void shouldGetPatientNotes() {
        // Given
        when(noteRepository.findByPatientIdOrderByCreatedAtDesc(1L))
                .thenReturn(List.of(testNote));
        when(modelMapper.map(any(Note.class), eq(NoteCreatedResponse.class)))
                .thenReturn(testNoteResponse);

        // When
        ResponseEntity<List<NoteCreatedResponse>> result = noteService.getPatientNotes(1L);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertFalse(result.getBody().isEmpty());
        assertEquals(1, result.getBody().size());
        verify(noteRepository).findByPatientIdOrderByCreatedAtDesc(1L);
    }

    @Test
    void shouldAddNote() {
        // Given
        when(noteRepository.save(any(Note.class)))
                .thenReturn(testNote);
        when(modelMapper.map(any(Note.class), eq(NoteCreatedResponse.class)))
                .thenReturn(testNoteResponse);

        // When
        ResponseEntity<NoteCreatedResponse> result = noteService.addNote(createNoteRequest);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(testNoteResponse.getContent(), result.getBody().getContent());
        verify(noteRepository).save(any(Note.class));
    }

    @Test
    void shouldUpdateNote() {
        // Given
        when(noteRepository.findById("1"))
                .thenReturn(Optional.of(testNote));
        when(noteRepository.save(any(Note.class)))
                .thenReturn(testNote);
        when(modelMapper.map(any(Note.class), eq(NoteCreatedResponse.class)))
                .thenReturn(testNoteResponse);

        // When
        ResponseEntity<NoteCreatedResponse> result = noteService.updateNote("1", updateNoteRequest);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(testNoteResponse.getContent(), result.getBody().getContent());
        verify(noteRepository).save(any(Note.class));
    }

    @Test
    void shouldDeleteNote() {
        // Given
        when(noteRepository.existsById("1")).thenReturn(true);
        doNothing().when(noteRepository).deleteById("1");

        // When
        ResponseEntity<Void> result = noteService.deleteNote("1");

        // Then
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(noteRepository).deleteById("1");
    }
}
