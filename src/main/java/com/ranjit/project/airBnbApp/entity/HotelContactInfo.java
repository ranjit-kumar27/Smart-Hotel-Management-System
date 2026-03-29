package com.ranjit.project.airBnbApp.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class HotelContactInfo {
    private String address;
    private String phoneNumber;
    private String email;
    private String Location;
}
