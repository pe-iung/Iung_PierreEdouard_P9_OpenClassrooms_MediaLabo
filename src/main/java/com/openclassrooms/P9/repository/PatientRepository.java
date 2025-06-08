package com.openclassrooms.P9.repository;

import com.openclassrooms.P9.Model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface PatientRepository extends MongoRepository<Patient, String> {
    Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);
}
