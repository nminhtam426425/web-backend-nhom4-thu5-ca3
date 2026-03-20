package com.example.testHibernate.dto;

import lombok.Data;

@Data
public class BranchUpdateRequest {
    private String address;
    private String phone;
    private String email;
    private String description;
}
