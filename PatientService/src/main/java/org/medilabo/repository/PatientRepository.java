package org.medilabo.repository;

import org.medilabo.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * Repository interface for Patient entity operations.
 * Provides database access methods for patient management.
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {

    void deleteById(Long id);
}
