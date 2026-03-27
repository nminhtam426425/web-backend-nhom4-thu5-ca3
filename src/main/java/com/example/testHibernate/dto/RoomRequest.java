package com.example.testHibernate.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoomRequest {
    private String roomNumber;
    private Integer branchId;
    private Integer typeId;
    private String status;
    private List<Integer> amenityIds;
}
