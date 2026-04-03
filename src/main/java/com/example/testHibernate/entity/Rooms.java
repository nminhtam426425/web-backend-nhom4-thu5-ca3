package com.example.testHibernate.entity;

import com.example.testHibernate.enums.RoomStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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
    @ManyToOne
    @JoinColumn(name = "type_id")
    private RoomTypes roomTypes;
    @Column(name = "status")
    @Convert(converter = RoomStatusConverter.class)
    private RoomStatus status;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branches branch;
    @ManyToMany
    @JoinTable(
            name = "room_amenitites",
            joinColumns = @JoinColumn(name = "id_room"),
            inverseJoinColumns = @JoinColumn(name = "id_amenities")
    )
    private List<Amenities>amenitites;
}
