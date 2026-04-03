package com.example.testHibernate.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "room_amenitites")
@Data
public class RoomAmenitites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "id_room")
    private Integer idRoomType;
    @Column(name = "id_amenities")
    private Integer idAmenities;
}
