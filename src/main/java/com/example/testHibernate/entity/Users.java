package com.example.testHibernate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @Column(name = "user_id", columnDefinition = "varchar(36)")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "full_name")
    private String fullName;
    private String email;
    @Column(columnDefinition = "varchar(10)")
    private  String phone;
    private String address;
//    Cái này Bảo thêm để phân quyền
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "created_at",insertable = false,updatable = false)
    private java.sql.Timestamp createdAt;
}
