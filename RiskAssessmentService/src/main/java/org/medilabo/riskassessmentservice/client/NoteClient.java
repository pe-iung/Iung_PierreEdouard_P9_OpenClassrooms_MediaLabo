package org.medilabo.riskassessmentservice.client;

import lombok.extern.slf4j.Slf4j;
import org.medilabo.riskassessmentservice.configuration.FeignClientConfig;
import org.medilabo.riskassessmentservice.dto.NoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

/**
 * Feign client for accessing the Note service.
 * Retrieves patient medical notes for risk assessment.
 */
@FeignClient(name = "noteClient", url = "${note.service.url}", configuration = FeignClientConfig.class)
public interface NoteClient {
    /**
     * Retrieves all medical notes for a specific patient.
     * @param patientId The patient's unique identifier
     * @return List of medical notes
     */
    @GetMapping("/api/notes/patient/{patientId}")
    List<NoteDTO> getPatientNotes(@PathVariable("patientId") Long patientId);
}
