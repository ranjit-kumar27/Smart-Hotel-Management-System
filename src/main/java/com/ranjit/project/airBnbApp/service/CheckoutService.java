package com.ranjit.project.airBnbApp.service;

import com.ranjit.project.airBnbApp.entity.Booking;

public interface CheckoutService {

    String getCheckoutSession(Booking booking, String successUrl, String failureUrl);
}
