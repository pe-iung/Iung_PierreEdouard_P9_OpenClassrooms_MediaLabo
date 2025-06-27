package org.medilabo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medilabo.dto.PatientRequest;
import org.medilabo.exceptions.model.Patient;
import org.medilabo.exceptions.model.SexEnum;
import org.medilabo.repository.PatientRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PatientServiceImpl patientService;

    private Patient testPatient;
    private PatientRequest testPatientRequest;

    @BeforeEach
    void setUp() {
        testPatient = new Patient();
        testPatient.setId(1L);
        testPatient.setFirstName("Test");
        testPatient.setLastName("User");
        testPatient.setDateOfBirth(new Date(1990, 1, 1));
        testPatient.setGender(SexEnum.M);

        testPatientRequest = new PatientRequest(testPatient);

    }

    @Test
    void shouldGetAllPatients() {
        // Given
        List<Patient> patients = List.of(testPatient);
        when(patientRepository.findAll()).thenReturn(patients);
        when(modelMapper.map(any(), eq(PatientRequest.class)))
                .thenReturn(testPatientRequest);

        // When
        List<PatientRequest> result = patientService.getAllPatients();

        // Then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(patientRepository).findAll();
    }

    @Test
    void shouldGetPatientById() {
        // Given
        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
        when(modelMapper.map(testPatient, PatientRequest.class)).thenReturn(testPatientRequest);

        // When
        PatientRequest result = patientService.getPatient( 1L);

        // Then
        assertNotNull(result);
        assertEquals(testPatientRequest.getFirstName(), result.getFirstName());
        verify(patientRepository).findById(1L);
    }


//    @Test
//    void shouldThrowExceptionWhenPatientNotFound() {
//        // Given
//        when(patientRepository.findById(1L)).thenReturn(Optional.empty());
//
//        // When/Then
//        assertThrows(PatientNotFoundException.class, () ->
//                patientService.getPatient("1")
//        );
//    }

    @Test
    void shouldCreatePatient() {
        // Given
        when(modelMapper.map(testPatientRequest, Patient.class)).thenReturn(testPatient);
        when(patientRepository.save(testPatient)).thenReturn(testPatient);
        when(modelMapper.map(testPatient, PatientRequest.class)).thenReturn(testPatientRequest);

        // When
        PatientRequest result = patientService.createPatient(testPatientRequest);

        // Then
        assertNotNull(result);
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    void shouldUpdatePatient() {
        // Given
        when(patientRepository.existsById(1L)).thenReturn(true);
        when(modelMapper.map(testPatientRequest, Patient.class)).thenReturn(testPatient);
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);
        when(modelMapper.map(testPatient, PatientRequest.class)).thenReturn(testPatientRequest);

        // When
        PatientRequest result = patientService.updatePatient(1L, testPatientRequest);

        // Then
        assertNotNull(result);
        verify(patientRepository).save(any(Patient.class));
    }
}
