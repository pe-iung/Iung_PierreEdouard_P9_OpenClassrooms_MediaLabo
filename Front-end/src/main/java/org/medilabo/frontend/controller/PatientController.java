package org.medilabo.frontend.controller;

import lombok.extern.slf4j.Slf4j;
import org.medilabo.frontend.backend.PatientServiceImpl;
import org.medilabo.frontend.backend.RiskAssessmentServiceImpl;
import org.medilabo.frontend.dto.PatientDTO;
import org.medilabo.frontend.dto.RiskAssessmentDTO;
import org.medilabo.frontend.exceptions.PatientNotFoundException;
import org.medilabo.frontend.exceptions.RiskAssessmentException;
import org.medilabo.frontend.exceptions.UIException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientServiceImpl patientServiceImpl;
    private final RiskAssessmentServiceImpl riskAssessmentServiceImpl;

    public PatientController(PatientServiceImpl patientServiceImpl, RiskAssessmentServiceImpl riskAssessmentServiceImpl) {
        this.patientServiceImpl = patientServiceImpl;
        this.riskAssessmentServiceImpl = riskAssessmentServiceImpl;
    }

    @GetMapping
    public String listPatients(Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("patients", patientServiceImpl.getAllPatients());
            return "patients/list";
        } catch (UIException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }
    }


    @GetMapping("/new")
    public String newPatientForm(Model model) {
        model.addAttribute("patient", new PatientDTO());
        return "patients/form";
    }

    @PostMapping
    public String createPatient(@ModelAttribute PatientDTO patient) {
        patientServiceImpl.createPatient(patient);
        return "redirect:/patients";
    }

    @GetMapping("/{id}/edit")
    public String editPatientForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            log.info("Fetching patient for editing, id: {}", id);
            PatientDTO patient = patientServiceImpl.getPatient(id);
            model.addAttribute("patient", patient);
            return "patients/form";
        } catch (PatientNotFoundException e) {
            log.warn("Patient not found {}: {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patients";
        } catch (Exception e) {
            log.error("Unexpected error while fetching patient {}", id, e);
            redirectAttributes.addFlashAttribute("error", "An unexpected system error occurred");
            return "redirect:/patients";
        }
    }

    @PostMapping("/{id}")
    public String updatePatient(@PathVariable Long id, @ModelAttribute PatientDTO patient) {
        patientServiceImpl.updatePatient(id, patient);
        return "redirect:/patients";
    }

    @GetMapping("/{id}/delete")
    public String deletePatient(@PathVariable Long id) {
        patientServiceImpl.deletePatient(id);
        return "redirect:/patients";
    }
    @GetMapping("/{id}/risk")
    public String showRiskAssessment(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            RiskAssessmentDTO risk = riskAssessmentServiceImpl.assessPatient(id);
            PatientDTO patient = patientServiceImpl.getPatient(id);
            model.addAttribute("risk", risk);
            model.addAttribute("patient", patient);
            return "patients/risk";
        } catch (RiskAssessmentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patients/" + id;
        } catch (Exception e) {
            log.error("Unexpected error assessing risk for patient {}", id, e);
            redirectAttributes.addFlashAttribute("error", "An unexpected error occurred while assessing risk");
            return "redirect:/patients/" + id;
        }
    }
}