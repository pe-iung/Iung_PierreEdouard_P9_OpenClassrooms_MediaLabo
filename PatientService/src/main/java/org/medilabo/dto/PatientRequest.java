package org.medilabo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.medilabo.model.Patient;
import org.medilabo.model.SexEnum;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
public class PatientRequest {
    private Long id;
    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
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

