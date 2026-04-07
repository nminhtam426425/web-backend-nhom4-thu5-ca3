package com.example.testHibernate.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingDetailResponse {
    private String name;
    private String phone;
    private String email;
    private Double priceAtBooking;
    private String typeName;
    private String roomNumber;
    private String staffName;

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    private java.sql.Timestamp dateOrder;
    private LocalDateTime dateConfirm;

    private FeedbackResponse feedback;
}
