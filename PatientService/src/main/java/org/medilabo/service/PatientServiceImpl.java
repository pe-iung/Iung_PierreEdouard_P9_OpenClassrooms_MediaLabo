package org.medilabo.service;

import lombok.NoArgsConstructor;
import org.medilabo.dto.PatientRequest;

import org.medilabo.exceptions.PatientNotFoundException;
import org.medilabo.model.Patient;
import org.medilabo.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService{
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PatientRequest> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patient -> modelMapper.map(patient, PatientRequest.class))
                .collect(Collectors.toList());
    }

    @Override
    public PatientRequest getPatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
        return modelMapper.map(patient, PatientRequest.class);
    }

    @Override
    public PatientRequest createPatient(PatientRequest PatientRequest) {
        Patient patient = modelMapper.map(PatientRequest, Patient.class);
        Patient savedPatient = patientRepository.save(patient);
        return modelMapper.map(savedPatient, PatientRequest.class);
    }

    @Override
    public PatientRequest updatePatient(Long id, PatientRequest patientDTO) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found");
        }
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        patient.setId(id);
        Patient updatedPatient = patientRepository.save(patient);
        return modelMapper.map(updatedPatient, PatientRequest.class);
    }

    @Override
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        patientRepository.deleteAll();
    }
}
