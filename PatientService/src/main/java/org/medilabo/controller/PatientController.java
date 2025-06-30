package org.medilabo.controller;

import lombok.extern.slf4j.Slf4j;
import org.medilabo.dto.PatientRequest;
import org.medilabo.dto.PatientResponse;
import org.medilabo.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        return ResponseEntity.ok().body(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatient(@PathVariable Long id) {
        log.info("inside patient Service, patient date of birth = {}", patientService.getPatient(id).getDateOfBirth());
        return ResponseEntity.ok().body(patientService.getPatient(id));
    }

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@Valid @RequestBody PatientRequest PatientRequest) {
        return ResponseEntity.ok().body(patientService.createPatient(PatientRequest));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PatientResponse>> createPatients(@RequestBody List<PatientRequest> patients) {
        return ResponseEntity.ok().body(patients.stream()
                .map(patientService::createPatient)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long id,
                                    @Valid @RequestBody PatientRequest PatientRequest) {
        return ResponseEntity.ok().body(patientService.updatePatient(id, PatientRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok().build();
    }
}