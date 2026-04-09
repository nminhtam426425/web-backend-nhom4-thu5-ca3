package com.example.testHibernate.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class BookingServiceResponse {
    private Integer bookingId;
    private String serviceId;
    private LocalDate dateToUse;
    private LocalTime timeServiceStart;
    private String serviceName;
    private Double price;
    private String description;
}
