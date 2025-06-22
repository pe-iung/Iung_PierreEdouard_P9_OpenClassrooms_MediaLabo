package org.medilabo.risk.client;

import org.medilabo.risk.dto.NoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "noteClient", url = "${gateway.url}")
public interface NoteClient {
    @GetMapping("/api/notes/patient/{patientId}")
    List<NoteDTO> getPatientNotes(@PathVariable("patientId") Long patientId);
}
