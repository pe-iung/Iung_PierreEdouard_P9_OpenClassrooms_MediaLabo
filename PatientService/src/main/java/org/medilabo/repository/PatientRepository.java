package org.medilabo.repository;

import org.medilabo.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, String> {
    Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);
}
