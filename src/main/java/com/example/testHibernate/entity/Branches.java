package com.example.testHibernate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "branches")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Branches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private Integer branchId;

    @Column(name = "branch_name",nullable = false,length = 100)
    private  String branchName;

    @Column(nullable = false,columnDefinition = "text")
    private String address;

    @Column(length = 15)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
