package com.example.testHibernate.entity;

import com.example.testHibernate.enums.RoomStatus;
import jakarta.persistence.*;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;

@Entity
@Table(name ="rooms")
@Data
public class Rooms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer roomId;
    @Column(name = "room_number")
    private String roomNumber;
    @Column(name = "branch_id")
    private Integer branchId;
    @Column(name = "type_id")
    private Integer typeId;

    @Column(name = "status")
    @Convert(converter = RoomStatusConverter.class)
    private RoomStatus status;
}
