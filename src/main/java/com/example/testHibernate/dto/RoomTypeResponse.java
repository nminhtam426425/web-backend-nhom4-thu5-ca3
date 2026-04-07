package com.example.testHibernate.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class RoomTypeResponse {
    private Integer typeId;
    private String typeName;
    private Integer descriptionRoom;
    private BigDecimal basePrice;
    private Double priceSundayNormal;
    private Double pricePeakSeason;
    private Double pricePeakSunday;
    private Double priceHour;
    private Integer capacity;

    private List<RoomImageResponse> images;
    private List<Integer> branchIds;
    private List<AmenityResponse> amenities;
    private Long totalRooms;
    private Double revenue;
    private List<RoomResponse> rooms;
}
