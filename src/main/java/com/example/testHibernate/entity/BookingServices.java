package com.example.testHibernate.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "booking_services")
@Data
@IdClass(BookingServiceId.class)
public class BookingServices {
    @Id
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Bookings booking;
    @Id
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Services service;
    @Id
    @Column(name = "date_to_use")
    private LocalDate dateToUse;
    @Column(name = "time_service_start")
    private LocalTime timeServiceStart;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private Double price;
    @Column(name = "service_name")
    private String serviceName;


}