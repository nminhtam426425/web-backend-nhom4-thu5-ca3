package com.example.testHibernate.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class CustomerBookingResponse {
    private String name;
    private String phone;
    private String email;
    private Double alreadySpent;
    private List<RoomRentResponse> roomRent;
}
