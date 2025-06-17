package org.medilabo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.medilabo.model.Patient;
import org.medilabo.model.SexEnum;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PatientRequest {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private SexEnum gender;
    private String address;
    private String phone;

    public PatientRequest(Patient patient)
    {
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.dateOfBirth = patient.getDateOfBirth();
        this.gender = patient.getGender();
        this.address = patient.getAddress();
        this.phone = patient.getPhone();

    }
}

