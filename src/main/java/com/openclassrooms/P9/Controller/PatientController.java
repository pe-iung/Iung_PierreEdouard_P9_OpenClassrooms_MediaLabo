package com.openclassrooms.P9.Controller;

import com.openclassrooms.P9.DTO.PatientRequest;
import com.openclassrooms.P9.Service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public PatientRequest getPatient(@PathVariable String id) {
        return patientService.getPatient(id);
    }

    @PostMapping
    public PatientRequest createPatient(@Valid @RequestBody PatientRequest PatientRequest) {
        return patientService.createPatient(PatientRequest);
    }

    @PutMapping("/{id}")
    public PatientRequest updatePatient(@PathVariable String id,
                                    @Valid @RequestBody PatientRequest PatientRequest) {
        return patientService.updatePatient(id, PatientRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok().build();
    }
}