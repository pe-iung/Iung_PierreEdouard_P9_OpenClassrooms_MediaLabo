package org.medilabo.riskassessmentservice.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.Date;

@Data
public class PatientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String gender;
    private String address;
    private String phone;
}
