package org.medilabo.frontend.backend;

import org.medilabo.frontend.dto.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "patientClient", url = "${gateway.url}")
public interface PatientClient {

    @GetMapping("/api/patients")
    List<PatientDTO> getAllPatients();

    @GetMapping("/api/patients/{id}")
    PatientDTO getPatient(@PathVariable("id") Long id);

    @PostMapping("/api/patients")
    PatientDTO createPatient(@RequestBody PatientDTO patient);

    @PutMapping("/api/patients/{id}")
    void updatePatient(@PathVariable("id") Long id, @RequestBody PatientDTO patient);

    @DeleteMapping("/api/patients/{id}")
    void deletePatient(@PathVariable("id") Long id);
}
