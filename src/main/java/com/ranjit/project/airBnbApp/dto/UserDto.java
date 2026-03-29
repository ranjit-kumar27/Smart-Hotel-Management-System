package com.ranjit.project.airBnbApp.dto;

import com.ranjit.project.airBnbApp.entity.enums.Gender;

import java.time.LocalDate;

public class UserDto {
    private Long id;
    private String email;
    private String name;
    private Gender gender;
    private LocalDate birthDate;
}
