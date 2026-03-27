package com.example.testHibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchResponse {
    private Integer branchId;
    private String branchName;
    private String address;
    private String phone;
    private String email;
    private String description;
    private Boolean isActive;
//    Tổng số phòng
    private Long rooms;
}
