package com.example.testHibernate.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class RoomTypeCreateRequest {
    private String typeName;
    private Integer descriptionRoom;
    private BigDecimal basePrice;
    private Double priceSundayNormal;
    private Double pricePeakSeason;
    private Double pricePeakSunday;
    private Double priceHour;
    private Integer capacity;
    private List<String> images;
    private List<Integer> branchIds;
    private List<Integer> amenities;
}
