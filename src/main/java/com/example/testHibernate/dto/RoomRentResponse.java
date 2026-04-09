package com.example.testHibernate.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class RoomRentResponse {
    private Integer bookingId;
    private String roomNumber;
    private String typeName;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Integer rating;
    private String comment;
    private Double alreadySpent;
}
