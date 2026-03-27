package com.example.testHibernate.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "room_images")
@Data
public class RoomImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer imageId;

    @Column(name = "image_url")
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomTypes roomType;
}
