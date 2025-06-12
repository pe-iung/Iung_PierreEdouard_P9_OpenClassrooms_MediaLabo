package org.medilabo.model;

import jakarta.persistence.*;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;
import java.util.UUID;

@Table
@Entity
@Data
public class Patient {

    @Id
    private Long id;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is mandatory")
    @Enumerated(value = EnumType.STRING)
    private SexEnum gender;

    private String address;
    private String phone;
}
