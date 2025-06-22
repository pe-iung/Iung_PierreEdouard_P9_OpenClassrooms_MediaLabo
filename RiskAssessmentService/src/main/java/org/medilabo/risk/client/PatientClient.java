package org.medilabo.risk.client;

import org.medilabo.risk.dto.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patientClient", url = "${gateway.url}")
public interface PatientClient {
    @GetMapping("/api/patients/{id}")
    PatientDTO getPatient(@PathVariable("id") Long id);
}
