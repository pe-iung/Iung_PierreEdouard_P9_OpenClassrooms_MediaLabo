//package org.medilabo.note.service;
//
//import org.junit.jupiter.api.Test;
//import org.medilabo.note.dto.NoteDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Testcontainers
//public class NoteIT {
//
//    @Container
//    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:6.0.2"));
//
//    @DynamicPropertySource
//    static void setProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
//    }
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    void shouldCreateAndRetrieveNote() {
//        // Given
//        NoteDTO noteDTO = new NoteDTO();
//        noteDTO.setPatientId(1L);
//        noteDTO.setContent("Test note content");
//
//        // When
//        ResponseEntity<NoteDTO> createResponse = restTemplate.postForEntity(
//                "http://localhost:" + port + "/api/notes",
//                noteDTO,
//                NoteDTO.class
//        );
//
//        // Then
//        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(createResponse.getBody()).isNotNull();
//        assertThat(createResponse.getBody().getContent()).isEqualTo("Test note content");
//
//        ResponseEntity<NoteDTO[]> getResponse = restTemplate.getForEntity(
//                "http://localhost:" + port + "/api/notes/patient/1",
//                NoteDTO[].class
//        );
//
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(getResponse.getBody()).isNotNull();
//        assertThat(getResponse.getBody()).hasSize(1);
//        assertThat(getResponse.getBody()[0].getContent()).isEqualTo("Test note content");
//    }
//}
