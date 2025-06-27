package org.medilabo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.medilabo.exceptions.model.Patient;
import org.medilabo.exceptions.model.SexEnum;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PatientRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private SexEnum gender;
    private String address;
    private String phone;

    public PatientRequest(Patient patient)
    {
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.dateOfBirth = patient.getDateOfBirth();
        this.gender = patient.getGender();
        this.address = patient.getAddress();
        this.phone = patient.getPhone();

    }
}

