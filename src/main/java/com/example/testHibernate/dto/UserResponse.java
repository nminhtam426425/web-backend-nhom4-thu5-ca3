package com.example.testHibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Integer roleId;
    private Boolean isActive;
}
