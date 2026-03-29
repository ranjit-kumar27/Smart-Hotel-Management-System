package com.ranjit.project.airBnbApp.repository;

import com.ranjit.project.airBnbApp.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {
}
