package org.medilabo.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medilabo.model.Patient;
import org.medilabo.model.SexEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    void shouldSavePatient() {
        // Given
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setDateOfBirth(new Date(1990, 1, 1));
        patient.setGender(SexEnum.M);

        // When
        Patient savedPatient = patientRepository.save(patient);

        // Then
        assertNotNull(savedPatient.getId());
        assertEquals("John", savedPatient.getFirstName());
    }

    @Test
    void shouldFindByFirstNameAndLastName() {
        // Given
        Patient patient = new Patient();
        patient.setFirstName("Jane");
        patient.setLastName("Smith");
        patient.setDateOfBirth(new Date(1990, 1, 1));
        patient.setGender(SexEnum.F);
        patientRepository.save(patient);

        // When
        Optional<Patient> found = patientRepository.findByFirstNameAndLastName("Jane", "Smith");

        // Then
        assertTrue(found.isPresent());
        assertEquals("Jane", found.get().getFirstName());
        assertEquals("Smith", found.get().getLastName());
    }
}
