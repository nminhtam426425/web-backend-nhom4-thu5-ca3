package com.example.testHibernate.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "room_types_branches")
@Data
public class RoomTypeBranches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "id_room_type")
    private Integer roomTypeId;
    @Column(name = "id_branches")
    private Integer branchId;
}
