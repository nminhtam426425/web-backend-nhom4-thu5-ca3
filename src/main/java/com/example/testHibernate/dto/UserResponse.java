package com.example.testHibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String userId;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Integer roleId;
    private Boolean isActive;
    private Double alreadySpent;
    private Integer branchId;
    private String roomTypeName;   // thêm
    private LocalDateTime checkIn; // thêm
    private String status; // thêm
}
