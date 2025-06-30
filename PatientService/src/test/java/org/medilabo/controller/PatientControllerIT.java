package org.medilabo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.MediaType;
import org.medilabo.configuration.SecurityConfig;
import org.medilabo.dto.PatientRequest;
import org.medilabo.dto.PatientResponse;
import org.medilabo.exceptions.PatientNotFoundException;
import org.medilabo.model.SexEnum;
import org.medilabo.service.PatientService;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
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

@WebMvcTest(PatientController.class)
@ExtendWith(MockitoExtension.class)
@Import(SecurityConfig.class)
@TestPropertySource(properties = {
        "api.username=testuser",
        "api.password=testpass",
        "API_USERNAME=testuser",
        "API_PASSWORD=testpass"
})
class PatientControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    private PatientRequest testPatientRequest;
    private PatientResponse testPatientResponse;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Setup test data
        testPatientRequest = createTestPatientRequest();
        testPatientResponse = createTestPatientResponse();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpass", roles = "USER")
    void getAllPatients_ShouldReturnPatientsList() throws Exception {
        // Given
        when(patientService.getAllPatients()).thenReturn(List.of(testPatientResponse));

        // When/Then
        mockMvc.perform(get("/api/patients")
                        .with(httpBasic("testuser", "testpass")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpass", roles = "USER")
    void createPatient_ShouldReturnCreatedPatient() throws Exception {
        // Given
        when(patientService.createPatient(any())).thenReturn(testPatientResponse);

        // When/Then
        mockMvc.perform(post("/api/patients")
                        .with(httpBasic("testuser", "testpass"))
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(testPatientRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.gender").value("M"));
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpass", roles = "USER")
    void getPatient_ShouldReturnPatient() throws Exception {
        // Given
        when(patientService.getPatient(1L)).thenReturn(testPatientResponse);

        // When/Then
        mockMvc.perform(get("/api/patients/1")
                        .with(httpBasic("testuser", "testpass")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpass", roles = "USER")
    void getPatient_WithInvalidId_ShouldReturnNotFound() throws Exception {
        // Given
        when(patientService.getPatient(999L))
                .thenThrow(new PatientNotFoundException("Patient not found with ID: 999"));

        // When/Then
        mockMvc.perform(get("/api/patients/999")
                        .with(httpBasic("testuser", "testpass")))
                .andExpect(status().isNotFound());
    }

    private PatientRequest createTestPatientRequest() {
        PatientRequest request = new PatientRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setDateOfBirth(new Date(90, 0, 1)); // 1990-01-01
        request.setGender(SexEnum.M);
        request.setAddress("123 Test St");
        request.setPhone("123-456-7890");
        return request;
    }

    private PatientResponse createTestPatientResponse() {
        PatientResponse response = new PatientResponse();
        response.setId(1L);
        response.setFirstName("John");
        response.setLastName("Doe");
        response.setDateOfBirth(new Date(90, 0, 1));
        response.setGender(SexEnum.M);
        response.setAddress("123 Test St");
        response.setPhone("123-456-7890");
        return response;
    }
}
