package com.example.testHibernate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "staff")
public class Staffs {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "branch_id")
    private Integer branchId;
}
