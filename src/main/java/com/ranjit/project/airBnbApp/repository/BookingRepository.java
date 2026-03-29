package com.ranjit.project.airBnbApp.repository;

import com.ranjit.project.airBnbApp.entity.Booking;
import com.ranjit.project.airBnbApp.entity.Hotel;
import com.ranjit.project.airBnbApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByPaymentSessionId(String sessionId);

    List<Booking> findByHotel(Hotel hotel);

    List<Booking> findByHotelAndCreatedAtBetween(Hotel hotel, LocalDateTime startDatetime, LocalDateTime endDatetime);

    List<Booking> findByUser(User user);
}
