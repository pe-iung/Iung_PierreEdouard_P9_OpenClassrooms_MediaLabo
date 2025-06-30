package org.medilabo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medilabo.dto.PatientRequest;
import org.medilabo.dto.PatientResponse;
import org.medilabo.exceptions.PatientNotFoundException;
import org.medilabo.model.Patient;
import org.medilabo.model.SexEnum;
import org.medilabo.repository.PatientRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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
    private PatientResponse testPatientResponse;

    @BeforeEach
    void setUp() {
        testPatient = createTestPatient();
        testPatientRequest = createTestPatientRequest();
        testPatientResponse = createTestPatientResponse();
    }

    @Test
    void getAllPatients_ShouldReturnPatientsList() {
        // Given
        when(patientRepository.findAll()).thenReturn(List.of(testPatient));
        when(modelMapper.map(testPatient, PatientResponse.class))
                .thenReturn(testPatientResponse);

        // When
        List<PatientResponse> result = patientService.getAllPatients();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testPatientResponse.getFirstName(), result.get(0).getFirstName());
        verify(patientRepository).findAll();
    }

    @Test
    void getPatient_ShouldReturnPatient() {
        // Given
        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
        when(modelMapper.map(testPatient, PatientResponse.class))
                .thenReturn(testPatientResponse);

        // When
        PatientResponse result = patientService.getPatient(1L);

        // Then
        assertNotNull(result);
        assertEquals(testPatientResponse.getFirstName(), result.getFirstName());
        verify(patientRepository).findById(1L);
    }

    @Test
    void getPatient_WithInvalidId_ShouldThrowException() {
        // Given
        when(patientRepository.findById(999L)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(PatientNotFoundException.class, () ->
                patientService.getPatient(999L)
        );
    }

    @Test
    void createPatient_ShouldReturnCreatedPatient() {
        // Given
        when(modelMapper.map(testPatientRequest, Patient.class)).thenReturn(testPatient);
        when(patientRepository.save(testPatient)).thenReturn(testPatient);
        when(modelMapper.map(testPatient, PatientResponse.class))
                .thenReturn(testPatientResponse);

        // When
        PatientResponse result = patientService.createPatient(testPatientRequest);

        // Then
        assertNotNull(result);
        assertEquals(testPatientResponse.getFirstName(), result.getFirstName());
        verify(patientRepository).save(testPatient);
    }

    private Patient createTestPatient() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setDateOfBirth(new Date(90, 0, 1));
        patient.setGender(SexEnum.M);
        patient.setAddress("123 Test St");
        patient.setPhone("123-456-7890");
        return patient;
    }

    private PatientRequest createTestPatientRequest() {
        PatientRequest request = new PatientRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setDateOfBirth(new Date(90, 0, 1));
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
