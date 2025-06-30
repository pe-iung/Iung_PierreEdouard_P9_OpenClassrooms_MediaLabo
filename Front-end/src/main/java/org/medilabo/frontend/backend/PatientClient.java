package org.medilabo.frontend.backend;

import org.medilabo.frontend.dto.patient.PatientResponse;
import org.medilabo.frontend.dto.patient.UpsertPatientRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "patientClient", url = "${gateway.url}")
public interface PatientClient {

    @GetMapping("/api/patients")
    List<PatientResponse> getAllPatients();

    @GetMapping("/api/patients/{id}")
    PatientResponse getPatient(@PathVariable("id") Long id);

    @PostMapping("/api/patients")
    PatientResponse createPatient(@RequestBody UpsertPatientRequest patient);

    @PutMapping("/api/patients/{id}")
    void updatePatient(@PathVariable("id") Long id, @RequestBody UpsertPatientRequest patient);

    @DeleteMapping("/api/patients/{id}")
    void deletePatient(@PathVariable("id") Long id);
}
