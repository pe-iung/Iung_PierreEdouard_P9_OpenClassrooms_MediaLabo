package org.medilabo.note.service;

import lombok.RequiredArgsConstructor;
import org.medilabo.note.dto.NoteDTO;
import org.medilabo.note.exceptions.NoteNotFoundException;
import org.medilabo.note.model.Note;
import org.medilabo.note.repository.NoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<NoteDTO> getPatientNotes(Long patientId) {
        return noteRepository.findByPatientIdOrderByCreatedAtDesc(patientId)
                .stream()
                .map(note -> modelMapper.map(note, NoteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public NoteDTO addNote(NoteDTO noteDTO) {
        Note note = modelMapper.map(noteDTO, Note.class);
//        note.setCreatedAt(LocalDateTime.now());
//        note.setUpdatedAt(LocalDateTime.now());
        Note savedNote = noteRepository.save(note);
        return modelMapper.map(savedNote, NoteDTO.class);
    }

    @Override
    public NoteDTO updateNote(String id, NoteDTO noteDTO) {
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        existingNote.setContent(noteDTO.getContent());
        //existingNote.setUpdatedAt(LocalDateTime.now());

        Note updatedNote = noteRepository.save(existingNote);
        return modelMapper.map(updatedNote, NoteDTO.class);
    }

    @Override
    public void deleteNote(String id) {
        if (!noteRepository.existsById(id)) {
            throw new NoteNotFoundException(id);
        }
        noteRepository.deleteById(id);
    }
}
