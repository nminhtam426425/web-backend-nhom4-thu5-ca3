package com.example.testHibernate.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "room_types_branches")
@Data
public class RoomTypeBranches {
    @EmbeddedId
    private RoomTypeBranchesId id;
}
