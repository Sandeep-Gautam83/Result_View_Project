package com.result.view.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.result.view.entity.Mark;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentForm {

    @NotBlank(message = "Name is required !!")
    private String name;

    @NotBlank(message = "Roll Number is required !!")
    private String rollNumber;

    @NotBlank(message = "Invalid Email !!")
    private String email;

    @NotBlank(message = "School is required !!")
    private String schoolName;

    @NotBlank(message = "Address is required !!")
    private String address;

    private String photoName;

    @NotNull(message = "Date of Birth is required !!")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Standard is required !!")
    private String standard;

    @NotBlank(message = "Father's Name is required !!")
    private String fatherName;

    @NotBlank(message = "Mother's Name is required !!")
    private String motherName;

    @NotBlank(message = "Gender is required !!")
    private String gender;


    private List<MarkForm> marks = new ArrayList<>();

}
