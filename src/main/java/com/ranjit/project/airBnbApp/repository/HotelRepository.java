package com.ranjit.project.airBnbApp.repository;

import com.ranjit.project.airBnbApp.entity.Hotel;
import com.ranjit.project.airBnbApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface  HotelRepository extends JpaRepository<Hotel,Long> {
    List<Hotel> findByOwner(User user);
}
