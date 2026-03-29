package com.ranjit.project.airBnbApp.service;

import com.ranjit.project.airBnbApp.dto.ProfileUpdateRequestDto;
import com.ranjit.project.airBnbApp.dto.UserDto;
import com.ranjit.project.airBnbApp.entity.User;

public interface UserService {
    User getUserById(Long id);

    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto);

    UserDto getMyProfile();
}
