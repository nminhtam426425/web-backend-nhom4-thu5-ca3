package com.example.testHibernate.dto;

import com.example.testHibernate.enums.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {
    private Integer branchId;
    private Integer totalCheckInToday;
    private Long totalRoomForUser;
    private Long totalRoom;
    private Double totalRevenue;
    private Map<String, Long> rooms;
    private List<UserResponse> newCustomer;
    private List<Double> revenueLast7Day;
    private List<String> dayRevenue;
}
