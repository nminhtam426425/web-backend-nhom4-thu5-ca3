package com.example.testHibernate.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "room_types")
@Data
public class RoomTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Integer typeId;
    @Column(columnDefinition = "varchar(50)",name = "type_name",nullable = false)
    private String typeName;
    @Column(name = "description_room")
    private Integer descriptionRoom;
    @Column(name = "base_price",precision = 15,scale = 2,nullable = false)
    private BigDecimal basePrice;
    @Column(name = "price_sunday_normal",nullable = false)
    private Double priceSundayNormal;
    @Column(name = "price_peak_season",nullable = false)
    private  Double pricePeakSeason;
    @Column(name = "price_peak_sunday",nullable = false)
    private Double pricePeakSunday;
    @Column(name = "price_hour",nullable = false)
    private Double priceHour;
    @Column(name = "capacity",nullable = false)
    private Integer capacity;
}
