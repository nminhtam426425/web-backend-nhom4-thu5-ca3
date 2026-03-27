package com.example.testHibernate.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoomAmenityRequest {
    private List<Integer> amenityIds;
}
