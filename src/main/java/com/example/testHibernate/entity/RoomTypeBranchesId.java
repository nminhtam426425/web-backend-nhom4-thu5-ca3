package com.example.testHibernate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class RoomTypeBranchesId {
    @Column(name = "id_room_type")
    private Integer roomTypeId;

    @Column(name = "id_branches")
    private Integer branchId;
}
