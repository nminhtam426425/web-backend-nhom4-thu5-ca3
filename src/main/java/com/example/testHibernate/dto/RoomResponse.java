package com.example.testHibernate.dto;

import com.example.testHibernate.enums.RoomStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomResponse {
    private Integer id;
    private String numberRoom;
    private RoomStatus status;
    private String checkIn;
    private String checkOut;
}
