package com.example.testHibernate.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BranchRoomResponse {
    private Integer branchId;
    private Integer totalRooms;
    private Double totalRevenue;
    private List<RoomTypeResponse> room;
}
