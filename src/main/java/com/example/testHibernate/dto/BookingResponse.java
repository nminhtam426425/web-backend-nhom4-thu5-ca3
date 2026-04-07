package com.example.testHibernate.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponse {
    private Integer bookingId;
    private String bookingCode;
    private UserResponse customer;
    private String staffId;

    private Integer branchId;
    private String branchName;

    private Integer roomTypeId;
    private String roomTypeName;

    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;

    private BigDecimal priceAtBooking;
    private String status;
    private String note;
    private java.sql.Timestamp createdAt;
}
