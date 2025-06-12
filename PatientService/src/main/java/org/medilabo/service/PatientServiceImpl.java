package org.medilabo.Service;

import org.medilabo.DTO.PatientRequest;
import org.medilabo.Model.Patient;
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
    public PatientRequest getPatient(String id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return modelMapper.map(patient, PatientRequest.class);
    }

    @Override
    public PatientRequest createPatient(PatientRequest PatientRequest) {
        Patient patient = modelMapper.map(PatientRequest, Patient.class);
        Patient savedPatient = patientRepository.save(patient);
        return modelMapper.map(savedPatient, PatientRequest.class);
    }

    @Override
    public PatientRequest updatePatient(String id, PatientRequest patientDTO) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found");
        }
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        patient.setId(id);
        Patient updatedPatient = patientRepository.save(patient);
        return modelMapper.map(updatedPatient, PatientRequest.class);
    }

    @Override
    public void deletePatient(String id) {
        patientRepository.deleteById(id);
    }
}
