package org.medilabo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.MediaType;
import org.medilabo.PatientServiceApplication;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medilabo.dto.PatientRequest;
import org.medilabo.model.Patient;
import org.medilabo.model.SexEnum;
import org.medilabo.service.*;
import org.mockito.internal.matchers.Equals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = PatientServiceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PatientControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

    }

    @Autowired
    private PatientService patientService;


    private PatientRequest testPatientRequest;
    private Patient testPatient;

    @BeforeEach
    void setUp() {
        patientService.deleteAll();
    }

    @AfterEach
    void cleanUp() {
        patientService.deleteAll();
    }

    @Test
    void shouldGetAllPatients() throws Exception {
        // Given
        createJohnDo();
        createJohnDo();


        // When/Then
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldGetPatientById() throws Exception {
        // Given
        createJohnDo();

        // When/Then
        MvcResult result = mockMvc.perform(get("/api/patients/1"))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
        assertThat(content).contains("John");

    }

//    @Test
//    void shouldCreatePatient() throws Exception {
//        // Given
//
//        // When/Then
//        MvcResult result = mockMvc.perform(post("/api/patients")
//                        .content(objectMapper.writeValueAsString(testPatientRequest)))
//                .andExpect(status().isOk())
//                .andReturn();
//        String content = result.getResponse().getContentAsString();
//        System.out.println(content);
//        content.contains("tchobiloute");
//    }

    @Test
    void shouldUpdatePatient() throws Exception {
        // Given
        createJohnDo();
        testPatient = new Patient();
        testPatient.setId(1L);
        testPatient.setFirstName("JohnUpdated");
        testPatient.setLastName("Doe");
        testPatient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        testPatient.setGender(SexEnum.M);

        testPatientRequest = new PatientRequest(testPatient);


        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(testPatientRequest);
        //String requestJson=testPatientRequest.toString();

        // When/Then
        MvcResult result1 = mockMvc.perform(put("/api/patients/1")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON_UTF_8))
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        PatientRequest patientUpdated = patientService.getPatient(1L);
        assertThat(patientUpdated.getFirstName()).contains("Updated");
                }

    @Test
    void shouldDeletePatient() throws Exception {
        // When/Then
        mockMvc.perform(delete("/api/patients/1"))
                .andExpect(status().isOk());

        verify(patientService).deletePatient(1L);
    }

    void createJohnDo(){
        testPatient = new Patient();
        testPatient.setFirstName("John");
        testPatient.setLastName("Doe");
        testPatient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        testPatient.setGender(SexEnum.M);

        testPatientRequest = new PatientRequest(testPatient);
        patientService.createPatient(testPatientRequest);
        System.out.println("john doe created with id = " + testPatient.getId());
    }
}
