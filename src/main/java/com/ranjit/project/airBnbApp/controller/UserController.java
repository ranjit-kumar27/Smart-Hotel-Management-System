package com.ranjit.project.airBnbApp.controller;

import com.ranjit.project.airBnbApp.dto.BookingDto;
import com.ranjit.project.airBnbApp.dto.ProfileUpdateRequestDto;
import com.ranjit.project.airBnbApp.dto.UserDto;
import com.ranjit.project.airBnbApp.service.BookingService;
import com.ranjit.project.airBnbApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BookingService bookingService;

    @PatchMapping("/profile")
    public ResponseEntity<Void> updateProfile(@RequestBody ProfileUpdateRequestDto profileUpdateRequestDto) {
        userService.updateProfile(profileUpdateRequestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/myBookings")
    public ResponseEntity<List<BookingDto>> getMyBookings() {
        return ResponseEntity.ok(bookingService.getMyBookings());
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getMYProfile() {
        return ResponseEntity.ok(userService.getMyProfile());
    }

}
