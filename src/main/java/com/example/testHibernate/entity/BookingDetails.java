package com.example.testHibernate.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "booking_details")
@Data
public class BookingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Integer detailId;
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Bookings booking;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Rooms room;
}
