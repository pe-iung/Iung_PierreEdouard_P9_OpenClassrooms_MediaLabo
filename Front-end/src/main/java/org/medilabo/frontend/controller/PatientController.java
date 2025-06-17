package org.medilabo.frontend.controller;

import org.medilabo.frontend.dto.PatientDTO;
import org.medilabo.frontend.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients/list";
    }

    @GetMapping("/new")
    public String newPatientForm(Model model) {
        model.addAttribute("patient", new PatientDTO());
        return "patients/form";
    }

    @PostMapping
    public String createPatient(@ModelAttribute PatientDTO patient) {
        patientService.createPatient(patient);
        return "redirect:/patients";
    }

    @GetMapping("/{id}/edit")
    public String editPatientForm(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientService.getPatient(id));
        return "patients/form";
    }

    @PostMapping("/{id}")
    public String updatePatient(@PathVariable Long id, @ModelAttribute PatientDTO patient) {
        patientService.updatePatient(id, patient);
        return "redirect:/patients";
    }

    @GetMapping("/{id}/delete")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/patients";
    }
}
