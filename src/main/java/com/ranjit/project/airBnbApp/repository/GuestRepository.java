package com.ranjit.project.airBnbApp.repository;

import com.ranjit.project.airBnbApp.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest,Long> {
}
