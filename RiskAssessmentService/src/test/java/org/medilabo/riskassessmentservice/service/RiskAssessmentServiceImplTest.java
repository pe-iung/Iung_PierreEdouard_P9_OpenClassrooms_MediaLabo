package org.medilabo.riskassessmentservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medilabo.riskassessmentservice.client.NoteClient;
import org.medilabo.riskassessmentservice.client.PatientClient;
import org.medilabo.riskassessmentservice.dto.NoteDTO;
import org.medilabo.riskassessmentservice.dto.PatientDTO;
import org.medilabo.riskassessmentservice.exceptions.InvalidPatientDataException;
import org.medilabo.riskassessmentservice.model.RiskAssessment;
import org.medilabo.riskassessmentservice.model.RiskLevel;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RiskAssessmentServiceImplTest {

    @Mock
    private PatientClient patientClient;

    @Mock
    private NoteClient noteClient;

    @InjectMocks
    private RiskAssessmentServiceImpl riskService;

    private PatientDTO testPatient;
    private List<NoteDTO> testNotes;

    private long incremental_id = 1L;
    @BeforeEach
    void setUp() {

        Calendar cal = Calendar.getInstance();

        testPatient = new PatientDTO();
        testPatient.setId(1L);
        testPatient.setFirstName("Test");
        testPatient.setLastName("TestNone");
        cal.set(1966, Calendar.DECEMBER, 31);
        testPatient.setDateOfBirth(cal.getTime());
        testPatient.setGender("F");

        // Setup test notes
        NoteDTO note = new NoteDTO();
        note.setId("1");
        note.setPatientId(1L);
        note.setContent("Patient states that they feel very well. Weight at or below recommended level");
        note.setCreatedAt(Instant.now());
        testNotes = List.of(note);
    }

    @Test
    void assessPatientRisk_ShouldReturnNoneForPatientWithNoTriggers() {
        // Given
        when(patientClient.getPatient(anyLong())).thenReturn(testPatient);
        when(noteClient.getPatientNotes(anyLong())).thenReturn(testNotes);

        // When
        RiskAssessment result = riskService.assessPatientRisk(1L);

        // Then
        assertNotNull(result);
        assertEquals(RiskLevel.NONE, result.getRiskLevel());
        assertEquals(0, result.getTriggerCount());
    }

    @Test
    void assessPatientRisk_ShouldCountTriggersCorrectly() {
        // Given
        NoteDTO noteWithTriggers = new NoteDTO();
        noteWithTriggers.setContent("Patient présente comportement anormal. Cholestérol levels high. Patient est fumeur.");
        List<NoteDTO> notesWithTriggers = List.of(noteWithTriggers);

        // When
        long triggerCount = riskService.countTriggers(notesWithTriggers);

        // Then
        assertEquals(3, triggerCount); // Should count "anormal", "cholestérol", and "fumeur"
    }

    @Test
    void calculateRiskLevel_ShouldReturnCorrectLevel() {
        // Test cases for different age groups and trigger counts
        PatientDTO over80Patient = createTestPatient(82, "F");
        PatientDTO over60Patient = createTestPatient(61, "M");
        PatientDTO over30Patient = createTestPatient(40, "M");
        PatientDTO under30MalePatient = createTestPatient(25, "M");
        PatientDTO under30FemalePatient = createTestPatient(25, "F");

        // Over 30 risk levels
        assertEquals(RiskLevel.EARLY_ONSET, riskService.calculateRiskLevel(over80Patient, 9));
        assertEquals(RiskLevel.IN_DANGER, riskService.calculateRiskLevel(over80Patient, 7));
        assertEquals(RiskLevel.EARLY_ONSET, riskService.calculateRiskLevel(over60Patient, 9));
        assertEquals(RiskLevel.IN_DANGER, riskService.calculateRiskLevel(over60Patient, 7));
        assertEquals(RiskLevel.BORDERLINE, riskService.calculateRiskLevel(over80Patient, 2));
        assertEquals(RiskLevel.BORDERLINE, riskService.calculateRiskLevel(over60Patient, 3));

        assertEquals(RiskLevel.NONE, riskService.calculateRiskLevel(over30Patient, 1));
        assertEquals(RiskLevel.BORDERLINE, riskService.calculateRiskLevel(over30Patient, 2));
        assertEquals(RiskLevel.IN_DANGER, riskService.calculateRiskLevel(over30Patient, 6));
        assertEquals(RiskLevel.EARLY_ONSET, riskService.calculateRiskLevel(over30Patient, 8));

        // Under 30 male risk levels
        assertEquals(RiskLevel.NONE, riskService.calculateRiskLevel(under30MalePatient, 2));
        assertEquals(RiskLevel.IN_DANGER, riskService.calculateRiskLevel(under30MalePatient, 3));
        assertEquals(RiskLevel.EARLY_ONSET, riskService.calculateRiskLevel(under30MalePatient, 5));

        // Under 30 female risk levels
        assertEquals(RiskLevel.NONE, riskService.calculateRiskLevel(under30FemalePatient, 4));
        assertEquals(RiskLevel.IN_DANGER, riskService.calculateRiskLevel(under30FemalePatient, 7));
        assertEquals(RiskLevel.EARLY_ONSET, riskService.calculateRiskLevel(under30FemalePatient, 5));
        assertEquals(RiskLevel.EARLY_ONSET, riskService.calculateRiskLevel(under30FemalePatient, 6));
        assertEquals(RiskLevel.IN_DANGER, riskService.calculateRiskLevel(under30FemalePatient, 8));
    }

    @Test
    void assessPatientRisk_ShouldThrowExceptionForMissingBirthDate() {
        // Given
        PatientDTO patientWithoutBirthDate = new PatientDTO();
        patientWithoutBirthDate.setId(1L);
        when(patientClient.getPatient(anyLong())).thenReturn(patientWithoutBirthDate);

        // When/Then
        assertThrows(InvalidPatientDataException.class, () ->
                riskService.assessPatientRisk(1L)
        );
    }

    private PatientDTO createTestPatient(int age, String gender) {
        PatientDTO patient = new PatientDTO();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -age);
        patient.setDateOfBirth(cal.getTime());
        patient.setGender(gender);
        patient.setId(incremental_id);
        incremental_id = incremental_id + 1;
        return patient;
    }
}
