package org.medilabo.riskassessmentservice.client;

import lombok.extern.slf4j.Slf4j;
import org.medilabo.riskassessmentservice.configuration.FeignClientConfig;
import org.medilabo.riskassessmentservice.dto.NoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;


@FeignClient(name = "noteClient", url = "${note.service.url}", configuration = FeignClientConfig.class)
public interface NoteClient {
    @GetMapping("/api/notes/patient/{patientId}")

    List<NoteDTO> getPatientNotes(@PathVariable("patientId") Long patientId);
}
