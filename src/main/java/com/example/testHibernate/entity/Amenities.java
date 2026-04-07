package com.example.testHibernate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name ="amenities")
@Data
public class Amenities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_amenitites")
    private Integer idAmenities;
    private String description;
    @ManyToMany(mappedBy = "amenities")
    @JsonIgnore
    private List<RoomTypes> roomTypes;
}
