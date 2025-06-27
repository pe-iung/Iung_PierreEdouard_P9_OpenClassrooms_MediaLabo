package org.medilabo.note.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.medilabo.note.dto.NoteDTO;
import org.medilabo.note.service.NoteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader {
    private final NoteService noteService;
    private final ObjectMapper objectMapper;
    private final MongoTemplate mongoTemplate;

    @Bean
    CommandLineRunner loadData() {
        return args -> {
            try {
                log.info("Starting to load test data for notes");
                mongoTemplate.dropCollection("notes");

                ClassPathResource resource = new ClassPathResource("notes.json");
                List<NoteDTO> notes = objectMapper.readValue(
                        resource.getInputStream(),
                        new TypeReference<List<NoteDTO>>() {}
                );

                log.info("Loading {} test notes", notes.size());
                notes.forEach(noteService::addNote);
                log.info("Finished loading test data for notes");

            } catch (IOException e) {
                log.error("Failed to load test data", e);
                throw new RuntimeException("Failed to load test data", e);
            }
        };
    }
}
