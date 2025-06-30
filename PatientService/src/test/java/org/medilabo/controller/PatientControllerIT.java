package org.medilabo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.medilabo.dto.PatientRequest;
import org.medilabo.dto.PatientResponse;
import org.medilabo.model.SexEnum;
import org.medilabo.service.PatientService;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PatientService patientService;

    private PatientRequest testPatientRequest;
    private PatientResponse testPatientResponse;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        testPatientRequest = new PatientRequest();
        testPatientRequest.setFirstName("John");
        testPatientRequest.setLastName("Doe");
        testPatientRequest.setDateOfBirth(new Date(1990,1,1));
        testPatientRequest.setGender(SexEnum.M);

        testPatientResponse = new PatientResponse();
        testPatientResponse.setFirstName("John");
        testPatientResponse.setLastName("Doe");
        testPatientResponse.setDateOfBirth(new Date(1990,1,1));
        testPatientResponse.setGender(SexEnum.M);

        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void getAllPatients_ShouldReturnPatientsList() throws Exception {
        List<PatientResponse> patients = List.of(testPatientResponse);
        when(patientService.getAllPatients()).thenReturn(patients);

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void createPatient_ShouldReturnCreatedPatient() throws Exception {
        when(patientService.createPatient(any())).thenReturn(testPatientResponse);

        mockMvc.perform(post("/api/patients")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(testPatientRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void updatePatient_ShouldReturnUpdatedPatient() throws Exception {
        when(patientService.updatePatient(eq(1L), any())).thenReturn(testPatientResponse);

        mockMvc.perform(put("/api/patients/1")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(testPatientRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void deletePatient_ShouldReturnSuccess() throws Exception {
        doNothing().when(patientService).deletePatient(1L);

        mockMvc.perform(delete("/api/patients/1"))
                .andExpect(status().isOk());
    }
}
