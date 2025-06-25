package org.medilabo.controller;

import org.medilabo.dto.PatientRequest;
import org.medilabo.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public List<PatientRequest> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public PatientRequest getPatient(@PathVariable Long id) {
        return patientService.getPatient(id);
    }

    @PostMapping
    public PatientRequest createPatient(@Valid @RequestBody PatientRequest PatientRequest) {
        return patientService.createPatient(PatientRequest);
    }

    @PostMapping("/batch")
    public List<PatientRequest> createPatients(@RequestBody List<PatientRequest> patients) {
        return patients.stream()
                .map(patientService::createPatient)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public PatientRequest updatePatient(@PathVariable Long id,
                                    @Valid @RequestBody PatientRequest PatientRequest) {
        return patientService.updatePatient(id, PatientRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok().build();
    }
}