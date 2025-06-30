package org.medilabo.riskassessmentservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medilabo.riskassessmentservice.client.NoteClient;
import org.medilabo.riskassessmentservice.client.PatientClient;
import org.medilabo.riskassessmentservice.dto.NoteDTO;
import org.medilabo.riskassessmentservice.dto.PatientDTO;
import org.medilabo.riskassessmentservice.exceptions.RiskNotFoundException;
import org.medilabo.riskassessmentservice.model.RiskAssessment;
import org.medilabo.riskassessmentservice.model.RiskLevel;
import org.medilabo.riskassessmentservice.service.RiskAssessmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

@WebMvcTest(RiskController.class)
@Import({RiskAssessmentServiceImpl.class})
class RiskControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RiskAssessmentServiceImpl riskService;

    @MockBean
    private PatientClient patientClient;

    @MockBean
    private NoteClient noteClient;

    private PatientDTO testPatient1;
    private PatientDTO testPatient2;
    private PatientDTO testPatient3;
    private PatientDTO testPatient4;

    private List<NoteDTO> testNotes1;
    private List<NoteDTO> testNotes2;
    private List<NoteDTO> testNotes3;
    private List<NoteDTO> testNotes4;
    private RiskAssessment testRiskAssessment;

    @BeforeEach
    void setUp() {
        // Setup test patient
        testPatient1 = createPatient1();
        testPatient2 = createPatient2();
        testPatient3 = createPatient3();
        testPatient4 = createPatient4();

        // Setup test notes
        testNotes1 = createPatient1Notes();
        testNotes2 = createPatient2Notes();
        testNotes3 = createPatient3Notes();
        testNotes4 = createPatient4Notes();

        // Setup test risk assessment
        testRiskAssessment = new RiskAssessment(
                1L,
                "Test TestNone",
                30,
                0,
                RiskLevel.NONE
        );
    }

    @Test
    void assessPatient_ShouldReturnRiskAssessment() throws Exception {
        // Given
        when(patientClient.getPatient(1L)).thenReturn(createPatient1());
        when(noteClient.getPatientNotes(1L)).thenReturn(createPatient1Notes());

        // When/Then
        mockMvc.perform(get("/api/risk/assess/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientId").value(1))
                .andExpect(jsonPath("$.patientName").value("Test TestNone"))
                .andExpect(jsonPath("$.riskLevel").value("NONE"));
    }

    @Test
    void assessPatient_WithInvalidId_ShouldReturnNotFound() throws Exception {

        mockMvc.perform(get("/api/risk/assess/999"))
                .andExpect(status().isNotFound());
    }


    @Test
    void shouldCalculateCorrectRiskLevelsForAllTestPatients() throws Exception {

        when(patientClient.getPatient(1L)).thenReturn(createPatient1());
        when(noteClient.getPatientNotes(1L)).thenReturn(createPatient1Notes());


        MvcResult result = mockMvc.perform(get("/api/risk/assess/1"))
                .andDo(print()) // This will print the response
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response content: " + responseContent);


        mockMvc.perform(get("/api/risk/assess/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientId").value(1L))
                .andExpect(jsonPath("$.patientName").value("Test TestNone"))
                .andExpect(jsonPath("$.age").exists())
                .andExpect(jsonPath("$.riskLevel").value("NONE"))
                .andExpect(jsonPath("$.triggerCount").value(1));

        // Repeat for other test cases...
    }

    @Test
    void shouldCalculateRiskLevel_None_ForPatient1() throws Exception {
        // Given
        when(patientClient.getPatient(1L)).thenReturn(createPatient1());
        when(noteClient.getPatientNotes(1L)).thenReturn(createPatient1Notes());

        // When/Then
        mockMvc.perform(get("/api/risk/assess/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientId").value(1))
                .andExpect(jsonPath("$.patientName").value("Test TestNone"))
                .andExpect(jsonPath("$.riskLevel").value("NONE"))
                .andExpect(jsonPath("$.triggerCount").value(1));
    }

    @Test
    void shouldCalculateRiskLevel_Borderline_ForPatient2() throws Exception {
        // Given
        when(patientClient.getPatient(2L)).thenReturn(createPatient2());
        when(noteClient.getPatientNotes(2L)).thenReturn(createPatient2Notes());

        // When/Then
        mockMvc.perform(get("/api/risk/assess/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientId").value(2))
                .andExpect(jsonPath("$.patientName").value("Test TestBorderline"))
                .andExpect(jsonPath("$.riskLevel").value("BORDERLINE"))
                .andExpect(jsonPath("$.triggerCount").value(2)); // 'anormal' appears twice
    }

    @Test
    void shouldCalculateRiskLevel_InDanger_ForPatient3() throws Exception {
        // Given
        when(patientClient.getPatient(3L)).thenReturn(createPatient3());
        when(noteClient.getPatientNotes(3L)).thenReturn(createPatient3Notes());

        // When/Then
        mockMvc.perform(get("/api/risk/assess/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientId").value(3))
                .andExpect(jsonPath("$.patientName").value("Test TestInDanger"))
                .andExpect(jsonPath("$.riskLevel").value("IN_DANGER"))
                .andExpect(jsonPath("$.triggerCount").value(3)); // 'fumeur', 'anormal', 'cholestérol'
    }

    @Test
    void shouldCalculateRiskLevel_EarlyOnset_ForPatient4() throws Exception {
        // Given
        when(patientClient.getPatient(4L)).thenReturn(createPatient4());
        when(noteClient.getPatientNotes(4L)).thenReturn(createPatient4Notes());

        // When/Then
        mockMvc.perform(get("/api/risk/assess/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientId").value(4))
                .andExpect(jsonPath("$.patientName").value("Test TestEarlyOnset"))
                .andExpect(jsonPath("$.riskLevel").value("EARLY_ONSET"))
                .andExpect(jsonPath("$.triggerCount").value(7));
    }



    private PatientDTO createPatient1(){
        Calendar cal = Calendar.getInstance();
        cal.set(1966, Calendar.DECEMBER, 31);
        return new PatientDTO(1L,
                "Test",
                "TestNone",
                cal.getTime(),
                "F",
                "1 Brookside St",
                "100-222-3333");

    }
    private PatientDTO createPatient2() {
        Calendar cal = Calendar.getInstance();
        cal.set(1945, Calendar.JUNE, 24);
        return new PatientDTO(2L,
                "Test",
                "TestBorderline",
                cal.getTime(),
                "M",
                "2 High St",
                "200-333-4444");
    }

    private PatientDTO createPatient3() {
        Calendar cal = Calendar.getInstance();
        cal.set(2004, Calendar.JUNE, 18);
        return new PatientDTO(3L,
                "Test",
                "TestInDanger",
                cal.getTime(),
                "M",
                "3 Club Road",
                "300-444-5555");
    }

    private PatientDTO createPatient4() {
        Calendar cal = Calendar.getInstance();
        cal.set(2002, Calendar.JUNE, 28);
        return new PatientDTO(4L,
                "Test",
                "TestEarlyOnset",
                cal.getTime(),
                "F",
                "4 Valley Dr",
                "400-555-6666");
    }

    private List<NoteDTO> createPatient1Notes() {
        Instant now = Instant.now();
        return List.of(
                new NoteDTO("1", 1L,
                        "Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé",
                        now, now)
        );
    }

    private List<NoteDTO> createPatient2Notes() {
        Instant now = Instant.now();
        return List.of(
                new NoteDTO("2", 2L,
                        "Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement",
                        now, now),
                new NoteDTO("3", 2L,
                        "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale",
                        now, now)
        );
    }

    private List<NoteDTO> createPatient3Notes() {
        Instant now = Instant.now();
        return List.of(
                new NoteDTO("4", 3L,
                        "Le patient déclare qu'il fume depuis peu",
                        now, now),
                new NoteDTO("5", 3L,
                        "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d'apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé",
                        now, now)
        );
    }

    private List<NoteDTO> createPatient4Notes() {
        Instant now = Instant.now();
        return List.of(
                new NoteDTO("6", 4L,
                        "Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d'être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments",
                        now, now),
                new NoteDTO("7", 4L,
                        "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps",
                        now, now),
                new NoteDTO("8", 4L,
                        "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé",
                        now, now),
                new NoteDTO("9", 4L,
                        "Taille, Poids, Cholestérol, Vertige et Réaction",
                        now, now)
        );
    }


}
