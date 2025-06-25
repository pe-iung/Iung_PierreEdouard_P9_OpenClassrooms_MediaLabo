package org.medilabo.frontend.controller;

import org.medilabo.frontend.backend.PatientServiceImpl;
import org.medilabo.frontend.backend.RiskAssessmentServiceImpl;
import org.medilabo.frontend.dto.PatientDTO;
import org.medilabo.frontend.dto.RiskAssessmentDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String listPatients(Model model) {
        model.addAttribute("patients", patientServiceImpl.getAllPatients());
        return "patients/list";
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
    public String editPatientForm(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientServiceImpl.getPatient(id));
        return "patients/form";
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
    public String showRiskAssessment(@PathVariable Long id, Model model) {
        RiskAssessmentDTO risk = riskAssessmentServiceImpl.assessPatient(id);
        PatientDTO patient = patientServiceImpl.getPatient(id);
        model.addAttribute("risk", risk);
        model.addAttribute("patient", patient);
        return "patients/risk";
    }
}