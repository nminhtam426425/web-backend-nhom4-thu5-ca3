package com.example.testHibernate.dto;

import com.example.testHibernate.enums.RoomStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RoomResponse {
    private Integer id;
    private String numberRoom;
    private RoomStatus status;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
}
