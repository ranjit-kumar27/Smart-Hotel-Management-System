package com.ranjit.project.airBnbApp.dto;

import com.ranjit.project.airBnbApp.entity.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileUpdateRequestDto {
    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;

}
