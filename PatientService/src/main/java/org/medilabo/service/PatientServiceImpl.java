package org.medilabo.service;

import org.medilabo.dto.PatientRequest;

import org.medilabo.dto.PatientResponse;
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
    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patient -> modelMapper.map(patient, PatientResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public PatientResponse getPatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
        return modelMapper.map(patient, PatientResponse.class);
    }

    @Override
    public PatientResponse createPatient(PatientRequest PatientRequest) {
        Patient patient = modelMapper.map(PatientRequest, Patient.class);
        Patient savedPatient = patientRepository.save(patient);
        return modelMapper.map(savedPatient, PatientResponse.class);
    }

    @Override
    public PatientResponse updatePatient(Long id, PatientRequest patientDTO) {
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Patient not found with ID: " + id);
        }
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        patient.setId(id);
        Patient updatedPatient = patientRepository.save(patient);
        return modelMapper.map(updatedPatient, PatientResponse.class);
    }

    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Patient not found with ID: " + id);
        }
        else { patientRepository.deleteById(id);}
    }

    @Override
    public void deleteAll() {
        patientRepository.deleteAll();
    }
}
