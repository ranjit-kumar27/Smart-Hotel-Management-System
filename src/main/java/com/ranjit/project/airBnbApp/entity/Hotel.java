package com.ranjit.project.airBnbApp.entity;

import com.ranjit.project.airBnbApp.repository.HotelRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String city;

    @Column(columnDefinition = "TEXT[]")
    private String[] photos;

    @Column(columnDefinition ="TEXT[]" ) // wi_fi pool
    private String[] amenities;

    @CreationTimestamp
    private LocalDateTime CreatedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Embedded
    private HotelContactInfo contactInfo;

    @Column(nullable = false)
    private  Boolean active;

    @ManyToOne(optional = false)
    private User owner;

    @OneToMany(mappedBy ="hotel")
    private List<Room> rooms;
}
