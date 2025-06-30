package org.medilabo.riskassessmentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.medilabo.riskassessmentservice.client.NoteClient;
import org.medilabo.riskassessmentservice.client.PatientClient;
import org.medilabo.riskassessmentservice.dto.NoteDTO;
import org.medilabo.riskassessmentservice.dto.PatientDTO;
import org.medilabo.riskassessmentservice.exceptions.InvalidPatientDataException;
import org.medilabo.riskassessmentservice.exceptions.RiskNotFoundException;
import org.medilabo.riskassessmentservice.model.RiskAssessment;
import org.medilabo.riskassessmentservice.model.RiskLevel;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiskAssessmentServiceImpl implements RiskAssessmentService{
    private final PatientClient patientClient;
    private final NoteClient noteClient;

    private static final Set<String> TRIGGER_TERMS = Set.of(

            // the triggers are alphabetically ordered
            // new triggers should be added respecting the order and lowercase
            "anormal",
            "anticorps",
            "cholestérol",
            //"fumer",
            "(?:fum(?:e(?:ur|use)|er))",
//            "fumeur",
//            "fumeuse",
            "hémoglobine a1c",
            "microalbumine",
            "poids",
            "réaction",
            "rechute",
            "taille",
            "vertiges"
    );
    @Override
    public RiskAssessment assessPatientRisk(Long patientId) {
        try {
            PatientDTO patient = patientClient.getPatient(patientId);
            List<NoteDTO> notes = noteClient.getPatientNotes(patientId);

            if (patient.getDateOfBirth() == null) {
                throw new InvalidPatientDataException(patientId, "date of birth");
            }

            int age = calculateAge(patient.getId(), patient.getDateOfBirth());
            long triggerCount = countTriggers(notes);
            RiskLevel riskLevel = calculateRiskLevel(patient, triggerCount);

            return new RiskAssessment(
                    patient.getId(),
                    patient.getFirstName() + " " + patient.getLastName(),
                    age,
                    triggerCount,
                    riskLevel
            );
        } catch (InvalidPatientDataException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error assessing patient {}: {}", patientId, e.getMessage());
            throw new RiskNotFoundException(patientId);
        }
    }
//    @Override
//    public long countTriggers(List<NoteDTO> notes) {
//        if (notes == null || notes.isEmpty()) {
//            return 0;
//        }
//
//        String allNotes = notes.stream()
//                .map(note -> note.getContent().toLowerCase())
//                .collect(Collectors.joining(" "));
//
//        return TRIGGER_TERMS.stream()
//                .filter(allNotes::contains)
//                .count();
//    }

    @Override
    public long countTriggers(List<NoteDTO> notes) {
        if (notes == null || notes.isEmpty()) {
            return 0;
        }

        String allNotes = notes.stream()
                .map(note -> note.getContent().toLowerCase())
                .collect(Collectors.joining(" "));

        return TRIGGER_TERMS.stream()
                .filter(term -> Pattern.compile(term).matcher(allNotes).find())
                .count();
    }

    /**
    Les règles pour déterminer les niveaux de risque sont les suivantes :

        //// NONE ////
        - aucun risque (None) : Le dossier du patient ne contient aucune note du médecin
        contenant les déclencheurs (terminologie) ;

        //// BORDERLINE ////
        - risque limité (Borderline) : Le dossier du patient contient entre deux et cinq
            déclencheurs et le patient est âgé de plus de 30 ans ;

        //// IN DANGER ////
        - danger (In Danger) : Dépend de l'âge et du sexe du patient. Si le patient est un homme
            de moins de 30 ans, alors trois termes déclencheurs doivent être présents.
            Si le patient est une femme et a moins de 30 ans, il faudra quatre termes déclencheurs.
            Si le patient a plus de 30 ans, alors il en faudra six ou sept ;

        //// EARLY ONSET ////
        - apparition précoce (Early onset) : Encore une fois, cela dépend de l'âge et du sexe.
            Si le patient est un homme de moins de 30 ans, alors au moins cinq termes déclencheurs sont nécessaires.
            Si le patient est une femme et a moins de 30 ans, il faudra au moins sept termes déclencheurs.
            Si le patient a plus de 30 ans, alors il en faudra huit ou plus.

     */
    @Override
    public RiskLevel calculateRiskLevel(PatientDTO patient, long triggerCount) {
        if (triggerCount == 0) {
            return RiskLevel.NONE;
        }

        int age = calculateAge(patient.getId(), patient.getDateOfBirth());
        boolean isMale = "M".equals(patient.getGender());

        if (age > 30) {
            if (triggerCount >= 8) return RiskLevel.EARLY_ONSET;
            if (triggerCount >= 6) return RiskLevel.IN_DANGER;
            if (triggerCount >= 2) return RiskLevel.BORDERLINE;
        } else if (isMale) {
            if (triggerCount >= 5) return RiskLevel.EARLY_ONSET;
            if (triggerCount >= 3) return RiskLevel.IN_DANGER;
        } else {
            if (triggerCount >= 7) return RiskLevel.EARLY_ONSET;
            if (triggerCount >= 4) return RiskLevel.IN_DANGER;

        }

        return RiskLevel.NONE;
    }

    private int calculateAge(long patientId, Date birthDate) {
        if (birthDate == null) {
            throw new InvalidPatientDataException(patientId, "date of birth");
        }
        LocalDate birthLocalDate = birthDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return Period.between(birthLocalDate, LocalDate.now()).getYears();
    }
}
