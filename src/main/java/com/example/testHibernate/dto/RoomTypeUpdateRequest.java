package com.example.testHibernate.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomTypeUpdateRequest {
    private BigDecimal basePrice;
    private Integer capacity;
}
