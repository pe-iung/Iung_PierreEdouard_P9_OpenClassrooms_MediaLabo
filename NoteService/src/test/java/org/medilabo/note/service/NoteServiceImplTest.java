package org.medilabo.note.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medilabo.note.dto.NoteDTO;
import org.medilabo.note.model.Note;
import org.medilabo.note.repository.NoteRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    private NoteDTO testNoteDTO;

    @BeforeEach
    void setUp() {
        testNote = new Note();
        testNote.setId("1");
        testNote.setPatientId(1L);
        testNote.setContent("Test note");
        testNote.setCreatedAt(LocalDateTime.now());
        testNote.setUpdatedAt(LocalDateTime.now());

        testNoteDTO = new NoteDTO();
        testNoteDTO.setId("1L");
        testNoteDTO.setPatientId(1L);
        testNoteDTO.setContent("Test note");
        testNoteDTO.setCreatedAt(testNote.getCreatedAt());
        testNoteDTO.setUpdatedAt(testNote.getUpdatedAt());
    }

    @Test
    void shouldGetPatientNotes() {
        when(noteRepository.findByPatientIdOrderByCreatedAtDesc(1L))
                .thenReturn(List.of(testNote));
        when(modelMapper.map(any(Note.class), eq(NoteDTO.class)))
                .thenReturn(testNoteDTO);

        List<NoteDTO> result = noteService.getPatientNotes(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(noteRepository).findByPatientIdOrderByCreatedAtDesc(1L);
    }

    @Test
    void shouldAddNote() {
        when(modelMapper.map(any(NoteDTO.class), eq(Note.class)))
                .thenReturn(testNote);
        when(noteRepository.save(any(Note.class)))
                .thenReturn(testNote);
        when(modelMapper.map(any(Note.class), eq(NoteDTO.class)))
                .thenReturn(testNoteDTO);

        NoteDTO result = noteService.addNote(testNoteDTO);

        assertNotNull(result);
        assertEquals(testNoteDTO.getContent(), result.getContent());
        verify(noteRepository).save(any(Note.class));
    }

    @Test
    void shouldUpdateNote() {
        when(noteRepository.findById("1"))
                .thenReturn(Optional.of(testNote));
        when(modelMapper.map(any(NoteDTO.class), eq(Note.class)))
                .thenReturn(testNote);
        when(noteRepository.save(any(Note.class)))
                .thenReturn(testNote);
        when(modelMapper.map(any(Note.class), eq(NoteDTO.class)))
                .thenReturn(testNoteDTO);

        NoteDTO result = noteService.updateNote("1", testNoteDTO);

        assertNotNull(result);
        assertEquals(testNoteDTO.getContent(), result.getContent());
        verify(noteRepository).save(any(Note.class));
    }

    @Test
    void shouldDeleteNote() {
        noteService.deleteNote("1");
        verify(noteRepository).deleteById("1");
    }
}
