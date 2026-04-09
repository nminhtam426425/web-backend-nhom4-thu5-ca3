package com.example.testHibernate.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
@Data
public class BookingServiceId implements Serializable {
    private Integer booking;
    private String service;
    private LocalDate dateToUse;
}
