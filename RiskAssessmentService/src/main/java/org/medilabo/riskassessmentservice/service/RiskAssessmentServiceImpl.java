package org.medilabo.riskassessmentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.medilabo.riskassessmentservice.client.NoteClient;
import org.medilabo.riskassessmentservice.client.PatientClient;
import org.medilabo.riskassessmentservice.dto.NoteDTO;
import org.medilabo.riskassessmentservice.dto.PatientDTO;
import org.medilabo.riskassessmentservice.model.RiskAssessment;
import org.medilabo.riskassessmentservice.model.RiskLevel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiskAssessmentServiceImpl implements RiskAssessmentService{
    private final PatientClient patientClient;
    private final NoteClient noteClient;

    private static final Set<String> TRIGGER_TERMS = Set.of(
            "hémoglobine A1C", "microalbumine", "taille", "poids",
            "fumeur", "fumeuse", "anormal", "cholestérol", "vertiges",
            "rechute", "réaction", "anticorps"
    );
    @Override
    public RiskAssessment assessPatientRisk(Long patientId) {
        PatientDTO patient = patientClient.getPatient(patientId);
        List<NoteDTO> notes = noteClient.getPatientNotes(patientId);
        log.info("inside RiskAssessment assessing patient {}:", patientId );

        int age = calculateAge(patient.getDateOfBirth());
        int triggerCount = countTriggers(notes);
        RiskLevel riskLevel = calculateRiskLevel(patient, triggerCount);

        return new RiskAssessment(
                patient.getId(),
                patient.getFirstName() + " " + patient.getLastName(),
                age,
                triggerCount,
                riskLevel
        );
    }
    @Override
    public int countTriggers(List<NoteDTO> notes) {
        if (notes == null || notes.isEmpty()) {
            return 0;
        }

        String allNotes = notes.stream()
                .map(note -> note.getContent().toLowerCase())
                .collect(Collectors.joining(" "));

        return (int) TRIGGER_TERMS.stream()
                .filter(allNotes::contains)
                .count();
    }

    @Override
    public RiskLevel calculateRiskLevel(PatientDTO patient, int triggerCount) {
        if (triggerCount == 0) {
            return RiskLevel.NONE;
        }

        int age = calculateAge(patient.getDateOfBirth());
        boolean isMale = "M".equals(patient.getGender());

        if (age > 30) {
            if (triggerCount >= 8) return RiskLevel.EARLY_ONSET;
            if (triggerCount >= 6) return RiskLevel.IN_DANGER;
            if (triggerCount >= 2) return RiskLevel.BORDERLINE;
        } else if (isMale) {
            if (triggerCount >= 5) return RiskLevel.EARLY_ONSET;
            if (triggerCount >= 3) return RiskLevel.IN_DANGER;
        } else {
            if (triggerCount >= 5) return RiskLevel.EARLY_ONSET;
            if (triggerCount >= 7) return RiskLevel.IN_DANGER;
        }

        return RiskLevel.NONE;
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
