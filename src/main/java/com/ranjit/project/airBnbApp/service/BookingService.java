package com.ranjit.project.airBnbApp.service;

import com.ranjit.project.airBnbApp.dto.BookingDto;
import com.ranjit.project.airBnbApp.dto.BookingRequest;
import com.ranjit.project.airBnbApp.dto.GuestDto;
import com.ranjit.project.airBnbApp.dto.HotelReportDto;
import com.ranjit.project.airBnbApp.entity.enums.BookingStatus;
import com.stripe.model.Event;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BookingService {
    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);

    String initiatePayments(Long bookingId);

    void capturePayment(Event event);

    void cancelBooking(Long bookingId);

    String getBookingStatus(Long bookingId);

    List<BookingDto> getAllBookingsByHotelId(Long hotelId);

    HotelReportDto getHotelReport(Long hotelId,LocalDate startDate, LocalDate endDate);

    List<BookingDto> getMyBookings();
}
