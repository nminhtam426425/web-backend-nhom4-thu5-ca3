package com.example.testHibernate.dto;

import lombok.Data;

@Data
public class BranchRequest {
    private String branchName;
    private String address;
    private String phone;
    private String email;
    private String description;
    private Boolean isActive;
}
